package comp3111.popnames.controllers.JTT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import comp3111.popnames.JourneyThroughTime;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Scene3_controller {

    @FXML
    private StackPane JTT3_source_StackPane;

    @FXML
    private AnchorPane JTT3_anchor_AnchorPane;

    @FXML
    private Button JTT3_nextScene_Button;

    @FXML
    private ImageView JTT3_yourImage_ImageView;

    @FXML
    private Label JTT3_fact1_Label;

    @FXML
    private Label JTT3_fact2_Label;

    /* Methods */
    @FXML
    /**
     * Initialize all the elements on the interface.
     * In this case, its facts about your birth year, and an image related to your year.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void initialize() throws FileNotFoundException {
    	int birthYear = JourneyThroughTime.getYOB();
    	List<String> usersFacts = JourneyThroughTime.getFacts(birthYear);
    	JTT3_fact1_Label.setText(usersFacts.get(0) + ".");
    	JTT3_fact2_Label.setText("On top of that, " + usersFacts.get(1) + ".");
    	JTT3_yourImage_ImageView.setImage(JourneyThroughTime.getImage(birthYear));
    }
    
    @FXML
    /**
     * Used to jump to the next scene.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void nextScene() {
    	Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/interfaces/Scene4_interface.fxml"));
		} catch (IOException e) {
			System.out.println("I FAILED...");
			System.out.println(e);
			return;
		}
    	
    	Scene scene = JTT3_nextScene_Button.getScene();
    	root.translateYProperty().set(scene.getHeight());
    	
    	JTT3_source_StackPane.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
    	KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
    	KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.setOnFinished(event->{
    		JTT3_source_StackPane.getChildren().remove(JTT3_anchor_AnchorPane);
    	});
    	
    	timeline.play();
    }

}