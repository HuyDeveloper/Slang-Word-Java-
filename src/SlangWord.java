import java.util.*;
import java.io.*;

public class SlangWord {
    private Map<String, List<String>> dictionaryWords;
    public Scanner sc = new Scanner(System.in);
    public Map<String, List<String>> cloneDict;
    SlangWord() {
        dictionaryWords = new HashMap<>();
    }

    SlangWord(String fileName) throws IOException {
        dictionaryWords = new HashMap<>();
        cloneDict = new HashMap<>();
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        try {
            br1 = new BufferedReader(new FileReader(fileName));
            br2 = new BufferedReader(new FileReader("backup.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Can not open file");
        }
        String line = null;
        String tLine = null;
        while(true){
            line = br1.readLine();
            if(line == null){
                break;
            }
            String[] info = line.split("`", 2);
            String key = info[0];
            List<String> values = new ArrayList<>();
            if(info.length >= 2) {
                String[] tempValue = info[1].split("\\| ");
                for (String temp : tempValue) {
                    values.add(temp);
                }
            }
            dictionaryWords.put(key, values);
        }

        while(true){
            tLine = br2.readLine();
            if(tLine == null){
                break;
            }
            String[] tInfo = tLine.split("`", 2);
            String tKey = tInfo[0];
            List<String> tValues = new ArrayList<>();
            if(tInfo.length >= 2) {
                String[] tempValue1 = tInfo[1].split("\\| ");
                for (String temp : tempValue1) {
                    tValues.add(temp);
                }
            }
            cloneDict.put(tKey, tValues);
        }
        br1.close();
        br2.close();
    }

    public void saveToHistoryFile(String word) {
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(new FileOutputStream("historyFile.txt", true));
        }
        catch(IOException exc)
        {
            System.out.println("Error open file !");
            return;
        }

        try {
            dos.writeUTF(word);
            dos.close();
        }
        catch(IOException exc)
        {
            System.out.println("Error write file.");
        }
    }
    public boolean findBySlangWord(String word){
        saveToHistoryFile(word);
        if(dictionaryWords.containsKey(word)){
            List<String> values = dictionaryWords.get(word);
            System.out.println(values);
            return true;
        }
        return false;
    }

    public boolean findByDefinition(String definition){
        saveToHistoryFile(definition);
        boolean flag = false;
        for(Map.Entry<String, List<String>> entry : dictionaryWords.entrySet()){
            List<String> values = entry.getValue();
            for(String s : values){
                if (s.equals(definition)){
                    System.out.println(entry.getKey());
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public void showHistory() throws IOException {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream("historyFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error to open file");
        }
        try {
            while (true) {
                try {
                    String s = dis.readUTF();
                    System.out.println(s);

                } catch (EOFException e) {
                    break;
                }
            }
            dis.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

    }

    public void save(String filename){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            for (Map.Entry<String, List<String>> entry: dictionaryWords.entrySet()) {
                fw.write(entry.getKey() + "`");
                List<String> values = entry.getValue();
                int i = 0;
                for (String s: values) {
                    if (i != 0) {
                        fw.write('|');
                    }
                    fw.write(s);
                    i++;
                }
                fw.write('\n');
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Can not save to file slang.txt");
        }
    }
    public void addSlangWord(String key, List<String> values){
        if(dictionaryWords.containsKey(key)){
            System.out.println("This key is existed");
            System.out.println("1.Overwrite this slang word");
            System.out.println("2. Duplicate this slang word");
            System.out.println("Please enter your choice:");
            int choice = sc.nextInt();
            if(choice == 1){
                dictionaryWords.replace(key, values);
                System.out.println("Slang word is overwrite");
                save("slang.txt");
            }
            else if (choice == 2){
                dictionaryWords.put(key, values);
                System.out.println("Slang word is duplicated");
            }
        }
        else{
            dictionaryWords.put(key, values);
            save("slang.txt");
            System.out.println("Slang word is added");
        }
    }
    public void editSlangWord(String key){
        int n;
        List<String> values = new ArrayList<>();
        if(dictionaryWords.containsKey(key)) {
            System.out.print("Enter number value you want to change: ");
            n = sc.nextInt();
            for(int i = 0; i < n; i++){
                String str = sc.next();
                values.add(str);
            }
            dictionaryWords.replace(key, values);
            System.out.println("This slang word is edited");
            save("slang.txt");
        }
        else{
            System.out.println("This key is not existed");
        }
    }

    public void deleteSlangWord(String key){
        if (dictionaryWords.containsKey(key)){
            while(true) {
                System.out.println("Do you want to delete this slang word");
                System.out.println("1. YES");
                System.out.println("2. NO");
                System.out.println("Enter your choice");
                int choice = sc.nextInt();
                if (choice == 1) {
                    dictionaryWords.remove(key);
                    System.out.println("This slang word is deleted");
                    save("slang.txt");
                    break;
                } else if (choice == 2) {
                    System.out.println("Nothing happened");
                    break;
                }
                else{
                    System.out.println("Your choice is not existed");
                }
            }
        }
        else{
            System.out.println("This key is not existed");
        }
    }

    public void resetListSlangWord(){
        dictionaryWords.clear();
        dictionaryWords.putAll(cloneDict);
        save("slang.txt");
        System.out.println("This list slang word is reset");
    }

    public String randomSlangWord(){
        double randomDouble = Math.random();
        randomDouble = randomDouble * dictionaryWords.size() + 1;
        int index = (int) randomDouble;
        String[] keys = dictionaryWords.keySet().toArray(new String[0]);
        //System.out.println(keys[index]);
        return keys[index];
    }
    public void quizBySlangWord() {
        String key, word1, word2, word3;
        do {
            key = randomSlangWord();
            word1 = randomSlangWord();
            word2 = randomSlangWord();
            word3 = randomSlangWord();
        } while (key.equals(word1) || key.equals(word2) || key.equals(word3));
        String[] answers = {key, word1, word2, word3};
        Arrays.sort(answers);

        System.out.println("Question: What is mean of " + key + "?");
        int trueAnswer = 0;
        for (int i = 0; i < answers.length; i++) {
            System.out.println((i + 1)+ ". " + dictionaryWords.get(answers[i]));
            if (answers[i].equals(key)) {
                trueAnswer = i + 1;
            }
        }
        System.out.print("Your answer: ");
        int answer;
        Scanner scanner = new Scanner(System.in);
        answer = scanner.nextInt();
        if (answer == trueAnswer) {
            System.out.println("Right!");
        }
        else {
            System.out.println("Wrong!");
            System.out.println("The right answer is " + trueAnswer);
        }
    }
    public void quizByDefinition(){
        String key, word1, word2, word3;
        do {
            key = randomSlangWord();
            word1 = randomSlangWord();
            word2 = randomSlangWord();
            word3 = randomSlangWord();
        } while (key.equals(word1) || key.equals(word2) || key.equals(word3));
        String[] answers = {key, word1, word2, word3};
        Arrays.sort(answers);
        String[] values;
        values = dictionaryWords.get(key).toString().split("\\|");
        dictionaryWords.get(key);
        if(values.length == 1) {
            System.out.println("Question: What is slang word of " + values[0] + " ?");
        }
        if(values.length > 1){
            System.out.println("Question: What is slang word of " + values[0] + "] ?");
        }
        int trueAnswer = 0;
        for (int i = 0; i < answers.length; i++) {
            System.out.println((i + 1)+ ". " + answers[i]);
            if (answers[i].equals(key)) {
                trueAnswer = i + 1;
            }
        }
        System.out.print("Your answer: ");
        int answer;
        Scanner scanner = new Scanner(System.in);
        answer = scanner.nextInt();
        if (answer == trueAnswer) {
            System.out.println("Right!");
        }
        else {
            System.out.println("Wrong!");
            System.out.println("The right answer is " + trueAnswer);
        }
    }

}
