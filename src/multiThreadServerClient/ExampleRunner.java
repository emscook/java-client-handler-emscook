package multiThreadServerClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ExampleRunner {
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		String myAddy = InetAddress.getLocalHost().getHostAddress();
		String[] messages = new String[] {"haha","hoho","hehe","jaja","jojo","jeje","nana","nono","nene","nootnoot"};
		String[] responses = new String[] {"Message Accepted: haha","Message Accepted: hoho","Message Accepted: hehe","Message Accepted: jaja","Message Accepted: jojo","Message Accepted: jeje","Message Accepted: nana","Message Accepted: nono","Message Accepted: nene","Message Accepted: nootnoot"};
		Thread[] clientThreads  = new Thread[10];
		
		//THESE CLIENTS EXPECT NEWLINE AT THE END OF RECEIVED MESSAGES  
		//THESE CLIENTS END SENT MESSAGE WITH NEWLINE
		MultiThreadClient[] myClients = new MultiThreadClient[10];
		for(int i = 0; i < 10; i++) {
			myClients[i] = new MultiThreadClient(messages[i], myAddy, 10025, i + 1);
			clientThreads[i] = new Thread( myClients[i]);
		}
		//THIS SERVER EXPECTS NEWLINE AT THE END OF RECEIVED MESSAGES
		//THIS SERVER ENDS SENT MESSAGE WITH NEWLINE
		MultiThreadServer myServer = new MultiThreadServer(new String[]{"10025"});
		Thread serverThread = new Thread(myServer);
		serverThread.start();
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++) 
			clientThreads[i].start();
		for(int i = 0; i < 10; i++) {
			clientThreads[i].join();
			if(myClients[i].getResponse().equals(responses[i]))
				System.out.println("Expected response for Client[" + (i + 1) + "] received. Got: \"" + myClients[i].getResponse() + "\".");
			
			else 
				System.out.println("Expected response \"" + responses[i] + "\" for Client[" + (i + 1) + "], but received \"" + myClients[i].getResponse() + "\".");
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Got all responses in " + estimatedTime + " milliseconds.");
	}
	
	
}
