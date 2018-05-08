import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree.
 * (Haupler, Sen & Tarajan '15)
 *
 */

public class WAVLTree implements Iterable {
    private WAVLNode root;
    private int treeSize;

    //TODO constructor? overloaded constructor?
    /*public WAVLTree(int key, String value) {
        this.root = new WAVLNode(key, value);
    }*/

    public WAVLTree(WAVLNode node) {
        this.root = node;
    }

    /**
     * Returns node if the tree does not have any nodes in it, false otherwise.
     *
     * The function runs in O(1) time as it only checks whether a pointer is null or not.
     *
     * @return          true if tree is empty, otherwise false
     *
     */
  public boolean empty() {
      return (root == null);
  }

    /**
     * Searches the tree for key k and returns its value, or null if k isn't in
     * the tree.
     *
     * Used as a wrapper function that passes k and the tree root to an overloaded
     * search function to search each subtree along the path from the root
     * to the required key.
     *
     * This method runs in O(h) = O(logn) time as it traverses a simple path
     * from the root to the deepest leaf in the worst case.
     *
     * @param k         the key being searched
     * @return          value associated with key k, or null if k is not
     *                  in the tree.
     */
  public String search(int k) {
      return search(k, root);
  }

    /**
     * Search function with an additional node parameter.
     *
     * This method utilizes the BST property of the WAVL tree to move recursively
     * from a root node to one of its subtrees, according to k and the key held at
     * the node.
     *
     * @param k         the key being searched
     * @param node      node at the root of the subtree in which we search for k
     * @return          value associated with key k, or null if k is not
     *                  in the tree.
     */
  private String search(int k, WAVLNode node) {
      if (k == node.key)
          return node.value;
      else if (k > node.key && node.right != null)
          return search(k, node.right);
      else if (k < node.key && node.left != null)
          return search(k, node.left);
      return null;
  }

//TODO Nadine - Insert
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
          return 42;    // to be replaced by student code
   }

//TODO Noa - Delete
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
           return 42;   // to be replaced by student code
   }


    /**
     * Returns the value associated with the minimal key in the tree, or null if the
     * tree is empty.
     *
     * Used as a wrapper function that passes k and the tree root to an overloaded, more
     * general min function, which finds the minimal key of the subtree whose root is the
     * given node.
     *
     * This function runs in O(h) = O(logn) time as it traverses a path from the root
     * to the deepest leaf in the worst case.
     *
     * @return      the value associated with the minimal key in the tree
     */
   public String min() {
       return min(root);
   }

    /**
     * Returns the value associated with the minimal key in the tree, or null if the
     * tree is empty.
     *
     * As a result of the BST property of the WAVL tree, the smallest key in the
     * tree will always be at the leftmost node. The function traverses the leftmost
     * path in the tree until it is exhausted, i.e. when there is no longer any node
     * to the left, then returns the node at the end of it.
     *
     * @param node  the node at the root of the subtree of which the minimal key
     *              is required
     * @return      the value associated with the minimal key in the tree
     */
   private String min(WAVLNode node) {
       if (node == null)
           return null;

       WAVLNode curr = node;
       while (curr.left != null) {
           curr = curr.left;
       }
       return curr.value;
   }

   //TODO Check this out and see if we need an overloaded option max() too
    /**
     * Returns the value associated with the maximal key in the tree, or null if the
     * tree is empty.
     *
     * As a result of the BST property of the WAVL tree, the greatest key in the
     * tree will always be at the rightmost node. The function traverses the rightmost
     * path in the tree until it is exhausted, i.e. when there is no longer any node
     * to the right, then returns the node at the end of it.
     *
     * This function runs in O(h) = O(logn) time as it traverses a path from the root
     * to the deepest leaf in the worst case.
     *
     * @return      the value associated with the maximal key in the tree
     */
   public String max() {
       if (this.empty())
           return null;

       WAVLNode curr = root;
       while (curr.right != null) {
           curr = curr.right;
       }
       return curr.value;
   }

    @Override
    public Iterator iterator() {
        return new WAVLIterator();
    }

    private class WAVLIterator implements Iterator {
        private int counter = 0;
        private WAVLNode curr;

        private WAVLIterator() {
            this.curr = root;
        }

        @Override
        public boolean hasNext() {
            if (counter < treeSize)
                return true;
            return false;
        }

        /**
         * Returns the next node in an in-order traversal of the tree.
         *
         * This function implements the Successor algorithm of the BST.
         *
         * @return the successor of the current node.
         */

        @Override
        public WAVLNode next() {
            if (curr.right != null)
                return
        }
    }

   /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */

//TODO Nadine
   public int[] keysToArray()
   {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
   }

   /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */

// TODO Noa
   public String[] infoToArray()
   {
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
   }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    */

//TODO Nadine
   public int size()
   {
           return 42; // to be replaced by student code
   }
   
     /**
    * public WAVLNode getRoot()
    *
    * Returns the root WAVL node, or null if the tree is empty
    *
    */
   public WAVLNode getRoot()
   {
           return this.root;
   }


    /**
    * public int select(int i)
    *
    * Returns the value of the i'th smallest key (return -1 if tree is empty)
    * Example 1: select(1) returns the value of the node with minimal key 
        * Example 2: select(size()) returns the value of the node with maximal key 
        * Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor  
    *
    */


// TODO Nadine
   public String select(int i)
   {
           return null; 
   }

    /**
   * public class WAVLNode
   */
  public class WAVLNode{
	  
    private int key;
    private String value;
    private WAVLNode left;
    private WAVLNode right;
    private int subtreeSize;
    //private int rank;

    public WAVLNode(int key,String value) {
        this.key = key;
        this.value = value;
        this.right = null;
        this.left = null;
        this.subtreeSize = 0;
    }

    public int getKey() {
        return key;
    }

    public String getValue()
    {
        return this.value;
    }
    public WAVLNode getLeft()
    {
        return left;
    }
    public WAVLNode getRight()
    {
        return right;
    }
    public boolean isInnerNode()
    {
        if (left != null || right != null)
            return true;
        return false;
    }

//TODO Required complexity: O(1), let's think about it later
    public int getSubtreeSize()
    {
        return 42; // to be replaced by student code
    }


  }

}
