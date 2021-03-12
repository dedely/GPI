package data;

public class Coordinate {

	private double abscissa;
	private double ordinate;

	public Coordinate(double newX, double newY) {
		this.abscissa = newX;
		this.ordinate = newY;
	}

	public double getOrdinate() {
		return ordinate;
	}

	public void setOrdinate(double ordinate) {
		this.ordinate = ordinate;
	}

	public double getAbscissa() {
		return abscissa;
	}

	public void setAbscissa(double abscissa) {
		this.abscissa = abscissa;
	}

	@Override
	public String toString() {
		return "Coordinate [abscissa=" + abscissa + ", ordinate=" + ordinate + "]";
	}
}
