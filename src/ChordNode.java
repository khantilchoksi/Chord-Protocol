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
        System.out.println("Node: " + this.value + " Find successor for: " + id);
        // if id belongs to (n, successor]
        if (RangeChecker.checkOpenCloseRange(id, this.value, this.successor.value)) {
            return this.successor;
        } else {
            ChordNode closestChordNode = closestPrecedingNode(id);
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
        System.out.println("Node: " + this.value + " -> Join Method -> Successor set is: " + this.successor.value);
        this.stabilize();
        this.fixFingers();
    }

    // Asking this node to find id's successor
    // Based on original old paper
    // public ChordNode findSuccessor(int id) {
    // System.out.println("Node: " + this.value + " Find successor for: " + id);
    // ChordNode predecessorNode = findPredecessor(id);
    // System.out.println("Node: " + this.value + " Found successor: " +
    // predecessorNode.successor.value);
    // return predecessorNode.successor;
    // }

    // Asking this node to find id's predecessor
    // Based on original old paper
    // public ChordNode findPredecessor(int id) {
    // ChordNode tempNode = this;
    // // It is a half closed interval.

    // // while (id doesnot (n, successor] )
    // // while (tempNode.value < tempNode.successor.value ? (id <= tempNode.value
    // &&
    // // id > tempNode.successor.value)
    // // : !(id <= tempNode.value && id > tempNode.successor.value)) {
    // // tempNode = tempNode.closestPrecedingFinger(id);
    // // }

    // while (!RangeChecker.checkOpenCloseRange(id, tempNode.value,
    // tempNode.successor.value)) {
    // tempNode = tempNode.closestPrecedingFinger(id);
    // }
    // return tempNode;
    // }

    // To find and return closed preceding figner
    // i.e. we have to scan finger table
    // According to old paper
    // public ChordNode closestPrecedingFinger(int id) {
    // for (int i = m; i >= 1; i--) {
    // ChordNode fingerNode = fingerTable.get(i).node;
    // // Do I need to check for interval??
    // // if (finger[i]∈(n,id))
    // // if (this.value < id ? (fingerNode.value > this.value && fingerNode.value <
    // // id)
    // // : !(fingerNode.value <= this.value && fingerNode.value >= id)) {
    // // return fingerNode;
    // // }

    // if (RangeChecker.checkOpenRange(fingerNode.value, this.value, id)) {
    // return fingerNode;
    // }
    // }
    // return this;
    // }

    // // Create new chord ring
    // public void create(int value){
    // this.value = value;
    // }

    // This node joins the ring containing any arbitary node n'
    // According to old paper
    // public void join(ChordNode arbitaryNode) {
    // if (arbitaryNode != null) {
    // initFingerTable(arbitaryNode);
    // updateOthers();
    // // move keys in (predecessor, n] from successor --> ??
    // } else {
    // // this is the only node in the chord
    // for (int k = 1; k <= m; k++) {
    // fingerTable.set(k, new FingerEntry(m, this.value, k, this));
    // }
    // this.predecessor = this;
    // }
    // }

    // Initialize the finger table of current local node
    // With arbitary node n' already in the network
    // According to old paper
    // public void initFingerTable(ChordNode arbitaryNode) {
    // // First finger table entry
    // FingerEntry firstEntry = new FingerEntry(m, this.value, 1);
    // System.out.println("initFingerTable Arbitary Node: " + arbitaryNode.value + "
    // Find successor for: "
    // + firstEntry.intervalStart);

    // firstEntry.node = arbitaryNode.findSuccessor(firstEntry.intervalStart);

    // System.out.println(
    // "initFingerTable Arbitary Node: " + arbitaryNode.value + " Found successor: "
    // + firstEntry.node.value);

    // fingerTable.set(1, firstEntry);

    // // Finger table's first entry is the successor for that node
    // this.successor = firstEntry.node;

    // this.predecessor = this.successor.predecessor;
    // this.successor.predecessor = this;

    // System.out.println("Joined Node: " + this.value + " Successor: " +
    // this.successor.value + " Predecessor: "
    // + this.predecessor.value);

    // System.out.println("Index: " + 1 + " Finger Table Entry: " +
    // fingerTable.get(1).toString());
    // for (int i = 1; i < m; i++) {
    // FingerEntry nextFingerEntry = new FingerEntry(m, this.value, i + 1);
    // FingerEntry previousFingerEntry = fingerTable.get(i);
    // // if (finger[i+1].start belongs to [n, finger[i].node)
    // // finger[i+1].node = finger[i].node
    // /*
    // * if (this.value < previousFingerEntry.node.value ?
    // * (nextFingerEntry.intervalStart >= this.value &&
    // nextFingerEntry.intervalStart
    // * < previousFingerEntry.node.value) : !(nextFingerEntry.intervalStart <
    // * this.value && nextFingerEntry.intervalStart >=
    // * previousFingerEntry.node.value))
    // */
    // if (RangeChecker.checkCloseOpenRange(nextFingerEntry.intervalStart,
    // this.value,
    // previousFingerEntry.node.value)) {
    // nextFingerEntry.node = previousFingerEntry.node;
    // } else {
    // nextFingerEntry.node =
    // arbitaryNode.findSuccessor(nextFingerEntry.intervalStart);
    // }

    // fingerTable.set(i + 1, nextFingerEntry);
    // System.out.println("Index: " + (i + 1) + " Finger Table Entry: " +
    // fingerTable.get(i + 1).toString());
    // }
    // }

    // Update all nodes whose finger tables should refer to this node (n)
    // According to old paper
    // public void updateOthers() {
    // System.out.println("Node: +" + this.value + " says Update Others");
    // for (int i = 1; i <= m; i++) {
    // // Finding last node p whose ith finger might be n
    // ChordNode pNode = findPredecessor(this.value - (int) Math.pow(2, i - 1));
    // System.out.println(
    // "Found Predecessor for " + (this.value - (int) Math.pow(2, i - 1)) + " is: "
    // + pNode.value);
    // pNode.updateFingerTable(this, i);
    // }
    // }

    // If s is the ith finger of n, update n's finger table with s
    // According to old paper
    // public void updateFingerTable(ChordNode sNode, int i) {
    // // if(s belongs to [n, finger[i].node)

    // FingerEntry ithEntry = this.fingerTable.get(i);
    // System.out.println("Called UpdateFingerTable for :" + this.value + " sNode: "
    // + sNode.value + " I: " + i
    // + " Finger Entry: " + ithEntry.toString());
    // /*
    // * if (this.value < ithEntry.node.value ? (sNode.value >= this.value &&
    // * sNode.value < ithEntry.node.value) : !(sNode.value < this.value &&
    // * sNode.value >= ithEntry.node.value)) {
    // */
    // if (RangeChecker.checkCloseOpenRange(sNode.value, this.value,
    // ithEntry.node.value)) {
    // this.fingerTable.get(i).node = sNode;
    // // Get first node preceding n
    // ChordNode pNode = this.predecessor;
    // pNode.updateFingerTable(sNode, i);
    // }
    // }

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
        System.out.println("Node: " + this.value + " -> Stabilize called!");
        ChordNode xNode = this.successor.predecessor;
        // if(x belongs to (n, successor))
        if (xNode != null && RangeChecker.checkOpenRange(xNode.value, this.value, this.successor.value)) {
            System.out.println("Node: " + this.value + " Successor set is: " + xNode.value);
            this.successor = xNode;
        }
        this.successor.notifyStabilize(this);
    }

    // arbitaryNode thinks it might be our predecessor
    public void notifyStabilize(ChordNode arbitaryNode) {
        System.out.println(
                "Node: " + this.value + " -> Notify Stabilize called with arbitary Node: " + arbitaryNode.value);
        // if predecessor is nil or arbitaryNode belongs to (predecessor, n)
        if (this.predecessor == null
                || RangeChecker.checkOpenRange(arbitaryNode.value, this.predecessor.value, this.value)) {

            this.predecessor = arbitaryNode;
            System.out.println("Node: " + this.value + " Predecessor set is: " + arbitaryNode.value);
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

        builder.append(" suc: " + this.successor.value + " , ");

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