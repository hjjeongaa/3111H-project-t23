package comp3111.popnames.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Compatibility_controller {

    @FXML
    private RadioButton T6_male_RadioButton;

    @FXML
    private RadioButton T6_female_RadioButton;

    @FXML
    private TextField T6_user_name_TextField;

    @FXML
    private ComboBox<?> T6_user_type_ComboBox;

    @FXML
    private ComboBox<?> T6_user_country_ComboBox;

    @FXML
    private ComboBox<?> T6_user_yob_ComboBox;

    @FXML
    private RadioButton T6_mate_male_RadioButton;

    @FXML
    private RadioButton T6_mate_female_RadioButton;

    @FXML
    private TextField T6_mate_name_TextField;

    @FXML
    private ComboBox<?> T6_mate_type_ComboBox;

    @FXML
    private ComboBox<?> T6_mate_country_ComboBox;

    @FXML
    private ComboBox<?> T6_preferences_ComboBox;

    @FXML
    private ComboBox<?> T6_ranking_algorithm_ComboBox;

    @FXML
    private ComboBox<?> T6_ranking_resolver_ComboBox;

    @FXML
    private Button T6_generate_Button;

    @FXML
    private Arc T6_nkt6_Arc;

    @FXML
    private Circle nkt;

    @FXML
    private Text T6_nkt6_score_Text;

    @FXML
    private Arc T6_parm_Arc;

    @FXML
    private Text T6_parm_score_Text;

    @FXML
    private Arc T6_pasrm_Arc;

    @FXML
    private Text T6_pasrm_score_Text;

    @FXML
    private Arc T6_dld_Arc;

    @FXML
    private Text T6_dld_score_Text;

    @FXML
    void compatibilityScore() {

    }

}
