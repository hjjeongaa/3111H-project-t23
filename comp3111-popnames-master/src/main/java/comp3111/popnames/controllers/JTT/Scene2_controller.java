package comp3111.popnames.controllers.JTT;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import comp3111.popnames.GlobalSettings;
import comp3111.popnames.JourneyThroughTime;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Scene2_controller {

	@FXML
    private StackPane JTT2_stack_StackPane;

    @FXML
    private AnchorPane JTT2_source_AnchorPane;

    @FXML
    private Label JTT2_titleLabel_Label;

    @FXML
    private Button JTT2_nextScene_Button;

    @FXML
    private Button JTT2_calculate_Button;

    @FXML
    private TextField JTT2_lifeExpectancy_TextField;

    @FXML
    private Label JTT2_lifeExpectancy_Label;
    
    @FXML
    private AreaChart<Integer, Integer> JTT2_chart_AreaChart;

    @FXML
    private NumberAxis JTT2_xAxis_NumberAxis;

    @FXML
    private NumberAxis JTT2_yAxis_NumberAxis;
    

    /* Methods */
    
	
	@FXML
	/**
     * Initialize the graph.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void initialize() {
		JTT2_titleLabel_Label.setText(String.format("Population of %s and %s", JourneyThroughTime.getUserName(), JourneyThroughTime.getSoulmateName()));
    	renderGraph(70);
    }
	
	@SuppressWarnings("unchecked")
	/**
     * Used to draw to the graph with a given life expectancy.
     * @author Ryder Khoi Daniel
     * v1.0
     */
	void renderGraph(int le) {
		// Creation and initialization of the series.
    	XYChart.Series<Integer,Integer> seriesUser = new Series<>();
    	XYChart.Series<Integer,Integer> seriesSoulmate = new Series<>();
    	
    	seriesUser.setName(JourneyThroughTime.getUserName());
    	seriesSoulmate.setName(JourneyThroughTime.getSoulmateName());
    	
    	// Populate the series
    	List<Integer> userData = JourneyThroughTime.getPopulationOf(JourneyThroughTime.getUserName(), JourneyThroughTime.getUserGender(), le);
    	List<Integer> soulmateData = JourneyThroughTime.getPopulationOf(JourneyThroughTime.getSoulmateName(), JourneyThroughTime.getSoulmateGender(), le);
    	//System.out.println(userData[0]);
    	int startYear = GlobalSettings.getLowerBound();
    	int endYear = GlobalSettings.getUpperBound();
    	//seriesUser.getData().add(new XYChart.Data<>(1880,userData[0]));
    	for(int year = 0; year < endYear - startYear; ++year) {
    		int xLab = startYear + year;
    		int yLabUser = userData.get(year);
    		int yLabSoulmate = soulmateData.get(year);
    		seriesUser.getData().add(new XYChart.Data<>(xLab, yLabUser));
    		seriesSoulmate.getData().add(new XYChart.Data<>(xLab, yLabSoulmate));
    	}
    	
    	// Draw to graph
    	JTT2_chart_AreaChart.getData().addAll(seriesUser, seriesSoulmate);
	}
    
	/**
     * Used to sanitize the input of the life expectancy.
     * @author Ryder Khoi Daniel
     * v1.0
     */
	private int getCleanedLifeExpectancy() {
		int res = -1;
		try {
    		res = Integer.parseInt(JTT2_lifeExpectancy_TextField.getText());
    		if(res < 1) {
    			JTT2_lifeExpectancy_Label.setText("Invalid life expectancy.");
    			return -1;
    		}
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		JTT2_lifeExpectancy_Label.setText("Invalid life expectancy.");
    		return res;
    	}
		return res;
	}
	
    @FXML
    /**
     * Called when the user has enetered a life expectancy and want to see the graph drawn.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void calculateGraph() {
    	JTT2_lifeExpectancy_Label.setText("Life Expectancy");
    	int lifeExpectancy = getCleanedLifeExpectancy();
    	if(lifeExpectancy == -1) return;
    	JTT2_chart_AreaChart.getData().clear();
    	renderGraph(lifeExpectancy);
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
			root = FXMLLoader.load(getClass().getResource("/interfaces/Scene3_interface.fxml"));
		} catch (IOException e) {
			System.out.println("I FAILED...");
			System.out.println(e);
			return;
		}
    	
    	Scene scene = JTT2_nextScene_Button.getScene();
    	root.translateYProperty().set(scene.getHeight());
    	
    	JTT2_stack_StackPane.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
    	KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
    	KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.setOnFinished(event->{
    		JTT2_stack_StackPane.getChildren().remove(JTT2_source_AnchorPane);
    	});
    	
    	timeline.play();
    }
    
}

