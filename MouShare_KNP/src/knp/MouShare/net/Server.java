package knp.MouShare.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	Scanner scanner;
	PrintStream printStream;
	
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();
		scanner = new Scanner(socket.getInputStream());
		printStream = new PrintStream(socket.getOutputStream());
	}
	
	public PrintStream getPrintStream() {
		return printStream;
	}
	
	public Scanner getScanner() {
		return scanner;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
