import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class FpGrowth {
    HashMap<Integer, ArrayList<Item>> table ;
    ArrayList<Item> itemKey;
    ArrayList<Integer> itemFrequences;
    ArrayList<Item> deleted;
    int minSup;

    public FpGrowth(HashMap<Integer, ArrayList<Item>> table,int minSup) {
        this.table = table;
        itemKey = new ArrayList<>();
        itemFrequences = new ArrayList<>();
        deleted = new ArrayList<>();
        this.minSup = minSup;
    }

    public void findFrequences(){
        int flag = 0;
        for(int i = 0 ; i < table.size(); i++){
            for(int j = 0 ; j < table.get(i).size(); j++){
                for(int k = 0 ; k < itemKey.size(); k++){
                    if(table.get(i).get(j).value.equals(itemKey.get(k).value)){
                        flag = 1;
                        int num = itemFrequences.get(k);
                        num++;
                        itemFrequences.set(k,num);
                        break;
                    }else{
                        flag = 0;
                    }
                }
                if(flag == 0){
                    itemKey.add(table.get(i).get(j));
                    itemFrequences.add(1);
                }
                flag = 0;
            }
        }
        for(int i = 0 ; i < itemFrequences.size(); i++){
            //System.out.println("--> " + itemFrequences.get(i) + "  minSup : " + minSup);
            if(itemFrequences.get(i) < minSup){
                //System.out.println("-------------");
                deleted.add(itemKey.get(i));
                itemKey.remove(i);
                itemFrequences.remove(i);
                i--;
            }
        }
    }
    public void printFrequences(){
       for(int i = 0 ; i < itemKey.size(); i++){
           System.out.println(itemKey.get(i).value + " --> " + itemFrequences.get(i));
       }

    }

    public void sortTable(){
        for (int i = 0 ; i < table.size() ; i++){
            for (int j = 0 ; j < table.get(i).size(); j++){
                if(findElemntinArrayList(table.get(i).get(j),deleted) != -1){
                    table.get(i).remove(j);
                    j--;
                }
            }
            for (int j = 0 ; j < table.get(i).size() - 1; j++){
                for(int k = 0 ; k < table.get(i).size() - j - 1  ; k++){
                    int index = findElemntinArrayList(table.get(i).get(k),itemKey);
                    int counter = itemFrequences.get(index);
                    //System.out.println("----------------------First-------------------");
                    //System.out.println("k : " + k + "Size : " + table.get(i).size() + "index : " + index + " counter : " + counter + " Element : " + table.get(j).get(k).value);


                    int index1 = findElemntinArrayList(table.get(i).get(k+1),itemKey);
                    int counter1 = itemFrequences.get(index1);
                    //System.out.println("--------------------Second---------------------------");
                    //System.out.println("k : " + k + "Size : " + table.get(i).size() + "index : " + index1 + " counter : " + counter1 + " Element : " + table.get(j).get(k).value);

                    if(counter < counter1){

                        Item item = table.get(i).get(k);
                        table.get(i).set(k,table.get(i).get(k+1));
                        table.get(i).set(k+1,item);
                    }
                }
            }
        }

    }
    public int findElemntinArrayList(Item item,ArrayList<Item> arr){
        for(int i = 0 ; i < arr.size(); i++){
            if(arr.get(i).value.equals(item.value))
                return i;
        }
        return -1;
    }

    public void sortFrequences(){
        for(int i = 0 ; i < itemFrequences.size() - 1; i++){
            for(int j = 0 ; j < itemFrequences.size() - i - 1; j++){
                if(itemFrequences.get(j) < itemFrequences.get(j+1)){
                    int temp = itemFrequences.get(j);
                    Item item = itemKey.get(j);
                    itemKey.set(j,itemKey.get(j+1));
                    itemFrequences.set(j,itemFrequences.get(j+1));
                    itemKey.set(j+1,item);
                    itemFrequences.set(j+1,temp);
                }
            }
        }
    }


}
