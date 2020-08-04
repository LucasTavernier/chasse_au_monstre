/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *	Resolutions
 *	Cette enumération permet de changer la résolution de la fenêtre de jeu.
 */

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public enum Resolutions {

	tresGrandEcran(1920,1080,"",false),grandEcran(1600,900,"",false), moyenEcran(1440,900,"",false), moyenEcran2(1280,720,"",false), petitEcran(1024,768,"",false), tresPetitEcran(800,600,"",false);

	private int x,y;
	private String recommande;
	private boolean recommanded = false;
	private boolean selected;

	//Récupère les valeurs de la taille UTILE(sans la barre de tâche etc) de l'écran
	static GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();

	//Créé un rectangle dimensionné aux valeurs récupérées avant.
	static Rectangle rectangle = graphicsEnvironment.getMaximumWindowBounds();

	/**
	 * 
	 * @param x : largeur de la résolution
	 * @param y : hauteur de la résolution 
	 * @param a : Affiche soit rien soit (recommandé) si la résolution est celle recommandée pour l'utilisateur
	 * @param selected
	 */
	private Resolutions(int x, int y, String a, boolean selected) {
		//Récupère les valeurs de la taille UTILE(sans la barre de tâche etc) de l'écran
		GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();

		//Créé un rectangle dimensionné aux valeurs récupérées avant.
		Rectangle rectangle = graphicsEnvironment.getMaximumWindowBounds();

		this.x = x;
		this.y = y;
		this.selected = selected;

		if(this.x == (int) rectangle.getWidth()) {
			recommande = " (Recommandé)";
			recommanded = true;
			this.selected = true;
		}
		else if(this.x == 1920 && (int) rectangle.getWidth() >= 1920) { //Si on a un écran plus grand que 1920px en largeur,	
			recommande = " (Recommandé)";								//la taille recommandé est quand même 1920
			recommanded = true;
			this.selected = true;
		}
		else {
			this.recommande = a;
		}
	}

	/**
	 * @return Indique si la résolution est recommandé ou non (un écran 1600*900 aura la résolution grandEcran en recommandé)
	 */
	public boolean isRecommanded() {
		return recommanded;
	}

	/**
	 * @return Indique si la résolution est celle choisi par l'utilisateur.
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * 
	 * @return la largeur de la résolution ( tresGrandEcran(1920,1080,"",false) -> 1920 )
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return la hauteur de la résolution ( tresGrandEcran(1920,1080,"",false) -> 1080 )
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return Retourne la largeur UTILE de l'écran (la largeur sans la barre des tâches par exemple si elle est mise à droite/gauche de l'écran)
	 */
	public static int getPrefX() {
		return (int) rectangle.getWidth();
	}

	/**
	 * @return Retourne la hauteur UTILE de l'écran (la hauteur sans la barre des tâches par exemple)
	 */
	public static int getPrefY() {
		return (int) rectangle.getHeight();
	}

	/**
	 * @return Retourne la largeur de la résolution selectionné.
	 */
	public int getXSelected() {
		if(this.isSelected()) {
			if(this.getX() > Resolutions.getPrefX()) {
				return getPrefX();
			}
			else {
				return this.getX();
			}
		}
		return this.getX();
	}

	/**
	 * @return Retourne la hauteur de la résolution selectionné.
	 */
	public int getYSelected() {
		if(this.isSelected()) {
			if(this.getY() > Resolutions.getPrefY()) {
				return getPrefY();
			}
			else {
				return this.getY();
			}
		}
		return this.getX();

	}

	/**
	 * @return Affiche la résolution sous une meilleure forme ( tresGrandEcran devient : 1920x1080 + (recommandé) si c'est la résolution recommandé ) 
	 */
	public String toStringRes() {
		return x + "x" + y + recommande;
	}

	/**
	 * Déselectionne une résolution
	 */
	public void setUnselected() {
		this.selected = false;
	}

	/**
	 * Selectionne une résolution
	 */
	public void setSelected() {
		this.selected = true;
	}

}
