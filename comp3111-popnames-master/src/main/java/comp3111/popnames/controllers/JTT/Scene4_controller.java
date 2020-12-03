package comp3111.popnames.controllers.JTT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Scene4_controller {

	@FXML
    private StackPane JTT4_stack_StackPane;
	
    @FXML
    private AnchorPane JTT4_anchor_AnchorPane;

    @FXML
    private TextField JTT4_learnYear_TextField;

    @FXML
    private Button JTT4_learn_Button;

    @FXML
    private ImageView JTT4_yearImage_ImageView;

    @FXML
    private Label JTT4_invalidYear_Label;

    @FXML
    private Label JTT4_fact1_TextField;

    @FXML
    private Label JTT4_fact2_TextField;

    @FXML
    private Button JTT4_goHome_Button;

    /* Methods */
    
    @FXML
    void initialize() throws FileNotFoundException {
    	Random rand = new Random();
    	int randomYear = 1880 + rand.nextInt(140);
    	loadDisplay(randomYear);
    	
    }
    
    void loadDisplay(int year) throws FileNotFoundException {
    	JTT4_yearImage_ImageView.setImage(JourneyThroughTime.getImage(year));
    	JTT4_learnYear_TextField.setText(String.format("%d", year));
    	List<String> initFacts = JourneyThroughTime.getFacts(year);
    	JTT4_fact1_TextField.setText(String.format("In %d, %s.", year, initFacts.get(0)));
    	JTT4_fact2_TextField.setText(String.format("On top of that, %s.",  initFacts.get(1)));
    }
    
    int getCleanedYear() {
    	int res = -1;
    	try {
    		res = Integer.parseInt(JTT4_learnYear_TextField.getText());
    		if(res < 1880 || res > 2019) {
    			JTT4_invalidYear_Label.setVisible(true);
    			return -1;
    		}
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		JTT4_invalidYear_Label.setVisible(true);
    		return res;
    	}
    	return res;
    }
    
    @FXML
    void findInfo() throws FileNotFoundException {
    	JTT4_invalidYear_Label.setVisible(false);
    	int year = getCleanedYear();
    	if(year == -1) return;
    	
    	loadDisplay(year);
    }

    @FXML
    void goHome() {
    	Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/interfaces/SoulmateName_interface.fxml"));
		} catch (IOException e) {
			System.out.println("I FAILED...");
			System.out.println(e);
			return;
		}
    	
    	Scene scene = JTT4_goHome_Button.getScene();
    	root.translateYProperty().set(scene.getHeight());
    	
    	JTT4_stack_StackPane.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
    	KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
    	KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.setOnFinished(event->{
    		JTT4_stack_StackPane.getChildren().remove(JTT4_anchor_AnchorPane);
    	});
    	
    	timeline.play();
    }

}