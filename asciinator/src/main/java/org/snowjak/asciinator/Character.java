package org.snowjak.asciinator;

import org.snowjak.asciinator.subdivision.GridSquareSubdivisionScheme;
import org.snowjak.asciinator.subdivision.Subdivision1x1;

import javafx.scene.text.Font;

/**
 * Defines a single ASCII character.
 * 
 * @author snowjak88
 *
 */
public class Character {

	private char character;

	private Coverage coverage;

	/**
	 * Construct a new Character.
	 * 
	 * @param character
	 *            the <code>char</code> this Character represents
	 * @param coverage
	 *            {@code character}'s calculated {@link Coverage}
	 */
	public Character(char character, Coverage coverage) {
		this.character = character;
		this.coverage = coverage;
	}

	/**
	 * Construct a new Character, using the default size-12 {@link Font} and
	 * {@link Subdivision1x1}.
	 * 
	 * @param character
	 *            the <code>char</code> this Character represents
	 */
	public Character(char character) {

		this(character, new Font(12.0), Subdivision1x1.class);
	}

	/**
	 * Construct a new Character.
	 * 
	 * @param character
	 *            the <code>char</code> this Character represents
	 * @param font
	 *            the JavaFX {@link Font} to use when calculating this
	 *            Character's coverage
	 * @param subdivisionScheme
	 *            the enum implementing {@link GridSquareSubdivisionScheme},
	 *            defining the subdivision scheme to use when calculating this
	 *            Character's coverage
	 */
	public Character(char character, Font font, Class<? extends GridSquareSubdivisionScheme> subdivisionScheme) {
		this.character = character;
		this.coverage = new Coverage(character, font, subdivisionScheme, 1.0);
	}

	@SuppressWarnings("javadoc")
	public char getCharacter() {

		return character;
	}

	@SuppressWarnings("javadoc")
	public Coverage getCoverage() {

		return coverage;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + character;
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Character))
			return false;
		Character other = (Character) obj;
		if (character != other.character)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Character ['" + character + "', coverage=" + coverage + "]";
	}

}
