/**
 * The basic idea behind red-black BSTs is to encode 2-3 tress by starting with standard BSTs.
 * we think of the links as being of two different types: red links, which bind together two 2-nodes
 * as represent 3-nodes, and black links, which bind together the 2-3 tree. Specifically, we represent
 * 3-nodes as two 2-nodes connected by a single red link that leans left (one of the 2-nodes is the left
 * child of the other).
 *
 *
 * Whether left or right, every rotation leaves us with a link. We always use the link returned
 * by rotateRight() or rotateLeft() to reset the appropriate link in the parent (or the root of the tree).
 * That may be a right or a left link, but we can always use it to reset the link in the parent. This link
 * may be red or black—both rotateLeft() and rotateRight() preserve its color by setting x.color to h.color.
 *
 *
 * In fact, red right-leaning and 4-nodes also can be used, but the left-leaning convention reduces
 * the number of cases and therefore requires substantially less code.
 *
 * Created by WanGe on 2017/3/26.
 */


package Search;

public class RedBlackTree<Key extends Comparable<Key>, Value>{
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    //BST node with color bit
    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;     //color of link from parent to this node

        Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    private int size(Node x) {
        if (x == null) return 0;
        else           return x.N;
    }

    private boolean isRed(Node x) {
        if (x == null)  return false;
        return x.color == RED;
    }

    public boolean isEmpty() {return root == null;}

    private Value get(Node x, Key key) {
        //Return value associated with key in the subtree rooted at x
        //return null if key not present in subtree at x
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)    return get(x.left, key);
        else if (cmp > 0)    return get(x.right, key);
        else    return x.val;
    }

    private Node min(Node x) {
        if (x.left == null)     return x;
        return min(x.left);
    }

    public void print() {print(this.root);}

    // Inorder traversal
    private void print(Node x) {
        if (x == null)  return;
        print(x.left);
        System.out.print(x.key + "  ");
        print(x.right);
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * This flipColorDelete() only use for balance() and moveRed..()
     * Here we flip the children to BLACK, and flip the parent to RED
     */
    private void flipColorsDelete(Node h) {
        h.color = BLACK;
        h.left.color = RED;
        h.right.color = RED;
    }

    public void put(Key key, Value val) {
        // Search for key. Update value if found; grow table if new
        root = put(root, key, val);
        root.color = BLACK;
    }

    /**
     * We can accomplish the insertion by performing the following operations, one after the other, on
     * each node as we pass up the tree from the point of insertion:
     * 1. If the right child is red and the left child is black, rotate left.
     * 2. If both the left child and its left child are red, rotate right.
     * 3. If both children are red, flip colors.
     *
     * @return insert completed
     */
    private Node put(Node h, Key key, Value val) {
        if (h == null)          // Do standard insert, with red link to parent
            return new Node(key, val, 1, RED);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0)    h.left = put(h.left, key, val);
        else if (cmp > 0)    h.right = put(h.right, key, val);
        else h.val = val;

        if (isRed(h.right) && !isRed(h.left))       h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))    h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))        flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.right) && !isRed(h.left))       h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))   h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))        flipColorsDelete(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node moveRedLeft(Node h) {
        //Assuming that h is red and both h.left and h.left.left
        //are black, make h.left or one of its children red.
        flipColorsDelete(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColorsDelete(h);
        if (!isRed(h.left.left))
            h = rotateRight(h);
        return h;
    }

    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node deleteMax(Node h) {
        if (isRed(h.left))  h = rotateRight(h);
        if (h.right == null) return null;
        if (!isRed(h.right) && !isRed(h.right.left))
            h =moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))  h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null)) return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }
}
