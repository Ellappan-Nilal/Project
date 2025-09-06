import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/UpdateCollegeServlet")
public class UpdateCollegeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //List<College> colleges = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/college_info", "root", "root");
           
//            
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            rs = stmt.executeQuery("SELECT order_value FROM orders ORDER BY order_value ASC");
//
//            while (rs.next()) {
//                System.out.println(rs.getInt("order_value"));
//            }
//            while (rs.next()) {
//                College c = new College(
//                        rs.getInt("id"),
//                        rs.getInt("code"),
//                        rs.getString("name"),
//                        rs.getString("district"),
//                        rs.getInt("order")
//                );
//                colleges.add(c);
//            }
//
//            rs.close();
//            stmt.close();
//            con.close();
//            Collections.sort(colleges, Comparator.comparingInt(c -> c.Order));
//
//            request.setAttribute("college_list", colleges);
//            RequestDispatcher rd = request.getRequestDispatcher("College_list.jsp");
//            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// College model
class College {

    int id;
    int code;
    String name;
    String district;
    int Order;

    College(int id, int code, String name, String district, int Order) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.district = district;
        this.Order = Order;
    }
}