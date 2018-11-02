import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        //System.out.println("Hello");
        int m = 3; // 2 ** m = number of nodes

        Scanner inputScanner = null;
        //System.out.println("Arguments Received: "+args.length);
        
        // for(String arg: args){
        //     System.out.println(arg);
        // }

        if (args.length < 1) {
            System.out.println("ERROR: must pass value of m for chord ring.");
            System.exit(0);
        }

        // Command Line Arguments passing
        if (args.length == 3){
            if(!args[0].equals("-i")){
                System.out.println("ERROR: invalid argument type '-i' is expected.");
                System.exit(0);
            }

            String inputFileName = args[1];

            try{
                File inputFile = new File(inputFileName);
                inputScanner = new Scanner(inputFile);
            }catch(Exception ex){
                System.out.println("ERROR: can not read from file : " + args[1]);
                System.exit(0);
            }
            
            try {
                m = Integer.parseInt(args[2]);
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: invalid value of m " + args[2]);
                System.exit(0);
            }

        }else if(args.length == 1){
            //No input file specified
            try {
                m = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: invalid value of m " + args[0]);
                System.exit(0);
            }
            inputScanner = new Scanner(System.in);
        }else{
            System.out.println("ERROR: must pass value of m for chord ring.");
            System.exit(0);
        }

        if (m < 1) {
            System.out.println("ERROR: m must be positive.");
            System.exit(0);
        }

        int maxNodes = (int) Math.pow(2, m);

        if(inputScanner == null){
            System.out.println("ERROR: input can not be read.");
            System.exit(0);
        }

        TreeMap<Integer, ChordNode> treeMap = new TreeMap<>();
        ChordNode chordNode = null;

        while (true) {
            String inputString = "";
            try{
                inputString = inputScanner.nextLine();
            }catch(Exception ex){
                //File output consumed
                // Now take input from console
                inputScanner = new Scanner(System.in);
                inputString = inputScanner.nextLine();
            }

            String[] commands = inputString.split(" ");
            int id = 0;
            if(commands.length == 0){
                System.out.println("ERROR: Invalid command");
            }

            switch (commands[0]) {
            case "end":
                // System.out.println("Bye, Thank you.");
                System.exit(0);

            case "add":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: add expects " + 2 + " parameters not " + commands.length);
                    break;
                }

                try {
                    id = Integer.parseInt(commands[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[1]);
                    break;
                }

                // ERROR: node id must be in [0,<n>)
                if (id < 0 || id >= maxNodes) {
                    System.out.println("ERROR: node id must be in [0," + maxNodes + ").");
                    break;
                }

                if (treeMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " exists.");
                    break;
                }

                chordNode = new ChordNode(m, id);
                treeMap.put(id, chordNode);

                System.out.println("Added node " + id);
                break;

            case "join":
                if (commands.length != 3) {
                    System.out.println("SYNTAX ERROR: join expects " + 3 + " parameters not " + commands.length);
                    break;
                }
                int id2 = 0;
                try {
                    id = Integer.parseInt(commands[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[1]);
                    break;
                }

                try {
                    id2 = Integer.parseInt(commands[2]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[2]);
                    break;
                }

                // ERROR: node id must be in [0,<n>)
                if (id < 0 || id >= maxNodes || id2 < 0 || id2 >= maxNodes) {
                    System.out.println("ERROR: node id must be in [0," + maxNodes + ").");
                    break;
                }

                if (!treeMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exists.");
                    break;
                }
                chordNode = treeMap.get(id);

                if (!treeMap.containsKey(id2)) {
                    System.out.println("ERROR: Node " + id2 + " does not exists.");
                    break;
                }
                ChordNode arbitaryChordNode = treeMap.get(id2);
                chordNode.join(arbitaryChordNode);

                break;

            case "drop":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: drop expects " + 2 + " parameters not " + commands.length);
                    break;
                }

                try {
                    id = Integer.parseInt(commands[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[1]);
                    break;
                }

                // ERROR: node id must be in [0,<n>)
                if (id < 0 || id >= maxNodes) {
                    System.out.println("ERROR: node id must be in [0," + maxNodes + ").");
                    break;
                }

                if (!treeMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exist.");
                    break;
                }

                try{
                    chordNode = treeMap.get(id);
                    chordNode.dropNode();
                    treeMap.remove(id);
                    System.out.println("Dropped node "+id);
                }catch(Exception ex){
                    ex.printStackTrace();
                    System.out.println("ERROR: can not drop node.");
                }

                break;

            case "show":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: show expects" + 2 + " parameters not " + commands.length);
                    break;
                }

                try {
                    id = Integer.parseInt(commands[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[1]);
                    break;
                }

                // ERROR: node id must be in [0,<n>)
                if (id < 0 || id >= maxNodes) {
                    System.out.println("ERROR: node id must be in [0," + maxNodes + ").");
                    break;
                }

                if (!treeMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exist.");
                    break;
                }
                chordNode = treeMap.get(id);
                System.out.println(chordNode.toString());
                break;

            case "stab":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: stab expects" + 2 + " parameters not " + commands.length);
                    break;
                }

                try {
                    id = Integer.parseInt(commands[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[1]);
                    break;
                }

                // ERROR: node id must be in [0,<n>)
                if (id < 0 || id >= maxNodes) {
                    System.out.println("ERROR: node id must be in [0," + maxNodes + ").");
                    break;
                }

                if (!treeMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exist.");
                    break;
                }

                // Stabilize
                Iterator<Map.Entry<Integer, ChordNode>> iterator = treeMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, ChordNode> entry = iterator.next();
                    // int key = entry.getKey();
                    chordNode = entry.getValue();
                    chordNode.stabilize();
                }

                // Fix finger table entries
                Iterator<Map.Entry<Integer, ChordNode>> iteratorFingers = treeMap.entrySet().iterator();
                while (iteratorFingers.hasNext()) {
                    Map.Entry<Integer, ChordNode> entry = iteratorFingers.next();
                    // int key = entry.getKey();
                    chordNode = entry.getValue();
                    chordNode.fixFingers();
                }

                chordNode = treeMap.get(id);
                chordNode.stabilize();

                break;

            case "fix":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: fix expects" + 2 + " parameters not " + commands.length);
                    break;
                }

                try {
                    id = Integer.parseInt(commands[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid integer " + commands[1]);
                    break;
                }

                // ERROR: node id must be in [0,<n>)
                if (id < 0 || id >= maxNodes) {
                    System.out.println("ERROR: node id must be in [0," + maxNodes + ").");
                    break;
                }

                if (!treeMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exists.");
                    break;
                }

                // // Fix all finger table entries of a node
                Iterator<Map.Entry<Integer, ChordNode>> iteratorFixFingers = treeMap.entrySet().iterator();
                while (iteratorFixFingers.hasNext()) {
                    Map.Entry<Integer, ChordNode> entry = iteratorFixFingers.next();
                    // int key = entry.getKey();
                    chordNode = entry.getValue();
                    chordNode.fixFingers();
                }

                chordNode = treeMap.get(id);
                chordNode.fixFingers();

                break;

            case "list":
                if (commands.length != 1) {
                    System.out.println("SYNTAX ERROR: list expects" + 1 + " parameters not " + commands.length);
                    break;
                }

                // List
                Iterator<Map.Entry<Integer, ChordNode>> iteratorList = treeMap.entrySet().iterator();
                System.out.print("Nodes: ");
                while (iteratorList.hasNext()) {
                    Map.Entry<Integer, ChordNode> entry = iteratorList.next();
                    // int key = entry.getKey();
                    chordNode = entry.getValue();
                    // According to old repo, print detialed node
                    // System.out.println(chordNode.toString());

                    //Updated output according to repo
                    System.out.print(entry.getKey());
                    if(iteratorList.hasNext())
                        System.out.print(" , ");
                }
                System.out.println("");
                break;

            default:
                System.out.println("ERROR: Invalid command");

            }
        }
    }

}