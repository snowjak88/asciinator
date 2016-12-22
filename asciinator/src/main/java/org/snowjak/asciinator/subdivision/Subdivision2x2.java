package org.snowjak.asciinator.subdivision;

/**
 * Defines that a grid-square will be subdivided into a 2x2 grid:
 * 
 * <pre>
 *  NW | NE
 * ----+----
 *  SW | SE
 * </pre>
 * 
 * @author snowjak88
 *
 */
public enum Subdivision2x2 implements GridSquareSubdivisionScheme {

	/**
	 * This sub-region occupies the NW corner of the grid-square.
	 */
	NW(0, 0, 0.5, 0.5),
	/**
	 * This sub-region occupies the NE corner of the grid-square.
	 */
	NE(0.5, 0, 1, 0.5),
	/**
	 * This sub-region occupies the SW corner of the grid-square.
	 */
	SW(0, 0.5, 0.5, 1),
	/**
	 * This sub-region occupies the SE corner of the grid-square.
	 */
	SE(0.5, 0.5, 1, 1);

	private double startX, startY, endX, endY;

	Subdivision2x2(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public double getRegionStartX() {

		return startX;
	}

	public double getRegionStartY() {

		return startY;
	}

	public double getRegionEndX() {

		return endX;
	}

	public double getRegionEndY() {

		return endY;
	}
}
