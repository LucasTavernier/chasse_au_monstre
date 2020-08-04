/**
 * 	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	Coord2D
 *
 *	Cette contient les coordonnées des différentes cases du Plateau
 */
public class Coord2D {
	private int x;
	private int y;
	
	/**
	 * 
	  * @param x Les coordonnées en ordonnées 
	  * @param y Les coordonnées en abscisses
	 */
	public Coord2D(int x, int y) {
		this.x=x;
		this.y=y;		
	}
	/**
	 * 
	 * @return Renvoie la coordonnée en ordonné
	 */
	public int getX() {
		return x;
	}
	/**
	 * 
	 * @return Renvoie la coordonnée en abscisse
	 */
	public int getY() {
		return y;
	}
}
