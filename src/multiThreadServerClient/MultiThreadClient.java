package multiThreadServerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MultiThreadClient implements Runnable {
	private int myPort;
	private int myID;
	private String myMessage;
	private String myHostname;
	private String myResponse;
	public String getResponse() {
		return this.myResponse;
	}
	public int getID() {
		return this.myID;
	}
	public MultiThreadClient(String myMessage, String myHostname, int myPort, int myID) {
		this.myPort = myPort;
		this.myHostname = myHostname;
		this.myMessage = myMessage;
		this.myID = myID;
	}
	@Override
	public void run() {
		try (Socket client = new Socket(this.myHostname, this.myPort);
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintStream os = new PrintStream(client.getOutputStream());) {
			System.out.println("Client [" + this.myID + "] connected. Waiting to send \"" + this.myMessage +  "\"...");
			Thread.sleep(3000);
			os.write(new String(this.myMessage + "\n").getBytes());
			String fromServer = br.readLine();
			this.myResponse = fromServer;
			System.out.println("Client[" + this.myID + "] received a response: \"" + fromServer + "\".");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// TODO Auto-generated method stub
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
