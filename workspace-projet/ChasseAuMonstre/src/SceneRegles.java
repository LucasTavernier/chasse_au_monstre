/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneRegles
 *
 *	Cette classe contient et affiche les différentes règles du jeu.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneRegles extends Application{

	protected VBox vboxRegles = new VBox();
	Image imageBackgroundRegles = new Image("file:./res/backgroundMenuFixe.bmp");
	BackgroundImage backgroundReglesImage = new BackgroundImage(imageBackgroundRegles,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundRegles= new Background(backgroundReglesImage);
	Button retourButton = new Button("RETOUR");

	VBox vboxForLabels = new VBox();
	Label msg  = new Label ("But du Jeu");
	Label msg1 = new Label ("\nSur un plateau initialisé préalablement, un monstre se déplace et un chasseur tire sur des cases chacun leur tour.");
	Label msg2 = new Label ("Le monstre doit passer sur toutes les cases du plateau alors que le chasseur lui doit trouver le monstre \n\t\t\t\t\t\tavant que ce dernier accomplisse son but.");
	Label msg3 = new Label ("\nDeroulement d'une partie.");
	Label msg4 = new Label ("Le ou les joueurs choisissent combien de joueurs vont jouer (0 si ils veulent regarder 2 IA s'affronter : \n\t\t\t\t1 si ils veulent jouer contre l'IA : 2 si ils veulent jouer ensemble.");
	Label msg5 = new Label ("\nEnsuite la taille du plateau est choisi par l'utilisateur.");
	Label msg6 = new Label ("\nPuis l'attribution des rôles est effectué.");
	Label msg7 = new Label ("\nLe monstre choisit sa case de départ et son nom");
	Label msg8 = new Label ("\nPuis le chasseur choisit son nom");
	Label msg9 = new Label ("\nLa partie débute, le monstre se déplace puis le chasseur tire et ainsi de suite à tour de rôles \n\t\t\t\t    jusqu'à ce que soit le chasseur ait trouvé le monstre \n\t\t\t\t\tsoit que le montre ait découvert toute la carte.");

	DropShadow dropShadow = new DropShadow(10, Color.BLACK);

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	/**
	 * Crée une instance de SceneChoixJeu (scène graphique JavaFX pour les règles du jeu )
	 */
	public SceneRegles() {

		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}

		vboxRegles.setBackground(backgroundRegles);
		vboxRegles.setPrefWidth(resolutionSelectionne.getXSelected());
		vboxRegles.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxRegles.setAlignment(Pos.TOP_CENTER);
		vboxRegles.getChildren().addAll(vboxForLabels,retourButton);
		vboxRegles.getStylesheets().add("file:./res/ProjetCss.css");

		vboxForLabels.setPrefWidth(resolutionSelectionne.getXSelected()*0.65);
		vboxForLabels.setPrefHeight(resolutionSelectionne.getYSelected()*0.60);
		vboxForLabels.getChildren().addAll(msg,msg1,msg2,msg3,msg4,msg5,msg6,msg7,msg8,msg9);
		vboxForLabels.setAlignment(Pos.CENTER);
		vboxForLabels.setTranslateY((resolutionSelectionne.getYSelected())*0.1);
		vboxForLabels.setStyle("-fx-font-size: " +resolutionSelectionne.getYSelected()*0.02 + "");
		vboxForLabels.setEffect(dropShadow);

		retourButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		retourButton.setTranslateY((resolutionSelectionne.getYSelected())*0.25);
		retourButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;

		retourButton.setOnMouseEntered(e->{
			retourButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		retourButton.setOnMouseExited(e->{
			retourButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		msg.setStyle("-fx-text-fill: white");
		msg.setEffect(dropShadow);
		msg1.setStyle("-fx-text-fill: white");
		msg1.setEffect(dropShadow);
		msg2.setStyle("-fx-text-fill: white");
		msg2.setEffect(dropShadow);
		msg3.setStyle("-fx-text-fill: white");
		msg3.setEffect(dropShadow);
		msg4.setStyle("-fx-text-fill: white");
		msg4.setEffect(dropShadow);
		msg5.setStyle("-fx-text-fill: white");
		msg5.setEffect(dropShadow);
		msg6.setStyle("-fx-text-fill: white");
		msg6.setEffect(dropShadow);
		msg7.setStyle("-fx-text-fill: white");
		msg7.setEffect(dropShadow);
		msg8.setStyle("-fx-text-fill: white");
		msg8.setEffect(dropShadow);
		msg9.setStyle("-fx-text-fill: white");
		msg9.setEffect(dropShadow);
	}

	/**
	 * @param r : Resolution choisi ( elle peut changer dans les options )
	 * Change la taille de tous les objets pour qu'ils soient bien adaptés à la nouvelle résolution d'écran
	 */
	public void updateSizeOfScene(Resolutions r) {
		vboxRegles.setPrefWidth(r.getXSelected());
		vboxRegles.setPrefHeight(r.getYSelected());
		vboxForLabels.setPrefWidth(r.getXSelected()*0.65);
		vboxForLabels.setPrefHeight(r.getYSelected()*0.60);
		vboxForLabels.setTranslateY((r.getYSelected())*0.1);
		vboxForLabels.setStyle("-fx-font-size: " +r.getYSelected()*0.02 + "");
		
		retourButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		retourButton.setTranslateY((r.getYSelected())*0.25);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}



