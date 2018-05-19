import java.util.*;

public class NoaTest {
    public static void main(String[] args) {
        testWAVL();
    }

    public static void testWAVL() {
        WAVLTree t = new WAVLTree();
        t.insert(1,"test1");
        t.insert(1,"test1");
        t.insert(-1,"test-1");
        t.insert(7,"test7");
        if ("test7" != t.max()) {
            return;
        }
        if (t.select(0) != null) {
            return;
        }
        if (!t.select(1).equalsIgnoreCase("test-1")) {
            return;
        }
        if (!t.select(2).equalsIgnoreCase("test1")) {
            return;
        }
        if (!t.select(3).equalsIgnoreCase("test7")) {
            return;
        }
        if (t.select(4) != null) {
            return;
        }
        if (t.infoToArray().length != 3) {
            return;
        }
        if (!t.min().equals("test-1")) {
            return;
        }
        if (!t.max().equals("test7")) {
            return;
        }
        t.delete(1);
        t.delete(-1);
        t.delete(7);
        t.delete(7);
        if (t.min() != null) {
            return;
        }
        if (t.max() != null) {
            return;
        }
        if (t.infoToArray().length != 0) {
            return;
        }
        if (t.iterator().hasNext()) {
            return;
        }

//
//        t.insert(10, "5");
//        t.insert(7, "4");
//        t.insert(16, "7");
//        t.insert(3, "2");
//        t.insert(12, "2");
//        t.insert(9, "2");
//        t.insert(1, "2");
//
//        System.out.println(WAVLTreePrinter.toString(t));
//
//        t.delete(16);
//        System.out.println(WAVLTreePrinter.toString(t));
//
        fuzz(10000, false, false);
    }

    private static void fuzz(int count, boolean print, boolean validateStructure) {
        WAVLTree t = new WAVLTree();
        Random r = new Random();
        Set<Integer> inserted = new HashSet<>();

        for (int i=0; i < count; i++) {
            int randomKey = r.nextInt();
            t.insert(randomKey, String.valueOf(randomKey));

            if (validateStructure && !validateBinaryStructure(t.getRoot())) {
                System.out.println("Tree failed validation during insert");
                System.out.println(WAVLTreePrinter.toString(t));
                return;
            }

            inserted.add(randomKey);
        }

        System.out.println("Tree built: " + t.getRoot().getSubtreeSize());

        if (t.keysToArray().length != inserted.size()) {
            System.out.println("keysToArray.length != inserted.size(), " + t.keysToArray().length + "!=" + inserted.size());
            return;
        }

        ArrayList<Integer> keys = new ArrayList<>();
        for (int k : t.keysToArray()) {
            keys.add(k);
        }

        if (!inserted.containsAll(keys)) {
            System.out.println("keysToArray != inserted");
            return;
        }

        for (Integer k : keys) {
            if (t.search(k) == null) {
                System.out.println("Couldn't search for: " + k);
                return;
            }
        }

        if (t.size() != keys.size()) {
            System.out.println("wrong tree size");
            return;
        }

        Collections.shuffle(keys, r);

        System.out.println("Keys shuffles: " + keys.size());

        for (Integer k : keys) {
            if (print) {
                System.out.println("Deleting key: " + k);
            }

            t.delete(k);

            if (print) {
                System.out.println(WAVLTreePrinter.toString(t));
            }

            if (validateStructure && !validateBinaryStructure(t.getRoot())) {
                System.out.println("Tree failed validation during delete");
                System.out.println(WAVLTreePrinter.toString(t));
                return;
            }

            if (t.search(k) != null) {
                System.out.println("Didn't expect to find key after deletion: " + k);
                break;
            }
        }

        if (!t.empty()) {
            System.out.println("Tree isn't empty:");
            System.out.println(WAVLTreePrinter.toString(t));
        }

        if (t.size() != 0) {
            System.out.println("wrong tree size");
            return;
        }
    }

    static boolean validateBinaryStructure(WAVLTree.WAVLNode n) {
        if (n == null) {
            return true;
        }
        if (n.getRight() != null) {
            for(WAVLTree.WAVLNode d : getDecendents(n.getRight())) {
                if (d.getKey() <= n.getKey()) {
                    System.out.println("Invalid place for key " + d.getKey() + " <= " + n.getKey());
                    return false;
                }
            }
        }
        if (n.getLeft() != null) {
            for(WAVLTree.WAVLNode d : getDecendents(n.getLeft())) {
                if (d.getKey() >= n.getKey()) {
                    System.out.println("Invalid place for key " + d.getKey() + " >= " + n.getKey());
                    return false;
                }
            }
        }

        return (validateBinaryStructure(n.getLeft()) &&
            validateBinaryStructure(n.getRight()));
    }

    static ArrayList<WAVLTree.WAVLNode> getDecendents(WAVLTree.WAVLNode n) {
        ArrayList<WAVLTree.WAVLNode> nodes = new ArrayList<>();
        if (n.getLeft() != null) {
            nodes.add(n.getLeft());
            nodes.addAll(getDecendents(n.getLeft()));
        }
        if (n.getRight() != null) {
            nodes.add(n.getRight());
            nodes.addAll(getDecendents(n.getRight()));
        }
        return nodes;
    }
}
