/**
 * Author: Colin Koo
 * Professor: Davarpanah
 * Assignment: Multithread echo-server and echo-client communication.
 */
import java.lang.Runnable;
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
					new multiClient(socket).start();
					multiClient.sleep(10000);
				}
			}
		}
	}
	public static class multiClient extends Thread implements Runnable {
		Socket socket;
		public multiClient(Socket socket){
			this.socket = socket;
		}
		public void run(){
			try{
				String address = socket.getInetAddress().getHostAddress();
				System.out.printf("Client connected: %s%n", address);
				
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr); 

				OutputStream os = socket.getOutputStream();
				PrintStream out = new PrintStream(os, true, "UTF-8");

				while (true){
					String input = br.readLine();
					if (!input.toLowerCase().equals("exit")){
						out.println("Server> " + input);
						out.flush();
					}
					else{
						System.out.printf("Client disconnected: %s%n", address);
						break;
					}
				}
			} catch (Exception e) {}
		} 
	}
}
