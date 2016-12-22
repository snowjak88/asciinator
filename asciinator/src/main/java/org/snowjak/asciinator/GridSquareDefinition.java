package org.snowjak.asciinator;

/**
 * Defines how much space a single grid-square takes up. The grid-square is the
 * basic unit of ASCII-art: it defines the space that a single ASCII-character
 * will represent.
 * 
 * @author snowjak88
 *
 */
public class GridSquareDefinition {

	private final int sourcePixelWidth, sourcePixelHeight;

	private final int finalPixelWidth, finalPixelHeight;

	/**
	 * Construct a new GridSquareDefinition.
	 * 
	 * @param sourcePixelWidth
	 *            the grid-square width, as measured against the source image
	 * @param sourcePixelHeight
	 *            the grid-square height, as measured against the source image
	 * @param finalPixelWidth
	 *            the grid-square width, as measured against the final image
	 * @param finalPixelHeight
	 *            the grid-square height, as measured against the final image
	 */
	public GridSquareDefinition(int sourcePixelWidth, int sourcePixelHeight, int finalPixelWidth,
			int finalPixelHeight) {
		this.sourcePixelWidth = sourcePixelWidth;
		this.sourcePixelHeight = sourcePixelHeight;
		this.finalPixelWidth = finalPixelWidth;
		this.finalPixelHeight = finalPixelHeight;
	}

	@SuppressWarnings("javadoc")
	public int getSourcePixelWidth() {

		return sourcePixelWidth;
	}

	@SuppressWarnings("javadoc")
	public int getSourcePixelHeight() {

		return sourcePixelHeight;
	}

	@SuppressWarnings("javadoc")
	public int getFinalPixelWidth() {

		return finalPixelWidth;
	}

	@SuppressWarnings("javadoc")
	public int getFinalPixelHeight() {

		return finalPixelHeight;
	}

}
