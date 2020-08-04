import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * 	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *	14 avr. 2019
 * 	Case
 *  cette classe est un objet possédant des coordonnées x,y. Elle représente une case comme dans un jeu de societé
 */
public class Case extends Coord2D {

	protected TypeCase ancienType;
	protected TypeCase typeActuel;

	Image imageAncienType;
	Image imageTypeActuel;

	Rectangle rectangle = new Rectangle();
	private boolean rectangleClicked = false;

	private boolean visitedByMonstre = false;
	private boolean shooted = false;
	private boolean BeenShooted = false;
	private int tourVisite;
	
	/**
	 * @param x Les coordonnées en ordonnées 
	 * @param y	Les coordonnées en abscisses
	 * @param type Le type de la case
	 */
	public Case(int x, int y, TypeCase type) {
		super(x, y);
		this.ancienType = type;
		this.typeActuel = type;
		imageTypeActuel = type.getImage();
		this.rectangle.setFill(new ImagePattern(imageTypeActuel));
	}

	public String toString(){
		return ""+typeActuel.toStringID();
	}
	/**
	 * @param nouveauType Donne un nouveau type à la case
	 * Et met la case en visitée
	 */
	public void update(TypeCase nouveauType){
		this.setVisited();
		this.ancienType = typeActuel;
		imageAncienType = imageTypeActuel;
		typeActuel = nouveauType;
		imageTypeActuel = nouveauType.getImage();
	}
	/**
	 * Redonne l'ancien type à la case
	 */
	public void back(){
		TypeCase tmp = ancienType;
		Image tmpImage = ancienType.getImage();

		this.ancienType = typeActuel;
		imageAncienType = typeActuel.getImage();

		typeActuel = tmp;
		imageTypeActuel = tmpImage;
	}
	/**
	 * 
	 * @param problem Type d'erreur
	 * Version annexe de back()
	 */
	public void back(String problem){
		if (problem.equals("")){
			this.back();
		}
		else if (problem.equals("mauvaisDeplacement")){
			this.back();
			this.setUnvisited();
		}
		else if(problem.equals("immobile")) {
			this.typeActuel = this.ancienType;
			imageTypeActuel = this.imageAncienType;
			this.setUnvisited();
		}
	}
	/**
	 * Rend la case visitée
	 */
	public void setVisited() {
		this.visitedByMonstre = true;
	}
	/**
	 * Rend la case non-visitée
	 */
	public void setUnvisited() {
		this.visitedByMonstre = false;
	}
	/**
	 * Indique que la case vient d'être visé par le chasseur ce tour-ci
	 */
	public void setShooted() {
		this.shooted = true;
	}
	/**
	 * Indique à la case si elle a deja été visé par le chasseur
	 */
	public void setBeenShooted() {
		this.BeenShooted = true;

	}
	/**
	 * Indique que la case n'a pas encore été visé par le chasseur
	 */
	public void setUnShooted() {
		this.shooted = false;
	}
	/**
	 * 
	 * @return Si la case a ou non été visitée par le monstre
	 */
	public boolean isVisitedByMonstre() {
		return visitedByMonstre;
	}
	/**
	 * 
	 * @return Si la case est visée par le chasseur au tour actuel
	 */
	public boolean isShooted() {
		return shooted;
	}
	/**
	 * 
	 * @return Le numéro du tour où le monstre a visité la case
	 */
	public int getTourVisite() {
		return tourVisite;
	}
	/**
	 * 
	 * @param tourVisite Indique à quel tour le monstre à visité la case
	 */
	public void setTourVisite(int tourVisite) {
		this.tourVisite = tourVisite;
	}
	/**
	 * 
	 * @return Indique si la case a ou non deja été visé
	 */
	public boolean hadBeenShooted() {
		return BeenShooted;
	}

	/**
	 * 
	 * @return Indique si le rectangle lié à la case (voir JavaFX) est sélectionné ou non
	 */
	public boolean isRectangleClicked() {
		return rectangleClicked;
	}

	/**
	 * 
	 * @param rectangleClicked Passe le rectangle en "cliqué"
	 */
	public void setRectangleClicked(boolean rectangleClicked) {
		this.rectangleClicked = rectangleClicked;
	}
}
