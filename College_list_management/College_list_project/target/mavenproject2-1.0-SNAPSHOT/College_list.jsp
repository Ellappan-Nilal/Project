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
            @keyframes flashGreen {
                0%   {
                    background-color: #28a745;
                    color: white;
                }
                100% {
                    background-color: transparent;
                    color: inherit;
                }
            }

            .flash-success {
                animation: flashGreen 1s ease;
            }

            @keyframes flashRed {
                0%   {
                    background-color: #dc3545;
                    color: white;
                }
                100% {
                    background-color: transparent;
                    color: inherit;
                }
            }

            .flash-danger {
                animation: flashRed 1s ease;
            }

        </style>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
              crossorigin="anonymous">

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
                    String query = "SELECT * FROM college_list order by college_order ASC";
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
                            url: "NewServlet",
                            type: "POST",
                            data: {id: id, column: column, order: newValue},
                            success: function (response) {
                                
                                let row = td.closest("tr");

                                if (response.trim() === "success") {
                                    td.text(newValue);
                                    row.addClass("flash-success");
                                    setTimeout(() => row.removeClass("flash-success"), 1000);

                                    
                                    fetchColleges();
                                }


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
