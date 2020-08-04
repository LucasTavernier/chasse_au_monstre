/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	MainGame
 *
 *	Cette classe permet de lancer le jeu
 */

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainGame extends Application{

	private static int nbjoueurs = -1;
	private static int tour = 0;
	private static int taillePlateau = 0;
	private static String choixPerso1 = "";
	private static Scanner scan = new Scanner(System.in);
	int i = 0;
	int posDepart;
	boolean fullscreen;
	static Plateau plateau = null;

	boolean tourMonstreDone = false;
	boolean tourChasseurDone = false;


	public static void main(String[] args) {

		Application.launch();

		System.out.println("1. Jouer" + "\n" );
		System.out.println("2. Règles" + "\n");
		System.out.println("3. Quitter" + "\n");

		System.out.print("→ ");
		int choix = scan.nextInt();

		switch (choix) {
		case 1:
			initialisationJeu();
			break;
		case 2:
			System.out.println("Voici les règles: ");
			System.out.println("> Si vous êtes le monstre, vous devez visiter toutes les cases du plateau");
			System.out.println("Pour cela, au moment voulu, vous devrez indiquer la direction que vous souhaitez prendre");
			System.out.println("Vous avez le choix entre N / S / E / O / NE / NO / SE / SO pour respectivement 'nord', 'sud' ... 'nord-est' ...");
			System.out.println("Soyez stratégique afin que le chasseur ne puisse pas vous trouver, et attention à ne pas vous bloquer vous même !");
			System.out.println("Car vous ne pouvez pas revenir sur une case où vous êtes déjà passé." + "\n");

			System.out.println("> Si vous êtes le chasseur, vous devez trouver le monstre avant qu'il ne visite toutes les cases.");
			System.out.println("Pour cela, vous disposez d'un fusil et d'une balle par tour que vous pouvez utiliser afin de tirer sur une case du plateau.");
			System.out.println("Vous devrez pour ce faire écrire la case choisi sous la forme 'A,1' par exemple." + "\n");

			System.out.println("Que voulez vous faire à présent ?" + "\n");
			System.out.println("1. Jouer" + "\n");
			System.out.println("2. Quitter");
			System.out.print("→ ");
			int choix2 = scan.nextInt();

			switch (choix2) {
			case 1:
				initialisationJeu();
				break;
			case 2:
				System.exit(0);
				break;
			}

			break;
		case 3:
			System.exit(0);
			break;
		}

		plateau = new Plateau(taillePlateau, tour);
		if (nbjoueurs > 0) {
			if (choixPerso1.equals("monstre")) {
				plateau.initialisationMonstre();
			} else {
				plateau.initialisationChasseur();
			}
		}
		System.out.println(" ");
		System.out.println(" ");

		plateau.initialisationAutrePerso(nbjoueurs);

		System.out.println("Le monstre commence !");


		while (!plateau.fini()) {
			++tour;

			System.out.println("Tour n°" + tour);

			boolean errorArray = true;

			System.out.println(plateau.perso1.getNom() + ", à toi de jouer !");
			while (errorArray) {
				try {
					System.out.println(plateau.reStringMonstre());
					plateau.perso1.deplacement(plateau.deplacementMonstre(), tour);
					errorArray = false;

				} catch (ArrayIndexOutOfBoundsException ignored) {
					System.out.println("Déplacement impossible ! La case est hors-plateau.");
				}
			}
			System.out.println(plateau.reStringMonstre());

			errorArray = true;

			System.out.println(plateau.perso2.getNom() + ", à toi de jouer !");
			while (errorArray) {
				try {
					plateau.perso2.tir(plateau);
					errorArray = false;

				} catch (ArrayIndexOutOfBoundsException ignored) {
					System.out.println("Déplacement impossible ! La case est hors-plateau.");
				}
			}
		}

		if (plateau.allCaseVisited()) {
			System.out.println("Félicitation " + plateau.perso1.getNom() + "!");
			System.out.println("Tu as réussi à visiter toutes les cases du plateau.");
		} else if(plateau.perso1.isStucked(plateau.plateau)) {
			System.out.println("Le monstre s'est bloqué (le nul !)");
			System.out.println("Tu as donc gagné " + plateau.perso2.getNom() + ", félicitation !");
		}else {
			System.out.println("Félicitation " + plateau.perso2.getNom() + "!");
			System.out.println("Tu as trouvé le monstre !");
		}
	}


	/**
	 * initialise le jeu (taille du plateau, nombre de joueurs ...)
	 */
	public static void initialisationJeu() {
		System.out.println("Bienvenue dans le jeu Chasse Au Monstre");

		System.out.println("Combien y a-t-il de joueurs ?");
		while (nbjoueurs < 0 || nbjoueurs > 2) {
			System.out.println("Entre 0, 1 ou 2");
			System.out.print("→ ");
			nbjoueurs = scan.nextInt();
		}

		System.out.println("Sur un plateau de combien de cases voulez-vous jouer ? ( minimum 3 cases, maximum 26 cases )");
		while (taillePlateau < 3 || taillePlateau > 26) {
			System.out.println("Taille du plateau ?");
			System.out.print("→ ");

			boolean saisi = false;
			do {
				try {
					taillePlateau = scan.nextInt();
					saisi = true;
				} catch (InputMismatchException ime) {
					System.out.println("Veuillez entrer un chiffre supérieur à 3 et inférieur à 27");
					scan.next();
				}

			} while (!saisi);
		}
		if (nbjoueurs > 0) {
			System.out.println("Joueur 1, qui veux tu jouer, le monstre ou le chasseur ?");
			while ((!choixPerso1.equals("monstre")) && (!choixPerso1.equals("chasseur"))) {
				System.out.println(" 'monstre' ou 'chasseur' ");
				System.out.print("→ ");
				choixPerso1 = scan.next();
			}
		}
	}

	public void start(Stage stage) {

		SceneMenu menu = new SceneMenu();

		SceneRegles regles = new SceneRegles();

		SceneChoixJeu jeu = new SceneChoixJeu();

		SceneJeuSolo jeuSolo = new SceneJeuSolo();

		SceneJeuMulti jeuMulti = new SceneJeuMulti();

		Scene sceneMenu = new Scene(menu.vboxMenu);

		Scene sceneRegles = new Scene(regles.vboxRegles);

		Scene sceneJeu = new Scene(jeu.vboxJeu);

		Scene sceneJeuSolo = new Scene(jeuSolo.vboxJeuSolo);

		Scene sceneJeuMulti = new Scene(jeuMulti.vboxJeuMulti);

		//Créé une musique
		//final File file = new File("res/soundTrackGame.mp3");
		//final Media media = new Media(file.toURI().toString());
		//final MediaPlayer mediaPlayer = new MediaPlayer(media);

		/*------------ SCENE POUR LES MENU -------------*/
		menu.jouerButton.setOnMouseClicked(e->{
			stage.setTitle("Jeu");
			stage.setScene(sceneJeu);
			stage.setFullScreen(fullscreen);
		});
		menu.reglesButton.setOnMouseClicked(e->{ //change le contenu de la fenêtre
			stage.setTitle("Règles");
			stage.setScene(sceneRegles);
			stage.setFullScreen(fullscreen);
		});

		menu.quitterButton.setOnMouseClicked(e->{
			stage.close();
		});

		/*------------ SCENE POUR LE JEU-------------*/

		//Permet de revenir au menu depuis le jeu
		jeu.retourButton.setOnMouseClicked(e -> {
			stage.setTitle("Menu");
			stage.setScene(sceneMenu);
			stage.setFullScreen(fullscreen);
		});

		jeu.soloButton.setOnMouseClicked(e->{
			stage.setTitle("Solo");
			stage.setScene(sceneJeuSolo);
			stage.setFullScreen(fullscreen);
		});

		jeu.multiButton.setOnMouseClicked(e->{
			stage.setTitle("Multijoueur");
			stage.setScene(sceneJeuMulti);
			stage.setFullScreen(fullscreen);
		});

		jeu.IAButton.setOnMouseClicked(e->{
			Random rand = new Random();
			this.nbjoueurs = 0;
			this.taillePlateau = rand.nextInt(24)+3;
			plateau = new Plateau(taillePlateau, tour);
			plateau.setStage(stage);

			plateau.victoire.retourButton.setOnMouseClicked(event->{
				tour = 0;

				plateau = null;
				stage.setMaximized(false);
				stage.setX(-8);
				stage.setY(0);
				stage.setTitle("Menu");
				stage.setScene(sceneMenu);
			});

			++tour;

			plateau.tourNum.setText("Tour n°" + tour);

			plateau.initialisationAutrePerso(nbjoueurs);

			if(plateau != null) {

				plateau.tourNum.setText("Tour n°" + tour);

				stage.setScene(plateau.scenePlateau);
				plateau.SceneMonstre();


			}
		});

		/*------------ SCENE POUR LE JEU-SOLO-------------*/

		jeuSolo.taillePlateauSlider.valueProperty().addListener(e->{
			jeuSolo.valueSliderTaillePlateau.setText("" + (int) jeuSolo.taillePlateauSlider.getValue());
		});

		//Permet de revenir au menu depuis les règles
		jeuSolo.retourButton.setOnMouseClicked(e -> {
			stage.setTitle("Jeu");
			stage.setScene(sceneJeu);
			stage.setFullScreen(fullscreen);
		});

		jeuSolo.validerButton.setOnMouseClicked(e -> {
			if((!jeuSolo.choixPerso.equals("")) && (!jeuSolo.pseudoTF.getText().equals(""))) {
				this.nbjoueurs = 1;
				this.choixPerso1 = jeuSolo.choixPerso;
				this.taillePlateau = Integer.parseInt(jeuSolo.valueSliderTaillePlateau.getText());
				plateau = new Plateau(taillePlateau, tour);
				plateau.setStage(stage);
				plateau.victoire.retourButton.setOnMouseClicked(event->{
					plateau = null;
					stage.setMaximized(false);
					stage.setX(-8);
					stage.setY(0);
					stage.setTitle("Menu");
					stage.setScene(sceneMenu);
				});

				++tour;

				plateau.tourNum.setText("Tour n°" + tour);

				if (choixPerso1.equals("monstre")) {
					plateau.initialisationMonstreIHM(jeuSolo.pseudoTF.getText());
				} else {
					plateau.initialisationChasseurIHM(jeuSolo.pseudoTF.getText());
				}
				plateau.initialisationAutrePersoIHM(nbjoueurs, "");
			}


			if(plateau != null) {

				plateau.tourNum.setText("Tour n°" + tour);

				plateau.SceneMonstre();
				stage.setScene(plateau.scenePlateau);

			}
		});

		/*------------ SCENE POUR LE JEU-SOLO-------------*/

		jeuMulti.taillePlateauSlider.valueProperty().addListener(e->{
			jeuMulti.valueSliderTaillePlateau.setText("" + (int) jeuMulti.taillePlateauSlider.getValue());
		});

		//Permet de revenir au menu depuis les règles
		jeuMulti.retourButton.setOnMouseClicked(e -> {
			stage.setTitle("Jeu");
			stage.setScene(sceneJeu);
			stage.setFullScreen(fullscreen);
		});

		jeuMulti.validerButton.setOnMouseClicked(e -> {

			if(!jeuMulti.pseudoTF1.getText().equals("") && !jeuMulti.pseudoTF2.getText().equals("")){
				this.nbjoueurs = 2;

				jeuMulti.nomPerso1 = jeuMulti.pseudoTF1.getText();
				jeuMulti.nomPerso2 = jeuMulti.pseudoTF2.getText();

				this.taillePlateau = Integer.parseInt(jeuMulti.valueSliderTaillePlateau.getText());
				plateau = new Plateau(taillePlateau, tour);
				plateau.setStage(stage);
				plateau.victoire.retourButton.setOnMouseClicked(event->{
					plateau = null;
					stage.setMaximized(false);
					stage.setX(-8);
					stage.setY(0);
					stage.setTitle("Menu");
					stage.setScene(sceneMenu);
				});

				++tour;

				plateau.tourNum.setText("Tour n°" + tour);

				jeuMulti.choixPerso();

				jeuMulti.validerButton.setOnMouseClicked(event ->{
					if(!jeuMulti.choixPerso1.equals("")) {

						this.choixPerso1 = jeuMulti.choixPerso1;

						if (choixPerso1.equals("monstre")) {
							plateau.initialisationMonstreIHM(jeuMulti.pseudoTF1.getText());
						} else {
							plateau.initialisationChasseurIHM(jeuMulti.pseudoTF1.getText());
						}
						plateau.initialisationAutrePersoIHM(nbjoueurs, jeuMulti.pseudoTF2.getText());
					}

					if(plateau != null) {

						plateau.tourNum.setText("Tour n°" + tour);

						plateau.SceneMonstre();
						stage.setScene(plateau.scenePlateau);

					}
				});
			}

		});


		/*------------ SCENE POUR LES OPTIONS -------------*/

		Stage optionsStage = new Stage();
		optionsStage.initModality(Modality.APPLICATION_MODAL);

		SceneOptions options = new SceneOptions();

		Scene sceneOptions = new Scene(options.vboxOptions);

		options.quitterButtonForOptions.setOnMouseClicked(e->{
			optionsStage.close(); //ferme la fenêtre options
		});

		//Detecte quand on choisi une nouvelle résolution dans la liste, et effectue des changements.
		options.comboBox.valueProperty().addListener((ChangeListener<Resolutions>) (ov, oldVal, newVal) -> {
			if(!fullscreen) {

				oldVal.setUnselected();
				newVal.setSelected();

				stage.setWidth(newVal.getXSelected()+16);
				stage.setHeight(newVal.getYSelected()+39);
				stage.setX((Resolutions.getPrefX()-stage.getWidth())/2);

				optionsStage.setWidth(newVal.getXSelected()-84);
				optionsStage.setHeight(newVal.getYSelected()-61);
				optionsStage.setX((Resolutions.getPrefX()-optionsStage.getWidth())/2);

				menu.updateSizeOfScene(newVal);
				regles.updateSizeOfScene(newVal);
				options.updateSizeOfScene(newVal);
				jeu.updateSizeOfScene(newVal);
				jeuSolo.updateSizeOfScene(newVal);
				if(plateau != null) {
					plateau.updateSizeOfScene(newVal);
				}
				jeuMulti.updateSizeOfScene(newVal);
			}
		});

		//Pour la musique
		options.sliderMusic.setValue(50);
		//mediaPlayer.setVolume(options.sliderMusic.getValue()/100);
		options.textValueVolume.setText(""+(int) options.sliderMusic.getValue());

		// Détecte le mouvement du slider pour récupérer sa nouvelle donnée.
		options.sliderMusic.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				//mediaPlayer.setVolume(options.sliderMusic.getValue()/100);
				options.textValueVolume.setText(""+(int) options.sliderMusic.getValue());
			}
		});

		options.checkboxMusic.setOnMouseClicked(e->{
			if(options.checkboxMusic.isSelected()) {
				//mediaPlayer.setMute(true);
			}
			else {
				//mediaPlayer.setMute(false);
				//mediaPlayer.stop();
				//mediaPlayer.play();
			}
		});

		options.checkboxFullscreen.setOnMouseClicked(e->{
			if(options.checkboxFullscreen.isSelected()) {
				fullscreen = true;
				stage.setFullScreen(true);
				optionsStage.setAlwaysOnTop(true);
			}
			else {
				fullscreen = false;
				stage.setFullScreen(false);
				optionsStage.setAlwaysOnTop(false);
			}
		});



		/*------------ ON REVIENT DANS MENU -------------*/

		menu.optionsButton.setOnMouseClicked(e->{
			optionsStage.setTitle("Options");
			optionsStage.setScene(sceneOptions);

			GaussianBlur blur = new GaussianBlur(100); //va venir appliquer un effet de flou dans la scène en dessous
			menu.vboxMenu.setEffect(blur);
			optionsStage.showAndWait();
		});

		optionsStage.setOnHiding(e->{
			menu.vboxMenu.setEffect(null); // enlève l'effet de flou quand on quitte les options
		});


		/*------------ SCENE POUR LES REGLES-------------*/

		//Permet de revenir au menu depuis les règles
		regles.retourButton.setOnMouseClicked(e -> {
			stage.setTitle("Menu");
			stage.setScene(sceneMenu);
			stage.setFullScreen(fullscreen);
		});

		/*----------------------------------------------*/

		stage.setMaximized(false);
		stage.setX(-8);
		stage.setY(0);
		stage.setTitle("Menu");
		stage.setScene(sceneMenu);
		stage.show();
		//mediaPlayer.play();

	}
}