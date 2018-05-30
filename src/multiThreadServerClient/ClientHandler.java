package multiThreadServerClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.BindException;
import java.net.Socket;
public class ClientHandler implements Runnable{
	private Socket mySocket;
	public ClientHandler(Socket mySocket) {
		this.mySocket = mySocket;
	}
	@Override
	public void run() {
		try (
				OutputStream os = new PrintStream(mySocket.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));)
				{
			String receivedMessage = br.readLine();
			os.write(new String("Message Accepted: " + receivedMessage + "\n").getBytes());
		} catch (BindException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
