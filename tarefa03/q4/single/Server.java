import java.net.*;
import java.io.*;
import java.util.concurrent.*;

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
		ExecutorService pool = Executors.newSingleThreadExecutor();

		try {
			sock = new ServerSocket(6013);
			while (true) 
				pool.execute(new ClientResponse(sock.accept()));
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
