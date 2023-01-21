import sun.misc.Signal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InterruptedException {

        List<String> dictionary = new ArrayList<>();
        List<String> base;
        List<User> userList = new ArrayList<>();

        try {
            System.out.println("Wczytywanie haseł ze słownika...");
            dictionary = Files.readAllLines(Paths.get("../hasla"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Wczytywanie danych z bazy...");
            base = Files.readAllLines(Paths.get("../baza"));
            base.forEach(line -> {
                String[] words = line.split("\\s+");
                User user = new User(words[0], words[1], words[2], words[3]);
                if (words.length > 4) {
                    user.setUsername(words[3] + " " + words[4]);
                }
                userList.add(user);
            });
            System.out.println("IT'S HACKING TIME!!! :DDDD");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert !dictionary.isEmpty();
        assert !userList.isEmpty();

        Decryptor decryptor = new Decryptor(dictionary, userList);

        // Catch SIGHUP signal
        Signal.handle(new Signal("HUP"), signal -> {
            System.out.println(signal.getName() + " (" + signal.getNumber() + ")");
            System.out.println(decryptor.passwordList);
        });

        Thread thread = new Thread(decryptor::originalWord);
        Thread thread1 = new Thread(decryptor::addingNumbers);
        Thread thread2 = new Thread(decryptor::uppercase);
        Thread thread3 = new Thread(decryptor::uppercaseT2);
        Thread thread4 = new Thread(decryptor::upperCaseNumbersBetween);

        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}