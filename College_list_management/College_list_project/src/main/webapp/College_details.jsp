<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>College List</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            td {
                cursor: pointer;
            }
            input.editable {
                width: 100%;
            }
        </style>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <h2>College List</h2>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Order</th>
                    <th>Code</th>
                    <th>Name</th>
                    <th>District</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/college_info", "root", "root");
                    String query = "SELECT * FROM college_list";
                    Statement sm = con.createStatement();
                    ResultSet rs = sm.executeQuery(query);
                    while (rs.next()) {
                %>
                <tr data-id="<%=rs.getInt("id")%>">
                    <td class="editable" data-column="code"><%=rs.getInt("college_order")%></td>
                    <td><%=rs.getString("college_code")%></td>
                    <td><%=rs.getString("college_name")%></td>
                    <td><%=rs.getString("district")%></td>
                </tr>
                <% }%>
            </tbody>
        </table>

        <script>
            $(document).on("click", "td.editable", function () {
                let td = $(this);
                let oldValue = td.text();
                let input = $("<input type='text' class='editable'>").val(oldValue);
                td.html(input);
                input.focus();

                input.blur(function () {
                    let newValue = $(this).val();
                    let row = td.closest("tr");
                    let id = row.data("id");
                    let column = td.data("column");

                    if (newValue !== oldValue) {
                        $.ajax({
                            url: "UpdateCollegeServlet",
                            type: "POST",
                            data: {id: id, column: column, value: newValue},
                            success: function () {
                                td.html(newValue);
                            },
                            error: function () {
                                alert(newValue);
                                td.html(oldValue);
                            }
                        });
                    } else {
                        td.html(oldValue);
                    }
                });
            });
        </script>
    </body>
</html>


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nilal
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int order = Integer.parseInt(request.getParameter("order"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/college_info", "root", "root"
            );
            String checkSql = "SELECT COUNT(*) FROM college_list WHERE college_order=? AND id<>?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, order);
            checkPs.setInt(2, id);

            ResultSet rs = checkPs.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                response.setContentType("text/plain");
                response.getWriter().write("duplicate");
            } else {
                String sql = "UPDATE college_list SET college_order=? WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, order);
                ps.setInt(2, id);

                int result = ps.executeUpdate();

                response.setContentType("text/plain");
                if (result == 1) {
                    response.getWriter().write("success");
                } else {
                    response.getWriter().write("failed");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("error");
        }

    }

}

