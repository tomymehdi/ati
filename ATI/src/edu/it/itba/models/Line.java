package edu.it.itba.models;

public class Line implements Comparable<Line> {
	public double ro;
	public double theta;
	public int votes;

	public Line(double ro, double theta, int votes) {
		this.ro = ro;
		this.theta = theta;
		this.votes = votes;
	}

	@Override
	public boolean equals(Object obj) {
		return ro == ((Line) obj).ro
				&& theta == ((Line) obj).theta;
	}

	@Override
	public int hashCode() {
		return (int) (3 * ro + 5 * theta);
	}

	@Override
	public int compareTo(Line obj) {
		return obj.votes - votes;
	}

}