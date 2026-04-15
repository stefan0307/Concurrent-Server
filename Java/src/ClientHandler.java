import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final int id;

    public ClientHandler(Socket client, int id) {
        this.client = client;
        this.id = id;
    }

    @Override
    public void run() {
        try (client; BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                int other = 1 - id;
                System.out.println("Client " + id + " says: " + line);
                synchronized (Server.writers) {
                    if (Server.writers[other] != null) {
                        Server.writers[other].println("Client " + id + ": " + line);
                        Server.writers[other].flush();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client " + id + " disconnected.");
        }
    }
}