package knp.MouShare.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	Socket socket;
	Scanner scanner;
	PrintStream printStream;
	
	public Client(Inet4Address address, int port) throws IOException {
		socket = new Socket(address, port);
		scanner = new Scanner(socket.getInputStream());
		printStream = new PrintStream(socket.getOutputStream());
	}
	
	public PrintStream getPrintStream() {
		return printStream;
	}

	public Scanner getScanner() {
		return scanner;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
