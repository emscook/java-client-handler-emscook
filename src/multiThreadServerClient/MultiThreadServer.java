package multiThreadServerClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadServer implements Runnable {
	private String[] myArgs;

	public MultiThreadServer(String[] myArgs) {
		this.myArgs = myArgs;
		// TODO Auto-generated constructor stub
	}

	public MultiThreadServer() {
		this.myArgs = new String[] { "ECHO_PORT" };
		// TODO Auto-generated constructor stub
	}

	private static int ECHO_PORT = 10025;

	// adds back the newlines
	public static String[] getLines(String inputFilename) {
		List<String> lineList = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFilename))) {
			for (String line; (line = br.readLine()) != null;)
				lineList.add(new String(line + "\n"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return lineList.toArray(new String[0]);
	}

	public static void main(String[] args) {
		if (args.length == 1)
			ECHO_PORT = Integer.parseInt(args[0]);
		try {
			System.out.println("Started Server with hostname " + InetAddress.getLocalHost().getHostAddress()
					+ " listening on port " + ECHO_PORT + ".");
		} catch (Exception err) {
		}
		try (ServerSocket server = new ServerSocket(ECHO_PORT)) {
			while (true) {
				try {
					Socket client = server.accept();
					Thread thread = new Thread(new ClientHandler(client));
					thread.start();
				} catch (BindException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		MultiThreadServer.main(this.myArgs);
	}
}
