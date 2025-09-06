import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nilal
 */
@WebServlet(urlPatterns = {"/CollegeOrderServlet"})
public class CollegeOrderServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ids = request.getParameter("id");
        String column = request.getParameter("column");
        String order = request.getParameter("order");
        
        PrintWriter out = response.getWriter();
        out.print(ids + " " + column + " " + order);
    }
    
}
