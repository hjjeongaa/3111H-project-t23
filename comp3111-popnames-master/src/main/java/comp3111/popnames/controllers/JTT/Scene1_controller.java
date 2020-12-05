package comp3111.popnames.controllers.JTT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import comp3111.popnames.JourneyThroughTime;
import comp3111.popnames.controllers.SoulmateName_controller;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Scene1_controller {

	@FXML
    private StackPane JTT1_stack_StackPane;
	
	@FXML
    private AnchorPane JTT1_source_AnchorPane;
	
    @FXML
    private Button JTT1_seePartner_Button;

    @FXML
    private Label JTT1_userDescription2_Label;

    @FXML
    private Label JTT1_userDescription1_Label;

    @FXML
    private Label JTT1_userDescription3_Label;

    @FXML
    private Label JTT1_userTitle_Label;

    @FXML
    private Button JTT1_nextScene_Button;

    @FXML
    private Label JTT1_soulmateDescription2_Label;

    @FXML
    private Label JTT1_soulmateDescription1_Label;

    @FXML
    private Label JTT1_soulmateDescription3_Label;

    @FXML
    private Label JTT1_soulmateTitle_Label;

    @FXML
    private Button JTT1_seeUser_Button;

    @FXML
    private VBox Scene1_imageHolder_VBox;

    @FXML
    private ImageView JTT_soulmateImage_ImageView;

    @FXML
    private ImageView JTT_userImage_ImageView;

    /* Methods */
    @FXML
    /**
     * Initialize all the elements on the interface like Images.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void initialize() throws FileNotFoundException{
    	String userName = JourneyThroughTime.getUserName();
    	String soulmateName = JourneyThroughTime.getSoulmateName();
    	String country = JourneyThroughTime.getCountry();
    	String userGender = JourneyThroughTime.getUserGender();
    	String soulmateGender = JourneyThroughTime.getSoulmateGender();
    	
    	JTT1_userTitle_Label.setText("[ " + userName + " ]");
    	JTT1_soulmateTitle_Label.setText("[ " + soulmateName + " ]");
    	
    	// Note the soulmate's name is Guaranteed to be found in the set.
    	int usersFirstYear = JourneyThroughTime.getFirstAppearance(userName, userGender);
    	int soulmatesFirstYear = JourneyThroughTime.getFirstAppearance(soulmateName, soulmateGender);
    	
    	if(usersFirstYear == -1) {
    		JTT1_userDescription1_Label.setText("WOW! You sure have a unique name!");
    		JTT1_userDescription2_Label.setText("Though the name oracle hasnt seen this name in " + country + ", the oracle is sure you will find something interesting here.");
    		JTT1_userDescription3_Label.setText("");
    		Random rand = new Random();
    		usersFirstYear = 1880 + rand.nextInt(140);
    	} else {
    		List<String> usersFacts = JourneyThroughTime.getFacts(usersFirstYear);
    		JTT1_userDescription1_Label.setText("The name " + userName + " First appears in " + country + " in " + String.format("%d", usersFirstYear) + ".");
    		JTT1_userDescription2_Label.setText("In that year, " + usersFacts.get(0) + ".");
    		JTT1_userDescription3_Label.setText("On top of that, " + usersFacts.get(1) + ".");
    	}
    	
    	List<String> soulmatesFacts = JourneyThroughTime.getFacts(soulmatesFirstYear);
    	JTT1_soulmateDescription1_Label.setText("The name " + soulmateName + " First appears in " + country + " in " + String.format("%d", soulmatesFirstYear) + ".");
    	JTT1_soulmateDescription2_Label.setText("In that year, " + soulmatesFacts.get(0) + ".");
		JTT1_soulmateDescription3_Label.setText("On top of that, " + soulmatesFacts.get(1) + ".");
		
		// Setting Images.
		JTT_soulmateImage_ImageView.setImage(JourneyThroughTime.getImage(soulmatesFirstYear));
		JTT_userImage_ImageView.setImage(JourneyThroughTime.getImage(usersFirstYear));
		
		// Setting visibility
		JTT_soulmateImage_ImageView.setVisible(false);
		
    }
    
    @FXML
    /**
     * Used to start an animation to see information about your partner.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void seePartner() {
    	// Assumed state: 	JTT_soulmateImage_ImageView -> invisible
    	//					JTT_userImage_ImageView		-> visible
    	TranslateTransition translation = new TranslateTransition(Duration.seconds(1.2), Scene1_imageHolder_VBox);
    	translation.setToX(-462);
    	
    	FadeTransition userFade = new FadeTransition(Duration.seconds(1.2), JTT_userImage_ImageView);
    	userFade.setFromValue(1.0);
    	userFade.setToValue(0.0);
    	
    	FadeTransition soulFade = new FadeTransition(Duration.seconds(1.2), JTT_soulmateImage_ImageView);
    	soulFade.setFromValue(0.0);
    	soulFade.setToValue(1.0);
    	
    	JTT_soulmateImage_ImageView.setVisible(true);
    	ParallelTransition slideAndFade = new ParallelTransition(translation, userFade, soulFade);
    	slideAndFade.play();
    }

    @FXML
    /**
     * Used to go back to the original view of seeing the users data.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void seeUser() {
    	// Assumed state: 	JTT_soulmateImage_ImageView -> visible
    	//					JTT_userImage_ImageView		-> invisible
    	TranslateTransition translation = new TranslateTransition(Duration.seconds(1.2), Scene1_imageHolder_VBox);
    	translation.setToX(0);
    	
    	FadeTransition userFade = new FadeTransition(Duration.seconds(1.2), JTT_soulmateImage_ImageView);
    	userFade.setFromValue(1.0);
    	userFade.setToValue(0.0);
    	
    	FadeTransition soulFade = new FadeTransition(Duration.seconds(1.2), JTT_userImage_ImageView);
    	soulFade.setFromValue(0.0);
    	soulFade.setToValue(1.0);
    	
    	ParallelTransition slideAndFade = new ParallelTransition(translation, userFade, soulFade);
    	slideAndFade.play();
    }
    
    @FXML
    /**
     * Used to jump to the next scene in the journey through time.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void nextScene() {
    	Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/interfaces/Scene2_interface.fxml"));
		} catch (IOException e) {
			System.out.println("I FAILED...");
			System.out.println(e);
			return;
		}
    	
    	Scene scene = JTT1_nextScene_Button.getScene();
    	root.translateYProperty().set(scene.getHeight());
    	
    	JTT1_stack_StackPane.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
    	KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
    	KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.setOnFinished(event->{
    		JTT1_stack_StackPane.getChildren().remove(JTT1_source_AnchorPane);
    	});
    	
    	timeline.play();
    }

}
