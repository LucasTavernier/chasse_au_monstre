import javafx.scene.image.Image;

/**
 * 	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *  
 *  TypeCase
 *  
 *  Cette enumération contient les différentes types de case possible.
 */

import javafx.scene.image.Image;

/**
 * @author Arnaud Madou
 *	
 *	Projet Java 
 *	14 avril 2019
 *  
 *  Enum Type Case
 */
public enum TypeCase {

	FORET("Forêt","file:./res/wood.png"),
	PLAINE("Plaine","file:./res/land.png"),
	EAU("Eau","file:./res/water.png"),
	MONTAGNE("Montagne","file:./res/hill.png"),
	MONSTRE(""+'☠',"file:./res/skull.png"),
	CHASSEUR(""+'☺',"file:./res/weapon.png");

	private final String NAME;
	private final char ID;
	private final Image image;

	/**
	 * 
	 * @param name Nom du type de la case
	 */
	TypeCase(String name, String urlImage) {
		this.NAME=name;
		this.ID = name.charAt(0);
		this.image = new Image(urlImage);
	}
	
	/**
	 * @return La première lettre du nom du type de la case
	 */
	public char toStringID() {
		return this.ID;
	}

	/**
	 * @return Retourne l'image associé au type de la case
	 */
	public Image getImage() {
		return this.image;
	}

}
