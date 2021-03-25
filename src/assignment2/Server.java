package assignment2;

import java.awt.BorderLayout;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.net.*;
import java.util.Date; 


public class Server extends JFrame {
	
	  // Text area for displaying contents
	JTextArea jta = new JTextArea();
	JButton exit = new JButton("Exit");
	String username = Login.username;
	

	
	
	public static void main(String[] args) { 
		 new Server();
	};
			
	public Server() {
	
	 // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);

    setTitle("Server");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
    //Exit Button
    add(exit,BorderLayout.SOUTH);  
    
	//handles reload button
	//when clicked program is closed
	exit.addActionListener(e -> System.exit(0));
	
    
    try {
    	
    	//Create Server Socket 
    	ServerSocket serverSocket = new ServerSocket(8000);
    	jta.append("Server Started at " + new Date() + '\n');
    
		while (true) { 
			
			//Listen for connection request
			Socket socket = serverSocket.accept(); 
			jta.append("A new client " + username + "is connected : " + serverSocket + '\n'); 

			//Connect to a client thread
			Thread t = new ThreadClass(socket); 
			System.out.println("Assigning new thread for this client" + '\n'); 
			t.start();
		
//		    jta.append("Radius received from client: " + radius + '\n');
//		    jta.append("Area found: " + area + '\n');
				
		}
	} catch (IOException e) { 
			System.err.println(e);
		} 
	}
    
} 
