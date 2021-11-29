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
            // Cria o objeto arvore
            GeneralTree arvore = new GeneralTree();
            // Variaveis 
            String line;
            String aux[];
            // Adicionamos os primeiros dois elementos
            arvore.LeInicio(reader.readLine(), reader.readLine());
            // Lemos o resto da arvore e adicionamos os guerreiros
            while ((line = reader.readLine()) != null) {
                aux = line.split(" ");
                arvore.add(aux[1], Integer.parseInt(aux[2]), aux[0]);
            }
            // Adicionamos a heranca de terras para cada filho
            arvore.heranca();
            // Geramos o diagrama da arvore
            arvore.geraDOT();

        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
    }
}