package structures;

import java.util.Iterator;

public interface BSTInterface<T extends Comparable<T>> {

	boolean isEmpty();

	int getSize();

	boolean contains(T element);

	boolean removeElement(T element);

	T getElement(T element);

	void addElement(T element);

	public T getMin();

	public T getMax();

	public int height();

	Iterator<T> preorderIterator();

	Iterator<T> inorderIterator();

	Iterator<T> postorderIterator();

	public boolean equals(BSTInterface<T> other);

	public boolean sameValues(BSTInterface<T> other);

	public int countRange(T min, T max);

	public BSTNode<T> getRoot();

}