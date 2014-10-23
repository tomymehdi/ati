package edu.it.itba.models;

public class Circle implements Comparable<Circle> {
	public int a;
	public int b;
	public int r;
	public int votes;

	public Circle(int a, int b, int r, int votes) {
		this.a = a;
		this.b = b;
		this.r = r;
		this.votes = votes;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equalA = a == ((Circle) obj).a;
		boolean equalB = b == ((Circle) obj).b;
		boolean equalR = r == ((Circle) obj).r;
		return equalA && equalB && equalR;
	}

	@Override
	public int hashCode() {
		return (int) (3 * a + 5 * b + 7 * r);
	}

	@Override
	public int compareTo(Circle obj) {
		return obj.votes - votes;
	}

}