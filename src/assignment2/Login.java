package assignment2;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;  
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * References *
 * https://stackoverflow.com/questions/8632705/how-to-close-a-gui-when-i-push-a-jbutton
 * https://www.javaguides.net/2019/07/login-application-using-java-swing-jdbc-mysql-example-tutorial.html
 */

public class Login {

	public static ResultSet rs;
	static Connection con;
	static PreparedStatement st;
	static PreparedStatement login_counter_st;

	//User variables
	static public String  username="", password ="";
	static JFrame frame = new JFrame();
    
	//Field Labels
	static JLabel usernameLabel = new JLabel("Username:");
	static JLabel passwordLabel = new JLabel("Password:");	
	static JLabel loginResponse = new JLabel("");
	static JLabel lblLogin = new JLabel("Login");

	//Input Fields
	static JTextField usernameField = new JTextField(20);
	static JTextField passwordField = new JPasswordField(20);
	
	//Buttons
	static JButton login = new JButton("Login");
	static JButton exit = new JButton("Exit");
	static JButton ok = new JButton("Ok");

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		frame.setLocationRelativeTo(null);  
        frame.setResizable(false);  
        frame.setTitle("Login");  
        frame.getContentPane().add(lblLogin);
        frame.setBounds(100, 100, 300, 300);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.getContentPane().setLayout(null);
          
        //Username Text Field & Label
		usernameLabel.setBounds(80, 40, 70, 15);  
        frame.getContentPane().add(usernameLabel);    
        usernameField.setBounds(80, 55, 140, 30);  
        frame.getContentPane().add(usernameField);  
          
        //Password Text Field & Label
        passwordLabel.setBounds(80, 90, 70, 15);  
        frame.getContentPane().add(passwordLabel);
        passwordField.setBounds(80, 105, 140, 30);  
        frame.getContentPane().add(passwordField);  
          
        //login Response Label
        loginResponse.setBounds(100, 225, 250, 15);  
        frame.getContentPane().add(loginResponse);         
     
        //Login Button
        login.setBounds(70, 150, 160, 25);  
        frame.getContentPane().add(login); 
        
        //Exit Button
        exit.setBounds(110, 189, 80, 25);  
        frame.getContentPane().add(exit);  
        
        lblLogin.setBounds(120, 5, 60, 30);
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
        
		//Handles Save Button
		//login user and display client window
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String userName = usernameField.getText();
                String password = passwordField.getText();
                
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign2", "root", "");
                    st = con.prepareStatement("Select username, password from students where username=? and password=?");

                    st.setString(1, userName);
                    st.setString(2, password);
                    
                    rs = st.executeQuery();
                    
					if (rs.next()) {
						
						// Increase counter by 1 each time the sure logs in 
						login_counter_st.setString(1, userName);
	                    login_counter_st = con.prepareStatement("Update students set TOT_LOGINS + 1 where username=?" );
	                    rs = login_counter_st.executeQuery();


						//set login response label to green and user logged in
				        loginResponse.setForeground(Color.GREEN);
						loginResponse.setText("User Logged In");
						System.out.println("User Logged In");
						
						//show dialog pame
                        JOptionPane.showMessageDialog(ok, "Log in Successfully");
                        
                        //launch client window
                        Client client = new Client();
                        client.setTitle("Welcome " + userName);
                        client.setVisible(true);
                        
                    } else {
                        loginResponse.setForeground(Color.RED);
                    	loginResponse.setText("Incorrect Details");
                    	System.out.println("Incorrect Details");
                        JOptionPane.showMessageDialog(ok, "Wrong Username & Password");
                    }
					
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

			}
		});
		
		//handles reload button
		//when clicked program is closed
		exit.addActionListener(e -> System.exit(0));


	}

}
