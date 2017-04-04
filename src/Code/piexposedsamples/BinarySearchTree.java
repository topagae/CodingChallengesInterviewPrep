package Code.piexposedsamples;

import java.util.*;

/**
 * For reference, exercises from piexposed and a couple other places on the internet. Modified, added to, and annotated for learning. I should've fucking saved this from college.
 *
 * Rename
 *
 * @author Chris Lee
 */
public class Solution {

    public static void main(String arg[]) {
        Solution testTree = new Solution();
        testTree.insertNode(5);
        testTree.insertNode(3);
        testTree.insertNode(10);
        testTree.insertNode(1);
        testTree.insertNode(4);
        testTree.insertNode(7);
        testTree.insertNode(12);

        System.out.println("Height of the tree from root is: " + testTree.getTreeHeight(testTree.root));

        System.out.println("\nLowest common ancestor example(1 and 4, LCA should be 3): " + testTree.findLowestCommonAncestor(testTree.root,1,4).getValue());

        System.out.println("\nIn-order Traversal of Tree: ");

        testTree.inOrderTraversalRecursive(testTree.root);

        System.out.println("\nPost-order Traversal of Tree: ");

        testTree.postOrderTraversalRecursive(testTree.root);

        System.out.println("\nPre-order Traversal of Tree: ");

        testTree.preOrderTraversalRecursive(testTree.root);

        System.out.println("\nBreadth first search printout of Tree: ");

        testTree.breadthFirstSearchTraversal(testTree.root);

        System.out.println("\nDepth first search printout of Tree: ");

        testTree.depthFirstSearchTraversal(testTree.root);

        System.out.println("\n\nFind whether Node with value 12 exists: " + testTree.findValue(12));

        System.out.println("\nDelete Node with no children (7) Print Tree(Pre Order):" + testTree.deleteNode(7));
        testTree.preOrderTraversalRecursive(testTree.root);

        System.out.println("\nDelete Node with one child (10) Print Tree(Pre Order): " + testTree.deleteNode(10));
        testTree.preOrderTraversalRecursive(testTree.root);

        System.out.println("\nDelete Node with two children (3) Print Tree(Pre Order): " + testTree.deleteNode(3));
        testTree.preOrderTraversalRecursive(testTree.root);
    }

    // Each tree has a root node.
    public static Node root;

    // Start null, just add the root to an empty tree. Handled with a special case.
    public Solution() {
        this.root = null;
    }

    // To findValue a node in a BST, start from root. All left children are less then their parents and all right children are greater. So if your value is lesser, you need to go left.
    // And if your value is greater, you want to search right.
    public boolean findValue(int valueToFind) {

        Node currentFindNode = root;

        while (currentFindNode != null) {
            if (valueToFind == currentFindNode.getValue()) {
                return true;
            } else if (valueToFind < currentFindNode.getValue()) {
                currentFindNode = currentFindNode.getLeft();
            } else {
                // Could also check to see if valueToFind > current.getValue(), but that is redundant.
                currentFindNode = currentFindNode.getRight();
            }
        }
        //If you go through whole loop and don't find the value, return false.
        return false;
    }


    // Deleting Nodes in a BST is a specialized find. You have find the node, then depending on the number of children
    // remove the node and re-arrange the links to match.
    public boolean deleteNode(int nodeToDelete) {

        // We need to try and find the node to delete and keep track of it's parent node and child orientation for proper deletion protocol.
        // Very similar to find but we redoing it because we need to get the parent and orientation into memory as well.
        Node parent = root;
        Node currentFindNode = root;
        boolean isLeftChild = false;

        // Loop through all the nodes, update the parent/orientation as you go. Stop looping when you find the value to delete which gives the the node, its orientation, and its parent.
        // which is used later for proper deletion.
        while (currentFindNode.getValue() != nodeToDelete) {
            parent = currentFindNode;
            if (currentFindNode.getValue() > nodeToDelete) {
                isLeftChild = true;
                currentFindNode = currentFindNode.getLeft();
            } else {
                isLeftChild = false;
                currentFindNode = currentFindNode.getRight();
            }
            if (currentFindNode == null) {
                return false;
            }
        }

        // Found the node to be deleted, now we have to figure out which case it is.

        //Case 1: If node to be deleted has no children. Easy case. If it has no children, just delete links to it from parent.
        if (currentFindNode.getLeft() == null && currentFindNode.getRight() == null) {
            // Special case. If no children and root, you delete root.
            if (currentFindNode == root) {
                root = null;
            }
            // If left child, set parent left child to null, else right child to null.
            if (isLeftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
          // Case 2(Left Node) : If node to be deleted has only one child, and it is a left child.
        } else if (currentFindNode.getRight() == null) {
            // Special case. If you're the root, the left child is now the root.
            if (currentFindNode == root) {
                root = currentFindNode.getLeft();
            // If you're the left child. Delete self by setting parent link to deleted nodes left child(The only child the deleted node should have is the new child).
            } else if (isLeftChild) {
                parent.setLeft(currentFindNode.getLeft());
            } else {
                parent.setRight(currentFindNode.getLeft());
            }
          // Case 2(Right Node) : If node to be deleted has only one child. Same as above but slightly different as the node to be deleted has one right child.
        } else if (currentFindNode.getLeft() == null) {
            if (currentFindNode == root) {
                // Special case. If you're the root, the left child is now the root.
                root = currentFindNode.getRight();
              // If you're the left child. Delete self by setting parent link to deleted nodes right child(The only child the deleted node should have is the new child).
            } else if (isLeftChild) {
                parent.setLeft(currentFindNode.getRight());
            } else {
                parent.setRight(currentFindNode.getRight());
            }
            // Case 3: If the to be deleted node has two children, you need to find the smallest valued node in the to be deleted nodes right subtree. This works as a replacement for the deleted
            // node and keeps the tree balanced.
        } else if (currentFindNode.getLeft() != null && currentFindNode.getRight() != null) {
            // Now we have find the minimum element in the right sub-tree of the to be deleted nodes children. Farmed out to a helper method.
            Node successor = getSuccessorNodeForDeletion(currentFindNode);

            // If the node we want to delete is the root, the successor we just found is the new root.
            if (currentFindNode == root) {
                root = successor;
            // If the node is a left child, we set the parent left child to the new successor.
            } else if (isLeftChild) {
                parent.setLeft(successor);
            } else {
                parent.setRight(successor);
            }
            // Finally, we set the new left for the successor to the be old left of the deleted node. Always left since we
            successor.setLeft(currentFindNode.getLeft());
        }
        return true;
    }

    public Node getSuccessorNodeForDeletion(Node tobeDeletedNode) {

        // To find the successor node for deleting a node in a tree with two children, you need to find the smallest node in that nodes right subtree.
        Node successorNode = null;
        Node successorParentNode = null;
        Node current = tobeDeletedNode.getRight();

        // Set the first node to be searched to be the right node above. Then traverse down left until there's nothing left.
        while (current != null) {
            successorParentNode = successorNode;
            successorNode = current;
            current = current.getLeft();
        }

        // Check if successor has the right child, it cannot have left child. If it does have the right child, add it to the left of successorParent.
        if (successorNode != tobeDeletedNode.getRight()) {
            successorParentNode.setLeft(successorNode.getRight());
            successorNode.setRight(tobeDeletedNode.getRight());
        }
        return successorNode;
    }

    // Inserting is just a find but you have to do something when you find the node.
    public void insertNode(int nodeToInsertValue) {

        Node newNode = new Node(null,null,nodeToInsertValue);

        // Special Case: If you're inserting to an empty tree, it's the new root.
        if (root == null) {
            root = newNode;
            return;
        }

        // Traverse the tree starting from root. Keep track of parent as this becomes important to setting new children as part of inserting new node.
        Node current = root;
        Node parent = null;

        while (true) {
            parent = current;
            // If your node value is less then the current value of the node, you wanna keep going left.
            if (nodeToInsertValue < current.getValue()) {
                current = current.getLeft();
                // If you iterate to the the new left and it has no children. Voila, that parent has a new child!
                if (current == null) {
                    parent.setLeft(newNode);
                    return;
                }
            // Else, you wanna go to the right, and do the same thing.
            } else {
                current = current.getRight();
                if (current == null) {
                    parent.setRight(newNode);
                    return;
                }
            }
        }

    }

    // From the root, does an in-order traversal of the tree recursively. Done by going down left, printing value, then going down right.
    public void inOrderTraversalRecursive(Node currentNode) {
        if (currentNode != null) {
            inOrderTraversalRecursive(currentNode.getLeft());
            System.out.print(currentNode.getValue() + " " );
            inOrderTraversalRecursive(currentNode.getRight());
        }
    }

    // From the root, does a pre-order traversal of the tree recursively. Done by going down left, printing value, then going down right.
    public void preOrderTraversalRecursive(Node currentNode) {
        if (currentNode != null) {
            System.out.print(currentNode.getValue() + " ");
            preOrderTraversalRecursive(currentNode.getLeft());
            preOrderTraversalRecursive(currentNode.getRight());
        }
    }

    // From the root, does a pre-order traversal of the tree recursively. Done by going down left, printing value, then going down right.
    public void postOrderTraversalRecursive(Node currentNode) {
        if (currentNode != null) {
            postOrderTraversalRecursive(currentNode.getLeft());
            postOrderTraversalRecursive(currentNode.getRight());
            System.out.print(currentNode.getValue() + " ");
        }
    }

    // Usually you'd stop after searching for something in a breadth first SEARCH. But in this case I replaced the termination statement with a print to showcase the traversal of a tree.
    public void breadthFirstSearchTraversal(Node BFSRoot) {

        // A BFSQueue for BFS searching in the class. Queue works for this because queue is first in, first out. You pop things off in the order they were put in which works great for
        // a breadth first search so long as you add all a nodes children to the queue before moving to the next item in the queue.
        Queue<Node> BFSQueue = new LinkedList<Node>();

        // If you search a layer and come up empty, return.
        if (BFSRoot == null) {
            return;
        }
        // Add the first node.
        BFSQueue.add(BFSRoot);

        // While you're filling up the queue, I.E you have more nodes to search.
        while(!BFSQueue.isEmpty()){
            // Pop off the next node in the queue to look at.
            Node currentSearchNode = BFSQueue.remove();

            // Display what's in it for testing. You could also replace this with an if to do a search in lieu of this display.
            System.out.print(currentSearchNode.getValue() + " ");

            // If the node you're looking at has a left child, add it to the queue to be searched. Same for a right child.
            if(currentSearchNode.getLeft() != null)
            {
                BFSQueue.add(currentSearchNode.getLeft());
            }
            if(currentSearchNode.getRight() != null)
            {
                BFSQueue.add(currentSearchNode.getRight());
            }
        }

    }

    // Usually you'd stop after searching for something in a depth first SEARCH. But in this case I replaced the termination statement with a print to showcase the traversal of a tree.
    public void depthFirstSearchTraversal(Node BFSRoot) {

        // Depth first search is modeled after a stack because it keeps adding until it can't. Then pops off an element and does it again. This models going as deep as possible, checking for your
        // value at the lowest depth (Hence the name). Then if it doesn't find it, pop the next off the stack and try to go deep again.
        Stack<Node> searchStack = new Stack<Node>();

        // Add the node you want to start the search at that was passed in.
        searchStack.push(BFSRoot);

        // As long as the stack isn't empty. Keep digging.
        while(!searchStack.isEmpty())
        {
            // Pop off the next node to check
            Node currentNodeToCheck = searchStack.pop();

            // Add the children if it has any.
            if (currentNodeToCheck.getRight() != null)
            {
                searchStack.push(currentNodeToCheck.getRight());
            }
            if (currentNodeToCheck.getLeft() != null)
            {
                searchStack.push(currentNodeToCheck.getLeft());
            }
            // Display what's in current search node for testing. You could also replace this with an if to do a search in lieu of this display.
            System.out.print(currentNodeToCheck.getValue() + " ");
        }
    }

    // Snippet of a recursive way to get the height of a tree from a certain node (Most likely the root, but could work on subtrees). The height of a tree is 1 + the biggest subtree.
    // Lends itself well to recursion because you can just keep checking every subtree to get this number.
    public static int getTreeHeight(Node nodeToCheck)
    {
        // If you hit a null node, no height, return 0;
        if(nodeToCheck == null)
        {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(nodeToCheck.getLeft()),getTreeHeight(nodeToCheck.getRight()));
    }

    // Snippet to find the lowest common ancestor between two nodes in a tree.
    Node findLowestCommonAncestor(Node searchNode, int nodeValueOne, int nodeValueTwo ){

        //While you still have nodes you can search.
        while( searchNode != null ){
            int currentSearchNodeValue = searchNode.getValue();
            // While the value of the node your current searching is larger then both. You want to go left to find a smaller possible ancestor.
            if( currentSearchNodeValue > nodeValueOne && currentSearchNodeValue > nodeValueTwo ){
                searchNode = searchNode.getLeft();
            // While the value of the node your current searching is smaller then both. You want to go right to find a larger possible ancestor.
            } else if( currentSearchNodeValue < nodeValueOne && currentSearchNodeValue < nodeValueTwo ){
                searchNode = searchNode.getRight();
            // If you find a value that between, by definition you've found the lowest ancestor in common. Mozel tov.
            } else {
                return searchNode;
            }
        }
        return null; // only if empty tree
    }

}

/**
 * Describing the node of a tree. Not enforcing constraints yet, but this class is going to use it as a BST example.
 */
class Node {
    private Node left;
    private Node right;
    private int value;

    public Node(Node left, Node right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getValue() {
        return value;
    }

    public void setLeft(Node leftToSet) {
        this.left = leftToSet;
    }

    public void setRight(Node rightToSet) {
        this.right = rightToSet;
    }

    public void setValue(int valueToSet) {
        this.value = valueToSet;
    }
}