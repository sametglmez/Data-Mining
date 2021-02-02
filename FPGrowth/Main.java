import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        File file=new File("C:\\Users\\samet\\Desktop\\dataset2.txt");    //creates a new file instance
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
        ArrayList<String> dataBaseLine = new ArrayList<>();
        HashMap<Integer,ArrayList<Item> > table = new HashMap<Integer,ArrayList<Item>>();
        String line;
        int counter = 0;
        FpGrowth fpGrowth;

        while((line=br.readLine())!=null) {
            String[] currencies = line.split(" ");
            ArrayList<Item> arr = new ArrayList<>();
            for(int i = 0 ; i < currencies.length ; i++){
                //  System.out.print(currencies[i]);
                arr.add(new Item(currencies[i]));
            }

            table.put(counter,arr);
            counter++;
        }
        for(int i = 0 ; i < table.size(); i++){
            for(int j = 0 ; j < table.get(i).size(); j++){
                System.out.print(table.get(i).get(j).value + " ");
            }
            System.out.println();
        }
        fpGrowth = new FpGrowth(table,3);
        fpGrowth.findFrequences();
        System.out.println("\n\n---------------Frequences-------------------");
        fpGrowth.printFrequences();
        fpGrowth.sortTable();

        System.out.println("\n\n--------------------FP Growth ----------------------");
        for(int i = 0 ; i < table.size(); i++){
            for(int j = 0 ; j < table.get(i).size(); j++){
                System.out.print(table.get(i).get(j).value + " ");
            }
            System.out.println();
        }
        Item deneme = new Item("null");
        System.out.println("---------------------FpGrowth Tree-------------------------");
        Node node = new Node(deneme);
        BuildTree tree = new BuildTree(node);

        for(int i = 0 ; i < table.size(); i++){
            tree.addListToTree(table.get(i));
            //System.out.println(tree.root.item.value);
            //System.out.println("Bir Sıra Bitti !!!");
        }
        tree.printTree(tree.root," ");

    }
}
