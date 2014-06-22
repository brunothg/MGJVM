package de.bno.mgjvm.data;

public interface Pool<E> {

	public int getPoolCount();

	public E getPoolElement(int index);

}
