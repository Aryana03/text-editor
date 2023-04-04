import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class LoginPage extends JFrame implements ActionListener, loginOptions{

    // database connection //
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/login";

    static final String USER = "root";
    static final String PASS = "root";
    //
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JTextField username_field = new JTextField();
    JPasswordField password_field = new JPasswordField();
    JLabel username_label = new JLabel("username: ");
    JLabel password_label = new JLabel("password: ");
    JLabel message_label1 = new JLabel("Please login/register to use the ");
    JLabel message_label2 = new JLabel("Text Editor!");
    JLabel message_label3 = new JLabel("");

    private DBConnection dbConnection;


    public LoginPage() {
        this.setDefaultCloseOperation(3);
        this.setSize(new Dimension(420, 420));
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo((Component)null);


        username_label.setBounds(50, 100, 75, 25);
        password_label.setBounds(50, 150, 75, 25);

        message_label1.setPreferredSize(new Dimension( 300, 35));
        message_label1.setFont(new Font("Century", 0, 20));
        message_label2.setBounds(120,50, 300, 35);
        message_label2.setFont(new Font("Century",0, 20));
        message_label3.setBounds(120, 300, 300, 20);
        message_label3.setFont(new Font("Century", 0, 20));


        username_field.setBounds(125, 100, 200, 25);
        password_field.setBounds(125, 150, 200, 25);

        loginButton.setBounds(125, 200, 100, 25);
        loginButton.addActionListener(this);

        registerButton.setBounds(230, 200, 100, 25);
        registerButton.addActionListener(this);

        List<Component> components = Arrays.asList(loginButton, registerButton, username_field,
                                                    password_field, message_label1, message_label2,
                                                    username_label, password_label, message_label3);
        for(Component component : components)
        {
            this.add(component);
        }
        dbConnection = new DBConnection();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            login();
        }
        if(e.getSource() == registerButton) {
            register();
        }
    }

    @Override
    public void login() {
        String username = username_field.getText();
        String password = new String(password_field.getPassword());

        try
        {
            String sql = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet resultSet = dbConnection.executeQuery(sql);

            if(resultSet.next())
            {
                message_label3.setText("Login succesful!");
                message_label3.setForeground(Color.green);
                this.frame.setVisible(false);// close login page
                new textEditor(); // open Editor page
            }
            else
            {
                message_label3.setText("Login not succesful!");
                message_label3.setForeground(Color.red);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void register() {

        String username = username_field.getText();
        String password = new String(password_field.getPassword());

        String sql = "INSERT INTO user (username, password) VALUES ('" + username + "', '" + password + "')";

        dbConnection.executeUpdate(sql);
        message_label3.setText("Registration succesful!");
        message_label3.setForeground(Color.green);

    }
}
