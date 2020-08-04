/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneMenu
 *
 *	Cette classe affiche le menu du jeu
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneMenu extends Application{

	protected VBox vboxMenu = new VBox();
	Image imageBackgroundMenu = new Image("file:./res/image.gif");
	BackgroundImage backgroundMenuImage = new BackgroundImage(imageBackgroundMenu,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundMenu= new Background(backgroundMenuImage);

	Button jouerButton = new Button("JOUER");
	Button reglesButton = new Button("RÈGLES");
	Button quitterButton = new Button("QUITTER");
	Button optionsButton = new Button("OPTIONS");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;
	
	/**
	 * Crée une instance de SceneChoixJeu (scène graphique JavaFX pour choisir une action comme aller dans les options, lire les règles, jouer ou bien quitter)
	 */
	public SceneMenu() {
		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}
		
		vboxMenu.setBackground(backgroundMenu);
		vboxMenu.setPrefWidth(resolutionSelectionne.getXSelected());
		vboxMenu.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxMenu.setAlignment(Pos.CENTER);
		vboxMenu.setSpacing(30);
		vboxMenu.getChildren().addAll(jouerButton, reglesButton, optionsButton, quitterButton);
		//Je donne un fichier css à ma scène pour qu'elle connaisse les différents styles applicables à elle et ses enfants.
		vboxMenu.getStylesheets().add("file:./res/ProjetCss.css");

		//Style pour le bouton jouer
		jouerButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		jouerButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;

		jouerButton.setOnMouseEntered(e->{
			jouerButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		jouerButton.setOnMouseExited(e->{
			jouerButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		//Style pour le bouton règles
		reglesButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		reglesButton.getStyleClass().add("ButtonNormal"); 

		reglesButton.setOnMouseEntered(e->{
			reglesButton.getStyleClass().add("ButtonHoverOn"); 
		});
		reglesButton.setOnMouseExited(e->{
			reglesButton.getStyleClass().remove(2);
		});

		//Style pour le bouton options
		optionsButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		optionsButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;

		optionsButton.setOnMouseEntered(e->{
			optionsButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		optionsButton.setOnMouseExited(e->{
			optionsButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		//Style pour le bouton quitter
		quitterButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		quitterButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;

		quitterButton.setOnMouseEntered(e->{
			quitterButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		quitterButton.setOnMouseExited(e->{
			quitterButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});
	}

	/**
	 * @param r : Resolution choisi ( elle peut changer dans les options )
	 * Change la taille de tous les objets pour qu'ils soient bien adaptés à la nouvelle résolution d'écran
	 */
	public void updateSizeOfScene(Resolutions r) {		
		vboxMenu.setPrefWidth(r.getXSelected());
		vboxMenu.setPrefHeight(r.getYSelected());
		
		jouerButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		reglesButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		optionsButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		quitterButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}
