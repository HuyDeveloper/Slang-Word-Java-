import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        SlangWord dict = new SlangWord("slang.txt");
        while(true){
            System.out.println("0. Quit program");
            System.out.println("1. Find by slang word");
            System.out.println("2. Find slang word by definition");
            System.out.println("3. Show history list");
            System.out.println("4. Add new slang word into list");
            System.out.println("5. Edit slang word");
            System.out.println("6. Delete slang word");
            System.out.println("7. Reset original list");
            System.out.println("8. Random 1 slang word");
            System.out.println("9. Funny question(choose right definition");
            System.out.println("10. Funny question(choose right slang word");
            int choice = sc.nextInt();
            if(choice == 0){
                break;
            }
            if (choice == 1){
                String key = sc.next();
                dict.findBySlangWord(key);
            }
        }
    }
}