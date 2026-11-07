package lab.web;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentsList
 */
public class StudentsList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public StudentsList() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<html>");
            out.println("<head><title>Учет присутствия учеников</title></head>");
            out.println("<body>");
            out.println("<h1>Информация об ученике: " + (name != null ? name : "Имя не указано") + "</h1>");
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr>"
                    + "<td><b>ФИО Ученика</b></td>"
                    + "<td><b>Класс</b></td>"
                    + "<td><b>Присутствует в школе</b></td>"
                    + "</tr>");
            out.println("<tr><td>Иванов Иван Иванович</td><td>10-А</td><td>Да</td></tr>");
            out.println("<tr><td>Петров Петр Петрович</td><td>11-Б</td><td>Нет (болеет)</td></tr>");
            out.println("<tr><td>Сидорова Анна Сергеевна</td><td>9-В</td><td>Да</td></tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
