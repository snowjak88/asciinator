package org.snowjak.asciinator;

import java.util.HashMap;
import java.util.Map;

import org.snowjak.asciinator.subdivision.GridSquareSubdivisionScheme;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Defines how much of a single grid-square a particular ASCII character covers.
 * 
 * @author snowjak88
 *
 */
public class Coverage {

	private final Class<? extends GridSquareSubdivisionScheme> subdivisionScheme;

	private final Map<GridSquareSubdivisionScheme, Double> subdivisionCoverage;

	private final double overallCoverage;

	private double gamma;

	/**
	 * Calculate the coverage given by the specified character.
	 * 
	 * @param character
	 *            an ASCII character
	 * @param font
	 *            JavaFX {@link Font} specifying which font to use when
	 *            performing coverage calculations
	 * @param subdivisionScheme
	 *            the enum implementing {@link GridSquareSubdivisionScheme},
	 *            which defines the subdivision-scheme we'll be using
	 * @param gamma
	 *            gamma with which to pre-process the image's colors
	 */
	public Coverage(char character, Font font, Class<? extends GridSquareSubdivisionScheme> subdivisionScheme,
			double gamma) {

		this.gamma = gamma;

		Pane textPane = new Pane();
		textPane.setBackground(new Background(new BackgroundFill(new Color(1d, 1d, 1d, 0d), null, null)));

		Text text = new Text("" + character);
		text.setFont(font);
		text.setFill(Color.BLACK);
		textPane.getChildren().add(text);

		SnapshotParameters snapshotParams = new SnapshotParameters();
		snapshotParams.setFill(new Color(1d, 1d, 1d, 0d));
		Image textImage = textPane.snapshot(snapshotParams, null);

		PixelReader imageReader = textImage.getPixelReader();

		this.overallCoverage = calculateCoverage(imageReader, 0, 0, (int) Math.round(textImage.getWidth()),
				(int) Math.round(textImage.getHeight()));

		if (subdivisionScheme.getEnumConstants() == null)
			throw new IllegalArgumentException(
					"Cannot instantiate a Coverage instance with a GridSquareSubdivisionScheme that is not itself an enum!");

		subdivisionCoverage = new HashMap<>();
		for (GridSquareSubdivisionScheme s : subdivisionScheme.getEnumConstants()) {
			int startX = (int) Math.round(textImage.getWidth() * s.getRegionStartX());
			int startY = (int) Math.round(textImage.getHeight() * s.getRegionStartY());
			int endX = (int) Math.round(textImage.getWidth() * s.getRegionEndX());
			int endY = (int) Math.round(textImage.getHeight() * s.getRegionEndY());

			subdivisionCoverage.put(s, calculateCoverage(imageReader, startX, startY, endX, endY));
		}

		this.subdivisionScheme = subdivisionScheme;
	}

	/**
	 * Calculate the coverage of a particular region on an image.
	 * 
	 * @param image
	 *            the image for which to calculate coverage
	 * @param startX
	 *            region's start-coordinate
	 * @param startY
	 *            region's start-coordinate
	 * @param endX
	 *            region's end-coordinate
	 * @param endY
	 *            region's end-coordinate
	 * @param subdivisionScheme
	 *            the subdivision-scheme in-use
	 * @param gamma
	 *            gamma with which to pre-process the image's colors
	 */
	public Coverage(Image image, int startX, int startY, int endX, int endY,
			Class<? extends GridSquareSubdivisionScheme> subdivisionScheme, double gamma) {

		this.gamma = gamma;

		PixelReader imageReader = image.getPixelReader();

		this.overallCoverage = calculateCoverage(imageReader, startX, startY, endX, endY);

		if (subdivisionScheme.getEnumConstants() == null)
			throw new IllegalArgumentException(
					"Cannot instantiate a Coverage instance with a GridSquareSubdivisionScheme that is not itself an enum!");

		subdivisionCoverage = new HashMap<>();
		for (GridSquareSubdivisionScheme s : subdivisionScheme.getEnumConstants()) {
			int subdivisionStartX = (int) Math.round(((double) endX - (double) startX) * s.getRegionStartX()) + startX;
			int subdivisionStartY = (int) Math.round(((double) endY - (double) startY) * s.getRegionStartY()) + startY;
			int subdivisionEndX = (int) Math.round(((double) endX - (double) startX) * s.getRegionEndX()) + startX;
			int subdivisionEndY = (int) Math.round(((double) endY - (double) startY) * s.getRegionEndY()) + startY;

			subdivisionCoverage.put(s, calculateCoverage(imageReader, subdivisionStartX, subdivisionStartY,
					subdivisionEndX, subdivisionEndY));
		}

		this.subdivisionScheme = subdivisionScheme;
	}

	private double calculateCoverage(PixelReader pixelReader, int startX, int startY, int endX, int endY) {

		double aggregateLuminance = 0;
		int pixelsSampled = 0;

		for (int x = startX; x < endX; x++)
			for (int y = startY; y < endY; y++) {

				Color pixelColor = pixelReader.getColor(x, y);
				double grayscaleLuminance = 1d - (Math.pow(pixelColor.getRed(), gamma) * 0.2126d
						+ Math.pow(pixelColor.getGreen(), gamma) * 0.7152d
						+ Math.pow(pixelColor.getBlue(), gamma) * 0.0722d);

				grayscaleLuminance *= pixelColor.getOpacity();

				aggregateLuminance += grayscaleLuminance;
				pixelsSampled++;

			}

		return aggregateLuminance / ((double) pixelsSampled);
	}

	/**
	 * Calculate the fractional coverage (between 0.0 and 1.0) which some
	 * character affords to a single grid-square.
	 * 
	 * @return the fraction of coverage afforded
	 */
	public double getCoverage() {

		return overallCoverage;
	}

	/**
	 * Calculate the fractional coverage for each sub-region defined by the
	 * {@link GridSquareSubdivisionScheme} given when this Coverage instance was
	 * constructed.
	 * 
	 * @return the fractional coverage per sub-region
	 */
	public Map<GridSquareSubdivisionScheme, Double> getSubdivisionCoverage() {

		return subdivisionCoverage;
	}

	/**
	 * @return the subdivision scheme we used when calculating this Coverage
	 *         instance
	 */
	public Class<? extends GridSquareSubdivisionScheme> getSubdivisionScheme() {

		return subdivisionScheme;
	}
}
