package lab.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentsListTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    private StringWriter sw;
    private StudentsList servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet = new StudentsList();
    }

    @Test
    public void testDoGetWithName() throws Exception {
        when(request.getParameter("name")).thenReturn("Иванов Иван Иванович");

        servlet.doGet(request, response);

        String result = sw.toString();
        assertTrue(result.contains("<h1>Информация об ученике: Иванов Иван Иванович</h1>"));

        verify(request).setCharacterEncoding("utf-8");
        verify(request).getParameter("name");
        verify(response).setContentType("text/html;charset=UTF-8");
        verify(response).getWriter();
    }

    @Test
    public void testDoGetWithoutName() throws Exception {
        when(request.getParameter("name")).thenReturn(null);

        servlet.doGet(request, response);

        String result = sw.toString();
        assertTrue(result.contains("<h1>Информация об ученике: Имя не указано</h1>"));
        verify(request, times(1)).getParameter("name");
    }

    @Test
    public void testTableContent() throws Exception {
        when(request.getParameter("name")).thenReturn("Петров");

        servlet.doGet(request, response);

        String result = sw.toString();
        assertTrue(result.contains("<tr><td>Иванов Иван Иванович</td><td>10-А</td><td>Да</td></tr>"));
        assertTrue(result.contains("<tr><td>Петров Петр Петрович</td><td>11-Б</td><td>Нет (болеет)</td></tr>"));
        assertTrue(result.contains("<tr><td>Сидорова Анна Сергеевна</td><td>9-В</td><td>Да</td></tr>"));
        assertEquals("Таблица должна содержать заголовок и 3 ученика", 4, countOccurrences(result, "<tr>"));
    }

    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("name")).thenReturn("Сидорова Анна Сергеевна");

        servlet.doPost(request, response);

        String result = sw.toString();
        assertTrue(result.contains("<h1>Информация об ученике: Сидорова Анна Сергеевна</h1>"));
        assertTrue(result.trim().endsWith("</html>"));
        verify(response).setContentType("text/html;charset=UTF-8");
    }

    private int countOccurrences(String text, String part) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(part, idx)) != -1) {
            count++;
            idx += part.length();
        }
        return count;
    }
}