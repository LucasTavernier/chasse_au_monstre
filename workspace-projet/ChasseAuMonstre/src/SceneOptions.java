/**
 * 	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java
 *
 *	SceneOptions
 *	
 *	Cette classe permets une visualisation des différentes options et une possible modification de ces dernières 
 */

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneOptions extends Application{

	protected VBox vboxOptions = new VBox();
	
	GridPane grilleOptions = new GridPane();
	
	Image imageBackgroundOptions = new Image("file:./res/options.bmp");
	BackgroundImage backgroundOptionsImage = new BackgroundImage(imageBackgroundOptions,null,null,null,new BackgroundSize(100, 100, true, true, true, true));
	Background backgroundOptions = new Background(backgroundOptionsImage);
	
	Button quitterButtonForOptions = new Button("QUITTER");
	
	final ComboBox<Resolutions> comboBox = new ComboBox();
	
	Label textResolution = new Label("Choisissez votre résolution d'écran");
	Label textVolume = new Label("Volume");
	Label textValueVolume = new Label("");
	Label textMuteMusic = new Label("Désactiver la musique");
	Label textFullScreen = new Label("Plein Écran");

	Slider sliderMusic = new Slider();
	
	CheckBox checkboxMusic = new CheckBox("");
	CheckBox checkboxFullscreen = new CheckBox("");

	Resolutions[] tabResolutions = Resolutions.values();
	Resolutions resolutionSelectionne = null;

	/**
	 * Crée une instance de SceneChoixJeu (scène graphique JavaFX pour les options du jeu )
	 */
	public SceneOptions(){
		for(int i=0 ; i<tabResolutions.length ; i++) {
			if(tabResolutions[i].isSelected()) {
				resolutionSelectionne = tabResolutions[i];
			}
		}

		vboxOptions.setBackground(this.backgroundOptions);
		vboxOptions.setPrefWidth(resolutionSelectionne.getXSelected()-100);
		vboxOptions.setPrefHeight(resolutionSelectionne.getYSelected()-100);
		vboxOptions.getChildren().addAll(grilleOptions, quitterButtonForOptions);
		vboxOptions.setAlignment(Pos.TOP_CENTER);
		vboxOptions.getStylesheets().add("file:./res/ProjetCss.css"); //indique le fichier css à utiliser

		grilleOptions.setPrefSize(((resolutionSelectionne.getXSelected()-100)*0.80), (resolutionSelectionne.getYSelected()-100)*0.80);
		grilleOptions.setPadding(new Insets(10));
		grilleOptions.setVgap(75); 
		grilleOptions.setHgap(100); 
		grilleOptions.setAlignment(Pos.CENTER);
		grilleOptions.setStyle("-fx-font-size: 20px");
		grilleOptions.setGridLinesVisible(false);

		quitterButtonForOptions.setPrefSize(resolutionSelectionne.getXSelected()/2.15, resolutionSelectionne.getYSelected()/15);
		quitterButtonForOptions.setTranslateY((resolutionSelectionne.getYSelected()-100)*0.1);
		quitterButtonForOptions.getStyleClass().add("ButtonNormal"); //style du bouton venant du fichier css défini dans la scene;

		quitterButtonForOptions.setOnMouseEntered(e->{
			quitterButtonForOptions.getStyleClass().add("ButtonHoverOn"); //style quand on survole le bouton
		});
		quitterButtonForOptions.setOnMouseExited(e->{
			quitterButtonForOptions.getStyleClass().remove(2); // Styles du bouton [1: ButtonNormal ; 2: ButtonHoverOn]
		});

		comboBox.getItems().setAll(Resolutions.values());

		textResolution.setTextFill(Color.WHITE);
		textVolume.setTextFill(Color.WHITE);
		textValueVolume.setTextFill(Color.WHITE);
		textMuteMusic.setTextFill(Color.WHITE);
		textMuteMusic.setStyle("-fx-padding: 0 0 30 0");
		textFullScreen.setTextFill(Color.WHITE);
		textFullScreen.setStyle("-fx-padding: 0 0 30 0");

		sliderMusic.setMin(0);
		sliderMusic.setValue(50);
		sliderMusic.setMax(100);
		sliderMusic.setShowTickMarks(true);
		sliderMusic.setPrefWidth(300);
		sliderMusic.setPrefHeight(100);
		sliderMusic.setBlockIncrement(1);

		//Permet de changer l'affichage dans le bouton de la ComboBox par le toString des enums
		class SimpleResolutionsListCell extends ListCell<Resolutions> { 

			@Override 
			protected void updateItem(Resolutions item, boolean empty) { 
				super.updateItem(item, empty); 
				setText(null); 
				if (!empty && item != null) { 
					final String text = item.toStringRes();
					setText(text); 
				} 
			} 
		}
		comboBox.setButtonCell(new SimpleResolutionsListCell());

		for(Resolutions r : Resolutions.values()) {
			if(r.isRecommanded()) {
				comboBox.setValue(r);
			}
		}

		//Change l'affichage des enums par leur toString dans la liste de la ComboBox
		comboBox.setCellFactory(listView -> new SimpleResolutionsListCell());

		grilleOptions.add(textResolution,0,0);
		grilleOptions.setConstraints(textResolution, 0, 0, 1, 1, HPos.CENTER, VPos.TOP);
		grilleOptions.add(comboBox,0,0);
		grilleOptions.setConstraints(comboBox, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleOptions.add(textFullScreen,0,1);
		grilleOptions.setConstraints(textFullScreen, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		grilleOptions.add(checkboxFullscreen,0,1);
		grilleOptions.setConstraints(checkboxFullscreen, 0, 1, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleOptions.add(textVolume,1,0);
		grilleOptions.setConstraints(textVolume, 1, 0, 1, 1, HPos.CENTER, VPos.TOP);
		grilleOptions.add(sliderMusic,1,0);
		grilleOptions.setConstraints(sliderMusic, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		grilleOptions.add(textValueVolume,1,0);
		grilleOptions.setConstraints(textValueVolume, 1, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
		grilleOptions.add(textMuteMusic,1,1);
		grilleOptions.setConstraints(textMuteMusic, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		grilleOptions.add(checkboxMusic,1,1);
		grilleOptions.setConstraints(checkboxMusic, 1, 1, 1, 1, HPos.CENTER, VPos.BOTTOM);
	}

	/**
	 * @param r : Resolution choisi ( elle peut changer dans les options )
	 * Change la taille de tous les objets pour qu'ils soient bien adaptés à la nouvelle résolution d'écran
	 */
	public void updateSizeOfScene(Resolutions r) {
		vboxOptions.setPrefWidth(r.getXSelected()-100);
		vboxOptions.setPrefHeight(r.getYSelected()-100);
		grilleOptions.setPrefSize(((r.getXSelected()-100)*0.80), (r.getYSelected()-100)*0.80);
		quitterButtonForOptions.setPrefSize(r.getXSelected()/2.15, r.getYSelected()/15);
		quitterButtonForOptions.setTranslateY((r.getYSelected()-100)*0.1);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}


