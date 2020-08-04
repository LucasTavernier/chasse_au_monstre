/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneJeuMulti
 *
 *	Cette classe affiche le jeu en mode multijoueur ( 2 joueurs ) 
 */

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneJeuMulti extends Application{

	protected VBox vboxJeuMulti = new VBox();
	protected HBox hboxJeuMulti = new HBox();
	Image imageBackgroundJeuMulti = new Image("file:./res/backgroundMenuFixe.bmp");
	BackgroundImage backgroundImageJeuMulti = new BackgroundImage(imageBackgroundJeuMulti,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundJeuMulti= new Background(backgroundImageJeuMulti);

	GridPane grilleJeuMulti = new GridPane();

	Label taillePlateauLabel = new Label("Choisis la taille de ton plateau");
	Slider taillePlateauSlider = new Slider();
	Label valueSliderTaillePlateau = new Label();

	Image monstreImage = new Image("file:./res/Monster.png");
	ImageView monstreImageView = new ImageView(monstreImage);
	Pane paneForMonstreImage = new VBox();

	Label pseudoLabel1 = new Label("Joueur 1, comment veux-tu que je t'appelle ?");
	TextField pseudoTF1 = new TextField();
	
	Label pseudoLabel2 = new Label("Joueur 2, comment veux-tu que je t'appelle ?");
	TextField pseudoTF2 = new TextField();

	Image chasseurImage = new Image("file:./res/Hunter.png");
	ImageView chasseurImageView = new ImageView(chasseurImage);
	Pane paneForChasseurImage = new VBox();

	Button retourButton = new Button("RETOUR");
	Button validerButton = new Button("VALIDER");
	Button validerButton2 = new Button("VALIDER");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	String choixPerso1 = "";
	String nomPerso1 = "";
	String choixPerso2 = "";
	String nomPerso2 = "";
	
	Label information = new Label();

	/**
	 * Crée une instance de SceneChoixJeu (scène graphique JavaFX pour le mode de jeu Multi )
	 */
	public SceneJeuMulti() {

		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}

		paneForMonstreImage.getChildren().add(monstreImageView);
		paneForMonstreImage.setPrefSize(resolutionSelectionne.getXSelected()*0.15, resolutionSelectionne.getYSelected()*0.60);

		paneForChasseurImage.getChildren().add(chasseurImageView);
		paneForChasseurImage.setPrefSize(resolutionSelectionne.getXSelected()*0.15, resolutionSelectionne.getYSelected()*0.60);

		vboxJeuMulti.setBackground(backgroundJeuMulti);
		vboxJeuMulti.setPrefWidth(resolutionSelectionne.getXSelected());
		vboxJeuMulti.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxJeuMulti.setAlignment(Pos.CENTER);
		vboxJeuMulti.getChildren().addAll(grilleJeuMulti, hboxJeuMulti);
		vboxJeuMulti.getStylesheets().add("file:./res/ProjetCss.css");

		hboxJeuMulti.setPrefWidth(resolutionSelectionne.getXSelected());
		hboxJeuMulti.setPrefHeight(resolutionSelectionne.getYSelected()*0.20);
		hboxJeuMulti.getChildren().addAll(retourButton,validerButton);
		hboxJeuMulti.setAlignment(Pos.CENTER);
		hboxJeuMulti.setSpacing(20);

		grilleJeuMulti.setPrefSize(resolutionSelectionne.getXSelected()*0.80, (resolutionSelectionne.getYSelected()*0.80));
		grilleJeuMulti.setPadding(new Insets(10));
		grilleJeuMulti.setVgap(resolutionSelectionne.getYSelected()/57.333); 
		grilleJeuMulti.setHgap(0); 
		grilleJeuMulti.setAlignment(Pos.CENTER);
		grilleJeuMulti.setStyle("-fx-font-size: 20px");
		grilleJeuMulti.setGridLinesVisible(false);

		grilleJeuMulti.add(pseudoLabel1,1,2);
		grilleJeuMulti.setConstraints(pseudoLabel1, 1, 2, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleJeuMulti.add(pseudoTF1,1,3);
		grilleJeuMulti.setConstraints(pseudoTF1, 1, 3, 1, 1, HPos.CENTER, VPos.TOP);
		
		grilleJeuMulti.add(pseudoLabel2,1,5);
		grilleJeuMulti.setConstraints(pseudoLabel2, 1, 5, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleJeuMulti.add(pseudoTF2,1,6);
		grilleJeuMulti.setConstraints(pseudoTF2, 1, 6, 1, 1, HPos.CENTER, VPos.TOP);
		
		
		grilleJeuMulti.add(taillePlateauLabel,1,8);
		grilleJeuMulti.setConstraints(taillePlateauLabel, 1, 8, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleJeuMulti.add(taillePlateauSlider,1,9);
		grilleJeuMulti.setConstraints(taillePlateauSlider, 1, 9, 1, 1, HPos.CENTER, VPos.TOP);
		grilleJeuMulti.add(valueSliderTaillePlateau,1,9);
		grilleJeuMulti.setConstraints(valueSliderTaillePlateau, 1, 9, 1, 1, HPos.CENTER, VPos.BOTTOM);

		//Style pour le slider
		taillePlateauSlider.setPrefSize(resolutionSelectionne.getXSelected()/2, resolutionSelectionne.getYSelected()/20);
		taillePlateauSlider.setMin(3);
		taillePlateauSlider.setMax(26);
		taillePlateauSlider.setShowTickMarks(true);
		taillePlateauSlider.setShowTickLabels(true);
		taillePlateauSlider.setBlockIncrement(1);
		taillePlateauSlider.setMajorTickUnit(1);
		taillePlateauSlider.setMinorTickCount(0);
		taillePlateauSlider.setPadding(new Insets(0, 0, 25, 0));
		taillePlateauSlider.setValue(3);
		taillePlateauSlider.getStyleClass().add("axis");
		valueSliderTaillePlateau.setText(""+(int) taillePlateauSlider.getValue());
		valueSliderTaillePlateau.setTextFill(Color.WHITE);
		valueSliderTaillePlateau.setStyle("-fx-font-size: 22px");
		taillePlateauLabel.setTextFill(Color.WHITE);
		taillePlateauLabel.setStyle("-fx-font-size: 16px");

		//Style pour le pseudo
		pseudoTF1.setMaxSize(resolutionSelectionne.getXSelected()/8, resolutionSelectionne.getYSelected()/30);
		pseudoTF1.setAlignment(Pos.CENTER);
		pseudoLabel1.setTextFill(Color.WHITE);
		
		pseudoTF2.setMaxSize(resolutionSelectionne.getXSelected()/8, resolutionSelectionne.getYSelected()/30);
		pseudoTF2.setAlignment(Pos.CENTER);
		pseudoLabel2.setTextFill(Color.WHITE);
		
		information.setAlignment(Pos.CENTER);
		information.setTextFill(Color.WHITE);

		//Style pour les images
		Timeline timelineMonstre = new Timeline();
		timelineMonstre.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(paneForMonstreImage.scaleXProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForMonstreImage.scaleXProperty(), 1.2)),

				new KeyFrame(Duration.ZERO, new KeyValue(paneForMonstreImage.scaleYProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForMonstreImage.scaleYProperty(), 1.2))

				);

		paneForMonstreImage.getStyleClass().add("imageBorder");
		paneForMonstreImage.setOpacity(0.8);

		paneForMonstreImage.setOnMouseEntered(e->{
			paneForMonstreImage.setStyle("-fx-cursor: hand");
			paneForMonstreImage.setOpacity(1);
			timelineMonstre.setAutoReverse(true);
			timelineMonstre.setCycleCount(Timeline.INDEFINITE);
			timelineMonstre.play();
		});
		paneForMonstreImage.setOnMouseExited(e->{
			if(!choixPerso1.equals("monstre")) {
				paneForMonstreImage.setOpacity(0.8);
			}
			timelineMonstre.stop();
			paneForMonstreImage.setScaleX(1);
			paneForMonstreImage.setScaleY(1);
		});

		paneForMonstreImage.setOnMouseClicked(e->{
			if(choixPerso1.equals("chasseur")) {
				paneForChasseurImage.getStyleClass().remove(1);
				paneForChasseurImage.setOpacity(0.8);
			}
			choixPerso1 = "monstre";
			paneForMonstreImage.getStyleClass().add("imageBorderClicked");
			paneForMonstreImage.setOpacity(1);
		});


		Timeline timelineChasseur = new Timeline();
		timelineChasseur.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(paneForChasseurImage.scaleXProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForChasseurImage.scaleXProperty(), 1.2)),

				new KeyFrame(Duration.ZERO, new KeyValue(paneForChasseurImage.scaleYProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForChasseurImage.scaleYProperty(), 1.2))

				);

		paneForChasseurImage.getStyleClass().add("imageBorder");
		paneForChasseurImage.setOpacity(0.8);

		paneForChasseurImage.setOnMouseEntered(e->{
			paneForChasseurImage.setStyle("-fx-cursor: hand");
			paneForChasseurImage.setOpacity(1);
			timelineChasseur.setAutoReverse(true);
			timelineChasseur.setCycleCount(Timeline.INDEFINITE);
			timelineChasseur.play();
		});
		paneForChasseurImage.setOnMouseExited(e->{
			if(!choixPerso1.equals("chasseur")) {
				paneForChasseurImage.setOpacity(0.8);
			}
			timelineChasseur.stop();
			paneForChasseurImage.setScaleX(1);
			paneForChasseurImage.setScaleY(1);
		});

		paneForChasseurImage.setOnMouseClicked(e->{
			if(choixPerso1.equals("monstre")) {
				paneForMonstreImage.getStyleClass().remove(1);
				paneForMonstreImage.setOpacity(0.8);
			}
			choixPerso1 = "chasseur";
			paneForChasseurImage.getStyleClass().add("imageBorderClicked");
		});


		//Style pour le bouton retour
		retourButton.setPrefSize(resolutionSelectionne.getXSelected()/4, resolutionSelectionne.getYSelected()/15);
		retourButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;
		retourButton.setOnMouseEntered(e->{
			retourButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		retourButton.setOnMouseExited(e->{
			retourButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		//Style pour le bouton valider
		validerButton.setPrefSize(resolutionSelectionne.getXSelected()/4, resolutionSelectionne.getYSelected()/15);
		validerButton.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;
		validerButton.setOnMouseEntered(e->{
			validerButton.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		validerButton.setOnMouseExited(e->{
			validerButton.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});
	}
	
	/**
	 * Change l'affichage pour qu'on puisse choisir un personnage
	 */
	public void choixPerso() {
		grilleJeuMulti.getChildren().clear();
		
		grilleJeuMulti.add(paneForMonstreImage,0,1);
		grilleJeuMulti.setConstraints(paneForMonstreImage, 0, 1, 1, 4, HPos.CENTER, VPos.CENTER);
		grilleJeuMulti.add(paneForChasseurImage,2,1);
		chasseurImageView.setTranslateY(50);
		grilleJeuMulti.setConstraints(paneForChasseurImage, 2, 1, 1, 4, HPos.CENTER, VPos.CENTER);
		grilleJeuMulti.add(information,1,5);
		grilleJeuMulti.setConstraints(information, 1, 5, 1, 1, HPos.CENTER, VPos.CENTER);
		
		information.setText(nomPerso1 + " choisis qui tu veux jouer, " + nomPerso2 + " jouera l'autre personnage !");
	}
	
	/**
	 * @param r : Resolution choisi ( elle peut changer dans les options )
	 * Change la taille de tous les objets pour qu'ils soient bien adaptés à la nouvelle résolution d'écran
	 */
	public void updateSizeOfScene(Resolutions r) {
		vboxJeuMulti.setPrefWidth(r.getXSelected());
		vboxJeuMulti.setPrefHeight(r.getYSelected());
		
		hboxJeuMulti.setPrefWidth(r.getXSelected());
		hboxJeuMulti.setPrefHeight(r.getYSelected()*0.20);

		paneForMonstreImage.setPrefSize(r.getXSelected()*0.15, r.getYSelected()*0.60);
		paneForChasseurImage.setPrefSize(r.getXSelected()*0.15, r.getYSelected()*0.60);

		grilleJeuMulti.setPrefSize(r.getXSelected()*0.80, (r.getYSelected()*0.80));
		grilleJeuMulti.setVgap(r.getYSelected()/57.333); 

		taillePlateauSlider.setPrefSize(r.getXSelected()/2, r.getYSelected()/20);

		pseudoTF1.setMaxSize(r.getXSelected()/8, r.getYSelected()/30);
		pseudoTF2.setMaxSize(r.getXSelected()/8, r.getYSelected()/30);

		retourButton.setPrefSize(r.getXSelected()/4, r.getYSelected()/15);
		validerButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
