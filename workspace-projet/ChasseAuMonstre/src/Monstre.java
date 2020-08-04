/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *	
 *	Monstre
 *
 *	Cette classe contient les différentes informations pour le monstre (Déplacement,Découvert,Bloqué)
 */

public class Monstre extends Personnage {

	private boolean decouvert;
	/**
	 * 
	 * @param caseDepart  Donner la case de départ
	 * @param nom Donner un nom au monstre
	 */
	public Monstre(Case caseDepart, String nom){
		super(caseDepart,nom);
		this.decouvert = false;
	}
	/**
	 * Change l'état si le monstre a été découvert
	 */
	public void setDecouvert() {
		this.decouvert = true;
	}
	/**
	 * 
	 * @return Permet de savoir si le monstre a été ou non découvert
	 */
	public boolean isDecouvert() {
		return decouvert;
	}

	/**
	 * 
	 * @param p : plateau ( tableau de case )
	 * @return indique si oui ou non le monstre s'est bloqué.
	 */
	public boolean isStucked(Case[][] p ) {
		boolean stuck = true;
		int xMonstre = this.getPosition().getX();
		int yMonstre = this.getPosition().getY();

		if(xMonstre+1 < p.length) {
			if(!p[yMonstre][xMonstre+1].isVisitedByMonstre()) {
				stuck = false;
			}
		}

		if(xMonstre-1 < p.length && xMonstre-1 >= 0) {
			if(!p[yMonstre][xMonstre-1].isVisitedByMonstre()) {
				stuck = false;
			}
		}

		if(yMonstre+1 < p.length) {
			if(!p[yMonstre+1][xMonstre].isVisitedByMonstre()) {
				stuck = false;
			}
		}

		if(yMonstre-1 < p.length && yMonstre-1 >= 0) {
			if(!p[yMonstre-1][xMonstre].isVisitedByMonstre()) {
				stuck = false;
			}
		}


		if(xMonstre+1 < p.length && yMonstre+1 < p.length) {
			if(!p[yMonstre+1][xMonstre+1].isVisitedByMonstre()) {
				stuck = false;
			}
		}

		if((xMonstre-1 < p.length && xMonstre-1 >= 0) && (yMonstre-1 < p.length && yMonstre-1 >= 0)) {
			if(!p[yMonstre-1][xMonstre-1].isVisitedByMonstre()) {
				stuck = false;
			}
		}

		if(xMonstre+1 < p.length && (yMonstre-1 < p.length && yMonstre-1 >= 0)) {
			if(!p[yMonstre-1][xMonstre+1].isVisitedByMonstre()) {
				stuck = false;
			}
		}

		if((xMonstre-1 < p.length && xMonstre-1 >= 0 ) && yMonstre+1 < p.length) {
			if(!p[yMonstre+1][xMonstre-1].isVisitedByMonstre()) {
				stuck = false;
			}
		}
		return stuck;
	}
	
	/**
	 * 
	 * @param nouvellePositionMonstre : la nouvelle case choisi pour le déplacement du monstre
	 * @param p : plateau ( tableau de cases )
	 * @return indique si oui ou non la case où souhaite aller le monstre est bien à côté de là où il se trouve avant le déplacement
	 */
	boolean isNearPosition(Case nouvellePositionMonstre, Case[][] p) {
		boolean near = false;
		int xMonstre = this.getPosition().getX();
		int yMonstre = this.getPosition().getY();
		
		if(p[yMonstre][xMonstre] == nouvellePositionMonstre) {
			near = true;
		}
		
		if(xMonstre+1 < p.length) {
			if(p[yMonstre][xMonstre+1] == nouvellePositionMonstre) {
				near = true;
			}
		}

		if(xMonstre-1 < p.length && xMonstre-1 >= 0) {
			if(p[yMonstre][xMonstre-1] == nouvellePositionMonstre) {
				near = true;
			}
		}

		if(yMonstre+1 < p.length) {
			if(p[yMonstre+1][xMonstre] == nouvellePositionMonstre) {
				near = true;
			}
		}

		if(yMonstre-1 < p.length && yMonstre-1 >= 0) {
			if(p[yMonstre-1][xMonstre] == nouvellePositionMonstre) {
				near = true;
			}
		}


		if(xMonstre+1 < p.length && yMonstre+1 < p.length) {
			if(p[yMonstre+1][xMonstre+1] == nouvellePositionMonstre) {
				near = true;
			}
		}

		if((xMonstre-1 < p.length && xMonstre-1 >= 0) && (yMonstre-1 < p.length && yMonstre-1 >= 0)) {
			if(p[yMonstre-1][xMonstre-1] == nouvellePositionMonstre) {
				near = true;
			}
		}

		if(xMonstre+1 < p.length && (yMonstre-1 < p.length && yMonstre-1 >= 0)) {
			if(p[yMonstre-1][xMonstre+1] == nouvellePositionMonstre) {
				near = true;
			}
		}

		if((xMonstre-1 < p.length && xMonstre-1 >= 0 ) && yMonstre+1 < p.length) {
			if(p[yMonstre+1][xMonstre-1] == nouvellePositionMonstre) {
				near = true;
			}
		}
		return near;
	}
}
