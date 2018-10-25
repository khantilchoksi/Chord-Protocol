import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");
        int m = ; // 2 ** m = number of nodes
        int maxNodes = (int) Math.pow(2, m);

        Scanner inputScanner = new Scanner(System.in);
        HashMap<Integer, ChordNode> hashMap = new HashMap<>();
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

                if (hashMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " exists.");
                    break;
                }

                chordNode = new ChordNode(m, id);
                hashMap.put(id, chordNode);

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

                if (!hashMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exists.");
                    break;
                }
                chordNode = hashMap.get(id);

                if (!hashMap.containsKey(id2)) {
                    System.out.println("ERROR: Node " + id2 + " does not exists.");
                    break;
                }
                ChordNode arbitaryChordNode = hashMap.get(id2);
                chordNode.join(arbitaryChordNode);

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

                if (!hashMap.containsKey(id)) {
                    System.out.println("ERROR: Node " + id + " does not exists.");
                    break;
                }
                chordNode = hashMap.get(id);
                System.out.println(chordNode.toString());
                break;

            default:
                System.out.println("ERROR: Invalid command");

            }
        }
    }

}