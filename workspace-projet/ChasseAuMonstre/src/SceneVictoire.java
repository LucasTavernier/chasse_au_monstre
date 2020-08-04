/**
 *  @author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneVictoire
 *	
 *	Cette classe contient les différents scénarios en cas de victoire
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

public class SceneVictoire extends Application{

	protected VBox vboxVictoire = new VBox();
	protected HBox hboxVictoire = new HBox();
	Image imageBackgroundVictoire = new Image("file:./res/backgroundMenuFixe.bmp");
	BackgroundImage backgroundImageVictoire = new BackgroundImage(imageBackgroundVictoire,null,null,null,new BackgroundSize(100, 100, true, true, false, true));
	Background backgroundVictoire= new Background(backgroundImageVictoire);

	GridPane grilleVictoire = new GridPane();

	Image coupeImage1 = new Image("file:./res/coupe.png");
	ImageView coupeImageView1 = new ImageView(coupeImage1);
	Pane paneForCoupe1Image = new VBox();

	Image joueurGagnantImage = new Image("file:./res/skull.png");
	ImageView joueurGagnantView = new ImageView(joueurGagnantImage);
	Pane paneForjoueurGagnant = new VBox();
	
	Label infoGagnant = new Label();
	Label forSpace = new Label("");

	Image coupeImage2 = new Image("file:./res/coupe.png");
	ImageView coupeImageView2 = new ImageView(coupeImage2);
	Pane paneForCoupe2Image = new VBox();

	Button retourButton = new Button("RETOUR");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	String choixPerso = "";

	/**
	 * Crée une instance de SceneChoixJeu (scène graphique JavaFX pour la victoire d'un personnage )
	 */
	public SceneVictoire() {

		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}

		paneForCoupe1Image.getChildren().add(coupeImageView1);

		paneForCoupe2Image.getChildren().add(coupeImageView2);
		
		paneForjoueurGagnant.getChildren().add(joueurGagnantView);
		paneForjoueurGagnant.setTranslateX(resolutionSelectionne.getXSelected()/9);
		
		infoGagnant.setTextFill(Color.WHITE);
		infoGagnant.setStyle("-fx-font-size: 22px");
		
		vboxVictoire.setBackground(backgroundVictoire);
		vboxVictoire.setPrefWidth(resolutionSelectionne.getXSelected());
		vboxVictoire.setPrefHeight(resolutionSelectionne.getYSelected());
		vboxVictoire.setAlignment(Pos.CENTER);
		vboxVictoire.getChildren().addAll(grilleVictoire, hboxVictoire);
		vboxVictoire.getStylesheets().add("file:./res/ProjetCss.css");

		hboxVictoire.setPrefWidth(resolutionSelectionne.getXSelected());
		hboxVictoire.setPrefHeight(resolutionSelectionne.getYSelected()*0.20);
		hboxVictoire.getChildren().addAll(retourButton);
		hboxVictoire.setAlignment(Pos.CENTER);
		hboxVictoire.setSpacing(10);

		grilleVictoire.setPrefSize(resolutionSelectionne.getXSelected()*0.80, (resolutionSelectionne.getYSelected()*0.80));
		grilleVictoire.setPadding(new Insets(10));
		grilleVictoire.setVgap(resolutionSelectionne.getYSelected()/57.333); 
		grilleVictoire.setHgap(0); 
		grilleVictoire.setAlignment(Pos.CENTER);
		grilleVictoire.setStyle("-fx-font-size: 20px");
		grilleVictoire.setGridLinesVisible(false);

		grilleVictoire.add(paneForCoupe1Image,0,0);
		grilleVictoire.setConstraints(paneForCoupe1Image, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		grilleVictoire.add(paneForjoueurGagnant,1,1);
		grilleVictoire.setConstraints(paneForjoueurGagnant, 1, 1, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleVictoire.add(infoGagnant,1,2);
		grilleVictoire.setConstraints(infoGagnant, 1, 2, 1, 1, HPos.CENTER, VPos.TOP);
		grilleVictoire.add(forSpace,1,3);
		grilleVictoire.setConstraints(infoGagnant, 1, 3, 1, 1, HPos.CENTER, VPos.TOP);
		grilleVictoire.add(paneForCoupe2Image,2,0);
		grilleVictoire.setConstraints(paneForCoupe2Image, 2, 0, 1, 1, HPos.CENTER, VPos.CENTER);

		//Style pour les images
		Timeline timelineCoupe1 = new Timeline();
		timelineCoupe1.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(paneForCoupe1Image.scaleXProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForCoupe1Image.scaleXProperty(), 1.15)),

				new KeyFrame(Duration.ZERO, new KeyValue(paneForCoupe1Image.scaleYProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForCoupe1Image.scaleYProperty(), 1.15))

				);

		timelineCoupe1.setAutoReverse(true);
		timelineCoupe1.setCycleCount(Timeline.INDEFINITE);
		timelineCoupe1.play();

		Timeline timelineCoupe2 = new Timeline();
		timelineCoupe2.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(paneForCoupe2Image.scaleXProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForCoupe2Image.scaleXProperty(), 1.15)),

				new KeyFrame(Duration.ZERO, new KeyValue(paneForCoupe2Image.scaleYProperty(), 1)),
				new KeyFrame(new Duration(1000), new KeyValue(paneForCoupe2Image.scaleYProperty(), 1.15))

				);

		timelineCoupe2.setAutoReverse(true);
		timelineCoupe2.setCycleCount(Timeline.INDEFINITE);
		timelineCoupe2.play();


		//Style pour le bouton retour
		retourButton.setPrefSize(resolutionSelectionne.getXSelected()/4, resolutionSelectionne.getYSelected()/15);
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
		vboxVictoire.setPrefWidth(r.getXSelected());
		vboxVictoire.setPrefHeight(r.getYSelected());

		hboxVictoire.setPrefWidth(r.getXSelected());
		hboxVictoire.setPrefHeight(r.getYSelected()*0.20);

		grilleVictoire.setPrefSize(r.getXSelected()*0.80, (r.getYSelected()*0.80));
		grilleVictoire.setVgap(r.getYSelected()/57.333); 
		
		paneForjoueurGagnant.setTranslateX(resolutionSelectionne.getXSelected()/9);

		retourButton.setPrefSize(r.getXSelected()/4, r.getYSelected()/15);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
	}

}
