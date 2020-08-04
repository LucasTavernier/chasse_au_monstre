/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	Personnage
 *
 *	Cette classe contient les différentes actions du Monstre et du Chasseur
 */

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * @author tavernil
 * 
 * 19 avril 2019
 *
 */
public class Personnage {

	private Case position;
	private String nom;

	/**
	 * 
	 * @param positionDepart Donner la position de départ du joueur
	 * @param nom Donner un nom au joueur
	 */
	public Personnage(Case positionDepart, String nom) {
		this.position = positionDepart;
		this.nom = nom;
	}

	/**
	 * 
	 * @return La position du joueur
	 */
	public Case getPosition() {
		return position;
	}

	/**
	 * 
	 * @param nouvellePosition Change la case du personnage
	 * @param tourVisite Donne le tour où le monstre est passé
	 */
	public void deplacement(Case nouvellePosition, int tourVisite){
		this.position.setTourVisite(tourVisite);
		if(!(this.position == nouvellePosition)) {

			this.position.back();

			if(this.position.typeActuel == TypeCase.EAU) {
				this.position.imageTypeActuel = new Image("file:./res/waterVisited.png");
			}
			if(this.position.typeActuel == TypeCase.FORET) {
				this.position.imageTypeActuel = new Image("file:./res/woodVisited.png");
			}
			if(this.position.typeActuel == TypeCase.MONTAGNE) {
				this.position.imageTypeActuel = new Image("file:./res/hillVisited.png");
			}
			if(this.position.typeActuel == TypeCase.PLAINE) {
				this.position.imageTypeActuel = new Image("file:./res/landVisited.png");
			}

			this.position = nouvellePosition;

			if(this.position.ancienType == TypeCase.EAU) {
				this.position.imageTypeActuel = new Image("file:./res/waterSkull.png");
			}
			if(this.position.ancienType == TypeCase.FORET) {
				this.position.imageTypeActuel = new Image("file:./res/woodSkull.png");
			}
			if(this.position.ancienType == TypeCase.MONTAGNE) {
				this.position.imageTypeActuel = new Image("file:./res/hillSkull.png");
			}
			if(this.position.ancienType == TypeCase.PLAINE) {
				this.position.imageTypeActuel = new Image("file:./res/landSkull.png");
			}
		}
		else {
			if(this.position.ancienType == TypeCase.EAU) {
				this.position.imageTypeActuel = new Image("file:./res/waterSkull.png");
			}
			if(this.position.ancienType == TypeCase.FORET) {
				this.position.imageTypeActuel = new Image("file:./res/woodSkull.png");
			}
			if(this.position.ancienType == TypeCase.MONTAGNE) {
				this.position.imageTypeActuel = new Image("file:./res/hillSkull.png");
			}
			if(this.position.ancienType == TypeCase.PLAINE) {
				this.position.imageTypeActuel = new Image("file:./res/landSkull.png");
			}
		}
	}
	/**
	 * 
	 * @return Renvoie le nom du personnage
	 */
	public String getNom() {
		return nom;
	}
}
