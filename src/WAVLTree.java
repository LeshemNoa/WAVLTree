import java.util.ArrayList;
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
     * Returns true if the tree does not have any nodes in it, false otherwise.<br>
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
     * the tree.<br>
     *
     * Used as a wrapper function that passes k and the tree root to findNode,
     * another, more general search function, findNode, to search each subtree along the path
     * from the root to the required key.<br>
     *
     * This method runs in O(h) = O(logn) time as it traverses a simple path
     * from the root to the deepest leaf in the worst case.
     *
     * @param k         the key being searched
     * @return          value associated with key k, or null if k is not
     *                  in the tree.
     */
  public String search(int k) {
      WAVLNode found = findNode(k, root);
      if (found != null)
          return found.value;
      return null;
  }

    /**
     * Search function with an additional node parameter. Returns a node.
     *
     * This method utilizes the BST property of the WAVL tree to move recursively
     * from a root node to one of its subtrees, according to k and the key held at
     * the node.
     *
     * This method runs in O(h) = O(logn) time as it traverses a simple path
     * from the root to the deepest leaf in the worst case.
     *
     * @param k         the key being searched
     * @param node      node at the root of the subtree in which k is searched
     * @return          node with key k, or null if k is not in the tree.
     */
  private WAVLNode findNode(int k, WAVLNode node) {
      if (k == node.key)
          return node;
      else if (k > node.key && node.right != null)
          return findNode(k, node.right);
      else if (k < node.key && node.left != null)
          return findNode(k, node.left);
      return null;
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {

       /* Step 1: Check if key k is already in the tree, or where to insert it if it
       isn't, using the findInsertionPlace method. */
       WAVLNode parentNode = findInsertionPlace(k, root);
       if (parentNode.key == k)
           return -1;

       /* Step 2: Place new node in appropriate place.*/
       WAVLNode newNode = new WAVLNode(k, i, parentNode);
       if (k > parentNode.key)
           parentNode.right = newNode;
       else
           parentNode.left = newNode;

       /*Step 3: Characterize the parent vertice by the rank differences on its edges with
        its children, and determine the course of action accordingly. */
       int[] parentNodeType = verticeType(parentNode);
       int[] newNodeType = verticeType(newNode);

       return 0;
       //TODO: UPDATE SUBTREE SIZES UP THE PATH TO THE ROOT
   }

    /**
     * Finds the appropriate parent node for a new node with key k to be inserted,
     * or an existing node with key k. <br>
     *
     * This method utilizes the BST property of the WAVL tree to move recursively
     * from a root node to one of its subtrees, according to k and the key held at
     * the node.<br>
     *
     * It searches the tree to find the unary node or the leaf which will be the
     * parent of the new node with key k. If the node returned has key k, then the
     * new node should ne be reinserted. <br>
     *
     * This function runs in O(h) = O(logn) time as it traverses a path from the root
     * to the deepest leaf in the worst case.
     *
     * @param k         key of the new node inserted
     * @param node      node at the root of the subtree in which the insertion place
     *                  is searched
     * @return          the node to which the node inserted is assigned to as
     *                  its left or right child
     *
     */
   private WAVLNode findInsertionPlace(int k, WAVLNode node) {
       if (k == node.key)
           return node;
       else if (k > node.key) {
           if (node.right == null)
           /* Node is free to accept new node with key k as its right child */
               return node;
           else
           /* Node has a right subtree, hence new node with key k will be placed there */
               return findInsertionPlace(k, node.right);
       }
       else {
           if (node.left == null)
           /* Node is free to accept new node with key k as its left child */
               return node;
           else
           /* Node has a left subtree, hence new node with key k will be placed there */
                return findInsertionPlace(k,node.left);
       }
   }

    /**
     * Characterizes the type of node by the rank differences with its children.<br>
     *
     * If the node is a unary node, then the missing child is referred to as an external
     * node with rank -1. Returns a size 2 array containing the rank differences, left to right.
     * This allows to determine the course of action during the rebalancing process.<br>
     *
     * This function runs in O(1) time as it only requires access to pointers, and the creation
     * of a fixed sized array.
     *
     * @param node      the node of which the rank differences are returned
     * @return          a size 2 array: array[0] - rank difference between the node
     *                  and its left child; array[1] - rank difference between the node
     *                  and its right child
     */
    private int[] verticeType(WAVLNode node) {
       int[] rankDiffs = new int[2];

       if (node.left != null)
           rankDiffs[0] = node.rank - node.left.rank;
       else
           rankDiffs[0] = node.rank - (-1);

       if (node.right != null)
           rankDiffs[1] = node.rank - node.right.rank;
        else
           rankDiffs[1] = node.rank - (-1);

       return rankDiffs;
    }

    /**
     * Receives a size 2 array of rank differences produced by the verticeType function,
     * and checks if the vertice type is valid according to the WAVL rules. <br>
     *
     * Returns a boolean value - true if the vertice type is one of the following: {(1,1),
     * (1,2), (2,1), (2,2)}, and false otherwise, implying rebalancing is required. <br>
     *
     * This function runs in O(1) time as it iterates on a fixed sized array.
     *
     * @param vType     a size 2 array of rank differences between a certain node in the tree
     *                  and its two children
     * @return          true if vertice type is in accordance with the WAVL invariants, false
     *                  otherwise
     */
    private boolean isValidType(int[] vType) {
        for (int rankDiff : vType) {
            if (rankDiff != 1) {
                if (rankDiff != 2)
                    return false;
            }
        }
        return true;
    }

    /**
     * Rebalances WAVL tree after insertion. <br>
     *
     * Receives a tree node where a violation of the WAVL tree invariants has occurred,
     * and corrects it by performing a series of rebalancing actions up the path from the
     * given node to the root of the tree.<br>
     *
     * @return          number of promotions, rotations and double rotations performed
     *                  during the rebalancing process
     */
    private int rebalance(WAVLNode node) {
        int counter = 0;
        WAVLNode curr = node;
        int[] currVType = verticeType(node);

        /* Rebalancing cases after insertion */
        while (! isValidType(currVType)) {
            if (currVType[0] == 0) { // Rolling up from the left
                switch (currVType[1]) {
                    case 1: // (0,1) node - promotion case - NON TERMINAL
                        // promote
                        counter++;
                        curr = curr.parent;
                        currVType = verticeType(curr);
                        break;
                    case 2: // (0,2) node - rotation cases - TERMINAL
                        WAVLNode currChild = curr.left;
                        int[] childVType = verticeType(currChild);

                        if (childVType[0] == 1 && childVType[1] == 2) {
                            // single rotation to the right
                            counter++;
                            return counter;
                        }
                        else if (childVType[0] == 2 && childVType [1] == 1) {
                            /* else if is for debugging purposes. I want to check later if
                            * any other conditions can occur - they shouldn't according to
                            * the algorithm. If everything's ok I'll change it to else*/
                            // double rotation to the left
                            counter++;
                            return counter;
                        }
                }
            }

            else if (currVType[1] == 0) { //Rolling up from the right
                switch (currVType[0]) {
                    case 1: // (1,0) node - promotion case - NON TERMINAL
                        // promote
                        counter++;
                        curr = curr.parent;
                        currVType = verticeType(curr);
                        break;
                    case 2: // (2,0) node - rotation cases - TERMINAL
                        WAVLNode currChild = curr.right;
                        int[] childVType = verticeType(currChild);

                        if (childVType[0] == 2 && childVType[1] == 1) {
                            // single rotation to the left
                            counter++;
                            return counter;
                        }

                        else if (childVType[0] == 1 && childVType[1] == 2) {
                            // double rotation to the left
                            counter++;
                            return counter;
                        }
                    }
                }
        }
        return counter;
    }

    /**
     * Performs a single rotation to the right of the subtree of which the node
     * provided is the root. <br>
     *
     * Illustration below is in accordance with variable names:
     *
     *        p                    p
     *        |                    |
     *        z                    x
     *      /  \  Rotate right   /  \
     *     x   y                a   z
     *    / \                      / \
     *   a  b                     b  y
     *
     * During this process, node z is demoted. All other nodes retain their ranks.
     *
     * @param z       the node at the root of the subtree rotated
     */
    private void rightRotate(WAVLNode z) {
        WAVLNode p = z.parent;
        WAVLNode x = z.left;
        WAVLNode b = x.right;

        if (z.key < p.key) { // rotating p's left subtree
            p.setLeftChild(x);
        }
        else { // rotating p's right subtree
            p.setRightChild(x);
        }

        x.setRightChild(z);
        z.setLeftChild(b);

        z.rank--;
    }

    /**
     * Performs a single rotation to the left of the subtree of which the node
     * provided is the root. <br>
     *
     * Illustration below is in accordance with variable names:
     *
     *     p                     p
     *     |                     |
     *     z                     y
     *   /  \   Rotate left    /  \
     *  x   y                 z   b
     *     / \               / \
     *    a  b              x  a
     *
     * During this process, node z is demoted. All other nodes retain their ranks.
     *
     * @param z     the node at the root of the subtree rotated
     */
    private void leftRotate(WAVLNode z) {
        WAVLNode p = z.parent;
        WAVLNode y = z.right;
        WAVLNode a = y.left;

        if (z.key < p.key) { // rotating p's left subtree
            p.setLeftChild(y);
        }
        else { // rotating p's right subtree
            p.setRightChild(y);
        }

        y.setLeftChild(z);
        z.setRightChild(a);

        z.rank--;
    }

    /**
     * Performs a double rotation to the right of the subtree of which the node
     * provided is the root. <br>
     *
     * Illustration below is in accordance with variable names:
     *
     *      p                            p
     *      |                            |
     *      z                            b
     *     / \                         /   \
     *    x  y  Double rotate right   x    z
     *   / \                         / \  / \
     *  a  b                        a  c d  y
     *    / \
     *   c  d
     *
     * During this process, nodes x and z are demoted, and node b is promoted.
     * All other nodes retain their ranks.
     *
     * @param z     the node at the root of the subtree rotated
     */

    private void doubleRotateRight(WAVLNode z) {
        WAVLNode p = z.parent;
        WAVLNode x = z.left;
        WAVLNode b = x.right;
        WAVLNode c = b.right;
        WAVLNode d = b.left;

        if (z.key < p.key) { // rotating p's left subtree
            p.setLeftChild(b);
        }
        else { // rotating p's right subtree
            p.setRightChild(b);
        }

        b.setLeftChild(x);
        b.setRightChild(z);
        x.setRightChild(c);
        z.setLeftChild(d);

        x.rank--;
        z.rank--;
        b.rank++;
    }

    /**
     * Performs a double rotation to the right of the subtree of which the node
     * provided is the root. <br>
     *
     * Illustration below is in accordance with variable names:
     *
     *    p                            p
     *    |                            |
     *    z                            a
     *   / \                         /   \
     *  x  y   Double rotate left   z    y
     *    / \                      / \  / \
     *   a  b                     x  c d  b
     *  / \
     * c  d
     *
     * During this process, nodes y and z are demoted, and node a is promoted.
     * All other nodes retain their ranks.
     *
     * @param z     the node at the root of the subtree rotated
     */
    private void doubleRotateLeft(WAVLNode z) {
        WAVLNode p = z.parent;
        WAVLNode y = z.right;
        WAVLNode a = y.left;
        WAVLNode c = a.left;
        WAVLNode d = a.right;

        if (z.key < p.key) { // rotating p's left subtree
            p.setLeftChild(a);
        }
        else { // rotating p's right subtree
            p.setRightChild(a);
        }

        a.setLeftChild(z);
        a.setRightChild(y);
        z.setRightChild(c);
        y.setLeftChild(d);

        y.rank--;
        z.rank--;
        a.rank++;
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
     * tree is empty.<br>
     *
     * Used as a wrapper function that passes the tree root to another, more
     * general function, nodeWithMinKey, which returns the node with the minimal key
     * of the subtree whose root is the node passed to the function.<br>
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

    /**
     * Returns the value associated with the maximal key in the tree, or null if the
     * tree is empty.<br>
     *
     * Used as a wrapper function that passes the tree root to another, more
     * general function, nodeWithMaxKey, which returns the node with the minimal key of
     * the subtree whose root is the node passed to the function.<br>
     *
     * This function runs in O(h) = O(logn) time as it traverses a path from the root
     * to the deepest leaf in the worst case.
     *
     * @return      the value associated with the maximal key in the tree
     */
   public String max() {
       return nodeWithMaxKey(root).value;
   }

    /**
     * Returns the node with the maximal key in the tree, or null if the
     * tree is empty.<br>
     *
     * As a result of the BST property of the WAVL tree, the greatest key in the
     * tree will always be at the rightmost node. The function traverses the rightmost
     * path in the tree until it is exhausted, i.e. when there is no longer any node
     * to the right, then returns the node at the end of it.<br>
     *
     * @return      the value associated with the maximal key in the tree
     */
   private WAVLNode nodeWithMaxKey(WAVLNode node) {
       if (node == null)
           return null;

       WAVLNode curr = node;
       while (curr.right != null) {
           curr = curr.right;
       }
       return curr;
   }

    @Override
    public Iterator iterator() {
        return new WAVLIterator();
    }

    /**
     * The WAVL tree iterator is a finite iterator which will return, upon each call to next(),
     * the next node to appear in an in-order traversal of the tree.<br>
     *
     * The iterator maintains two variables: a node curr, the node most recently returned by next(),
     * and a counter, which counts the number of calls to next() and allows to determine whether
     * the traversal is complete, and consequently if hasNext() is true or false.
     *
     */

    private class WAVLIterator implements Iterator {
        /** Counts calls to next */
        private int counter = 0;
        /** Last node returned by the iterator */
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
         * Returns the next node in an in-order traversal of the tree.<br>
         *
         * This function implements the Successor algorithm of the BST.<br>
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
     * Returns a sorted array of the tree's keys, or an empty array if the tree is empty.<br>
     *
     * The function runs in O(nlogn) in time as in the worst case, as it performs n cosecutive
     * Successor calls with worst case runtime complexity of O(logn). However, in HW2 we proved
     * that a tighter complexity bound would be O(n+h) = O(n+logn) = O(n).<br>
     *
     * @return      array of the tree's keys in ascending order
     */
   public int[] keysToArray() {
       List<Integer> sortedKeys = new ArrayList<>();
       WAVLIterator iter = new WAVLIterator();

       while (iter.hasNext()) {
           sortedKeys.add(iter.next().key);
       }

       /* Due to Java's problematic handling of primitive types, unboxing, arrays and generics,
        * a List<Integer> cannot be directly converted to int[], so we use Java 8 streams instead.*/
       return sortedKeys.stream().mapToInt(i->i).toArray();


   }

    /**
     * Returns a string array containing the values of the tree's nodes, sorted in ascending
     * order of their keys, or an empty array if the tree is empty.<br>
     *
     * The i-th cell in the array will contain the value associated with the i-th key.<br>
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
    private int rank;

    public WAVLNode(int key, String value) {
        this.key = key;
        this.value = value;
        this.right = null;
        this.left = null;
        this.parent = null;
        this.subtreeSize = 0;
    }

        /**
         * Overloaded constructor for the WAVLNode class, with an additional parent node
         * parameter.<br>
         *
         * @param key       the new node's key
         * @param value     the new node's info
         * @param parent    the new node's parent
         */
    private WAVLNode(int key, String value, WAVLNode parent) {
        this(key, value);
        this.rank = 0;
        this.parent = parent;
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

        /**
         * Sets the right child of a node to be the node provided. <br>
         *
         * Updates the parent field for the child node as well, to maintain the doubly
         * linked structure.
         *
         * @param rChild        node to be set as the right child
         */
    private void setRightChild(WAVLNode rChild) {
        this.right = rChild;
        rChild.parent = this;
    }
        /**
         * Sets the left child of a node to be the node provided. <br>
         *
         * Updates the parent field for the child node as well, to maintain the doubly
         * linked structure.
         *
         * @param lChild        node to be set as the right child
         */
    private void setLeftChild(WAVLNode lChild) {
        this.left = lChild;
        lChild.parent = this;
    }


  }

}
