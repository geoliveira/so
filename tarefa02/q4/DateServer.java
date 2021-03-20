import java.net.*;
import java.util.Scanner;
import java.io.*;

public class DateServer
{
	public static void main(String[] args) throws IOException {
		Socket client = null;
		ServerSocket sock = null;

		Integer fromID = null, toID = null;
		String ack = null, text = null, mailbox[][] = new String[11][11]; // line represents fromID and collumn represents toID

		try { // Time-of-day server listening to port 6013.
			sock = new ServerSocket(6013); 
			// now listen for connections
			while (true) {
				client = sock.accept(); // this function block code until a connection is made it.
				System.out.println("Connection stablished!\nserver = " + sock + "\nclient = " + client + "\n");

				Scanner scanner = new Scanner(client.getInputStream()); // recive data and send result for client
				String bin = scanner.nextLine();
				System.out.println(bin+"\n");
				
				String parts[] = bin.split(":");

				if (parts[0].equals("0")) {
					toID = Integer.parseInt(parts[1]);

					for (int i = 0; i < mailbox.length; i++)
						if (mailbox[i][toID] != null)
							ack = ack.concat("From "+i+": "+mailbox[i][toID]);

				} else if (parts[0].equals("1")) {
					fromID = Integer.parseInt(parts[1]);
					toID = Integer.parseInt(parts[2]);

					mailbox[fromID][toID] = parts[3].concat("\n");
					ack = "Registered email from "+fromID+" to "+toID;
				}
				
				PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
				pout.println(ack); // write the Date to the socket
				
				ack = "";
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
