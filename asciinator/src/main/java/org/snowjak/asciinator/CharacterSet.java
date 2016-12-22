package org.snowjak.asciinator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.snowjak.asciinator.subdivision.GridSquareSubdivisionScheme;

import javafx.scene.text.Font;

/**
 * Defines a set of {@link Character}s we can use when generating ASCII art.
 * 
 * @author snowjak88
 *
 */
public class CharacterSet {

	/**
	 * The default character set.
	 */
	public final static char[] DEFAULT_CHARACTERS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '0', ' ', '_', '-', '=', '*', '+', '/', '\\', '|', '!', '@', '#',
			'$', '%', '^', '&', '(', ')', '<', '>', '?', ',', '.', ';', ':', '\'', '"', '`', '~', '[', ']', '{', '}' };

	/**
	 * Construct a new CharacterSet from the given array of <code>char</code>
	 * values, using the given {@link Font} and
	 * {@link GridSquareSubdivisionScheme} to calculate character coverage
	 * values.
	 * 
	 * @param chars
	 *            the array of {@code char} values which will constitute the
	 *            {@link Character}s in this CharacterSet
	 * @param font
	 *            the JavaFX {@link Font} used to calculate Character coverage
	 * @param subdivisionScheme
	 *            the {@link GridSquareSubdivisionScheme} used to calculate
	 *            Character coverage
	 * @return the instantiated CharacterSet
	 */
	public static CharacterSet instantiate(char[] chars, Font font,
			Class<? extends GridSquareSubdivisionScheme> subdivisionScheme) {

		Character[] characterInstances = new Character[chars.length];

		for (int i = 0; i < chars.length; i++) {
			characterInstances[i] = new Character(chars[i], font, subdivisionScheme);
		}

		return new CharacterSet(characterInstances);
	}

	private final Set<Character> characters;

	/**
	 * Construct a new CharacterSet from the given collection of
	 * {@link Character} instances.
	 * 
	 * @param characters
	 *            the {@link Character}s which constitute the new CharacterSet
	 */
	public CharacterSet(Character... characters) {
		this.characters = new HashSet<>();
		this.characters.addAll(Arrays.asList(characters));
	}

	/**
	 * @return the collection of {@link Character}s constituting this
	 *         CharacterSet
	 */
	public Set<Character> getCharacters() {

		return characters;
	}
}
