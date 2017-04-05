

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {

    public static void main(String[] args) throws Exception {
    	//ServerSocket(int port)
        try (ServerSocket serverSocket = new ServerSocket(22222)) {
            while (true) {
            	
                try (Socket socket = serverSocket.accept()) {
                	InputStream is = socket.getInputStream();
                	InputStreamReader isr = new InputStreamReader(is);
                	BufferedReader in = new BufferedReader(isr); 
                	
                    String address = socket.getInetAddress().getHostAddress();
                    System.out.printf("Client connected: %s%n", address);
                   
                    
                    OutputStream os = socket.getOutputStream();
                    PrintStream out = new PrintStream(os, true, "UTF-8");
                    
                    //out.println("Hi " + address + ", thanks for connecting!");
                    while (true){
                    	String input = in.readLine();
                    	if (!input.toLowerCase().equals("exit")){
                    		out.println("Server> " + input);
                    		out.flush();
                    	}
                    	else{
                            System.out.printf("Client disconnected: %s%n", address);
                            break;
                    	}
                    }
                }
            }
        }
    }
}