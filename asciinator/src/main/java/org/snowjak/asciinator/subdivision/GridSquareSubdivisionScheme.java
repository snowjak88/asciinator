package org.snowjak.asciinator.subdivision;

/**
 * Defines how a single grid-square will be subdivided into multiple regions, to
 * permit better matches between image and ASCII-characters.
 * 
 * @author snowjak88
 *
 */
public interface GridSquareSubdivisionScheme {

	/**
	 * @return the fractional coordinate (0.0 - 1.0) at which this sub-region
	 *         starts within the grid-square
	 */
	public double getRegionStartX();

	/**
	 * @return the fractional coordinate (0.0 - 1.0) at which this sub-region
	 *         starts within the grid-square
	 */
	public double getRegionStartY();

	/**
	 * @return the fractional coordinate (0.0 - 1.0) at which this sub-region
	 *         ends within the grid-square
	 */
	public double getRegionEndX();

	/**
	 * @return the fractional coordinate (0.0 - 1.0) at which this sub-region
	 *         ends within the grid-square
	 */
	public double getRegionEndY();
}
