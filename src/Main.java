import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in).useDelimiter("\n");
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
            System.out.println("9. Funny question(choose right definition)");
            System.out.println("10. Funny question(choose right slang word)");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            if(choice == 0){
                break;
            }
            if (choice == 1){
                System.out.print("Enter your slang word you want to find:");
                String key = sc.next();
                if(dict.findBySlangWord(key) == false){
                    System.out.println("Not found");
                }
            }
            if(choice == 2){
                System.out.print("Enter your definition of slang word you want to find: ");
                String str = sc.next();
                if(dict.findByDefinition(str) == false){
                    System.out.println("Not found");
                }
            }
            if(choice == 3){
                System.out.println("The words were searched by user");
                dict.showHistory();
            }
            if (choice == 4){
                System.out.print("Enter your slang word you want to add: ");
                String key = sc.next();
                List<String> values = new ArrayList<>();
                System.out.print("Enter number definition of this slang word: ");
                int nValues = sc.nextInt();
                for(int i = 0; i < nValues; i++){
                    System.out.print("Enter definition: ");
                    String temp = sc.next();
                    values.add(temp);
                }
                dict.addSlangWord(key, values);
            }
            if(choice == 5){
                System.out.print("Enter your slang word you want to update: ");
                String key = sc.next();
                dict.editSlangWord(key);
            }
            if(choice == 6){
                String key = sc.next();
                dict.deleteSlangWord(key);
            }
            if (choice == 7){
                dict.resetListSlangWord();
            }
            if(choice == 8){
                String randomStr;
                randomStr = dict.randomSlangWord();
                System.out.print("Random slang word is: ");
                System.out.println((randomStr));
            }
            if(choice == 9){
                dict.quizBySlangWord();
            }
            if(choice == 10){
                dict.quizByDefinition();
            }

        }
    }
}