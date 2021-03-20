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
			System.out.println("User is connected\nPS: Users ID are contained in interval between 0 and 10\nMenu commands: 0 - READ MAILBOX\t1 - WRITE EMAIL\n0 - for read mailbox puts `0:myID`\n1 - write email as follow `1:fromID:toID:text`");
			
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