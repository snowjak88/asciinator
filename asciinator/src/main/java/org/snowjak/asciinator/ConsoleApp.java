package org.snowjak.asciinator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.snowjak.asciinator.subdivision.GridSquareSubdivisionScheme;
import org.snowjak.asciinator.subdivision.Subdivision3x3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
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
		Class<? extends GridSquareSubdivisionScheme> subdivisionScheme = Subdivision3x3.class;

		for (String s : asciinate(new Image("googlelogo_color_272x92dp.png"), 4, 6, font, subdivisionScheme,
				CharacterSet.instantiate(CharacterSet.DEFAULT_CHARACTERS, font, subdivisionScheme), 1d, 3)) {
			System.out.println(s);
		}

		Platform.exit();
	}

	private static List<String> asciinate(Image sourceImage, int sourceGridSquarePixelWidth,
			int sourceGridSquarePixelHeight, Font font, Class<? extends GridSquareSubdivisionScheme> subdivisionScheme,
			CharacterSet characterSet, double imageGamma, int finalCharacterSelectionPoolSize) {

		List<String> result = new LinkedList<>();

		for (int imageY = 0; imageY < sourceImage.getHeight(); imageY += sourceGridSquarePixelHeight) {

			StringBuilder currentLine = new StringBuilder();

			for (int imageX = 0; imageX < sourceImage.getWidth(); imageX += sourceGridSquarePixelWidth) {

				int endImageX = Math.min((int) sourceImage.getWidth(), imageX + sourceGridSquarePixelWidth);
				int endImageY = Math.min((int) sourceImage.getHeight(), imageY + sourceGridSquarePixelHeight);

				Coverage imageCoverage = new Coverage(sourceImage, imageX, imageY, endImageX, endImageY,
						subdivisionScheme, imageGamma);

				List<Pair<Double, Character>> distancedCharacters = new LinkedList<>();
				for (Character c : characterSet.getCharacters()) {

					double distance = 0;

					distance += Math.pow(c.getCoverage().getCoverage() - imageCoverage.getCoverage(), 2d);

					for (Entry<? extends GridSquareSubdivisionScheme, Double> characterSubdividedCoverage : c
							.getCoverage().getSubdivisionCoverage().entrySet()) {

						if (imageCoverage.getSubdivisionCoverage().containsKey(characterSubdividedCoverage.getKey())) {
							double characterCoverageValue = characterSubdividedCoverage.getValue();
							double imageCoverageValue = imageCoverage.getSubdivisionCoverage()
									.get(characterSubdividedCoverage.getKey());
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
