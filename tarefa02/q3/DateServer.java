import java.net.*;
import java.util.Scanner;
import java.io.*;

public class DateServer
{
	public static void main(String[] args) throws IOException {
		Socket client = null;
		ServerSocket sock = null;

		Integer operand1 = null, operand2 = null, result = null;

		try { // Time-of-day server listening to port 6013.
			sock = new ServerSocket(6013); 
			// now listen for connections
			while (true) {
				client = sock.accept(); // this function block code until a connection is made it.
				System.out.println("Connection stablished!\nserver = " + sock + "\nclient = " + client + "\n");

				Scanner scanner = new Scanner(client.getInputStream()); // recive data and send result for client
				String bin = scanner.nextLine();
				System.out.println(bin);
				
				String parts[] = bin.split(":");
				operand1 = Integer.parseInt(parts[1]);
				operand2 = Integer.parseInt(parts[2]);

				if (parts[0].equals("+"))
					result = operand1 + operand2;
				else if (parts[0].equals("-"))
					result = operand1 - operand2;
				else if (parts[0].equals("/"))
					result = operand1 / operand2;
				else if (parts[0].equals("*"))
					result = operand1 * operand2;
				
				PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
				pout.println(result.toString()); // write the Date to the socket

				pout.close();
				client.close();
			}
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
		finally {
			if (sock != null)
				sock.close();
			if (client != null)
				client.close();
		}
	}
}
