public class NoaTest {
    public static void main(String[] args) {
        testWAVL();
    }

    public static void testWAVL() {
        WAVLTree t = new WAVLTree();
        t.insert(1, "hi");
        t.insert(2, "bye");
        t.insert(3,"now what");

        System.out.println(WAVLTreePrinter.toString(t));

        t.insert(4, "hi");
        t.insert(5, "bye");
        t.insert(6,"now what");

        System.out.println(WAVLTreePrinter.toString(t));

        t.insert(0, "noa");
        t.insert(-1, "toothbrush");
        t.insert(-2, "david");

        System.out.println(WAVLTreePrinter.toString(t));

        t.insert(10, "noa");
        t.insert(-21, "toothbrush");
        t.insert(7, "david");

        System.out.println(WAVLTreePrinter.toString(t));

        t.insert(-22, "noa");
        t.insert(-23, "toothbrush");
        t.insert(-24, "david");

        System.out.println(WAVLTreePrinter.toString(t));

    }
}
