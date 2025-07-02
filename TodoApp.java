import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TodoApp extends JFrame {

    private JTextField taskInputField;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JButton addTaskButton;
    private JButton deleteTaskButton;

    public TodoApp() {
        // Set up the main frame
        setTitle("My To-Do List");
        setSize(400, 500); // Increased height for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        // Initialize components
        taskInputField = new JTextField(25); // Wider input field
        addTaskButton = new JButton("Add Task");
        deleteTaskButton = new JButton("Delete Selected Task");

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        // Allow single selection for deletion
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList); // Add scrollability to the list

        // Set up fonts for better readability
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Font taskFont = new Font("Arial", Font.PLAIN, 16);

        // Apply fonts
        taskInputField.setFont(taskFont);
        addTaskButton.setFont(buttonFont);
        deleteTaskButton.setFont(buttonFont);
        taskList.setFont(taskFont);

        // --- Layout Management ---
        // Use BorderLayout for the main frame
        setLayout(new BorderLayout(10, 10)); // Add some padding around components

        // Panel for input and add button (North region)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center align with padding
        inputPanel.add(new JLabel("New Task:"));
        inputPanel.add(taskInputField);
        inputPanel.add(addTaskButton);

        // Panel for delete button (South region)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center align with padding
        buttonPanel.add(deleteTaskButton);

        // Add panels and scroll pane to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // Task list in the center
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Event Handling ---
        // Add Task Button Listener
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskText = taskInputField.getText().trim();
                if (!taskText.isEmpty()) {
                    taskListModel.addElement(taskText);
                    taskInputField.setText(""); // Clear the input field
                } else {
                    JOptionPane.showMessageDialog(TodoApp.this,
                            "Task cannot be empty!",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Delete Task Button Listener
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) { // Check if an item is selected
                    taskListModel.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(TodoApp.this,
                            "Please select a task to delete.",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        // Ensure the GUI is created and updated on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoApp().setVisible(true);
            }
        });
    }
}