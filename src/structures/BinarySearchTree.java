package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;
	int size = 0;

	public boolean isLeafNode(BSTNode<T> node) {
		return (boolean)(node.getRight() == null && node.getLeft() == null);
	}

	public void printTree() {
		root.printSubtree(5);
	}

	public void toDotFormatRun() {
		System.out.println(toDotFormat(root));
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int getSize() {
		return size;
	}

	public boolean contains(T t) {
		if (t == null) {throw new NullPointerException();}
		return (boolean)(containsHelper(t, root) != null);
	}

	public BSTNode<T> containsHelper(T elem, BSTNode<T> node)
	{
		if (node == null) {
			return null;
		}

		if (node.getData().equals(elem)) {
			return node;
		}

		if (elem.compareTo(node.getData()) > 0) {
			return containsHelper(elem, node.getRight());
		}

		else {
			return containsHelper(elem, node.getLeft());
		}
	}

	public boolean removeElement(T t) {

		if (t == null) {throw new NullPointerException();}

		if (root == null || !contains(t)) {
			return false;
		}
		
		BSTNode<T> node = containsHelper(t, root);								//the node we want to remove

		if (root.getData().equals(t)) {											//if removing root
			if (isLeafNode(root)) {root = null;}
			else if (root.getRight() == null) {root = root.getLeft();}
			else if (root.getLeft() == null) {root = root.getRight();}
			else {
				getMaxHelper(root.getLeft()).setRight(root.getRight());
				root.getRight().setParent(getMaxHelper(root.getLeft()));
				root = root.getLeft();
			}
		}	

		else if (isLeafNode(node)) {													//if no child nodes
			if (node.getData().compareTo(node.getParent().getData()) > 0) {		//if bigger than parent
				node.getParent().setRight(null);
			}
			else {																//if smaller or equal to parent
				node.getParent().setLeft(null);
			}
		}

		else if (node.getRight() == null) {										//node has left child
			if (node.getData().compareTo(node.getParent().getData()) > 0) {		//if bigger than parent
				node.getParent().setRight(node.getLeft());
				node.getLeft().setParent(node.getParent());
			}
			else {																//if smaller or equal to parent
				node.getParent().setLeft(node.getLeft());
				node.getLeft().setParent(node.getParent());
			}
		}

		else if (node.getLeft() == null) {										//node has left child
			if (node.getData().compareTo(node.getParent().getData()) > 0) {		//if bigger than parent
				node.getParent().setRight(node.getRight());
				node.getRight().setParent(node.getParent());
			}
			else {																//if smaller or equal to parent
				node.getParent().setLeft(node.getRight());
				node.getRight().setParent(node.getParent());
			}
		}

		else {																	//has two children 
			if (node.getData().compareTo(node.getParent().getData()) > 0) {		//if bigger than parent
				node.getParent().setRight(node.getLeft());
				node.getLeft().setParent(node.getParent());
				getMaxHelper(node.getLeft()).setRight(node.getRight());
				node.getRight().setParent(getMaxHelper(node.getLeft()));
			}
			else {
				node.getParent().setLeft(node.getLeft());
				node.getLeft().setParent(node.getParent());
				getMaxHelper(node.getLeft()).setRight(node.getRight());
				node.getRight().setParent(getMaxHelper(node.getLeft()));
			}
		}

		size --;
		return true;
	}

	public T getHighestValueFromSubtree(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValueFromSubtree(node.getRight());
		}
	}

	public T getLowestValueFromSubtree(BSTNode<T> node) {
		if (node.getLeft() == null) {
			return node.getData();
		} else {
			return getLowestValueFromSubtree(node.getLeft());
		}
	}

	private BSTNode<T> removeRightmostFromSubtree(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmostFromSubtree(node.getRight()));
			if (node.getRight() != null){
				node.getRight().setParent(node);
			}
			size--;
			return node;
		}
	}

	public BSTNode<T> removeLeftmostFromSubtree(BSTNode<T> node) {
		if (node.getLeft() == null) {
			return node.getRight();
		} else {
			node.setLeft(removeLeftmostFromSubtree(node.getLeft()));
			if (node.getLeft() != null){
				node.getLeft().setParent(node);
			}
			size--;
			return node;
		}
	}

	public T getElement(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		else if (!contains(t)) {
			return null;
		}
		else {
			return getElementHelper(t, root);
		}
	}

	private T getElementHelper(T t, BSTNode<T> node) {
		if (node.getData().equals(t)) {
			return t;
		}
		else if (isLeafNode(node)) {
			return null;
		}
		else if (t.compareTo(node.getData()) > 0) {
			return getElementHelper(t, node.getRight());
		}
		else {
			return getElementHelper(t, node.getLeft());
		}
	}

	public void addElement(T t) {

		if (t == null) {throw new NullPointerException();}

		BSTNode newNode = new BSTNode<T>(t, null, null);
		if (root == null) {
			root = newNode;
		}
		else {
			addElementHelper(t, root);
		}
		size++;
	}

	private void addElementHelper(T t, BSTNode<T> node) {
		BSTNode newNode = new BSTNode(t, null, null);
		if (t.compareTo(node.getData()) > 0) {

			if (node.getRight() == null) {
				node.setRight(newNode);
				newNode.setParent(node);
			}
			else {
				addElementHelper(t, node.getRight());
			}

		}
		else {

			if (node.getLeft() == null) {
				node.setLeft(newNode);
				newNode.setParent(node);
			}
			else {
				addElementHelper(t, node.getLeft());
			}

		}
	}

	@Override
	public T getMin() {
		if (isEmpty()) {return null;}
		return inorderIterator().next();
	}


	@Override
	public T getMax() {
		if (isEmpty()) {return null;}
		else {
			return getMaxHelper(root).getData();
		}
	}

	private BSTNode<T> getMaxHelper(BSTNode<T> node) {
		if (node.getRight() == null) {
			return node;
		}
		else {
			return getMaxHelper(node.getRight());
		}
	}

	@Override
	public int height() {
		return helperHeight(root);
	}

	private int helperHeight(BSTNode node) {
		if (node == null) {
			return -1;
		}
		else {
			int countR = helperHeight(node.getLeft()) + 1;
			int countL = helperHeight(node.getRight()) + 1;

			if (countR > countL) {return countR;}
			else {return countL;}
		}
	}

	public Iterator<T> preorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}

	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}


	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}

	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}

	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {

		if (other == null) {
			throw new NullPointerException();
		}
		else if (other.getSize() != getSize()) {
			return false;
		}

		Iterator<T> iter = inorderIterator();
		Iterator<T> otherIter = other.inorderIterator();

		while (iter.hasNext()) {
			if (!iter.next().equals(otherIter.next())) {
				return false;
			}
		}

		iter = preorderIterator();
		otherIter = other.preorderIterator();

		while (iter.hasNext()) {
			if (!iter.next().equals(otherIter.next())) {
				return false;
			}
		}

		iter = postorderIterator();
		otherIter = other.postorderIterator();

		while (iter.hasNext()) {
			if (!iter.next().equals(otherIter.next())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {

		if (other == null) {
			throw new NullPointerException();
		}
		else if (getSize() != other.getSize()) {
			return false;
		}

		Iterator<T> iter = inorderIterator();
		Iterator<T> otherIter = other.inorderIterator();

		while (iter.hasNext()) {
			if (!iter.next().equals(otherIter.next())) {
				return false;
			}
		}

		return true;
	}
	
	@Override
	public int countRange(T min, T max) {
		int count = 0;
		Iterator<T> iter = inorderIterator();
		T elem;

		while (iter.hasNext()) {
			elem = iter.next();
			if ((elem.compareTo(min) > 0) && (elem.compareTo(max) < 0)) {
				count++;
			}
		}
		return count;
  }


	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}



	//Main 




	public static void main(String[] args) {
		BSTInterface<Integer> tree = new BinarySearchTree<Integer>();
		System.out.println(tree.height());
		for (Integer s : new Integer[] { 4, 2, 1, 3, 6, 5, 7 }) {
			tree.addElement(s);
		}

		System.out.println("in:");
		Iterator<Integer> iterator = tree.inorderIterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next());
		}
		System.out.println();
		
		System.out.println("pre:");
		iterator = tree.preorderIterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next());
		}
		System.out.println();

		System.out.println("post:");
		iterator = tree.postorderIterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next());
		}
		System.out.println();

		System.out.println(tree.height());
		System.out.println(tree.height());
		System.out.println(tree.getElement(1) + " - true");
		System.out.println(tree.getElement(2) + " - true");
		System.out.println(tree.getElement(3) + " - true");
		System.out.println(tree.getElement(4) + " - root true");
		System.out.println(tree.getElement(5) + " - false");
		System.out.println(tree.getElement(6) + " - true");
		System.out.println(tree.getElement(7) + " - true");
		System.out.println(tree.getElement(8) + " - false");
		System.out.println(tree.contains(null) + " - false");
	}
}