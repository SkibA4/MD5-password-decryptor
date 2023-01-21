import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Decryptor {

    private final List<String> dictionary;
    private final List<User> userData;
    public List<HackedUser> passwordList = new ArrayList<>();

    public Decryptor(List<String> dictionary, List<User> userData) {
        this.dictionary = dictionary;
        this.userData = userData;
    }

    private String hashPassword(String password) {

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] hashBytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private void comparePassword(String hashedPassword, User user, String word) {
        if (hashedPassword.equals(user.getHashedPassword())) {

            synchronized (this) {
                user.setHacked(true);
                HackedUser hackedUser = new HackedUser(user.getUserEmail(), word, user.getUsername());
                this.passwordList.add(hackedUser);
            }

            System.out.println("-------------------------------");
            System.out.print("  USERNAME: ");
            System.out.println(user.getUsername());
            System.out.print("  E-MAIL: ");
            System.out.println(user.getUserEmail());
            System.out.print("  PASSWORD: ");
            System.out.println(word);
            System.out.println("-------------------------------");
        }
    }

    // Test 1
    public void originalWord() {

        this.dictionary.forEach(word -> {
            String hashedPassword = this.hashPassword(word);
            userData.forEach(user -> {
                if (!user.getHacked())
                    comparePassword(hashedPassword, user, word);
            });
        });
    }

    // Test 2
    public void addingNumbers() {

        this.dictionary.forEach(word -> {
            for (int i = 0; i < 100; i++) {
                String newPasswordAfter = word + i;
                String newPasswordBefore = i + word;

                // Decrypting password with numbers after
                String hashedPasswordAfter = this.hashPassword(newPasswordAfter);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordAfter, user, newPasswordAfter);
                });

                // Decrypting password with numbers before
                String hashedPasswordBefore = this.hashPassword(newPasswordBefore);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordBefore, user, newPasswordBefore);
                });

                // Decrypting password with numbers after and before
                for (int j = 0; j < 100; j++) {
                    String newPasswordBetween = i + word + j;
                    String hashedPasswordBetween = this.hashPassword(newPasswordBetween);
                    userData.forEach(user -> {
                        if (!user.getHacked())
                            comparePassword(hashedPasswordBetween, user, newPasswordBetween);
                    });
                }
            }
        });
    }

    // Test 3
    public void uppercase() {

        this.dictionary.forEach(word -> {
            for (int i = 0; i < 100; i++) {
                String firstUppercase = word.substring(0, 1).toUpperCase() + word.substring(1);
                String lastUppercase = word.substring((word.length() -1), word.length()).toUpperCase();
                String allUppercase = word.toUpperCase();
                String firstUppercaseNumbersAfter = firstUppercase + i;
                String firstUppercaseNumbersBefore = i + firstUppercase;

                userData.forEach(user -> {
                    String hashedPassword = this.hashPassword(firstUppercase);
                    if (!user.getHacked())
                        comparePassword(hashedPassword, user, firstUppercase);
                });

                userData.forEach(user -> {
                    String hashedPassword = this.hashPassword(lastUppercase);
                    if (!user.getHacked())
                        comparePassword(hashedPassword, user, lastUppercase);
                });

                userData.forEach(user -> {
                    String hashedPassword = this.hashPassword(allUppercase);
                    if (!user.getHacked())
                        comparePassword(hashedPassword, user, allUppercase);
                });

                String hashedPasswordAfter = this.hashPassword(firstUppercaseNumbersAfter);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordAfter, user, firstUppercaseNumbersAfter);
                });

                String hashedPasswordBefore = this.hashPassword(firstUppercaseNumbersBefore);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordBefore, user, firstUppercaseNumbersBefore);
                });
            }
        });
    }

    public void uppercaseT2() {

        this.dictionary.forEach(word -> {
            for (int i = 0; i < 100; i++) {
                String lastUppercase = word.substring((word.length() -1), word.length()).toUpperCase();
                String allUppercase = word.toUpperCase();
                String allUppercaseNumbersBefore = i + allUppercase;
                String allUppercaseNumbersAfter = allUppercase + i;
                String lastUppercaseNumbersAfter = lastUppercase + i;
                String lastUppercaseNumbersBefore = i + lastUppercase;

                String hashedPasswordAfter = this.hashPassword(lastUppercaseNumbersAfter);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordAfter, user, lastUppercaseNumbersAfter);
                });

                String hashedPasswordBefore = this.hashPassword(lastUppercaseNumbersBefore);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordBefore, user, lastUppercaseNumbersBefore);
                });

                String hashedPasswordBeforeAll = this.hashPassword(allUppercaseNumbersBefore);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordBeforeAll, user, allUppercaseNumbersBefore);
                });

                String hashedPasswordAfterAll = this.hashPassword(allUppercaseNumbersAfter);
                userData.forEach(user -> {
                    if (!user.getHacked())
                        comparePassword(hashedPasswordAfterAll, user, allUppercaseNumbersAfter);
                });
            }
        });
    }

    public void upperCaseNumbersBetween() {
        this.dictionary.forEach(word -> {
            for (int i = 0; i < 100; i++) {
                String firstUppercase = word.substring(0, 1).toUpperCase() + word.substring(1);
                String lastUppercase = word.substring((word.length() -1), word.length()).toUpperCase();
                String allUppercase = word.toUpperCase();

                // Decrypting password with numbers after and before
                for (int j = 0; j < 100; j++) {
                    String firstUppercaseBetween = i + firstUppercase + j;
                    String lastUppercaseBetween = i + lastUppercase + j;
                    String allUppercaseBetween = i + allUppercase + j;

                    String hashedPasswordFirst = this.hashPassword(firstUppercaseBetween);
                    userData.forEach(user -> {
                        if (!user.getHacked())
                            comparePassword(hashedPasswordFirst, user, firstUppercaseBetween);
                    });

                    String hashedPasswordLast = this.hashPassword(lastUppercaseBetween);
                    userData.forEach(user -> {
                        if (!user.getHacked())
                            comparePassword(hashedPasswordLast, user, lastUppercaseBetween);
                    });

                    String hashedPasswordAll = this.hashPassword(allUppercaseBetween);
                    userData.forEach(user -> {
                        if (!user.getHacked())
                            comparePassword(hashedPasswordAll, user, allUppercaseBetween);
                    });
                }
            }
        });
    }
}
