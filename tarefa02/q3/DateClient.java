import java.net.*;
import java.util.Scanner;
import java.io.*;

public class DateClient
{
	public static void main(String[] args) throws IOException {
		InputStream in = null;
		BufferedReader bin = null;
		Socket sock = null;

		try {
			sock = new Socket("127.0.0.1", 6013);
			System.out.println("Client has connected with Server\nInput data as follow `operator:operand1:operand2`");
			
			Scanner scanner = new Scanner(System.in); // input data by user
			String expression = scanner.nextLine();
			scanner.close();
			
			PrintWriter pout = new PrintWriter(sock.getOutputStream(), true); // create an Out Stream
			pout.println(expression); // send data to Server throught Out Stream 

			// recive data sended by Server
			in = sock.getInputStream(); // create an Input Stream
			bin = new BufferedReader(new InputStreamReader(in)); // buffer to read data in Input Stream
			 
			String line;
			while( (line = bin.readLine()) != null)
				System.out.println(line);
				 
			pout.close();
			sock.close();
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
		finally {
			if (sock != null)
				sock.close();
		}
	}
}