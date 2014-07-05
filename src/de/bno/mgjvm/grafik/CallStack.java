package de.bno.mgjvm.grafik;

public interface CallStack {
	int popCallStack();

	void pushCallStack(int i);
}
