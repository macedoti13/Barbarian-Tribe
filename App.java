import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        Path path1 = Paths.get("TestFiles/exemplo.txt");
        
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
    }
}
