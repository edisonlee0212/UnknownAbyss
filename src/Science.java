import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    Science is a class which will generate and save all the science code, representing science elements.
    The science elements is hidden to the players. The players need to turn science to technology.
    The resource and energy will be consumed to create technology.
 */
class Science {
    private int elementAmount;
    private int totalLevel;
    private int maxBranchAmount;
    private double specialFactor;
    private List<Node> tree;
    //private List<Integer> levelList = new ArrayList<>();
    //private List<Double> energyList = new ArrayList<>();
    //private List<Resource> resourcesList = new ArrayList<>();
    private int nodeAmount = 0;
    private int counter = 0;
    private int amount;
    private int ctr;
    Science(int elementAmount, int totalLevel, int maxBranchAmount){
        this.totalLevel = totalLevel;
        this.elementAmount = elementAmount;
        this.maxBranchAmount = maxBranchAmount;
        if(totalLevel > 1)specialFactor = Math.pow((double)1/maxBranchAmount, (double)1/(totalLevel * (totalLevel - 1)/ 2));
        else specialFactor = 1;
        this.tree = new ArrayList<>();
        for(int i = 0; i < elementAmount; ++i) {
            this.tree.add(new Node(i));
            ++nodeAmount;
            buildScienceTree(totalLevel - 1, tree.get(i));
        }

    }
    private void buildScienceTree(int remainLevel, Node root){
        if(remainLevel > 0){
            int amount = maxBranchAmount - 2 + Client.random.nextInt(3);
            for (int i = 0; i < amount; ++i) {
                Node temp = new Node(Client.random.nextInt(elementAmount));
                if (Client.random.nextDouble() < Math.pow(specialFactor, (totalLevel - remainLevel))) {
                    root.addChild(temp);
                    ++nodeAmount;
                    buildScienceTree(remainLevel - 1, temp);
                }
            }
        }
    }

    double getSpecialFactor() {
        return specialFactor;
    }

    int getNodeAmount() {
        return nodeAmount;
    }

    int countNode() {
        Node temp;
        for(int i = 0; i < elementAmount; ++i){
            this.counter++;
            temp = tree.get(i);
            counter(temp);
        }
        return counter;
    }

    private void counter(Node i){
        if(i.getChildren() != null) {
            for(Node e : i.getChildren()){
                counter++;
                counter(e);
            }
        }
    }

    void printTree(){
        for(Integer i = 0; i < elementAmount; ++i){
            printTreeHelper(i.toString(), tree.get(i));
        }
    }

    void printTree(Integer start, int amount){
        this.ctr = 0;
        this.amount = start + amount;
        for(Integer i = 0; i < elementAmount; ++i){
            printTreeHelper2(i.toString(), tree.get(i), start);
        }
    }

    private void printTreeHelper(String codes, Node root){
        if(root.getChildren().size() != 0){
            for(Node i : root.getChildren()){
                String temp = codes + " > " +i.code;
                printTreeHelper(temp, i);
            }
        }
        else{
            System.out.println(codes);
        }
    }

    private void printTreeHelper2(String codes, Node root, Integer start){
        if(ctr >= amount) return;
        if(root.getChildren().size() != 0){
            for(Node i : root.getChildren()){
                String temp = codes + " > " +i.code;
                printTreeHelper2(temp, i, start);
            }
        }
        else{
            if(ctr >= start)System.out.println(codes);
            ++ctr;
        }
    }

    List<Node> getTree() {
        return tree;
    }


    class Node{
        private int code;
        private List<Node> children;
        Node(int code){
            this.children = new ArrayList<>();
            this.code = code;
        }

        int getCode() {
            return code;
        }

        List<Node> getChildren() {
            return children;
        }

        void addChild(Node child){
            this.children.add(child);
        }

        void addChildByIndex(int code){
            this.children.add(new Node(code));
        }

        Node getChildByIndex(int i){
            return children.get(i);
        }

        Node getChildByCode(int code){
            for(Node i : children){
                if(i.code == code)return i;
            }
            return null;
        }

        void setChildren(List<Node> children) {
            this.children = children;
        }
    }
}
