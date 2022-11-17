import java.util.*;
import java.io.*;

public class SlangWord {
    private Map<String, List<String>> dictionaryWords;
    public Scanner sc = new Scanner(System.in);


    SlangWord() {
        dictionaryWords = new HashMap<>();
    }

    SlangWord(String fileName) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Can not open file");
        }
        String line = null;
        while(true){
            line = br.readLine();
            if(line == null){
                break;
            }
            String[] info = line.split("`", 2);
            List<String> values = new ArrayList<>();
            String[] tempValue = info[1].split("|");
            for(int i = 0; i < tempValue.length;i++){
                values.add(tempValue[i]);
            }
            dictionaryWords.put(info[0], values);
        }
        br.close();
    }

    public void saveToHistoryFile(String word) throws  IOException{
        FileWriter fw;
        try {
            fw = new FileWriter("historyFile.txt");
        }
        catch (IOException exc){
            System.out.println("Error opening file");
            return ;
        }
        fw.write(word);
        fw.close();
    }
    public List<String> findBySlangWord(String word){
        //saveToHistoryFile(word);
        List<String> values = dictionaryWords.get(word);
        return values;
    }

    public boolean findByDefinition(String definition){
        //saveToHistoryFile(definition);
        boolean flag = false;
        for(Map.Entry<String, List<String>> entry : dictionaryWords.entrySet()){
            List<String> values = entry.getValue();
            for(String s : values){
                if (s.equals(definition)){
                    System.out.print(entry.getKey());
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public void showHistory() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("historyFile.txt"));
        String str = null;
        while(true){
            str = br.readLine();
            if(str == null){
                break;
            }
            System.out.println(str);
        }
        br.close();
    }

    public void save(String filename) throws IOException {
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
            }
            else if (choice == 2){
                dictionaryWords.put(key, values);
                System.out.println("Slang word is duplicated");
            }
        }
        else{
            dictionaryWords.put(key, values);
            System.out.println("Slang word is added");
        }
    }
}
