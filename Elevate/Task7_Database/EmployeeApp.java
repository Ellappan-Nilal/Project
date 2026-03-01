import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Employee\n2. View All\n3. Update\n4. Delete\n5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO employee (name, department, salary) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.print("Enter name: ");
            ps.setString(1, sc.next());
            System.out.print("Enter dept: ");
            ps.setString(2, sc.next());
            System.out.print("Enter salary: ");
            ps.setDouble(3, sc.nextDouble());

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void viewEmployees() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM employee";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Dept: %s | Salary: %.2f%n",
                    rs.getInt("id"), rs.getString("name"), rs.getString("department"), rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE employee SET name=?, department=?, salary=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.print("Enter ID: ");
            ps.setInt(4, sc.nextInt());
            System.out.print("New name: ");
            ps.setString(1, sc.next());
            System.out.print("New dept: ");
            ps.setString(2, sc.next());
            System.out.print("New salary: ");
            ps.setDouble(3, sc.nextDouble());

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteEmployee() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM employee WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.print("Enter ID to delete: ");
            ps.setInt(1, sc.nextInt());

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}