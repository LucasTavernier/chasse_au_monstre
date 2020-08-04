/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneJeuSolo
 *
 *	Cette classe permet d'afficher la scene du jeu en mode Solo vs un IA
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

public class SceneJeuSolo extends Application{

	protected VBox vboxJeuSolo = new VBox();
	protected HBox hboxJeuSolo = new HBox();
	Image imageBackgroundJeuSolo = new Image("file:./res/backgroundMenuFixe.bmp");
	BackgroundImage backgroundImageJeuSolo = new BackgroundImage(imageBackgroundJeuSolo,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundJeuSolo= new Background(backgroundImageJeuSolo);

	GridPane grilleJeuSolo = new GridPane();

	Label taillePlateauLabel = new Label("Choisis la taille de ton plateau");
	Slider taillePlateauSlider = new Slider();
	Label valueSliderTaillePlateau = new Label();

	Image monstreImage = new Image("file:./res/Monster.png");
	ImageView monstreImageView = new ImageView(monstreImage);
	Pane paneForMonstreImage = new VBox();

	Label pseudoLabel = new Label("Joueur, comment veux-tu que je t'appelle ?");
	TextField pseudoTF = new TextField();

	Image chasseurImage = new Image("file:./res/Hunter.png");
	ImageView chasseurImageView = new ImageView(chasseurImage);
	Pane paneForChasseurImage = new VBox();

	Button retourButton = new Button("RETOUR");
	Button validerButton = new Button("VALIDER");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	String choixPerso = "";

	/**
	 * Crée une instance de SceneChoixJeu Crée une instance de SceneChoixJeu (scène graphique JavaFX pour le mode de jeu Solo )
	 */
	public SceneJeuSolo() {

		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}

		paneForMonstreImage.getChildren().add(monstreImageView);
		paneForMonstreImage.setPrefSize(resolutionSelectionne.getXSelected()*0.15, resolutionSelectionne.getYSelected()*0.60);

		paneForChasseurImage.getChildren().add(chasseurImageView);
		paneForChasseurImage.setPrefSize(resolutionSelectionne.getXSelected()*0.15, resolutionSelectionne.getYSelected()*0.60);

		vboxJeuSolo.setBackground(backgroundJeuSolo);
		vboxJeuSolo.setPrefWidth(resolutionSelectionne.getXSelected());
		vboxJeuSolo.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxJeuSolo.setAlignment(Pos.CENTER);
		vboxJeuSolo.getChildren().addAll(grilleJeuSolo, hboxJeuSolo);
		vboxJeuSolo.getStylesheets().add("file:./res/ProjetCss.css");

		hboxJeuSolo.setPrefWidth(resolutionSelectionne.getXSelected());
		hboxJeuSolo.setPrefHeight(resolutionSelectionne.getYSelected()*0.20);
		hboxJeuSolo.getChildren().addAll(retourButton,validerButton);
		hboxJeuSolo.setAlignment(Pos.CENTER);
		hboxJeuSolo.setSpacing(20);

		grilleJeuSolo.setPrefSize(resolutionSelectionne.getXSelected()*0.80, (resolutionSelectionne.getYSelected()*0.80));
		grilleJeuSolo.setPadding(new Insets(10));
		grilleJeuSolo.setVgap(resolutionSelectionne.getYSelected()/57.333); 
		grilleJeuSolo.setHgap(0); 
		grilleJeuSolo.setAlignment(Pos.CENTER);
		grilleJeuSolo.setStyle("-fx-font-size: 20px");
		grilleJeuSolo.setGridLinesVisible(false);

		grilleJeuSolo.add(pseudoLabel,1,2);
		grilleJeuSolo.setConstraints(pseudoLabel, 1, 2, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleJeuSolo.add(pseudoTF,1,3);
		grilleJeuSolo.setConstraints(pseudoTF, 1, 3, 1, 1, HPos.CENTER, VPos.TOP);
		grilleJeuSolo.add(taillePlateauLabel,1,4);
		grilleJeuSolo.setConstraints(taillePlateauLabel, 1, 4, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleJeuSolo.add(taillePlateauSlider,1,5);
		grilleJeuSolo.setConstraints(taillePlateauSlider, 1, 5, 1, 1, HPos.CENTER, VPos.TOP);
		grilleJeuSolo.add(valueSliderTaillePlateau,1,5);
		grilleJeuSolo.setConstraints(valueSliderTaillePlateau, 1, 5, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleJeuSolo.add(paneForMonstreImage,0,1);
		grilleJeuSolo.setConstraints(paneForMonstreImage, 0, 1, 1, 4, HPos.CENTER, VPos.CENTER);
		grilleJeuSolo.add(paneForChasseurImage,2,1);
		chasseurImageView.setTranslateY(50);
		grilleJeuSolo.setConstraints(paneForChasseurImage, 2, 1, 1, 4, HPos.CENTER, VPos.CENTER);

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
		pseudoTF.setMaxSize(resolutionSelectionne.getXSelected()/8, resolutionSelectionne.getYSelected()/30);
		pseudoTF.setAlignment(Pos.CENTER);
		pseudoLabel.setTextFill(Color.WHITE);

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
			if(!choixPerso.equals("monstre")) {
				paneForMonstreImage.setOpacity(0.8);
			}
			timelineMonstre.stop();
			paneForMonstreImage.setScaleX(1);
			paneForMonstreImage.setScaleY(1);
		});

		paneForMonstreImage.setOnMouseClicked(e->{
			if(choixPerso.equals("chasseur")) {
				paneForChasseurImage.getStyleClass().remove(1);
				paneForChasseurImage.setOpacity(0.8);
			}
			choixPerso = "monstre";
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
			if(!choixPerso.equals("chasseur")) {
				paneForChasseurImage.setOpacity(0.8);
			}
			timelineChasseur.stop();
			paneForChasseurImage.setScaleX(1);
			paneForChasseurImage.setScaleY(1);
		});

		paneForChasseurImage.setOnMouseClicked(e->{
			if(choixPerso.equals("monstre")) {
				paneForMonstreImage.getStyleClass().remove(1);
				paneForMonstreImage.setOpacity(0.8);
			}
			choixPerso = "chasseur";
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
	 * @param r : Resolution choisi ( elle peut changer dans les options )
	 * Change la taille de tous les objets pour qu'ils soient bien adaptés à la nouvelle résolution d'écran
	 */
	public void updateSizeOfScene(Resolutions r) {
		vboxJeuSolo.setPrefWidth(r.getXSelected());
		vboxJeuSolo.setPrefHeight(r.getYSelected());
		
		hboxJeuSolo.setPrefWidth(r.getXSelected());
		hboxJeuSolo.setPrefHeight(r.getYSelected()*0.20);

		paneForMonstreImage.setPrefSize(r.getXSelected()*0.15, r.getYSelected()*0.60);
		paneForChasseurImage.setPrefSize(r.getXSelected()*0.15, r.getYSelected()*0.60);

		grilleJeuSolo.setPrefSize(r.getXSelected()*0.80, (r.getYSelected()*0.80));
		grilleJeuSolo.setVgap(r.getYSelected()/57.333); 

		taillePlateauSlider.setPrefSize(r.getXSelected()/2, r.getYSelected()/20);

		pseudoTF.setMaxSize(r.getXSelected()/8, r.getYSelected()/30);

		retourButton.setPrefSize(r.getXSelected()/4, r.getYSelected()/15);
		validerButton.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
