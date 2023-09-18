import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class EditorAssist {
    private static String filename;

    public static void main(String[] args) {
        List<String> text = new ArrayList<>();
        Set<String> dictionary = new HashSet<>();
        if (args.length == 1) {
            filename = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            filename = scanner.nextLine();
        }
        try {
            text = readFile("newsletter/" + filename);
            dictionary = getDictionary("dictionary");
        } catch (IOException ioe) {
            System.out.println("File not found!");
        }
        List<String> parsedWords = parseText(text);
        System.out.println("Word count in text is: " + parsedWords.size());
        printRepeatedWords(parsedWords);
        System.out.println("Found these obcene words: " + checkObscene(text, dictionary));
    }

    private static List<String> readFile(String filename) throws IOException {
        Scanner scanner = new Scanner(new FileReader(Paths.get(filename).toFile()));
        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.addAll(Arrays.asList(scanner.nextLine().split("\\s")));
        }
        list.removeAll(Arrays.asList("", null));
        return list;
    }

    private static List<String> parseText(List<String> text) {
        List<String> parsedText = new ArrayList<>();
        text = text.stream()
            .map(str -> str.replaceAll("['’`]s|[^a-zA-Z0-9А-Яа-я\\-]+", ""))
            .map(str -> str.toLowerCase())
            .collect(Collectors.toList());
        boolean flag = false;
        String previousWord = "";
        for (int i = 0; i < text.size(); i++) {
            String word = text.get(i);
            if (word.matches("\\w+-$")) {
                previousWord = word.replaceAll("-$", "");
                flag = true;
                continue;
            }
            if (flag) {
                word = previousWord + word;
                flag = false;
            }
            parsedText.add(word);
        }
    return parsedText;
    }

    private static Set<String> getDictionary(String filename) throws IOException {
        Set<String> dict = new HashSet<>(readFile(filename));
        return dict;
    }

    private static Set<String> checkObscene(List<String> text, Set<String> dictionary) {
        return dictionary.stream()
            .filter(word -> dictionary.contains(word))
            .collect(Collectors.toSet());
    }

    private static void printRepeatedWords(List<String> words) {
        Map<String, Integer> repeated = new HashMap<>();
        words.stream().forEach(word -> {
            if (repeated.containsKey(word)) {
                repeated.put(word, repeated.get(word) + 1);
            } else {
                repeated.put(word, 1);
            }
        });
        System.out.println("Repeated words are: ");
        repeated.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(entry -> {
                if (entry.getValue() > 1) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            });
    }
}