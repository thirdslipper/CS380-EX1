import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {

	public static void main(String[] args) throws Exception {

		try (ServerSocket serverSocket = new ServerSocket(22222)) {
			while (true) {

				try (Socket socket = serverSocket.accept()) {

					String address = socket.getInetAddress().getHostAddress();
					System.out.printf("Client connected: %s%n", address);
					
					
					new multiClient(socket).start();
					//out.println("Hi " + address + ", thanks for connecting!");

				}
			}
		}
	}
	private static class multiClient extends Thread {
		Socket socket;
		public multiClient(Socket socket){
			this.socket = socket;
		}

		public void run(){
			try	{
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader in = new BufferedReader(isr); 
				OutputStream os = socket.getOutputStream();
				PrintStream out = new PrintStream(os, true, "UTF-8");
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
			} catch (Exception e){ }
		}
	}
}