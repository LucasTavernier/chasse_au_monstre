/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneJeu
 *
 *	Cette classe affiche la pré-selection des modes de jeu.
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

public class SceneChoixJeu extends Application {
	protected VBox vboxJeu = new VBox();
	Image imageBackgroundJeu = new Image("file:./res/backgroundMenuFixe.bmp");
	BackgroundImage backgroundJeuImage = new BackgroundImage(imageBackgroundJeu,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundJeu= new Background(backgroundJeuImage);

	Button soloButton = new Button("SOLO");
	Button multiButton = new Button("MULTIJOUEUR");
	Button IAButton = new Button("IA");
	Button retourButton = new Button("RETOUR");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	/**
	 * Crée une instance de SceneChoixJeu (scène graphique JavaFX pour le choix du mode de jeu comme Solo / Multi )
	 */
	public SceneChoixJeu() {
		
		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}
		
		vboxJeu.setBackground(backgroundJeu);
		vboxJeu.setPrefWidth(resolutionSelectionne.getXSelected());
		vboxJeu.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxJeu.setAlignment(Pos.CENTER);
		vboxJeu.setSpacing(30);
		vboxJeu.getChildren().addAll(soloButton, multiButton, IAButton, retourButton);
		vboxJeu.getStylesheets().add("file:./res/ProjetCss.css");

		//Style pour le bouton solo
		soloButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		soloButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;
		soloButton.setOnMouseEntered(e->{
			soloButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		soloButton.setOnMouseExited(e->{
			soloButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		//Style pour le bouton multi
		multiButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		multiButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;
		multiButton.setOnMouseEntered(e->{
			multiButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		multiButton.setOnMouseExited(e->{
			multiButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		//Style pour le bouton IA
		IAButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		IAButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;
		IAButton.setOnMouseEntered(e->{
			IAButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		IAButton.setOnMouseExited(e->{
			IAButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		//Style pour le bouton retour
		retourButton.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		retourButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;
		retourButton.setOnMouseEntered(e->{
			retourButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		retourButton.setOnMouseExited(e->{
			retourButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});
	}
	
	/**
	 * @param r : Resolution choisi ( elle peut changer dans les options )
	 * Change la taille de tous les objets pour qu'ils soient bien adaptés à la nouvelle résolution d'écran
	 */
	public void updateSizeOfScene(Resolutions r) {
		vboxJeu.setPrefWidth(r.getXSelected());
		vboxJeu.setPrefHeight(r.getYSelected());
		
		soloButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		multiButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		IAButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		retourButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	}
}
