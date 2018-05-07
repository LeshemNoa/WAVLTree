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
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
      if (root == null)
          return true;
      return false;
  }
 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null. We use a wrapper function that calls an overloaded
   * search function with teo arguments: the key k and, the current node being inspected.
   * We use recursive calls of the second function in order to use the BST property.
   * Complexity analysis: we traverse a simple path from the root to the deepest leaf in the
   * worst case. Hence the W.C. time complexity is O(h) = O(logn).
   */
  public String search(int k) {
      return search(k, root);
  }

  private String search(int k, WAVLNode node) {
      if (k ==  node.key)
          return node.value;
      else if (k > node.key)
          return search(k, node.right);
      else
          return search(k, node.left);
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
