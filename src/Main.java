import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");
        int m = 3; // 2 ** m = number of nodes

        if (args.length < 1) {
            System.out.println("ERROR: must pass value of m for chord ring.");
            System.exit(0);
        }

        try {
            m = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR: invalid value of m " + args[0]);
            System.exit(0);
        }

        if (m < 1) {
            System.out.println("ERROR: m must be positive.");
            System.exit(0);
        }

        int maxNodes = (int) Math.pow(2, m);

        Scanner inputScanner = new Scanner(System.in);
        TreeMap<Integer, ChordNode> treeMap = new TreeMap<>();
        ChordNode chordNode = null;

        commandsloop: while (true) {
            String inputString = inputScanner.nextLine();

            String[] commands = inputString.split(" ");
            int id = 0;
            switch (commands[0]) {
            case "end":
                System.out.println("Bye, Thank you.");
                break commandsloop;

            case "add":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: add expects" + 2 + " parameters not " + commands.length);
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
                    System.out.println("SYNTAX ERROR: join expects" + 3 + " parameters not " + commands.length);
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

                break;

            case "drop":
                if (commands.length != 2) {
                    System.out.println("SYNTAX ERROR: drop expects" + 2 + " parameters not " + commands.length);
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
                while (iteratorList.hasNext()) {
                    Map.Entry<Integer, ChordNode> entry = iteratorList.next();
                    // int key = entry.getKey();
                    chordNode = entry.getValue();
                    System.out.println(chordNode.toString());
                }

                break;

            default:
                System.out.println("ERROR: Invalid command");

            }
        }
    }

}