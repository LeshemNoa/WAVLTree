import java.util.*;

/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree (Haupler, Sen and Tarajan '15).
 * In this implementation, the tree structure is doubly linked, and it does not contain
 * a representation of the "External leaf" object, whose rank is -1.
 *
 */

public class WAVLTree implements Iterable {
    private WAVLNode root;

    /**
     * Returns the WAVL node which is currently set to be the root of the tree, or null
     * if tree is empty.
     *
     * @return      the node object at the root of the tree or null if tree is empty
     */
    public WAVLNode getRoot()
    {
        return this.root;
    }

    /**
     * Returns the current number of nodes in the tree.
     *
     * @return      the number of nodes in the tree
     */
    public int size() {
        if (root == null) {
            return 0;
        }

        return root.getSubtreeSize();
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
     * Search function with an additional node parameter. Returns a node with the
     * required key, or null if there is no such key in the tree. <br>
     *
     * This method utilizes the BST property of the WAVL tree to move recursively
     * from a root node to one of its subtrees, according to k and the key held at
     * the node.<br>
     *
     * This method runs in O(h) = O(logn) time as it traverses a simple path
     * from the root to the deepest leaf in the worst case.
     *
     * @param k         the key being searched
     * @param node      node at the root of the subtree in which k is searched
     * @return          node with key k, or null if k is not in the tree.
     */
    private WAVLNode findNode(int k, WAVLNode node) {
      if (node == null)
          return null;
      else if (k == node.key)
          return node;
      else if (k > node.key && node.right != null)
          return findNode(k, node.right);
      else if (k < node.key && node.left != null)
          return findNode(k, node.left);
      return null;
  }

    /**
     * Inserts new node, with provided key and value, to the tree. <br>
     *
     * If a node with key k already exists in the tree, -1 will be returned. Otherwise,
     * the function returns the number of rank changes, rotations and double rotations
     * required in order to maintain the WAVL tree invariants, that may have been violated
     * during insertion.<br>
     *
     * This method runs in O(h) = O(logn) time in the worst case. The runtime complexity
     * will be determined by the time it takes to find the insertion place, the time
     * complexity of the rebalancing process, and the time it takes to update the subtree
     * sizes of the nodes that have been affected by the stuctural changes in the tree.
     * All of these processes will involve all nodes along the path from the deepest leaf
     * in the tree to the root in the worst case so they run in O(h) = O(logn), hence the
     * time complexity of insertion will be O(logn) as well.
     *
     * @param k     the key of the new node added to the tree
     * @param i     the info of the new node added to the tree
     * @return      the number of rebalancing operations performed during the rebalancing
     *              process, or -1 if a node with key k already exists in the tree
     */
    public int insert(int k, String i) {
       /* Step 1: Check if tree is empty. If it is, insert new node as root and finish */
       if (root == null) {
           root = new WAVLNode(k, i);
           return 0;
       }

       /* Step 2: Check if key k is already in the tree, or where to insert it if it
       isn't, using the findInsertionPlace method. */
       WAVLNode parentNode = findInsertionPlace(k, root);
       if (parentNode.key == k)
           return -1;

       /* Step 3: Place new node in appropriate place.*/
       WAVLNode newNode = new WAVLNode(k, i, parentNode);

       /* Step 4: Rebalance */
       return insertionRebalance(parentNode);
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
     * new node should not be reinserted. <br>
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
                return findInsertionPlace(k, node.left);
       }
   }

    /**
     * Characterizes the type of node by the rank differences with its children.<br>
     *
     * If the node is a unary node of a leaf, then the missing child is referred to as an external
     * node with rank -1. The function returns a size 2 array containing the rank differences,
     * left to right. This allows to determine the course of action during the rebalancing process.<br>
     *
     * This function runs in O(1) time as it only requires access to pointers, and the creation
     * of a fixed sized array.
     *
     * @param node      the node of which the rank differences are returned
     * @return          a size 2 array: array[0] - rank difference between the node
     *                  and its left child; array[1] - rank difference between the node
     *                  and its right child
     */
    private int[] vertexType(WAVLNode node) {
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
     * Receives a size 2 array of rank differences produced by the vertexType function,
     * and checks if the vertex type is valid according to the WAVL rules. <br>
     *
     * Returns a boolean value - true if the vertex type is one of the following: {(1,1),
     * (1,2), (2,1), (2,2)}, and false otherwise, implying rebalancing is required. <br>
     *
     * This function runs in O(1) time as it iterates on a fixed sized array.
     *
     * @param vType     a size 2 array of rank differences between a certain node in the tree
     *                  and its two children
     * @return          true if vertex type is in accordance with the WAVL invariants, false
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
     * Receives a tree node where a violation of the WAVL tree invariants may have occurred
     * as a result of an insertion, and corrects it by performing a series of rebalancing actions
     * up the path from the given node to the root of the tree.<br>
     *
     * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
     * by the number of promotions O(logn) promotions in the worst case), a the subtree size update
     * process, which takes place as a result of the structural changes in the tree.
     * The subtree size update traverses a path from the deepest leaf in the tree to the root
     * in the worst case, hence it runs in O(logn) time.
     *
     * @param node      WAVL node object where there is a violation of the WAVL tree invariants
     * @return          number of promotions, rotations and double rotations performed
     *                  during the rebalancing process
     */
    private int insertionRebalance(WAVLNode node) {
        int counter = 0;
        WAVLNode curr = node;
        int[] currVType = vertexType(node);

        while (curr != null && !isValidType(currVType)) { // curr != null: Haven't reached the root yet

            if (currVType[0] + currVType[1] == 1) { // (0,1), (1,0) Promotion cases
                counter++;
                curr.rank++;
                curr = curr.parent;
                if (curr != null)
                    currVType = vertexType(curr);
                continue;
            }
            // Rotation cases

            if (currVType[0] == 0) { // Rolling up from the left
                assert (currVType[1] == 2); // (0,2)

                int[] childVType = vertexType(curr.left);

                if (childVType[0] == 1 && childVType[1] == 2) { // child is (1,2)
                    counter += rotateRight(curr, true);
                } else {
                    assert (childVType[0] == 2 && childVType[1] == 1); // child is (2,1)
                    counter += doubleRotateRight(curr, true);
                }

            } else if (currVType[1] == 0) { // Rolling up from the right
                assert (currVType[0] == 2); // (2,0)

                int[] childVType = vertexType(curr.right);

                if (childVType[0] == 2 && childVType[1] == 1) { //child is (2,1)
                    counter += rotateLeft(curr, true);
                } else {
                    assert (childVType[0] == 1 && childVType[1] == 2); // child is (1,2)
                    counter += doubleRotateLeft(curr, true);
                }
            }
            return counter;
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
     * During insertion, node z is demoted during this rotation. All other nodes retain their ranks. <br>
     *
     * During deletion, node x is promoted, and z is demoted once or twice, depending
     * on z's children. If z becomes a leaf after the rotation and is only demoted once,
     * its rank difference with the external leaf y becomes 2, causing a violation of
     * the invariants, as leaves are only allowed to be (1,1) vertices.
     * To solve this, z is demoted once again. All other nodes retain their ranks.<br>
     *
     * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
     * by the subtree size update process, which takes place as a result of the structural
     * changes in the tree. The rotation process itself runs in O(1) time as it only involves
     * a fixed number of pointers and fields. The subtree size update traverses a path from the
     * deepest leaf in the tree to the root in the worst case, hence it runs in O(logn) time.
     *
     * @param z         the node at the root of the subtree rotated
     * @param insert    true if the rotation takes place during insertion, false if it does
     *                  during deletion
     * @return          the number of rebalancing step made during this process -
     *                  1 rotation + the number of promotions and demotions made
     */
    @SuppressWarnings("Duplicates")
    private int rotateRight(WAVLNode z, boolean insert) {
        int counter = 1;

        WAVLNode x = z.left;
        WAVLNode b = x.right;

        z.replaceWith(x);

        x.setRightChild(z);
        z.setLeftChild(b);

        if (insert) {
            z.rank--;
            counter++;
        }
        else {
            z.rank--;
            x.rank++;
            counter += 2;
            if (z.isLeaf()) {
                z.rank--;
                counter++;
            }
        }
        return counter;
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
     * During insertion, node z is demoted during this rotation. All other nodes retain their ranks<br>
     *
     * During deletion, node y is promoted, and z is demoted once or twice, depending
     * on z's children. If z becomes a leaf after the rotation and is only demoted once,
     * its rank difference with the external leaf x becomes 2, causing a violation of
     * the invariants, as leaves are only allowed to be (1,1) vertices.
     * To solve this, z is demoted once again. All other nodes retain their ranks.<br>
     *
     * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
     * by the subtree size update process, which takes place as a result of the structural
     * changes in the tree. The rotation process itself runs in O(1) time as it only involves
     * a fixed number of pointers and fields. The subtree size update traverses a path from the
     * deepest leaf in the tree to the root in the worst case, hence it runs in O(logn) time.
     *
     * @param z         the node at the root of the subtree rotated
     * @param insert    true if the rotation takes place during insertion, false if it does
     *                  during deletion
     * @return          the number of rebalancing step made during this process -
     *                  1 rotation + the number of promotions and demotions made
     */
    @SuppressWarnings("Duplicates")
    private int rotateLeft(WAVLNode z, boolean insert) {
        int counter = 1;

        WAVLNode y = z.right;
        WAVLNode a = y.left;

        z.replaceWith(y);

        y.setLeftChild(z);
        z.setRightChild(a);


        if (insert) {
            z.rank--;
            counter++;
        }
        else {
            z.rank--;
            y.rank++;
            counter += 2;
            if (z.isLeaf()) {
                z.rank--;
                counter++;
            }
        }
        return counter;
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
     * During insertion, nodes y and z are demoted, and node a is promoted during this
     * rotation. All other nodes retain their ranks.<br>
     *
     * During deletion, the rank of node b increases by 2, that of node z decreases by 2,
     * and that of x decreases by 1. All other nodes retains their ranks. <br>
     *
     * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
     * by the subtree size update process, which takes place as a result of the structural
     * changes in the tree. The rotation process itself runs in O(1) time as it only involves
     * a fixed number of pointers and fields. The subtree size update traverses a path from the
     * deepest leaf in the tree to the root in the worst case, hence it runs in O(logn) time.
     *
     * @param z         the node at the root of the subtree rotated
     * @param insert    true if the rotation takes place during insertion, false if it does
     *                  during deletion
     * @return          the number of rebalancing step made during this process -
     *                  1 rotation + the number of promotions and demotions made
     *
     */

    private int doubleRotateRight(WAVLNode z, boolean insert) {
        int counter = 1;

        WAVLNode x = z.left;
        WAVLNode b = x.right;
        WAVLNode c = b.left;
        WAVLNode d = b.right;

        z.replaceWith(b);

        b.setLeftChild(x);
        b.setRightChild(z);
        x.setRightChild(c);
        z.setLeftChild(d);

        if (insert) {
            x.rank--;
            z.rank--;
            b.rank++;
            counter += 3;
        } else {
            b.rank += 2;
            z.rank -= 2;
            x.rank--;
            counter += 3;
        }
        return counter;
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
     * During insertion, nodes y and z are demoted, and node a is promoted during this
     * rotation. All other nodes retain their ranks.<br>
     *
     * During deletion, the rank of node a increases by 2, that of node z decreases by 2,
     * and that of y decreases by 1. All other nodes retains their ranks. <br>
     *
     * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
     * by the subtree size update process, which takes place as a result of the structural
     * changes in the tree. The rotation process itself runs in O(1) time as it only involves
     * a fixed number of pointers and fields. The subtree size update traverses a path from the
     * deepest leaf in the tree to the root in the worst case, hence it runs in O(logn) time.
     *
     * @param z         the node at the root of the subtree rotated
     * @param insert    true if the rotation takes place during insertion, false if it does
     *                  during deletion
     * @return          the number of rebalancing step made during this process -
     *                  1 rotation + the number of promotions and demotions made
     *
     */
    private int doubleRotateLeft(WAVLNode z, boolean insert) {
        int counter = 1;

        WAVLNode y = z.right;
        WAVLNode a = y.left;
        WAVLNode c = a.left;
        WAVLNode d = a.right;

        z.replaceWith(a);

        a.setLeftChild(z);
        a.setRightChild(y);
        z.setRightChild(c);
        y.setLeftChild(d);

        if (insert) {
            y.rank--;
            z.rank--;
            a.rank++;
        } else {
            a.rank += 2;
            z.rank -= 2;
            y.rank--;
            counter += 3;
        }
        return counter;
    }

    /**
     * Removes node with provided key from the tree. <br>
     *
     * If a node with key k does not exist in the tree, -1 will be returned. Otherwise,
     * the function returns the number of rank changes, rotations and double-rotations
     * required in order to maintain the WAVL tree invariants, that may have been violated
     * during deletion.<br>
     *
     * Note that all deletion cases are reduced to the deletion of a leaf: a binary node
     * or a unary node deleted, takes the key and value of its successor / predecessor
     * (as they are available), and the latter is then removed from the tree. If it's a unary
     * node as well, it takes the key and value of its leaf child, and the leaf child is
     * deleted. <br>
     *
     * This method runs in O(h) = O(logn) time in the worst case. The runtime complexity
     * will be determined by the time it takes to find the node to delete, the time
     * complexity of the rebalancing process. Both of these processes run in O(logn) time
     * so the deletion process runs in O(logn) as well.
     *
     * @param k     key of node to be removed from the tree, if it exists in it
     * @return      the number of rebalancing operations performed during the rebalancing
     *              process, or -1 if node with key k was not found in the tree
     */
   public int delete(int k) {
       /* Step 1: Check if node with key k we wish to remove is in the tree, and find it
       * if it is */
       WAVLNode node = findNode(k, root);
       if (node == null)
           return -1;

       /* Step 2: Delete node */
       WAVLNode offender; // Node where WAVL invariant violation may have occurred

            /* Case a: Deleting a leaf */
       if (node.isLeaf()) { // leaf
           offender = deleteLeaf(node);
       }
            /* Case b: Deleting a unary node with right child or a binary node */
       else if (node.right != null) {
           /* Key and value of deleted node are replaced by the successor's, retaining all
           other info */
           WAVLNode successor = nodeWithMinKey(node.right);

           /*      - b.1. Can happen in case of deleting a unary node with right child who
                    is necessarily a leaf, or in case of deleting a binary node whose
                    successor is a leaf */
           if (successor.isLeaf()) {
               offender = deleteLeaf(successor);
               node.key = successor.key;
               node.value = successor.value;
           }

           /*      - b.2. Can happen in case of deleting a binary node, whose successor is
                     unary node with right child, who is necessarily a leaf */
           else {
               node.key = successor.key;
               node.value = new String(successor.value);
               successor.key = successor.right.key;
               successor.value = successor.right.value;
               offender = deleteLeaf(successor.right);
           }
       }
            /* Case c: Deleting a unary node with left child, who is necessarily a leaf*/
       else {
           int newKey = node.left.key;
           String newVal = node.left.value;
           offender = deleteLeaf(node.left);
           node.key = newKey;
           node.value = newVal;
       }

       /* Step 3: Rebalance */
       return deletionRebalance(offender);
   }

    /**
     * Rebalances WAVL tree after insertion. <br>
     *
     * Receives a tree node where a violation of the WAVL tree invariants may have occurred
     * as a result of a deletion, and corrects it by performing a series of rebalancing operations
     * up the path from the given node to the root of the tree.<br>
     *
     * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
     * by the number of promotions (O(logn) promotions in the worst case), a the subtree size update
     * process, which takes place as a result of the structural changes in the tree.
     * The subtree size update traverses a path from the deepest leaf in the tree to the root
     * in the worst case, hence it runs in O(logn) time.
     *
     * @param node      WAVL node object where there is a violation of the WAVL tree invariants
     * @return          number of promotions, rotations and double rotations performed
     *                  during the rebalancing process
     */
   private int deletionRebalance(WAVLNode node) {
       int counter = 0;
       WAVLNode curr = node;

       if (curr == null) { // Empty tree
           return 0;
       }

       int[] currVType = vertexType(node);

       // Check if offender is a leaf with non zero rank
       if (curr.isLeaf() && curr.rank != 0){
           curr.rank = 0;
           counter++;
           curr = curr.parent;
           if (curr != null)
               currVType = vertexType(curr);
       }

       while (curr != null && !isValidType(currVType)) {
           counter++;

           if (currVType[0] + currVType[1] == 5) { // (3,2), (2,3) Demotion Cases
               curr.rank--;
               curr = curr.parent;
               if (curr != null)
                    currVType = vertexType(curr);
               continue;
           }

           if (currVType[0] == 3) { // Rolling up from the right

               int[] childVType = vertexType(curr.right);

               if (currVType[1] == 1 && childVType[0] + childVType[1] == 4) { // child is (2,2) - Double demote
                   curr.rank--;
                   curr.right.rank--;
                   counter += 2; // Two demotions
                   curr = curr.parent;
                   if (curr != null)
                        currVType = vertexType(curr);
               }
               else if (childVType[1] == 1){ // child is (1,1) or (2,1)
                   counter += rotateLeft(curr, false);
                   return counter;

               }

               else {
                   assert (childVType[0] == 1 && childVType[1] == 2);
                   counter += doubleRotateLeft(curr, false);
                   return counter;
               }

           } else if (currVType[1] == 3) { // Rolling up from the left

               int[] childVType = vertexType(curr.left);

               if (currVType[0] == 1 && childVType[0] + childVType[1] == 4) { // child is (2,2) - Double demote
                   curr.rank--;
                   curr.left.rank--;
                   counter += 2;
                   curr = curr.parent;
                   if (curr != null)
                        currVType = vertexType(curr);
               }

               else if (childVType[0] == 1) { // child is (1,1) or (1,2)
                   counter += rotateRight(curr, false);
                   return counter;
               }

               else {
                   assert (childVType[0] == 2 && childVType[1] == 1);
                   counter += doubleRotateRight(curr, false);
                   return counter;
               }
           }
       }
       return counter;
   }



    /**
     * Deletes a leaf node by placing a null value in its parent's appropriate child field,
     * and a null value in its parent field. Returns the deleted node's parent. <br>
     *
     * That way, it becomes an isolated node object. With no reference left to it, the object
     * is eventually removed by the garbage collector.
     *
     * This method runs in O(1) time, as it only involves resetting a fixed number of pointers.
     *
     * @param node      leaf to be removed from the tree
     * @return          its parent node, or null in case the node deleted was the root
     *                  of a size 1 tree
     */
    private WAVLNode deleteLeaf(WAVLNode node) {

        WAVLNode parent = node.parent;

        if (parent != null) {
            if (node.key >= parent.key) { // remove right child
                parent.right = null;
            } else {
                parent.left = null;
            }
            node.parent = null;
        } else if (this.root == node) {
            this.root = null;
        }

        return parent;
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
       if (root == null) {
           return null;
       }
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
     * As a result of the BST property of the WAVL tree, the greatest key in the
     * tree will always be at the rightmost node. The function traverses the rightmost
     * path in the tree until it is exhausted, i.e. when there is no longer any node
     * to the right, then returns the node at the end of it.<br>
     *
     * This function runs in O(h) = O(logn) time as it traverses a path from the root
     * to the deepest leaf in the worst case.
     *
     * @return      the value associated with the maximal key in the tree
     */
   public String max() {
       if (root == null)
           return null;

       WAVLNode curr = root;
       while (curr.right != null) {
           curr = curr.right;
       }
       return curr.value;
   }

    /**
     * Implementing the Iterable interface. Returns a new tree iterator.
     * @return      a new WAVLIterator object
     */
    @Override
    public Iterator iterator() {
        return new WAVLIterator();
    }

    /**
     * The WAVL tree iterator is a finite iterator which will return, upon each call to next(),
     * the next node to appear in an in-order traversal of the tree.<br>
     *
     * The iterator object maintains two fields: a node curr, the node most recently returned
     * by next(), and a counter, which counts the number of calls to next() and allows to
     * determine whether the traversal is complete, and consequently if hasNext() is true or false.
     *
     */

    private class WAVLIterator implements Iterator {
        /** Counts calls to next */
        private int counter = 0;
        /** Last node returned by the iterator */
        private WAVLNode curr;

        private WAVLIterator() {
            this.curr = null;
        }

        /**
         * Checks if there are any remaining nodes unvisited by the iterator.
         *
         * @return      true if iterator is not exhausted, otherwise false
         */
        @Override
        public boolean hasNext() {
            return root != null && (counter < root.getSubtreeSize());
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
            /* If this is the first next call, set curr to be the minimal node in the tree */
            if (curr == null && counter == 0) {
                curr = nodeWithMinKey(root);
            }

            else {
                assert (curr != null);
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
            }

            counter++;
            return curr;
        }
    }

    /**
     * Returns a sorted array of the tree's keys, or an empty array if the tree is empty.<br>
     *
     * The function runs in O(nlogn) in time as in the worst case, as it performs n consecutive
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
     * The function runs in O(nlogn) in time as in the worst case, as it performs n consecutive
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
     * Returns the info of the i'th greatest key in the tree, or null if i = 0 or if there
     * are less than i keys in the tree. <br>
     *
     * Uses the WAVLIterator and finds the required node by performing i calls to next. <br>
     *
     * The function runs in O(nlogn) in time as in the worst case, as it performs n consecutive
     * Successor calls with worst case runtime complexity of O(logn) each. However, in HW2 we proved
     * that a tighter complexity bound would be O(n+h) = O(n+logn) = O(n).<br>
     *
     * @param i         the number of key to be found on the i'th step of an in-order
     *                  walk of the tree
     * @return          the value associated with the i'th greatest key in the tree
     */

    public String select(int i) {
        if (root == null || i > root.getSubtreeSize() || i == 0)
            return null;

        WAVLNode curr = null;
        WAVLIterator iter = new WAVLIterator();
        for (int j = 0; j < i; j++) {
            curr = iter.next();
        }
        return curr == null ? null : curr.value;
   }

    /**
     * WAVL node object represents a vertex in the tree. Each key node object contains
     * an integer key, which determines its place in the tree according to the BST
     * property, and a string value.<br>
     *
     * The tree structure is doubly linked, hence each node knows its parent as well as
     * its children.
     */
    public class WAVLNode {

        private int key;
        private String value;
        private WAVLNode left;
        private WAVLNode right;
        private WAVLNode parent;
        private int subtreeSize;
        private int rank;

        /**
         * Constructor for the WAVLNode class. Creates new node item with provided key
         * and value.<br>
         *
         * @param key       the node's key, which will determine its place in the tree
         * @param value     the info that will be associated with the key
         */

        private WAVLNode(int key, String value) {
            this.key = key;
            this.value = value;
            this.right = null;
            this.left = null;
            this.parent = null;
            this.subtreeSize = 1;
        }

        /**
         * Overloaded constructor for the WAVLNode class, with an additional parent node
         * parameter.<br>
         *
         * Used in the insertion process, this constructor allows to place a new node
         * as the child of an existing node, and update its ancestors subtree sizes after
         * the insertion. <br>
         *
         * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
         * by the subtree size update process - the process itself runs in O(1)
         * time as it only involves a fixed number of pointers. The subtree size update
         * traverses a path from the deepest leaf in the tree to the root in the worst case,
         * hence it runs in O(logn) time.
         *
         * @param key       the new node's key
         * @param value     the new node's info
         * @param parent    the new node's parent
         */
        private WAVLNode(int key, String value, WAVLNode parent) {
            this(key, value);
            this.rank = 0;
            if (key > parent.key)
                parent.setRightChild(this);
            else
                parent.setLeftChild(this);

            parent.updateSubtreeSize();

        }

        /**
         * Retrieves the key of a specific node object. <br>
         *
         * @return      the integer key of a particular node objects
         */
        public int getKey() {
            return key;
        }

        /**
         * Retrieves the value of a specific node object. <br>
         *
         * @return      the string value of a particular node objects
         */
        public String getValue()
        {
            return value;
        }

        /**
         * Retrieves the left child of a specific node object. <br>
         *
         * @return      the node object set as the left child of this node,
         *              or null if node does not have a left child.
         */
        public WAVLNode getLeft()
        {
            return left;
        }

        /**
         * Retrieves the right child of a specific node object. <br>
         *
         * @return      the node object set as the right child of this node,
         *              or null if node does not have a right child.
         */
        public WAVLNode getRight()
        {
            return right;
        }

        /**
         * Returns false if this node object is not an external leaf object (which does
         * not have a key and a value, and whose rank is -1).<br>
         *
         * In this implementation there is no actual representation for this object,
         * hence this function returns true for all nodes.
         *
         * @return          true if node is not an external leaf object. In actuality
         *                  it always returns true.
         */
        public boolean isInnerNode()
        {
            return (true);
        }

        /**
         * Returns false if the node has any (non-null) children, and true if it's a leaf. <br>
         *
         * @return          true if node does not have any children, false otherwise
         */
        private boolean isLeaf()
        {
            return (left == null && right == null);
        }

        /**
         * Returns the number of nodes in the subtree of which this node is the root. <br>
         *
         * This function runs in O(1) time as it only involves accessing a field.
         *
         * @return          the number of node in this nodes subtree: counting itself,
         *                  and the number of nodes in its left and right subtrees.
         */
        public int getSubtreeSize()
        {
            return subtreeSize;
        }

        /**
         * Sets the right child of a node to be the node provided, and updates subtree
         * sizes affected by the change. <br>
         *
         * Updates the parent field for the child node as well, to maintain the doubly
         * linked structure of the tree. <br>
         *
         * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
         * by the subtree size update process - the setting process itself runs in O(1)
         * time as it only involves a fixed number of pointers. The subtree size update
         * traverses a path from the deepest leaf in the tree to the root in the worst case,
         * hence it runs in O(logn) time.
         *
         * @param rChild        node to be set as the right child, or null value
         */
        private void setRightChild(WAVLNode rChild) {
            this.right = rChild;
            if (rChild != null) {
                rChild.parent = this;
            }

            this.updateSubtreeSize();
        }

        /**
         * Sets the left child of a node to be the node provided, and updates subtree
         * sizes affected by the change.<br>
         *
         * Updates the parent field for the child node as well, to maintain the doubly
         * linked structure of the tree. <br>
         *
         * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
         * by the subtree size update process - the setting process itself runs in O(1)
         * time as it only involves a fixed number of pointers. The subtree size update
         * traverses a path from the deepest leaf in the tree to the root in the worst case,
         * hence it runs in O(logn) time.
         *
         * @param lChild        node to be set as the right child, or null value
         */
        private void setLeftChild(WAVLNode lChild) {

            this.left = lChild;
            if (lChild != null) {
                lChild.parent = this;
            }

            this.updateSubtreeSize();
        }

        /**
         * Updates the subtree size field for the node based on the subtree sizes of its
         * children. Afterwards it updates the subtree sizes for the node's ancestors
         * recursively. <br>
         *
         * The function runs in O(h) = O(logn) time, as it traverses a path from the deepest
         * leaf in the tree to the root in the worst case.
         */
        private void updateSubtreeSize() {

            this.subtreeSize = 1 +
                    (this.right == null ? 0 : this.right.subtreeSize) +
                    (this.left == null ? 0 : this.left.subtreeSize);

            if (this.parent != null) {
                this.parent.updateSubtreeSize();
            }
        }


        /**
         * Replaces a node with one of its descendants, and updates the affected subtree tree sizes
         * after the change. <br>
         *
         * This methods takes care of the case where the node replaced is the root of the entire tree.<br>
         *
         * If the node replaced is the root of the tree, the replacing node's parent field is
         * nullified, and it's placed as the root node of the WAVL tree object. Otherwise, it's
         * set to be the appropriate child of the parent of the replaced node, according to how its key
         * relates to the parent's key. <br>
         *
         * The function runs in O(h) = O(logn) time, as its runtime complexity is determined
         * by the subtree size update process - the replacement process itself runs in O(1)
         * time as it only involves a fixed number of pointers. The subtree size update
         * traverses a path from the deepest leaf in the tree to the root in the worst case,
         * hence it runs in O(logn) time.
         *
         * @param replacer       the node to take this the current node's place
         */
        private void replaceWith(WAVLNode replacer) {

            WAVLNode parent = this.parent;
            WAVLNode repParent = replacer.parent;

            /* Step 1: Remove replacer as child of its parent */
            if (repParent != null) {
                if (replacer.key > repParent.key)
                    repParent.right = null;
                else
                    repParent.left = null;

                repParent.updateSubtreeSize();
            }

            /* Step 2: Set replacer as child of the replaced node, doubly linked */
            if (parent != null) {
                if (this.key < parent.key) {
                    parent.setLeftChild(replacer);
                } else {
                    parent.setRightChild(replacer);
                }
            }
            else {
                replacer.parent = null;
                root = replacer;
                root.updateSubtreeSize();
            }
        }
    }
}