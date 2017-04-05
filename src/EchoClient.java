import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public final class EchoClient {

	public static void main(String[] args) throws Exception {

		try (Socket socket = new Socket("localhost", 22222)) {
			Scanner kb = new Scanner(System.in);
			String input = "";
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			while (true){			
				System.out.print("Client> ");
				input = kb.nextLine();
				pw.println(input);
				pw.flush();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				System.out.println(br.readLine());
				if (input.equals("exit")){
					break;
				}
			}
			kb.close();
		}
	}
}