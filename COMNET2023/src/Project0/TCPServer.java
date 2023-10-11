package Project0;
import java.io.*;
import java.net.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TCPServer {
	static final int DEFAULT_PORT = 50000;
	
	static final int BUFFER = 4096;
	
	public static void main(String[] args) throws Exception{
		int PORT = DEFAULT_PORT;
		try {
			PORT = Integer.parseInt(args[0]);
		}catch(ArrayIndexOutOfBoundsException oob){
			//Missing argument
			PORT = DEFAULT_PORT;
			System.out.printf("Set port number to default(%d)\n", DEFAULT_PORT);
		}catch(NumberFormatException nfe) {
			//parseInt Error
			System.out.println("Port number is not an integer. Program terminated.");
			System.exit(1);
		}
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.printf("Server socket created with port number %d\n", PORT);

		while(true) {
			Socket connectionSocket = serverSocket.accept();
			InetAddress clientIP = connectionSocket.getInetAddress();
			int clientPort = connectionSocket.getPort();
			System.out.println("Connected with Client");
			System.out.println("Client IP: " + clientIP.getHostAddress());
			System.out.println("Client Port number: " + clientPort);
			
			BufferedReader inFromClient = 
					new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			DataOutputStream outToClient = 
					new DataOutputStream(connectionSocket.getOutputStream());
			while(true) {
			String clientSentence = inFromClient.readLine();
				System.out.println("Recieved: " + clientSentence);
				if(clientSentence.equals("disconnect")) {
					break;
				}
				LocalDateTime currentTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedTime = currentTime.format(formatter);
				outToClient.writeBytes("[" + formattedTime + "] " + clientSentence + '\n');
			}
			connectionSocket.close();
		}
	}
}
