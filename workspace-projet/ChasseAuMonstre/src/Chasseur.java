/**
 *	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *	Chasseur
 *  Cette classe contient les fonctions permettant au chasseur de tirer
 */

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Chasseur extends Personnage {
	/**
	 * 
	 * @param caseDepart  Donner la case de départ
	 * @param nom Donner un nom au chasseur
	 */
	public Chasseur(Case caseDepart, String nom) {
		super(caseDepart, nom);
	}
	
	/**
	 * 
	 * @param plateau Plateau du jeu
	 * L'utilisateur rentre une case composée de deux coordonnées et cette case se fait tirer dessus dans le plateau 
	 */
	public void tir(Plateau plateau) {
		Scanner scan = new Scanner(System.in);

		System.out.println(plateau.toStringChasseur("tir"));

		boolean errorArray = true;
		Case caseShooted = null;

		if(!super.getNom().equals("IACHASSEUR")) {
			while (errorArray) {
				try {
					System.out.println(this.getNom() + " Choisis une case sur laquelle tirer");
					System.out.println("Choisis la case sous la forme : 'A,1' par exemple");
					System.out.print("→ ");
					String tir = scan.next();

					int nbEltScan = 0;
					int x = 0;
					int y = 0,d=0;

					char a=' ';
					while (nbEltScan < 2) {


						Scanner s = new Scanner(tir).useDelimiter(",");
						try{
							a = s.next().charAt(0);
							a = Character.toUpperCase(a);
						}catch(LetterException e){
							System.err.println("Seulement les lettres sont autorisées en premier caractère");
						}
						try{
							d = s.nextInt();                        
						}catch(NumberFormatException e) {
							System.err.println("Seulement les nombres sont autorisées en second caractère");
						}
						try {
							x = (int) (a - 'A');                        
							y = d;                                             
							nbEltScan = 2;
						} catch (NoSuchElementException ignored) {
							System.out.println("Votre réponse est incomplète");
							System.out.println("Rappel: ");
							System.out.println("→ Ecrivez sous la forme : 'A,1' par exemple");
						}
						s.close();
					}

					caseShooted = plateau.plateau[y - 1][x];
					caseShooted.setShooted();

					errorArray = false;

				} 

				catch(InputMismatchException e) {
					System.err.println("D'abord une lettre ensuite un nombre");
				}
				catch (ArrayIndexOutOfBoundsException ignored) {
					System.err.println("Tir impossible ! La case est hors-plateau.");
				}
			}
		}
		else {
			int x = 0;
			int y = 0;
			Random rand = new Random();

			x = rand.nextInt(plateau.plateau.length);
			y = rand.nextInt(plateau.plateau.length);

			caseShooted = plateau.plateau[y][x];
			caseShooted.setShooted();
		}

		if(!this.getNom().equals("IACHASSEUR")) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println(plateau.toStringChasseur("tir2"));
		if(!this.getNom().equals("IACHASSEUR")) {
			try {
				Thread.sleep(600);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println(plateau.toStringChasseur("tir3"));
		if(!this.getNom().equals("IACHASSEUR")) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		caseShooted.setUnShooted();

	}

	/**
	 * 
	 * @param caseTir : la case choisi pour le tir
	 * @param plateau : Plateau du jeu
	 */
	public void tir(Case caseTir, Plateau plateau) {

		if(!super.getNom().equals("IACHASSEUR")) {
			Case caseShooted = caseTir;

			caseShooted.setShooted();

			plateau.toStringChasseurIHM("tir2");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			plateau.toStringChasseurIHM("tir3");

			try {
				Thread.sleep(1000);	
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			caseShooted.setUnShooted();
		}
		else {
			this.tir(plateau);
		}
	}
}
