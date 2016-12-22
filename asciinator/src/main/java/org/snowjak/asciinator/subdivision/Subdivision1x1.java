package org.snowjak.asciinator.subdivision;

/**
 * Defines a subdivision scheme that divides the grid-square into a 1x1 grid --
 * i.e., it doesn't subdivide at all!
 * 
 * @author snowjak88
 *
 */
public enum Subdivision1x1 implements GridSquareSubdivisionScheme {

	/**
	 * The central subdivision, and the only one.
	 */
	C;

	@Override
	public double getRegionStartX() {

		return 0;
	}

	@Override
	public double getRegionStartY() {

		return 0;
	}

	@Override
	public double getRegionEndX() {

		return 1;
	}

	@Override
	public double getRegionEndY() {

		return 1;
	}
}
