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
    private int treeSize; // not necessary??

    //TODO constructor? overloaded constructor?
    public WAVLTree(int key, String value) {
        this.root = new WAVLNode(key, value);
    }
// added by nadine
    public WAVLTree() {
        this.root = null;
        this.treeSize=0;
    }
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
// added by nadine empty condition
	  if (empty()) {
		  return null;
	  }
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
     * @param node      node at the root of the subtree in which we search for k
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

//TODO Nadine - Insert
  // nadine: created this new method.
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
  

   public int insert(int k, String i) {
	   
	   WAVLNode newnode= new WAVLNode(k,i);
	   insert(newnode,root);
	   return 0;
	   //nadine: not finished yet. depends on rotation method.
	   
   }
   /** Search function with an additional node parameter*/ 
   // insert a node and call promote and rotation method if needed. this method return the 
   //number of rotation which is taken from the promotion and rotation methods.
   public int insert(WAVLNode toInsert, WAVLNode node) {
	        if (root == null) {
	            root = toInsert;
	            
	        }
	        if (toInsert.key < node.key) {
	            if (node.left != null) {
	                insert(toInsert, node.left);
	               
	            } else {
	                toInsert.setParent(node);
	                node.left = toInsert;
	            }
	        } else if (toInsert.key >= node.key) {
	            if (node.right != null) {
	                insert(toInsert, node.right);
	               
	            } else {
	                toInsert.setParent(node);
	                node.right = toInsert;
	            }
	        }
	        int num_ofrotations=promote(toInsert);
	        return num_ofrotations;
	      
	    }

 /**
  * after the insertion, we need to update rank. the rank of a leaf is 1. then we continue in recursion to the node's father and 
  * we add to the father's rank 1 until one of 3 conditions occur:
  * 1- we reached the root. in this case we have no longer what to update
  * 2-if before the insertion we had a rank difference of 2 , it doesnt matter what is the rank of the other child
  * in either cases if we add to the father 1 we get a balanced tree
  * 3- after the insertion we have a rank differences of 0-2. in this case we cant promote the father 
  * as we get unbalanced tree. so the promotion process stops and now we need to do some rotations.
  * 
  */
   
   private int promote(WAVLNode node) {
       if (node == null) {
           return 0;
       }
       else if (node==root) {
    	   return 0;
       }
       else if (node.parent.rank-node.rank==2) {
    	   node.parent.setRank(1);
    	   return 0;
       }
       else if (node.rank==node.parent.rank) {
    	   if(node.parent.getBalance()==2) {
    		  int num_of_rotations= rotate(node.parent);
    		   return num_of_rotations;
    	   }
       }else {
    	   promote(node.parent);
       }
      return 0;
   }
   
  //rotation method. should check if another rotation is needed.
   private int rotate(WAVLNode node) {
	   int balance = update_Balance(node);
	   if(balance==-2) {
			if (node.left.left.rank>=node.left.right.rank) {
				 rotateRight(node);
				 return 1;
			} else {
				//doubleRotateLeftRight(node);
				return 2;
			}
	   } else if(balance==2) {
			if(node.right.right.rank>=node.right.left.rank) {
				rotateLeft(node);
				return 1;
			} else {
				//doubleRotateRightLeft(node);
				return 2;
			}}else {
				return 0;
			}
		}
	   
// check the rank difference between left and right node.
	private int update_Balance(WAVLNode node) {
		int balance = node.right.rank-node.left.rank;
		node.setBalance(balance);
		return 	balance;	
		}
// new- rotate left
	public void rotateLeft(WAVLNode node) {
		
		WAVLNode newtop_node = node.right;
		newtop_node.parent = node.parent;
		
		node.right = newtop_node.left;
		
		if(node.right!=null) {
			node.right.parent=node;
		}
		
		newtop_node.left = node;
		node.parent = newtop_node;
		
		if(newtop_node.parent!=null) {
			if(newtop_node.parent.right==node) {
				newtop_node.parent.right = newtop_node;
			} else if(newtop_node.parent.left==node) {
				newtop_node.parent.left = newtop_node;
			}
		}
		node.setRank(-1);
	
	}
// new- rotate right
public void rotateRight(WAVLNode node) {
		
		WAVLNode newtop_node = node.left;
		newtop_node.parent = node.parent;
		
		node.left = newtop_node.right;
		
		if(node.left!=null) {
			node.left.parent=node;
		}
		
		newtop_node.right = node;
		node.parent = newtop_node;
		
		
		if(newtop_node.parent!=null) {
			if(newtop_node.parent.right==node) {
				newtop_node.parent.right = newtop_node;
			} else if(newtop_node.parent.left==node) {
				newtop_node.parent.left = newtop_node;
			}
		}
		node.setRank(-1);
	
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

//returns how many nodes are in a tree. 
   public int size()
   {
	   return size_Node(root);
   }
 //returns how many nodes are in a specific node.  
   public int size_Node(WAVLNode node) {
	   
	        if (empty()) {
	            return 0;
	        }
	        int left = 0, right = 0;
	        if (node.left != null) {
	            left = size_Node(node.left);
	        }
	        if (node.right != null) {
	            right = size_Node(node.right);
	        }
	        node.setSize(left+right + 1);
	        return node.size;
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
   // size is added because there was a function above to complete. if we can
   // delete things maybe there is no use for size.
   // rank is added, parent,balance
  public class WAVLNode{
	  
    private WAVLNode parent;
	private int key;
    private String value;
    private WAVLNode left;
    private WAVLNode right;
    private int subtreeSize;
    private int rank;
    private int size;
    private int balance;

    public WAVLNode(int key,String value) {
        this.key = key;
        this.value = value;
        this.right = null;
        this.left = null;
        this.parent = null;
        this.subtreeSize = 0;
        this.rank=1;
        this.size=0;
        this.balance=0;
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
    // why is this function here ? it should be a tree function i think
    public int getSubtreeSize()
    {
        return 42; // to be replaced by student code
    }
// new
	public WAVLNode getParent() {
		return parent;
	}
//new
	public void setParent(WAVLNode parent) {
		this.parent = parent;
	}
//new
	public int getRank() {
		return rank;
	}
//new
	public void setRank(int addrank) {
		this.rank = rank+addrank;
	}
//new
	public int getSize() {
		return size;
	}
//new
	public void setSize(int size) {
		this.size = size;
	}
//new
	public int getBalance() {
		return balance;
	}
//new
	public void setBalance(int balance) {
		this.balance = balance;
	}

  }
  
  public static void main(String[] args) {
	  
  }

}
