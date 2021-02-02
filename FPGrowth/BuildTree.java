import java.util.ArrayList;

public class BuildTree {
    Node root;

    public BuildTree(Node root) {
        this.root = root;
        //this.root.next = new ArrayList<>();
    }

    public void addListToTree(ArrayList<Item> itemList){
        Node node;
        node = root;
        int flag;

        for(int i = 0 ; i < itemList.size() ; i++ ){
            flag = findElementInNode(itemList.get(i),node);
            if(flag != -1){
                node.next.get(flag).counter++;
                node = node.next.get(flag);
            }else{
                node.next.add(new Node(itemList.get(i)));
                node.next.get(node.next.size()-1).counter++;
                node = node.next.get(node.next.size()-1);
            }
        }
    }

    public void printTree(Node node,String indent){
        if(node.item == null){
            System.out.println(indent + "null");
        }else{
            System.out.println(indent + node.item.value + "(" + node.counter + ")");

        }
        if(node.next.size() != 0){
            Node temp;
            for(int i = 0 ; i < node.next.size(); i++){
                temp = node.next.get(i);
                printTree(temp,indent+"   ");
            }
        }
    }

    public int findElementInNode(Item item,Node node){
        Node temp = node;
        //System.out.println("Size : " + temp.next.size());
        for(int i = 0 ; i < temp.next.size(); i++){
            //System.out.println(temp.item.value + " numaralı Nodeun childleri : " + temp.next.get(i).item.value);
            if(temp.next.get(i).item.value.equals(item.value)){
                return i;
            }
            //temp = temp.next.get(i+1);
        }
        return -1;
    }
}
