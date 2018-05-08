/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree.
 * (Haupler, Sen & Tarajan ‘15)
 *
 */

public class WAVLTree {
//TODO think about whether we need to maintain tree size explicitly
    private WAVLNode root;

    //TODO overloaded constructor
    /*public WAVLTree(int key, String value) {
        this.root = new WAVLNode(key, value);
    }*/

    public WAVLTree(WAVLNode node) {
        this.root = node;
    }

    /**
     *
     * @return          true if tree is empty, otherwise false
     *
     * Complexity analysis:
     */
  public boolean empty() {
      if (root == null)
          return true;
      return false;
  }

    /**
     *
     * @param k         the key being searched
     * @return k.val    value associated with key k, or null if k is not
     *                  in the tree.
     *
     * Used as a wrapper function that passes k and the tree root to an overloaded
     * search function to search each subtree along the path from the root
     * to the required key.
     *
     * This method runs in O(h) = O(logn) time as it traverses a simple path
     * from the root to the deepest leaf in the worst case.
     *
     */
  public String search(int k) {
      return search(k, root);
  }

    /**
     *
     * @param k         the key being searched
     * @param node      node at the root of the subtree in which we search for k
     * @return k.val    value associated with key k, or null if k is not
     *                  in the tree.
     *
     * This method utilizes the BST property of the WAVL tree to move recursively
     * from a root node to one of its subtrees, according to k and the key held at
     * the node.
     *
     */
  private String search(int k, WAVLNode node) {
      if (k ==  node.key)
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

   /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   //TODO Noa - Delete
   public int delete(int k)
   {
           return 42;   // to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   // TODO Nadine
   public String min()
   {
           return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   //TODO Noa
   public String max()
   {
           return "42"; // to be replaced by student code
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
     //TODO Nadine
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
	  //private int rank;
	  
	  public WAVLNode(int key,String value) {
		  this.key = key;
		  this.value = value;
		  this.right = null;
		  this.left = null;

		  
	  }
	  
	  			public int getKey()
	  			{
	  				return key; // to be replaced by student code
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
