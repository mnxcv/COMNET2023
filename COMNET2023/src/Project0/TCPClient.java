package Project0;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 50000;

        try (Socket socket = new Socket(serverAddress, serverPort);) {
        	
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        	
            System.out.println("Connected");

            String message;
            while (true) {
                System.out.print("Message: ");
                message = reader.readLine();

                // 서버로 메시지 전송
                out.writeBytes(message + '\n');
                
                if ("disconnect".equals(message)) {
                    break;
                }
                
                System.out.println(in.readLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
