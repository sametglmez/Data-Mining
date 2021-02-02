import java.util.ArrayList;

public class Node {
    Item item;
    int counter;
    ArrayList<Node> next;

    public Node(Item item) {
        this.item = item;
        counter = 0;
        next = new ArrayList<>();
    }
}
