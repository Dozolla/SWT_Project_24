class BtNode<E> {
    private E data;
    private BtNode<E> left;
    private BtNode<E> right;
    static int count = 0;
    int recordNum = 0;

    public BtNode(E data) {
        this.data = data;
        left = null;
        right = null;
        recordNum = count;
        count++;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public BtNode<E> getLeft() {
        return left;
    }

    public void setLeft(BtNode<E> left) {
        this.left = left;
    }

    public BtNode<E> getRight() {
        return right;
    }

    public void setRight(BtNode<E> right) {
        this.right = right;
    }

}

public class BinaryTree<E extends Comparable<E>> {
    private BtNode<E> root;

    public BinaryTree() {
        root = null;
    }

    public void insert(E data) {
        root = insert(root, data);
    }

    private BtNode<E> insert(BtNode<E> node, E data) {
        if (node == null) {
            node = new BtNode<E>(data);
        } else {
            if (data.compareTo(node.getData()) <= 0) {
                node.setLeft(insert(node.getLeft(), data));
            } else {
                node.setRight(insert(node.getRight(), data));
            }
        }
        return node;
    }

    public void delete(E data) {
        root = delete(root, data);
    }

    private BtNode<E> delete(BtNode<E> node, E data) {
        if (node == null) {
            return node;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(delete(node.getLeft(), data));
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(delete(node.getRight(), data));
            } else {
                if (node.getLeft() == null && node.getRight() == null) {
                    node = null;
                } else if (node.getLeft() == null) {
                    node = node.getRight();
                } else if (node.getRight() == null) {
                    node = node.getLeft();
                } else {
                    BtNode<E> temp = findMin(node.getRight());
                    node.setData(temp.getData());
                    node.setRight(delete(node.getRight(), temp.getData()));
                }
            }
        }
        return node;
    }

    private BtNode<E> findMin(BtNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.getLeft() == null) {
            return node;
        }
        return findMin(node.getLeft());
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Empty Tree");
        } else {
            printTree(root);
        }
    }

    private void printTree(BtNode<E> node) {
        if (node != null) {
            printTree(node.getLeft());
            System.out.println(node.getData());
            printTree(node.getRight());
        }
    }

    public String toString() {
        return toString(root);
    }

    private String toString(BtNode<E> node) {
        if (node == null) {
            return "";
        } else {
            return toString(node.getLeft()) + node.getData() + toString(node.getRight());
        }
    }

    public boolean contains(E data) {
        return contains(root, data);
    }

    private boolean contains(BtNode<E> node, E data) {
        if (node == null) {
            return false;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return contains(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return contains(node.getRight(), data);
            } else {
                return true;
            }
        }
    }

    public int getRecordNum(E data) {
        return getRecordNum(root, data);
    }

    private int getRecordNum(BtNode<E> node, E data) {
        if (node == null) {
            return -1;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return getRecordNum(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return getRecordNum(node.getRight(), data);
            } else {
                return node.getRecordNum();
            }
        }
    }

    public int countNodes() {
        return BtNode.count;
    }

    public int countLeaves() {
        return countLeaves(root);
    }

    private int countLeaves(BtNode<E> node) {
        if (node == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        } else {
            return countLeaves(node.getLeft()) + countLeaves(node.getRight());
        }
    }

}
