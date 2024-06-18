
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String userInput;
            while (true) {
                System.out.println("Enter command (LIST, RENT <book_name>, RETURN <book_name>, ADD <book_json>, or QUIT):");
                userInput = scanner.nextLine();
                if ("QUIT".equalsIgnoreCase(userInput)) {
                    break;
                }

                out.println(userInput);

                String response = in.readLine();
                System.out.println("Response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}