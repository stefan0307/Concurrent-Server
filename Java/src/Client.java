import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 8080);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            Thread.ofVirtual().start(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null)
                        System.out.println(line);
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });

            String line;
            while ((line = userInput.readLine()) != null) {
                out.println(line);
                out.flush();
            }
        }
    }
}