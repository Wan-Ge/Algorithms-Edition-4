/**
 * This implementation of the ordered symbol-table API uses a binary search tree built from Node
 * objects that each contain a key, associated value, two links, and a node count N. Each node is
 * the root of a subtree containing N nodes, with its left link pointing to a Node that is the root
 * of a subtree with smaller keys. of course, right pointing larger keys.
 *
 * Created by WanGe on 2017/3/26.
 */

package Search;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Node root;      //root of BST

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;          // # nodes in subtree rooted here

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val =val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else           return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        //Return value associated with key in the subtree rooted at x
        //return null if key not present in subtree at x
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)    return get(x.left, key);
        else if (cmp > 0)    return get(x.right, key);
        else    return x.val;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    public Node put(Node x, Key key, Value val) {
        //Change key's value to val if key in subtree rooted at x
        //Otherwise, add new node to subtree associating key with val
        if (x == null)  return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)    x.left = put(x.left, key, val);
        else if (cmp > 0)    x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * The max() and ceiling methods are the same as min() and floor() (respectively)
     * with right and left (and < and >)interchanged
     */
    public Key min() {return min(root).key;}

    public Key max() {return max(root).key;}

    private Node min(Node x) {
        if (x.left == null)     return x;
        return min(x.left);
    }

    private Node max(Node x) {
        if (x.right == null)    return x;
        return max(x.right);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null)  return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)   return x;
        if (cmp < 0)    return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null)  return t;
        else            return x;
    }

    /**
     * return Node containing key of rank k
     * @param k rank k
     */
    public Key select(int k) {return select(root, k).key;};

    private Node select(Node x, int k) {
        if (x == null)  return null;
        int t = size(x.left);
        if      (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else            return x;
    }

    /**
     * Return number of keys less than x.key in the subtree rooted at x.
     */
    public int rank(Key key) {return rank(key, root);}

    private int rank(Key key, Node x) {
        if (x == null)  return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)        return rank(key, x.left);
        else if (cmp > 0)   return 1 + size(x.left) + rank(key, x.right);
        else                return size(x.left);
    }

    /**
     * The deleteMax() method is the same as deleteMin() with right and left interchanged.
     */
    public void deleteMin() {root = deleteMin(root);}

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * delete any key in this tree
     *
     * 1. Save a link to the node to be deleted in t.
     * 2. Set x to point to its successor min(t.right)
     * 3. Set the right link of x(which is supposed to point to the BST containing all the
     *    keys larger than x.key) to deleteMin(t.right), the link to the BST containing all
     *    the keys that are larger than x.key after the deletion
     * 4. Set the left link of x (which was null) to t,.left (all the keys that are less than
     *    both the deleted key and its successor)
     *
     * @param key should be deleted
     */
    public void delete(Key key) {root = delete(root, key);}

    private Node delete(Node x, Key key) {
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)    x.left = delete(x.left, key);
        else if (cmp > 0)   x.right = delete(x.right, key);
        else {
            if (x.right == null)    return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * inorder traversal
     */
    private void print(Node x) {
        if (x == null)  return;
        print(x.left);
        System.out.println(x.key);
        print(x.right);
    }

    //print all the elements in this tree
    public Iterable<Key> keys() {return keys(min(), max());}

    public Iterable<Key> keys(Key low, Key high) {
        // You can use other ways to implements the Queue<T> interface
        LinkedList<Key>  queue = new LinkedList<>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key low, Key high) {
        if (x == null)  return;
        int cmplow = low.compareTo(x.key);
        int cmphigh = high.compareTo(x.key);
        if (cmplow < 0) keys(x.left, queue, low, high);
        if (cmplow <= 0 && cmphigh >= 0)    queue.add(x.key);
        if (cmphigh > 0)    keys(x.right, queue, low, high);
    }
}
