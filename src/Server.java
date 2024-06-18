import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private static final int PORT = 12345;
    private Library library;

    public Server() {
        library = new Library();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, library).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private Library library;

        public ClientHandler(Socket socket, Library library) {
            this.clientSocket = socket;
            this.library = library;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String request;
                while ((request = in.readLine()) != null) {
                    String[] parts = request.split(" ", 2);
                    String command = parts[0];

                    switch (command) {
                        case "LIST":
                            List<Livros> books = library.listBooks();
                            out.println(new ObjectMapper().writeValueAsString(books));
                            break;
                        case "RENT":
                            if (library.rentBook(parts[1])) {
                                out.println("SUCCESS");
                            } else {
                                out.println("FAIL");
                            }
                            break;
                        case "RETURN":
                            if (library.returnBook(parts[1])) {
                                out.println("SUCCESS");
                            } else {
                                out.println("FAIL");
                            }
                            break;
                        case "ADD":
                            Livros newBook = new ObjectMapper().readValue(parts[1], Livros.class);
                            library.addBook(newBook);
                            out.println("SUCCESS");
                            break;
                        default:
                            out.println("UNKNOWN COMMAND");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}