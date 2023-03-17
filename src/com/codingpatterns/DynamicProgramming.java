package com.codingpatterns;

public class DynamicProgramming {

    public static class Node {
        Node right;
        Node left;
        int val;

        public Node(Node right, Node left, int value) {
            this.right = right;
            this.left = left;
            this.val = value;
        }
        
        public Node(int value) {
            this.val = value;
        }
    }

    public static class MaxObj {
        int val = 0;
    }

    public static int maxSumTreePath(Node node) {
        MaxObj obj = new MaxObj();
        obj.val = Integer.MIN_VALUE;
        maxPath(node, obj);
        return obj.val;
    }
    
    private static int maxPath(Node node, MaxObj res) {
        if (node == null) return 0;

        int leftSum = Math.max(maxPath(node.left, res), 0); // max sum from left subtree
        int rightSum = Math.max(maxPath(node.right, res), 0);
        res.val = Math.max(res.val, leftSum + rightSum + node.val); // path does not goes through root
        return node.val + Math.max(leftSum, rightSum);
    }

	public static void main(String[] args) {
	    Node root = new Node(6);
	    root.left = new Node(8);
	    root.right = new Node(13);
	    root.left.left = new Node(3);
	    root.left.right = new Node(80);
	    root.right.left = new Node(11);
	    root.right.right = new Node(1);

	    System.out.println(maxSumTreePath(root));
	}

}
