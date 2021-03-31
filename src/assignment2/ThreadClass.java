package assignment2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ThreadClass extends Thread {
	
	private Socket socket;
	private InetAddress address;
	private DataInputStream inputFromClient;
	private DataOutputStream outputToClient;
	
	public ThreadClass(Socket socket) {
		this.socket = socket;
		address = socket.getInetAddress();
		try { 
			System.out.print("Server IS/DOS" + '\n');
			inputFromClient = new DataInputStream(socket.getInputStream());
			outputToClient = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.print("exception in class");
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		try {
			
			while(true) {
				
				//radius from user
				double radius = inputFromClient.readDouble();
		        // Compute area
		        double area = radius * radius * Math.PI;
		        // Send area back to the client
		        outputToClient.writeDouble(area);
	
			}
		} catch (Exception e) {
			System.err.println(e + "on" + socket);
		}	
	}
}