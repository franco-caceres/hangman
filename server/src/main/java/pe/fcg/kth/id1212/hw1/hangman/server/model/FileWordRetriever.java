package pe.fcg.kth.id1212.hw1.hangman.server.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileWordRetriever implements RandomWordProvider {
    private final List<String> words = new ArrayList<>();

    public FileWordRetriever(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                words.add(line.toLowerCase());
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    @Override
    public String get() {
        int index = new Random().nextInt(words.size());
        return words.get(index);
    }
}
