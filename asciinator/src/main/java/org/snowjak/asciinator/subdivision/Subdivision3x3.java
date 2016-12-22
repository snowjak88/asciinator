package org.snowjak.asciinator.subdivision;

/**
 * Defines that a grid-square will be subdivided into a 3x3 grid:
 * 
 * <pre>
 *  NW | N | NE
 * ----+---+----
 *   W | C | E
 * ----+---+----
 *  SW | S | SE
 * </pre>
 * 
 * @author snowjak88
 *
 */
public enum Subdivision3x3 implements GridSquareSubdivisionScheme {

	/**
	 * NW corner of a 3x3 grid
	 */
	NW(0, 0, 0.33333, 0.33333),
	/**
	 * N side of a 3x3 grid
	 */
	N(0.33333, 0, 0.66666, 0.33333),
	/**
	 * NE corner of a 3x3 grid
	 */
	NE(0.66666, 0, 1, 0.33333),
	/**
	 * W side of a 3x3 grid
	 */
	W(0, 0.33333, 0.33333, 0.66666),
	/**
	 * Center of a 3x3 grid
	 */
	C(0.33333, 0.33333, 0.66666, 0.66666),
	/**
	 * E side of a 3x3 grid
	 */
	E(0.66666, 0.33333, 1, 0.66666),
	/**
	 * SW corner of a 3x3 grid
	 */
	SW(0, 0.66666, 0.33333, 1),
	/**
	 * S side of a 3x3 grid
	 */
	S(0.33333, 0.66666, 0.66666, 1),
	/**
	 * SE corner of a 3x3 grid
	 */
	SE(0.66666, 0.66666, 1, 1);

	private double startX, startY, endX, endY;

	Subdivision3x3(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	@Override
	public double getRegionStartX() {

		return startX;
	}

	@Override
	public double getRegionStartY() {

		return startY;
	}

	@Override
	public double getRegionEndX() {

		return endX;
	}

	@Override
	public double getRegionEndY() {

		return endY;
	}
}
