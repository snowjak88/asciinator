package org.snowjak.asciinator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.snowjak.asciinator.subdivision.GridSquareSubdivisionScheme;
import org.snowjak.asciinator.subdivision.Subdivision2x2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;

@SuppressWarnings("javadoc")
public class ConsoleApp extends Application {

	private static Random RND = new Random();

	public static void main(String[] args) {

		ConsoleApp.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Font font = new Font("Courier New", 32d);
		Class<? extends GridSquareSubdivisionScheme> subdivisionScheme = Subdivision2x2.class;

		for (String s : asciinate(
				new Image("image-2016-09-07.png"), 6, 12, font,
				subdivisionScheme, CharacterSet.instantiate(CharacterSet.DEFAULT_CHARACTERS, font, subdivisionScheme),
				0.25, 3)) {
			System.out.println(s);
		}

		Platform.exit();
	}

	private static List<String> asciinate(Image sourceImage, int sourceGridSquarePixelWidth,
			int sourceGridSquarePixelHeight, Font font, Class<? extends GridSquareSubdivisionScheme> subdivisionScheme,
			CharacterSet characterSet, double imageGamma, int finalCharacterSelectionPoolSize) {

		if (sourceImage.isError())
			throw new RuntimeException(
					"Cannot ASCII-inate this image! -- " + sourceImage.getException().getClass().getName() + ": "
							+ sourceImage.getException().getMessage());

		List<String> result = new LinkedList<>();

		double imageMinimumLuminance = Double.MAX_VALUE, imageMaximumLuminance = 0d;

		System.out.println("Pre-processing the image ...");
		PixelReader sourceImageReader = sourceImage.getPixelReader();
		for (int imageX = 0; imageX < sourceImage.getWidth(); imageX++)
			for (int imageY = 0; imageY < sourceImage.getHeight(); imageY++) {

				Color pixelColor = sourceImageReader.getColor(imageX, imageY);

				double grayscaleLuminance = 1d - (Math.pow(pixelColor.getRed(), imageGamma) * 0.2126d
						+ Math.pow(pixelColor.getGreen(), imageGamma) * 0.7152d
						+ Math.pow(pixelColor.getBlue(), imageGamma) * 0.0722d);
				grayscaleLuminance *= pixelColor.getOpacity();

				imageMinimumLuminance = Math.min(imageMinimumLuminance, grayscaleLuminance);
				imageMaximumLuminance = Math.max(imageMaximumLuminance, grayscaleLuminance);
			}

		System.out.println("Finding the ASCII-inated version of the image ...");
		for (int imageY = 0; imageY < sourceImage.getHeight(); imageY += sourceGridSquarePixelHeight) {

			StringBuilder currentLine = new StringBuilder();

			for (int imageX = 0; imageX < sourceImage.getWidth(); imageX += sourceGridSquarePixelWidth) {

				int endImageX = imageX + sourceGridSquarePixelWidth;
				int endImageY = imageY + sourceGridSquarePixelHeight;

				Coverage imageCoverage = new Coverage(sourceImage, imageX, imageY, endImageX, endImageY,
						subdivisionScheme, imageGamma);

				List<Pair<Double, Character>> distancedCharacters = new LinkedList<>();
				for (Character c : characterSet.getCharacters()) {

					double distance = 0;

					for (Entry<? extends GridSquareSubdivisionScheme, Double> characterSubdividedCoverage : c
							.getCoverage().getSubdivisionCoverage().entrySet()) {

						if (imageCoverage.getSubdivisionCoverage().containsKey(characterSubdividedCoverage.getKey())) {

							double characterCoverageValue = (characterSubdividedCoverage.getValue()
									/ (characterSet.getMaxCoverage() - characterSet.getMinCoverage()))
									+ characterSet.getMinCoverage();

							double imageCoverageValue = (imageCoverage.getSubdivisionCoverage()
									.get(characterSubdividedCoverage.getKey())
									/ (imageMaximumLuminance - imageMinimumLuminance)) + imageMinimumLuminance;

							distance += Math.pow(characterCoverageValue - imageCoverageValue, 2d);
						}

					}

					distancedCharacters.add(new Pair<>(distance, c));
				}

				distancedCharacters = distancedCharacters.stream()
						.sorted((p1, p2) -> Double.compare(p1.getKey(), p2.getKey()))
						.collect(Collectors.toCollection(LinkedList::new));

				currentLine.append(distancedCharacters
						.get(RND.nextInt(Math.min(distancedCharacters.size(), finalCharacterSelectionPoolSize)))
						.getValue()
						.getCharacter());
			}

			result.add(currentLine.toString());
		}

		return result;
	}

}
