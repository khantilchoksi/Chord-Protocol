import java.util.*;

public class ChordNode {
    int m;
    int k;
    int value; // this is n
    ChordNode successor;
    ChordNode predecessor;

    List<FingerEntry> fingerTable;

    // Adding node
    public ChordNode(int m, int value) {
        this.m = m;
        this.value = value;
        fingerTable = new ArrayList<FingerEntry>(m + 1); // 1 to m elements are only useful
        successor = null;
        predecessor = null;
    }

    // Asking this node to find id's successor
    public ChordNode findSuccessor(int id) {
        ChordNode predecessorNode = findPredecessor(id);
        return predecessorNode.successor;
    }

    // Asking this node to find id's predecessor
    public ChordNode findPredecessor(int id) {
        ChordNode tempNode = this;
        // It is a half closed interval.
        // while (id doesnot (n, successor] )
        while (tempNode.value < tempNode.successor.value ? (id <= tempNode.value && id > tempNode.successor.value)
                : !(id <= tempNode.value && id > tempNode.successor.value)) {
            tempNode = tempNode.closestPrecedingFinger(id);
        }
        return tempNode;
    }

    // To find and return closed preceding figner
    // i.e. we have to scan finger table
    public ChordNode closestPrecedingFinger(int id) {
        for (int i = m; i >= 1; i--) {
            ChordNode fingerNode = fingerTable.get(i).node;
            // Do I need to check for interval??
            // if (finger[i]∈(n,id))
            if (n < id ? (fingerNode.value > this.value && tempNode.value < id)
                    : !(fingerNode.value <= this.value && tempNode.value >= id)) {
                return fingerNode;
            }
        }
        return this;
    }

    // // Create new chord ring
    // public void create(int value){
    // this.value = value;
    // }

    // This node joins the ring containing any arbitary node n'
    public void joing(ChordNode arbitaryNode) {
        if (arbitaryNode != null) {
            initFingerTable(arbitaryNode);
            updateOthers();
            // move keys in (predecessor, n] from successor --> ??
        } else {
            // this is the only node in the chord
            for (int i = 1; i <= m; i++) {
                fingerTable.set(i, new FingerEntry(m, n, k, this));
            }
            this.predecessor = this;
        }
    }

    // Initialize the finger table of current local node
    // With arbitary node n' already in the network
    public void initFingerTable(ChordNode arbitaryNode) {
        // First finger table entry
        FingerEntry firstEntry = new FingerEntry(m, this.value, 1);
        firstEntry.node = arbitaryNode.findSuccessor(firstEntry.intervalStart);

        fingerTable.set(1, firstEntry);
        this.successor = firstEntry.no;

        this.predecessor = this.successor.predecessor;
        this.successor.predecessor = this;

        for (int i = 1; i < m; i++) {
            FingerEntry nextFingerEntry = new FingerEntry(m, this.value, i + 1);
            FingerEntry previousFingerEntry = fingerTable.get(i);
            // if (finger[i+1].start belongs to [n, finger[i].node)
            // finger[i+1].node = finger[i].node

            if (this.value < previousFingerEntry.node.value
                    ? (nextFingerEntry.intervalStart >= this.value
                            && nextFingerEntry.intervalStart < previousFingerEntry.node.value)
                    : !(nextFingerEntry.intervalStart < this.value
                            && nextFingerEntry.intervalStart >= previousFingerEntry.node.value)) {
                nextFingerEntry.node = previousFingerEntry.node;
            } else {
                nextFingerEntry.node = arbitaryNode.findSuccessor(nextFingerEntry.intervalStart);
            }

            fingerTable.set(i + 1, nextFingerEntry);

        }
    }

    // Update all nodes whose finger tables should refer to this node (n)
    public void updateOthers() {
        for (int i = 1; i <= m; i++) {
            // Finding last node p whose ith finger might be n
            ChordNode pNode = findPredecessor(this.value - (int) Math.pow(2, i - 1));
            pNode.updateFingerTable(this, i);
        }
    }

    // If s is the ith finger of n, update n's finger table with s
    public void updateFingerTable(ChordNode sNode, int i) {
        // if(s belongs to [n, finger[i].node)
        FingerEntry ithEntry = this.fingerTable.get(i);
        if (this.value < ithEntry.node.value ? (sNode.value >= this.value && sNode.value < ithEntry.node.value)
                : !(sNode.value < this.value && sNode.value >= ithEntry.node.value)) {
            this.fingerTable.get(i).node = sNode;
            // Get first node preceding n
            ChordNode pNode = this.predecessor;
            pNode.updateFingerTable(sNode, i);
        }
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
}