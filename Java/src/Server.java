import java.io.*;
import java.net.*;

public class Server {
    static PrintWriter[] writers = new PrintWriter[2];
    static int clientCount = 0;

    public static void main(String[] args) throws Exception {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Waiting for 2 clients...");
            while (clientCount < 2) {
                Socket client = server.accept();
                int id = clientCount++;
                writers[id] = new PrintWriter(client.getOutputStream(), true);
                System.out.println("Client " + id + " connected.");
                Thread.ofVirtual().start(new ClientHandler(client, id));
            }
            System.out.println("Both clients connected. Chat started!");
            Thread.currentThread().join();
        }
    }
}