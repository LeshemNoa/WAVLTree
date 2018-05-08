import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
     * Used as a wrapper function that passes k and the tree root to another, more
     * general function, which returns the node with the minimal key of the subtree
     * whose root is the node passed to the function.
     *
     * This function runs in O(h) = O(logn) time as it traverses a path from the root
     * to the deepest leaf in the worst case.
     *
     * @return      the value associated with the minimal key in the tree
     */
   public String min() {
       return nodeWithMinKey(root).value;
   }

    /**
     * Returns the node with the minimal key in the tree, or null if the tree is empty.
     *
     * As a result of the BST property of the WAVL tree, the node with the smallest
     * key in the tree will always be at the leftmost node. The function traverses the
     * leftmost path in the tree until it is exhausted, i.e. when there is no longer
     * any node to the left, then returns the node at the end of it.
     *
     * @param node  the node at the root of the subtree of which the node with minimal key
     *              is required
     * @return      the node with the smallest key in the tree
     */
   private WAVLNode nodeWithMinKey(WAVLNode node) {
       if (node == null)
           return null;

       WAVLNode curr = node;
       while (curr.left != null) {
           curr = curr.left;
       }
       return curr;
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

    /**
     * The WAVL tree iterator is a finite iterator which will return, upon each call to next(),
     * the next node to appear in an in-order traversal of the tree.
     *
     * The iterator maintains two variables: a node curr, the node most recently returned by next(),
     * and a counter, which counts the number of calls to next() and allows to determine whether
     * the traversal is complete, and consequently if hasNext() is true or false.
     *
     */

    private class WAVLIterator implements Iterator {
        private int counter = 0;
        private WAVLNode curr;

        private WAVLIterator() {
            this.curr = root;
        }

        /**
         * Checks if there are any remaining nodes unvisited by the iterator.
         *
         * @return      true if iterator is not exhausted, otherwise false
         */
        @Override
        public boolean hasNext() {
            return (counter < treeSize);
        }

        /**
         * Returns the next node in an in-order traversal of the tree.
         *
         * This function implements the Successor algorithm of the BST.
         *
         * The function runs in O(h) = O(logn) time, as it traverses a path from/to the
         * root to/from the deepest leaf in the worst case.
         *
         * @return      the successor of the current node
         */

        @Override
        public WAVLNode next() {

            /* Case 1: The current node's successor is in its subtree. In that case, it's
             * going to be at the node with the smallest key in curr's right subtree. */

            if (curr.right != null) {
                curr = nodeWithMinKey(curr.right);
            }

            /* Case 2: The current node's successor is not in its subtree. In that case,
            * it's going to be the lowest ancestor of curr, whose left child is also an
            * ancestor of curr. */

            else {
                WAVLNode parent = curr.parent;
                while (parent != null && curr == parent.right) {
                    curr = parent;
                    parent = parent.parent;
                }
                curr = parent;
            }

            counter++;
            return curr;
        }
    }

    /**
     * Returns a sorted array of the tree's keys.
     *
     * @return      array of the tree's keys in ascending order
     */
   public int[] keysToArray()
   {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
   }

    /**
     * Returns a string array containing the values of the tree's nodes, sorted in ascending
     * order of their keys, or an empty array if the tree is empty.
     *
     * The i-th cell in the array will contain the value associated with the i-th key.
     *
     * The function runs in O(nlogn) in time as in the worst case, as it performs n cosecutive
     * Successor calls with worst case runtime complexity of O(logn). However, in HW2 we proved
     * that a tighter complexity bound would be O(n+h) = O(n+logn) = O(n).
     *
     * @return      array of values associated with the keys in ascending order
     */

   public String[] infoToArray() {
       List<String> sortedVals = new ArrayList<>();
       WAVLIterator iter = new WAVLIterator();

       while (iter.hasNext()) {
           sortedVals.add(iter.next().value);
       }

       return sortedVals.toArray(new String[sortedVals.size()]);
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
    private WAVLNode parent;
    private int subtreeSize;
    //private int rank;

    public WAVLNode(int key,String value) {
        this.key = key;
        this.value = value;
        this.right = null;
        this.left = null;
        this.parent = null;
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
