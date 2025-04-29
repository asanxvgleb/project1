import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final String word = "java";
    private static final char[] maskedWord = new char[word.length()];
    private static int attempts = 6;
    public static final String[] hangmanStages = {
            """
    --------
    |      |
    |      O
    |     \\|/
    |      |
    |     / \\
    |
    """,
            """
    --------
    |      |
    |      O
    |     \\|/
    |      |
    |     /
    |
    """,
            """
    --------
    |      |
    |      O
    |     \\|/
    |      |
    |
    |
    """,
            """
    --------
    |      |
    |      O
    |     \\|
    |      |
    |
    |
    """,
            """
    --------
    |      |
    |      O
    |      |
    |      |
    |
    |
    """,
            """
    --------
    |      |
    |      O
    |
    |
    |
    |
    """,
            """
    --------
    |      |
    |
    |
    |
    |
    |
    """
    };


    public static void main(String[] args) {
        startGameLoop();

    }
    public static String menu(){
        System.out.println("Welcome to the game!");
        System.out.println("[N]ew game/ [E]xit");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().toUpperCase();
    }

    public static void startGameLoop() {
        if(Objects.equals(menu(), "N")) {
            makeMaskedWord();
            while (true) {
                System.out.println("Enter your letter: ");
                startGame();
                if (Arrays.equals(word.toCharArray(), maskedWord)) {
                    System.out.println("You won! Word is " + word);
                    break;
                }
                if (attempts == 0) {
                    System.out.println("You lose");
                    System.out.println("Word is " + word);
                    break;
                }
            }
        }
    }
    public static void makeMaskedWord() {
        Arrays.fill(maskedWord, '*');
        System.out.println(maskedWord);
    }

    public static void startGame() {
        char userChar = readChars();
        showMaskedWord(userChar);
        countUsersAttempts(userChar);
    }

    public static char readChars() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next().toLowerCase().charAt(0);

    }

    public static boolean checkUserInput(char userChar) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == userChar) {
                System.out.println(userChar + " is in word");
                return true;
            }
        }
        return false;
    }

    public static void showMaskedWord(char userChar) {
        if (checkUserInput(userChar)) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == userChar) {
                    maskedWord[i] = userChar;
                }
            }
            System.out.println(maskedWord);
            System.out.println("--------------------");
        }
    }

    public static int countUsersAttempts(char userChar) {
        if (!checkUserInput(userChar)) {
            attempts--;
            System.out.println(hangmanStages[attempts]);
            if(attempts==1) System.out.println("You have " + attempts + " attempt");
            else System.out.println("You have " + attempts + " attempts");
            System.out.println("----------------");
        }
        return attempts;
    }

    public static String getWord() {
        String word;
        try {
            List<String> words = Files.readAllLines(Path.of("src/words.txt"))
                    .stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            Random random = new Random();
            word = words.get(random.nextInt(words.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return word;
    }
}
