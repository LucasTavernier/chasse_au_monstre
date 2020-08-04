/**
 * 	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	Plateau
 *
 *	Cette classe est comme un plateau de jeu de société comportant des cases
 */

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Plateau extends Application{

	protected Case[][] plateau = new Case[10][10];

	Monstre perso1 = null;
	Case caseForMonstre = null;

	Chasseur perso2 = null;

	private final int TAILLE;
	int tour;

	private Random random = new Random();
	public TypeCase[] tabTypeCase = TypeCase.values();
	private Scanner scan = new Scanner(System.in);


	/***** IHM *****/
	Image imageBackgroundJeuSolo = new Image("file:../res/backgroundMenuFixe.bmp");
	BackgroundImage backgroundImageJeuSolo = new BackgroundImage(imageBackgroundJeuSolo,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundJeuSolo= new Background(backgroundImageJeuSolo);

	protected HBox hboxPlateau = new HBox();
	protected VBox vboxPlateau = new VBox();
	GridPane grillePlateau = new GridPane();

	Rectangle iconPlayer = new Rectangle();
	Label namePlayer = new Label();
	Label tourNum = new Label("Tour n°" + tour);

	Image monstreIcon = new Image("file:./res/skull.png");
	Image chasseurIcon = new Image("file:./res/weapon.png");

	Label erreur = new Label();
	Button boutonValider = new Button("VALIDER");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	Case caseForDeplacementMonstre = null;
	Case caseForTirChasseur = null;

	Stage initialisationMonstreStage = new Stage(); // à commenter pour repasser en mode texte

	Scene scenePlateau = new Scene(hboxPlateau);
	
	private boolean itsNotInitialization = false;
	
	SceneVictoire victoire = new SceneVictoire();
	
	Scene sceneVictoire = new Scene(victoire.vboxVictoire);
	
	Stage stage = null;
	
	/**************/

	/**
	 * 
	 * @param taille Taille du plateau
	 */
	public Plateau(int taille, int tour) {
		TAILLE = taille;
		plateau = new Case[TAILLE][TAILLE];
		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}
		this.tour = tour;
		this.initialiser();

		/***** IHM *****/

		hboxPlateau.setBackground(backgroundJeuSolo);
		hboxPlateau.setPrefWidth(resolutionSelectionne.getXSelected());
		hboxPlateau.setPrefHeight(resolutionSelectionne.getYSelected());
		hboxPlateau.setAlignment(Pos.TOP_LEFT);
		hboxPlateau.getChildren().addAll(grillePlateau, vboxPlateau);
		hboxPlateau.getStylesheets().add("file:./res/ProjetCss.css");

		grillePlateau.setPrefWidth(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/8.6));
		grillePlateau.setPrefHeight(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/8.6));
		grillePlateau.setTranslateX(resolutionSelectionne.getYSelected()/43);
		grillePlateau.setMaxWidth(grillePlateau.getPrefWidth());
		grillePlateau.setMaxHeight(grillePlateau.getPrefHeight());
		grillePlateau.setGridLinesVisible(true);

		vboxPlateau.setPrefWidth(resolutionSelectionne.getXSelected()-(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/8.6)));
		vboxPlateau.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxPlateau.setAlignment(Pos.TOP_RIGHT);
		vboxPlateau.getChildren().addAll(iconPlayer,tourNum, namePlayer, boutonValider, erreur);

		iconPlayer.setWidth(resolutionSelectionne.getYSelected()/5);
		iconPlayer.setHeight(resolutionSelectionne.getYSelected()/5);
		iconPlayer.setStroke(Color.DARKGRAY);
		iconPlayer.setStrokeWidth(2);

		namePlayer.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
		namePlayer.setTranslateX(-2);

		tourNum.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");
		tourNum.setTranslateX(-(resolutionSelectionne.getXSelected()-(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/8.6)))/2.5);
		tourNum.setTranslateY(-(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/1.26)));

		erreur.setPrefSize(resolutionSelectionne.getXSelected()/3.5, resolutionSelectionne.getYSelected()/16);
		erreur.setTranslateY((resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/2.2)));
		erreur.setTranslateX(-(resolutionSelectionne.getXSelected()-(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/8.6)))/12);
		erreur.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");


		boutonValider.setPrefSize(resolutionSelectionne.getXSelected()/4, resolutionSelectionne.getYSelected()/20);
		boutonValider.setTranslateY((resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/2.2)));
		boutonValider.setTranslateX(-(resolutionSelectionne.getXSelected()-(resolutionSelectionne.getYSelected()-(resolutionSelectionne.getYSelected()/8.6)))/6);
		boutonValider.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;

		boutonValider.setOnMouseEntered(e->{
			boutonValider.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		boutonValider.setOnMouseExited(e->{
			boutonValider.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		/**************/
	}

	/**
	 * Initialise toutes les cases du plateau
	 */
	public void initialiser() {

		for (int l = 0; l < plateau.length; l++) {
			for (int c = 0; c < plateau[l].length; c++) {

				if(c == 0) {
					Label chiffreLigne = new Label(""+(l+1));
					grillePlateau.add(chiffreLigne, c, l+1);
					grillePlateau.setConstraints(chiffreLigne, c, l+1, 1, 1, HPos.CENTER, null);
					if(l == 0) {
						grillePlateau.getColumnConstraints().add(new ColumnConstraints(25));
						grillePlateau.getRowConstraints().add(new RowConstraints(25));
					}
				}
				if(l == 0) {
					Label lettreColonne = new Label(""+ ((char) ('A'+c)));
					grillePlateau.add(lettreColonne, c+1, l);
					grillePlateau.setConstraints(lettreColonne, c+1, l, 1, 1, HPos.CENTER, null);
				}

				plateau[l][c] = new Case(c, l, tabTypeCase[random.nextInt(tabTypeCase.length - 2)]);
			}
		}
		for (int l = 1; l <= plateau.length; l++) {
			for (int c = 1; c <= plateau[l-1].length; c++) {
				Rectangle r = plateau[l-1][c-1].rectangle;

				r.setWidth(resolutionSelectionne.getYSelected()/(TAILLE+5));
				r.setHeight(resolutionSelectionne.getYSelected()/(TAILLE+5));


				r.setOnMouseEntered(e->{
					r.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 40, 0, 0, 0) ; -fx-cursor: hand");
				});
				r.setOnMouseExited(e->{
					if(caseForMonstre == null) {
						r.setStyle(null);
					}
					else {
						if(!caseForMonstre.rectangle.equals(r)) {
							r.setStyle(null);
						}
						else if(itsNotInitialization){
							r.setStyle(null);
						}
						else {
							r.setStyle(null);
							r.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(254,229,0,1), 40, 0, 0, 0)");
						}
					}

				});

				r.setOnMouseClicked(e->{
					if(perso1 == null) {

						if(caseForMonstre != null) {
							caseForMonstre.rectangle.setStyle(null);
						}

						for (int i = 1; i <= plateau.length; i++) {
							for (int j = 1; j <= plateau[i-1].length; j++) {
								if(plateau[i-1][j-1].rectangle.equals(r)) {
									caseForMonstre = plateau[i-1][j-1];
								}
							}
						}

						r.setStyle(null);
						r.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(254,229,0,1), 40, 0, 0, 0)");
					}
				});

				Pane paneRectangle = new Pane();
				paneRectangle.getChildren().add(r);

				grillePlateau.add(paneRectangle, c, l);
				grillePlateau.setConstraints(paneRectangle, c, l, 1, 1, HPos.CENTER, null,null,null, new Insets(1));
			}
		}
	}

	/**
	 * Initialise le monstre à la position souhaité par le joueur et lui demande son nom
	 */
	public void initialisationMonstre() {
		int posCaseDepart = -1;

		System.out.println(this.toStringMonstre("start"));
		System.out.println("Tu es le monstre !");
		System.out.println("Cela veut dire que tu dois éviter les tirs du chasseur");
		System.out.println("et aussi parcourir tout le plateau !");

		boolean saisi = false;
		do {
			try {
				System.out.println("Entre un chiffre entre 1 et " + this.plateau.length);
				System.out.print("Choisis ta case de départ: ");

				posCaseDepart = scan.nextInt() - 1;
				saisi = true;
			} catch (InputMismatchException error) {
				System.out.println("Un nombre entier est demandé entre 1 et " + this.plateau.length);
				scan.next();
			}

		} while (!saisi);

		System.out.println("Quel est ton nom ? ");
		System.out.print("→ ");
		String nom = scan.next();

		perso1 = new Monstre(this.plateau[posCaseDepart][0], nom);
		perso1.getPosition().update(TypeCase.MONSTRE);
	}

	/**
	 * @param nom : nom du monstre
	 * Initialise le monstre à la position souhaité par le joueur et lui ajoute un nom
	 */
	public void initialisationMonstreIHM(String nom) {
		initialisationMonstreStage.initModality(Modality.APPLICATION_MODAL); // à commenter pour repasser en mode texte
		initialisationMonstreStage.setTitle("Initialisation du Monstre"); // à commenter pour repasser en mode texte

		iconPlayer.setFill(new ImagePattern(monstreIcon));
		namePlayer.setText(nom + ",  à toi de jouer !");

		initialisationMonstreStage.setScene(scenePlateau); // à commenter pour repasser en mode texte

		boutonValider.setOnMouseClicked(e->{
			initialisationMonstreStage.close(); // à commenter pour repasser en mode texte
			this.itsNotInitialization = true;
			caseForMonstre.rectangle.setStyle(null);
		});

		initialisationMonstreStage.showAndWait(); // à commenter pour repasser en mode texte
		perso1 = new Monstre(caseForMonstre, nom);
		perso1.getPosition().update(TypeCase.MONSTRE);

		if(this.perso1.getPosition().ancienType == TypeCase.EAU) {
			this.perso1.getPosition().imageTypeActuel = new Image("file:./res/waterSkull.png");
			this.perso1.getPosition().rectangle.setFill(new ImagePattern(this.perso1.getPosition().imageTypeActuel));
		}
		if(this.perso1.getPosition().ancienType == TypeCase.FORET) {
			this.perso1.getPosition().imageTypeActuel = new Image("file:./res/woodSkull.png");
			this.perso1.getPosition().rectangle.setFill(new ImagePattern(this.perso1.getPosition().imageTypeActuel));
		}
		if(this.perso1.getPosition().ancienType == TypeCase.MONTAGNE) {
			this.perso1.getPosition().imageTypeActuel = new Image("file:./res/hillSkull.png");
			this.perso1.getPosition().rectangle.setFill(new ImagePattern(this.perso1.getPosition().imageTypeActuel));
		}
		if(this.perso1.getPosition().ancienType == TypeCase.PLAINE) {
			this.perso1.getPosition().imageTypeActuel = new Image("file:./res/landSkull.png");
			this.perso1.getPosition().rectangle.setFill(new ImagePattern(this.perso1.getPosition().imageTypeActuel));
		}
		this.reStringSceneMonstreIHM();
	}

	/**
	 * Initialise le chasseur et lui demande son nom
	 */
	public void initialisationChasseur() {
		System.out.println("Tu es le chasseur !");
		System.out.println("Cela veut dire que tu dois trouver le monstre");

		System.out.println("Quel est ton nom ? ");
		System.out.print("→ ");
		String nom = scan.next();

		perso2 = new Chasseur(this.plateau[random.nextInt(2) + (TAILLE / 2) - 1][random.nextInt(2) + (TAILLE / 2) - 1], nom);
	}

	/**
	 * @param nom : nom du Chasseur
	 * Initialise le chasseur et lui ajoute son nom
	 */
	public void initialisationChasseurIHM(String nom) {
		perso2 = new Chasseur(this.plateau[random.nextInt(2) + (TAILLE / 2) - 1][random.nextInt(2) + (TAILLE / 2) - 1], nom);
	}

	/**
	 * 
	 * @param nbJoueurs Nombre de joueurs réels
	 * Initialise l'autre type de personnage qui n'a pas été choisi en fonction du nombre de joueur
	 * Si il y a 0 joueur, initialise une IA de monstre et une IA de chasseur
	 * Si il y a 1 joueur, initialise une IA soit monstre, soit chasseur en fonction du choix du joueur
	 */
	public void initialisationAutrePerso(int nbJoueurs) {

		if (nbJoueurs == 0) {
			perso1 = new Monstre(plateau[random.nextInt(plateau.length)][0], "IAMONSTRE");
			perso1.getPosition().update(TypeCase.MONSTRE);

			perso2 = new Chasseur(plateau[random.nextInt(2) + (TAILLE / 2) - 1][random.nextInt(2) + (TAILLE / 2) - 1], "IACHASSEUR");
		}
		if (nbJoueurs == 1) {
			if (perso1 instanceof Monstre) {
				perso2 = new Chasseur(plateau[random.nextInt(2) + (TAILLE / 2) - 1][random.nextInt(2) + (TAILLE / 2) - 1], "IACHASSEUR");
			} else {
				perso1 = new Monstre(plateau[random.nextInt(plateau.length)][0], "IAMONSTRE");
				perso1.getPosition().update(TypeCase.MONSTRE);
			}
		} else if (nbJoueurs == 2) {
			if (perso1 instanceof Monstre) {
				this.initialisationChasseur();
			} else {
				this.initialisationMonstre();
			}
		}
	}

	/**
	 * 
	 * @param nbJoueurs :  Nombre de joueurs réels
	 * Initialise l'autre type de personnage qui n'a pas été choisi en fonction du nombre de joueur
	 * Si il y a 0 joueur, initialise une IA de monstre et une IA de chasseur
	 * Si il y a 1 joueur, initialise une IA soit monstre, soit chasseur en fonction du choix du joueur
	 */	public void initialisationAutrePersoIHM(int nbJoueurs, String nom) {
		if(nbJoueurs != 2) {
			initialisationAutrePerso(nbJoueurs);
		}
		else {
			if (perso1 instanceof Monstre) {
				this.initialisationChasseurIHM(nom);
			} else {
				this.initialisationMonstreIHM(nom);
			}
		}
	}

	/**
	 * 
	 * @param param Modifie l'affichage de la méthode
	 * @return Affiche le plateau côté monstre
	 */
	public String toStringMonstre(String param) {
		String res = "";

		for (int i = 0; i < plateau.length; i++) {
			res += "___";
		}
		res += "\n";

		for (int l = 0; l < plateau.length; l++) {
			for (int c = 0; c < plateau[l].length; c++) {
				if (param.equals("start") && c == 0) {
					res += "" + (l + 1) + '▶' + "|" + plateau[l][c].toString() + "|";
				} else {
					if (plateau[l][c].isVisitedByMonstre() && !plateau[l][c].typeActuel.equals(TypeCase.MONSTRE)) {
						res += "|" + 'V' + "|";
					} else {
						res += "|" + plateau[l][c].toString() + "|";
					}
				}
			}
			res += "\n";
		}
		for (int i = 0; i < plateau.length; i++) {
			res += "---";
		}

		return res;
	}

	/**
	 * @return Affiche le plateau côté monstre
	 */
	public String toStringMonstre() {
		return toStringMonstre("");
	}

	/**
	 * @return Affiche le plateau côté monstre
	 */
	public String reStringMonstre() {
		System.out.flush();

		return this.toStringMonstre();
	}

	/**
	 * Créé l'affichage graphique (JavaFX) pour la vue côté monstre
	 */
	public void SceneMonstre() {
		++tour;
		tourNum.setText("Tour n°" + tour);

		iconPlayer.setFill(new ImagePattern(monstreIcon));
		namePlayer.setText(perso1.getNom() + ",  à toi de jouer !");

		for (int l = 1; l <= plateau.length; l++) {
			for (int c = 1; c <= plateau[l-1].length; c++) {
				Rectangle r = plateau[l-1][c-1].rectangle;

				plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageTypeActuel));

				r.setOnMouseClicked(e->{
					for (int i = 1; i <= plateau.length; i++) {
						for (int j = 1; j <= plateau[i-1].length; j++) {

							if(plateau[i-1][j-1].rectangle.equals(r)) {
								caseForDeplacementMonstre = plateau[i-1][j-1];

								if(!caseForDeplacementMonstre.isVisitedByMonstre() || this.perso1.getPosition().rectangle == r) {
									if(perso1.isNearPosition(caseForDeplacementMonstre, this.plateau)) {
										if(this.perso1.getPosition().rectangle == r) {
											this.perso1.getPosition().back("immobile");
										}

										perso1.deplacement(deplacementMonstreIHM(caseForDeplacementMonstre), tour);
										if(!this.fini()) {
											erreur.setText("");
											this.SceneChasseur();
										}
										else {
											if (this.allCaseVisited()) {
												System.out.println("Félicitation " + this.perso1.getNom() + "!");
												System.out.println("Tu as réussi à visiter toutes les cases du plateau.");
												victoire.joueurGagnantImage = monstreIcon;
												victoire.infoGagnant.setText(this.perso1.getNom() + " tu as visité toutes les cases \n Tu as donc gagné, félicitation !");
												
											} else if(this.perso1.isStucked(this.plateau)) {
												System.out.println("Le monstre s'est bloqué (le nul !)");
												System.out.println("Tu as donc gagné " + this.perso2.getNom() + ", félicitation !");
												victoire.joueurGagnantImage = chasseurIcon;
												victoire.infoGagnant.setText(this.perso2.getNom() + ", le monstre (" +this.perso1.getNom()+" le nulos)\n s'est bloqué. \nTu as donc gagné, félicitation !");
											}else {
												System.out.println("Félicitation " + this.perso2.getNom() + "!");
												System.out.println("Tu as trouvé le monstre !");
												victoire.joueurGagnantImage = chasseurIcon;
												victoire.infoGagnant.setText(this.perso2.getNom() + " tu as trouvé " + this.perso1.getNom() +"\n Tu as donc gagné, félicitation !");
											}
											stage.setTitle("Victoire");
											stage.setScene(sceneVictoire);
										}
									}
									else {
										erreur.setText("Déplacement impossible ! Case non \naccessible.");
									}
								}
								else if(perso1.isStucked(this.plateau)) {
									perso1.getPosition().update(TypeCase.MONSTRE);
									erreur.setText("Dommage vous avez perdu car vous vous \nêtes bloqué !");
									perso1.setDecouvert();
								}
								else {
									erreur.setText("Déplacement impossible ! Case déjà visité.");
								}
							}
						}
					}
				});
			}
		}
		if(perso1.getNom().equals("IAMONSTRE")) {
			boolean errorArray = true;
			while (errorArray) {
				try {
					perso1.deplacement(deplacementMonstre(), tour);
					System.out.println(this.toStringMonstre());
					errorArray = false;

				} catch (ArrayIndexOutOfBoundsException ignored) {
					System.out.println("Déplacement impossible ! La case est hors-plateau.");
				}
			}

			if(!this.fini()) {
				this.SceneChasseur();
			}
			else {
				if (this.allCaseVisited()) {
					System.out.println("Félicitation " + this.perso1.getNom() + "!");
					System.out.println("Tu as réussi à visiter toutes les cases du plateau.");
					victoire.joueurGagnantImage = monstreIcon;
					victoire.infoGagnant.setText(this.perso1.getNom() + " tu as visité toutes les cases \n Tu as donc gagné, félicitation !");
					
				} else if(this.perso1.isStucked(this.plateau)) {
					System.out.println("Le monstre s'est bloqué (le nul !)");
					System.out.println("Tu as donc gagné " + this.perso2.getNom() + ", félicitation !");
					victoire.joueurGagnantImage = chasseurIcon;
					victoire.infoGagnant.setText(this.perso2.getNom() + ", le monstre (" +this.perso1.getNom()+" le nulos) s'est bloqué \n Tu as donc gagné, félicitation !");
				}else {
					System.out.println("Félicitation " + this.perso2.getNom() + "!");
					System.out.println("Tu as trouvé le monstre !");
					victoire.joueurGagnantImage = chasseurIcon;
					victoire.infoGagnant.setText(this.perso2.getNom() + " tu as trouvé " + this.perso1.getNom() +"\n Tu as donc gagné, félicitation !");
				}
				stage.setTitle("Victoire");
				stage.setScene(sceneVictoire);
			}
		}
	}

	/**
	 * Initialise l'affichage graphique (JavaFX) pour la vue côté chasseur
	 */
	public void SceneChasseur() {
		iconPlayer.setFill(new ImagePattern(chasseurIcon));
		namePlayer.setText(perso2.getNom() + ",  à toi de jouer !");

		this.toStringChasseurIHM("tir");
	}

	/**
	 * recrée l'affichage graphique (JavaFX) pour la vue côté monstre
	 */
	public void reStringSceneMonstreIHM() {
		for (int l = 1; l <= plateau.length; l++) {
			for (int c = 1; c <= plateau[l-1].length; c++) {
				plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageTypeActuel));

				if(plateau[l-1][c-1].typeActuel.equals(TypeCase.MONSTRE)) {
					if(plateau[l-1][c-1].ancienType.equals(TypeCase.EAU)) {
						plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/waterSkull.png")));
					}
					else if(plateau[l-1][c-1].ancienType.equals(TypeCase.FORET)) {
						plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/woodSkull.png")));
					}
					else if(plateau[l-1][c-1].ancienType.equals(TypeCase.MONTAGNE)) {
						plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/hillSkull.png")));
					}
					else if(plateau[l-1][c-1].ancienType.equals(TypeCase.PLAINE)) {
						plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/landSkull.png")));
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param param Modifie l'affichage de la méthode
	 * @return Affiche le plateau côté chasseur
	 */	
	public String toStringChasseur(String param) {
		String res = "";

		if (param.equals("tir")) {
			res += "  ";
			for (int i = 0; i < plateau.length; i++) {
				res += "___";
			}
			res += "\n  ";
			for (int i = 0; i < plateau[0].length; i++) {
				res += " " + (char) ('A' + i) + " ";
			}
			res += "\n  ";
			for (int i = 0; i < plateau[0].length; i++) {
				res += " " + '▼' + " ";
			}
		} else {
			for (int i = 0; i < plateau.length; i++) {
				res += "___";
			}
		}

		res += "\n";

		for (int l = 0; l < plateau.length; l++) {
			for (int c = 0; c < plateau[l].length; c++) {

				if (param.equals("tir")) {
					if (c == 0) {
						res += "" + (l + 1) + '▶';
					}
					if (plateau[l][c].typeActuel.equals(TypeCase.MONSTRE)) {
						res += "|" + plateau[l][c].ancienType.toStringID() + "|";
					} else {
						if (plateau[l][c].isVisitedByMonstre() && plateau[l][c].hadBeenShooted()) {
							res += "|" + plateau[l][c].getTourVisite() + "|";
						} else {
							res += "|" + plateau[l][c].toString() + "|";
						}
					}
				} else if (param.equals("tir2")) {
					if (plateau[l][c].isShooted()) {
						res += "|\uD83D\uDCA5|";
					} else {
						if (plateau[l][c].typeActuel.equals(TypeCase.MONSTRE)) {
							res += "|" + plateau[l][c].ancienType.toStringID() + "|";
						} else {
							if (plateau[l][c].isVisitedByMonstre() && plateau[l][c].hadBeenShooted()) {
								res += "|" + plateau[l][c].getTourVisite() + "|";
							} else {
								res += "|" + plateau[l][c].toString() + "|";
							}
						}
					}
				} else if (param.equals("tir3")) {
					if (plateau[l][c].isShooted()) {
						if (plateau[l][c].typeActuel.equals(TypeCase.MONSTRE)) {
							res += "|" + plateau[l][c].typeActuel.toStringID() + "|";
							perso1.setDecouvert();
						} else if (plateau[l][c].isVisitedByMonstre()) {
							res += "|" + plateau[l][c].getTourVisite() + "|";
							plateau[l][c].setBeenShooted();
						} else {
							res += "|" + plateau[l][c].typeActuel.toStringID() + "|";
						}
					} else {
						if (plateau[l][c].typeActuel.equals(TypeCase.MONSTRE)) {
							res += "|" + plateau[l][c].ancienType.toStringID() + "|";
						} else {
							if (plateau[l][c].isVisitedByMonstre() && plateau[l][c].hadBeenShooted()) {
								res += "|" + plateau[l][c].getTourVisite() + "|";
							} else {
								res += "|" + plateau[l][c].toString() + "|";
							}
						}
					}
				}
			}
			res += "\n";
		}
		for (int i = 0; i < plateau.length; i++) {
			res += "---";
		}
		return res;
	}

	/**
	 * 
	 * @return Affiche le plateau côté chasseur
	 */
	public String toStringChasseur() {
		return toStringChasseur("");
	}

	/**
	 * Créé l'affichage graphique (JavaFX) pour la vue côté chasseur
	 */
	public void toStringChasseurIHM(String param) {
		for (int l = 1; l <= plateau.length; l++) {
			for (int c = 1; c <= plateau[l-1].length; c++) {
				if(param.equals("tir")) {
					Rectangle r = plateau[l-1][c-1].rectangle;

					if (plateau[l-1][c-1].typeActuel.equals(TypeCase.MONSTRE)) {
						plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageAncienType));
					} else {
						plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].typeActuel.getImage()));
					}

					r.setOnMouseClicked(e->{
						for (int i = 1; i <= plateau.length; i++) {
							for (int j = 1; j <= plateau[i-1].length; j++) {
								if(plateau[i-1][j-1].rectangle.equals(r)) {
									caseForTirChasseur = plateau[i-1][j-1];
									perso2.tir(caseForTirChasseur, this);

									if(!this.fini()) {
										this.SceneMonstre();;
									}
									else {
										if (this.allCaseVisited()) {
											System.out.println("Félicitation " + this.perso1.getNom() + "!");
											System.out.println("Tu as réussi à visiter toutes les cases du plateau.");
											victoire.joueurGagnantImage = monstreIcon;
											victoire.infoGagnant.setText(this.perso1.getNom() + " tu as visité toutes les cases \n Tu as donc gagné, félicitation !");
											
										} else if(this.perso1.isStucked(this.plateau)) {
											System.out.println("Le monstre s'est bloqué (le nul !)");
											System.out.println("Tu as donc gagné " + this.perso2.getNom() + ", félicitation !");
											victoire.joueurGagnantImage = chasseurIcon;
											victoire.infoGagnant.setText(this.perso2.getNom() + ", le monstre (" +this.perso1.getNom()+" le nulos) s'est bloqué \n Tu as donc gagné, félicitation !");
										}else {
											System.out.println("Félicitation " + this.perso2.getNom() + "!");
											System.out.println("Tu as trouvé le monstre !");
											victoire.joueurGagnantImage = chasseurIcon;
											victoire.infoGagnant.setText(this.perso2.getNom() + " tu as trouvé " + this.perso1.getNom() +"\n Tu as donc gagné, félicitation !");
										}
										stage.setTitle("Victoire");
										stage.setScene(sceneVictoire);
									}
								}
							}
						}
					});
				}
				else if (param.equals("tir2")) {
					if (plateau[l-1][c-1].isShooted()) {
						if(plateau[l-1][c-1].ancienType.equals(TypeCase.EAU)) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/waterShoot.png")));
						}
						else if(plateau[l-1][c-1].ancienType.equals(TypeCase.FORET)) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/woodShoot.png")));
						}
						else if(plateau[l-1][c-1].ancienType.equals(TypeCase.MONTAGNE)) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/hillShoot.png")));
						}
						else if(plateau[l-1][c-1].ancienType.equals(TypeCase.PLAINE)) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/landShoot.png")));
						}
						
						if(plateau[l-1][c-1].isVisitedByMonstre() && plateau[l-1][c-1].typeActuel != TypeCase.MONSTRE) {
							Label tourCaseTrouve = new Label(""+ plateau[l-1][c-1].getTourVisite());
							tourCaseTrouve.setStyle("-fx-font-size: 40px; -fx-text-fill: #ffffff");
							grillePlateau.add(tourCaseTrouve, c, l);
							grillePlateau.setConstraints(tourCaseTrouve, c, l,1,1,HPos.CENTER, VPos.CENTER);
						}
					} else {
						if (plateau[l-1][c-1].typeActuel.equals(TypeCase.MONSTRE)) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageAncienType));
						} else {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].typeActuel.getImage()));
						}
					}
				} else if (param.equals("tir3")) {
					if (plateau[l-1][c-1].isShooted()) {
						if (plateau[l-1][c-1].typeActuel.equals(TypeCase.MONSTRE)) {
							if(plateau[l-1][c-1].ancienType.equals(TypeCase.EAU)) {
								plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/waterSkull.png")));
							}
							else if(plateau[l-1][c-1].ancienType.equals(TypeCase.FORET)) {
								plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/woodSkull.png")));
							}
							else if(plateau[l-1][c-1].ancienType.equals(TypeCase.MONTAGNE)) {
								plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/hillSkull.png")));
							}
							else if(plateau[l-1][c-1].ancienType.equals(TypeCase.PLAINE)) {
								plateau[l-1][c-1].rectangle.setFill(new ImagePattern(new Image("file:./res/landSkull.png")));
							}
							perso1.setDecouvert();
						} else if (plateau[l-1][c-1].isVisitedByMonstre()) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageTypeActuel));
							plateau[l-1][c-1].setBeenShooted();
						} else {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageTypeActuel));
						}
					} else {
						if (plateau[l-1][c-1].typeActuel.equals(TypeCase.MONSTRE)) {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].imageAncienType));
						} else {
							plateau[l-1][c-1].rectangle.setFill(new ImagePattern(plateau[l-1][c-1].typeActuel.getImage()));
						}
					}
				}
			}
		}
		if(perso2.getNom().equals("IACHASSEUR")) {
			perso2.tir(this);

			if(!this.fini()) {
				this.SceneMonstre();
			}
			else {
				if (this.allCaseVisited()) {
					System.out.println("Félicitation " + this.perso1.getNom() + "!");
					System.out.println("Tu as réussi à visiter toutes les cases du plateau.");
					victoire.joueurGagnantImage = monstreIcon;
					victoire.infoGagnant.setText(this.perso1.getNom() + " tu as visité toutes les cases \n Tu as donc gagné, félicitation !");
					
				} else if(this.perso1.isStucked(this.plateau)) {
					System.out.println("Le monstre s'est bloqué (le nul !)");
					System.out.println("Tu as donc gagné " + this.perso2.getNom() + ", félicitation !");
					victoire.joueurGagnantImage = chasseurIcon;
					victoire.infoGagnant.setText(this.perso2.getNom() + ", le monstre (" +this.perso1.getNom()+" le nulos) s'est bloqué \n Tu as donc gagné, félicitation !");
				}else {
					System.out.println("Félicitation " + this.perso2.getNom() + "!");
					System.out.println("Tu as trouvé le monstre !");
					victoire.joueurGagnantImage = chasseurIcon;
					victoire.infoGagnant.setText(this.perso2.getNom() + " tu as trouvé " + this.perso1.getNom() +"\n Tu as donc gagné, félicitation !");
				}
				stage.setTitle("Victoire");
				stage.setScene(sceneVictoire);
			}
		}
	}

	/**
	 * 
	 * @return Affiche le plateau côté chasseur
	 */
	public String reStringChasseur() {
		System.out.flush();

		return this.toStringChasseur();
	}

	/**
	 * 
	 * @return La nouvelle case du monstre
	 * Le monstre choisit où il veut se mouvoir
	 */
	public Case deplacementMonstre() {

		String deplacement = "";
		Random rand = new Random();

		if (!perso1.getNom().equals("IAMONSTRE")) {
			System.out.println("Dans quelle direction souhaites tu partir ?");
			System.out.print("→");
			deplacement = scan.next().toUpperCase();
		} else {
			String[] tabDeplacement = {"N","S","E","O","NO","NE","SO","SE","I"};
			deplacement = tabDeplacement[rand.nextInt(tabDeplacement.length)];
		}
		Case nouvelleCase = null;

		switch (deplacement) {
		case "N":
			nouvelleCase = this.plateau[perso1.getPosition().getY() - 1][perso1.getPosition().getX()];
			break;
		case "S":
			nouvelleCase = this.plateau[perso1.getPosition().getY() + 1][perso1.getPosition().getX()];
			break;
		case "E":
			nouvelleCase = this.plateau[perso1.getPosition().getY()][perso1.getPosition().getX() + 1];
			break;
		case "O":
			nouvelleCase = this.plateau[perso1.getPosition().getY()][perso1.getPosition().getX() - 1];
			break;
		case "NE":
			nouvelleCase = this.plateau[perso1.getPosition().getY() - 1][perso1.getPosition().getX() + 1];
			break;
		case "NO":
			nouvelleCase = this.plateau[perso1.getPosition().getY() - 1][perso1.getPosition().getX() - 1];
			break;
		case "SE":
			nouvelleCase = this.plateau[perso1.getPosition().getY() + 1][perso1.getPosition().getX() + 1];
			break;
		case "SO":
			nouvelleCase = this.plateau[perso1.getPosition().getY() + 1][perso1.getPosition().getX() - 1];
			break;
		case "I":
			perso1.getPosition().back("immobile");
			nouvelleCase = this.plateau[perso1.getPosition().getY() + 0][perso1.getPosition().getX() + 0];
			break;

		default:
			System.out.println("Ce déplacement n'existe pas !");
			nouvelleCase = this.deplacementMonstre();
			nouvelleCase.back("mauvaisDeplacement");
			break;

		}

		if(!perso1.isStucked(this.plateau)) {
			if (nouvelleCase.isVisitedByMonstre()) {
				System.out.println("Déplacement impossible ! Case déjà visité.");
				nouvelleCase = this.deplacementMonstre();
			} else {
				nouvelleCase.update(TypeCase.MONSTRE);
			}
		}
		else {
			perso1.getPosition().update(TypeCase.MONSTRE);
			System.out.println("Dommage vous avez perdu car vous vous êtes bloqué !");
			perso1.setDecouvert();
		}
		return nouvelleCase;
	}

	/**
	 * 
	 * @param caseChoisi : case choisi pour le déplacement du monstre
	 * @return Si toutes les conditions sont réunis ( case non visité, proche du monstre ) , déplace le monstre à la case voulue.
	 */
	public Case deplacementMonstreIHM(Case caseChoisi) {

		Case nouvelleCase = caseChoisi;

		if (perso1.getNom().equals("IAMONSTRE")) {
			nouvelleCase = deplacementMonstre();
		}
		else {
			nouvelleCase.update(TypeCase.MONSTRE);
		}

		reStringSceneMonstreIHM();
		return nouvelleCase;
	}

	/**
	 * 
	 * @return Vrai si toutes les cases ont été explorées
	 */
	public boolean allCaseVisited() {
		for (int l = 0; l < plateau.length; l++) {
			for (int c = 0; c < plateau[l].length; c++) {
				if (!plateau[l][c].isVisitedByMonstre()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @return Vrai si toutes les cases ont été visitées ou si le monstre a été décelé
	 */
	public boolean fini() {
		return allCaseVisited() || perso1.isDecouvert();
	}

	public void updateSizeOfScene(Resolutions r) {
		hboxPlateau.setPrefWidth(r.getXSelected());
		hboxPlateau.setPrefHeight(r.getYSelected());

		grillePlateau.setPrefWidth(r.getYSelected());
		grillePlateau.setPrefHeight(r.getYSelected());
		grillePlateau.setTranslateX(r.getYSelected()/43);

		vboxPlateau.setPrefWidth(r.getXSelected()-(r.getYSelected()-100));
		vboxPlateau.setPrefHeight(r.getYSelected());

		iconPlayer.setWidth(r.getYSelected()/5);
		iconPlayer.setHeight(r.getYSelected()/5);

		boutonValider.setPrefSize(r.getXSelected()/4, r.getYSelected()/20);
		boutonValider.setTranslateY((r.getYSelected()-(r.getYSelected()/2.2)));
		boutonValider.setTranslateX(-(r.getXSelected()-(r.getYSelected()-(r.getYSelected()/8.6)))/6);

		erreur.setPrefSize(r.getXSelected()/4, r.getYSelected()/20);
		erreur.setTranslateY((r.getYSelected()-(r.getYSelected()/2.2)));
		erreur.setTranslateX(-(r.getXSelected()-(r.getYSelected()-(r.getYSelected()/8.6)))/12);

		tourNum.setTranslateX(-(r.getXSelected()-(r.getYSelected()-(r.getYSelected()/8.6)))/4);
		tourNum.setTranslateY(-(r.getYSelected()-(r.getYSelected()/1.25)));
	}
	
	/**
	 * @param stage : Stage (JavaFX)
	 * paramètre un stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
