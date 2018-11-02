import java.util.*;

public class ChordNode {
    int m;
    int value; // this is n
    ChordNode successor;
    ChordNode predecessor;

    List<FingerEntry> fingerTable;

    // Adding node
    public ChordNode(int m, int value) {
        this.m = m;
        this.value = value;
        this.fingerTable = new ArrayList<FingerEntry>(m + 1); // 1 to m elements are only useful
        add();
    }

    public void add() {
        this.fingerTable.add(null);
        for (int k = 1; k <= m; k++) {
            fingerTable.add(new FingerEntry(m, this.value, k, this));
        }
        this.successor = this;
        this.predecessor = null;
    }

    // Asking this node to find id's successor
    public ChordNode findSuccessor(int id) {
        // System.out.println("Node: " + this.value + " Find successor for: " + id);
        // System.out.println("Node: " + this.value + " this.fingerTable.get(1).node.value: " + this.fingerTable.get(1).node.value);
        // System.out.println("Node: " + this.value + " Check Open Close Range id: "+ id +"  this.successor.value "+this.successor.value);

        // if id belongs to (n, successor]
        
        if (RangeChecker.checkOpenCloseRange(id, this.value, this.successor.value)) {
            // System.out.println("Node: " + this.value + " ::  serach id: "+id + " :: Successor found: "+this.successor);
            return this.successor;
        } else {
            ChordNode closestChordNode = closestPrecedingNode(id);
            // System.out.println("Node: " + this.value + " ::  serach id: "+ id + " :: closestChordNode found: "+closestChordNode.value);
            if(this.value == closestChordNode.value){
                return this;
            }
            return closestChordNode.findSuccessor(id);
        }
    }

    // To find and return closed preceding figner
    // i.e. we have to scan finger table
    public ChordNode closestPrecedingNode(int id) {
        for (int i = m; i >= 1; i--) {
            ChordNode fingerNode = fingerTable.get(i).node;
            // Do I need to check for interval??
            // if (finger[i] belongs to (n,id))

            //What if fingerNode is null??
            if (RangeChecker.checkOpenRange(fingerNode.value, this.value, id)) {
                return fingerNode;
            }
        }
        return this;
    }

    // This node joins the ring containing any arbitary node n'
    public void join(ChordNode arbitaryNode) {
        this.predecessor = null;
        this.successor = arbitaryNode.findSuccessor(this.value);
        // System.out.println("Node: " + this.value + " -> Join Method -> Successor set is: " + this.successor.value);
        //Update its own predecessor and successor
        // this.stabilize();
        //Initizalize and update its own fingers
        // this.fixFingers();
    }

    // Helper function to print Finger Table
    public void printFingerTable() {
        System.out.println("Finger Table for Node: " + this.value);
        for (int i = 1; i <= m; i++) {
            FingerEntry fingerEntry = fingerTable.get(i);
            System.out.println("Start: " + fingerEntry.intervalStart + " Node: " + fingerEntry.node.value);
        }
        System.out.println("\n");
    }
    // public int getStart(int m, int n, int k) {
    // return (n + (int) Math.pow(2, k - 1)) % (int) (Math.pow(2, m));
    // }

    // Verify current node's immediate successor and tell the successor about
    // current node
    public void stabilize() {
        // System.out.println("Node: " + this.value + " -> Stabilize called!");
        ChordNode xNode = this.successor.predecessor;
        // if(x belongs to (n, successor))
        if (xNode != null && RangeChecker.checkOpenRange(xNode.value, this.value, this.successor.value)) {
            // System.out.println("Node: " + this.value + " Successor set is: " + xNode.value);
            this.successor = xNode;
        }
        this.successor.notifyStabilize(this);
    }

    // arbitaryNode thinks it might be our predecessor
    public void notifyStabilize(ChordNode arbitaryNode) {
        // System.out.println(
        //         "Node: " + this.value + " -> Notify Stabilize called with arbitary Node: " + arbitaryNode.value);
        // if predecessor is nil or arbitaryNode belongs to (predecessor, n)
        if (this.predecessor == null
                || RangeChecker.checkOpenRange(arbitaryNode.value, this.predecessor.value, this.value)) {

            this.predecessor = arbitaryNode;
            // System.out.println("Node: " + this.value + " Predecessor set is: " + arbitaryNode.value);
        }
    }

    // Periodically refresh finger table entries
    public void fixFingers() {
        for (int i = 1; i <= m; i++) {
            this.fingerTable.get(i).node = findSuccessor(fingerTable.get(i).intervalStart);
        }
    }

    // Called periodically whether predecessor has failed
    public void checkPredecessor() {
        if (this.predecessor == null) {
            this.predecessor = null;
        }
    }

    // Drop this node
    public void dropNode() {
        // This node notifies its predecessor
        this.predecessor.successor = this.successor;

        // This node notifies its successor
        this.successor.predecessor = this.predecessor;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Node: " + this.value);

        builder.append(" suc: " + this.successor.value + " ,");

        if (this.predecessor != null) {
            builder.append(" pre: " + this.predecessor.value);
        } else {
            builder.append(" pre: None");
        }

        builder.append(", finger: ");
        // Finger table
        for (int i = 1; i < m; i++) {
            builder.append(fingerTable.get(i).toString() + ", ");
        }
        builder.append(fingerTable.get(m).toString());

        return builder.toString();
    }

}