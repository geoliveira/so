import java.net.*;
import java.io.*;

class ClientResponse implements Runnable {
	private Socket client;

	public ClientResponse(Socket c) {
		this.client = c;
	}

	public void run() {
		try {
			response();
		} catch (IOException ioe) { }
	}

	public void response() throws IOException {
		try {
			PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
			pout.println(new java.util.Date().toString());
			pout.close();
			client.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (client != null)
				client.close();
		}
	}
}

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket sock = null;

		try {
			sock = new ServerSocket(6013);
			// now listen for connections
			while (true) {
				Thread worker = new Thread(new ClientResponse(sock.accept()));
				worker.start();
				
				try {
					worker.join();
				} catch (InterruptedException ie) { }
			}
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
