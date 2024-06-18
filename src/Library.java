import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Library {
    private List<Livros> livros;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("books.json");
    private final Lock lock = new ReentrantLock();

    public Library() {
        loadBooks();
    }

    private void loadBooks() {
        try {
            if (file.exists()) {
                livros = objectMapper.readValue(file, new TypeReference<List<Livros>>() {});
            } else {
                livros = List.of();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBooks() {
        lock.lock();
        try {
            objectMapper.writeValue(file, livros);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public List<Livros> listBooks() {
        return livros;
    }

    public synchronized boolean rentBook(String name) {
        for (Livros livros : livros) {
            if (livros.getName().equalsIgnoreCase(name) && livros.getCopies() > 0) {
                livros.setCopies(livros.getCopies() - 1);
                saveBooks();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean returnBook(String name) {
        for (Livros livros : livros) {
            if (livros.getName().equalsIgnoreCase(name)) {
                livros.setCopies(livros.getCopies() + 1);
                saveBooks();
                return true;
            }
        }
        return false;
    }

    public synchronized void addBook(Livros livros) {
        livros.add(livros);
        saveBooks();
    }
}