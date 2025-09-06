import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int newOrder = Integer.parseInt(request.getParameter("order"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/college_info", "root", "root"
            );

            // Step 1: Get old order of this college
            String oldOrderSql = "SELECT college_order FROM college_list WHERE id=?";
            PreparedStatement psOld = con.prepareStatement(oldOrderSql);
            psOld.setInt(1, id);
            ResultSet rsOld = psOld.executeQuery();
            int oldOrder = -1;
            if (rsOld.next()) {
                oldOrder = rsOld.getInt(1);
            }

            if (oldOrder == -1) {
                out.write("failed");
                return;
            }

           
            if (newOrder < oldOrder) {
                
                String shiftDownSql = "UPDATE college_list SET college_order = college_order + 1 " +
                        "WHERE college_order >= ? AND college_order < ?";
                PreparedStatement psShift = con.prepareStatement(shiftDownSql);
                psShift.setInt(1, newOrder);
                psShift.setInt(2, oldOrder);
                psShift.executeUpdate();
            } else if (newOrder > oldOrder) {
                
                String shiftUpSql = "UPDATE college_list SET college_order = college_order - 1 " +
                        "WHERE college_order <= ? AND college_order > ?";
                PreparedStatement psShift = con.prepareStatement(shiftUpSql);
                psShift.setInt(1, newOrder);
                psShift.setInt(2, oldOrder);
                psShift.executeUpdate();
            }

            
            String updateSql = "UPDATE college_list SET college_order=? WHERE id=?";
            PreparedStatement psUpdate = con.prepareStatement(updateSql);
            psUpdate.setInt(1, newOrder);
            psUpdate.setInt(2, id);

            int result = psUpdate.executeUpdate();

            if (result == 1) {
                out.write("success");
            } else {
                out.write("failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("error");
        }
    }
}
