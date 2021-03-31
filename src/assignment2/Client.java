package assignment2;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {
	
  // Text field for receiving radius
  private JTextField jtf = new JTextField();

  // Text area to display contents
  private JTextArea jta = new JTextArea();
  
  //Exit Button
  JButton exit = new JButton("Exit");

  // username and total logins for user
  public static String username = "";
  public static String total_logins = "";
  
  // IO streams
  public DataInputStream fromServer;
  public DataOutputStream toServer;

  public static void main(String[] args) {
    new Client();
  }

  public Client() {
	  
	  // Panel p to hold the label and text field
	    JPanel p = new JPanel();
	    p.setLayout(new BorderLayout());
	    p.add(new JLabel("Enter radius"), BorderLayout.WEST);
	    p.add(jtf, BorderLayout.CENTER);
	    jtf.setHorizontalAlignment(JTextField.RIGHT);

	    setLayout(new BorderLayout());
	    add(p, BorderLayout.NORTH);
	    add(new JScrollPane(jta), BorderLayout.CENTER);

	    //add listener to text field 
	    jtf.addActionListener(new Listener()); // Register listener

	    //set attributes of client window
	    setTitle("Client Window");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!
	    
	    jta.append("Welcome " + username + '\n' + "You have logged in " + total_logins + " " + "Times" + '\n');

	    //handles exit button
		//when clicked program is closed
	    add(exit,BorderLayout.SOUTH);  
		exit.addActionListener(e -> System.exit(0));
		
   
    try {
    	
		InetAddress ip = InetAddress.getByName("localhost"); 

		// establish the connection with server port 5056 
		Socket socket = new Socket(ip, 8000); 

		// obtaining input and out streams 
		fromServer = new DataInputStream(socket.getInputStream()); 
		toServer = new DataOutputStream(socket.getOutputStream());
		toServer.writeUTF(username);
		
		jta.append("Connection to Server Established" + '\n');

    } catch (IOException ex) {
      jta.append(ex.toString() + '\n');
    }
    
  }

  private class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        // Get the radius from the text field
        double radius = Double.parseDouble(jtf.getText().trim());

        // Send the radius to the server
        toServer.writeDouble(radius);
        toServer.flush();

        // Get area from the server
        double area = fromServer.readDouble();

        // Display to the text area
        jta.append("Radius is " + radius + "\n");
        jta.append("Area received from the server is " + area + '\n');
        
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
      
    }
  }
  
}