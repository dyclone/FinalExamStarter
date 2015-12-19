package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;
import java.util.UUID;

import base.RateDAL;
import ch.makery.address.MainApp;
import ch.makery.address.model.Rate;


public class MortgageController {
	
	@FXML
	private Label incomeLabel = new Label();
	@FXML
	private Label expensesLabel = new Label();
	@FXML
	private Label creditscoreLabel = new Label();
	@FXML
	private Label termLabel = new Label();
	@FXML
	private Label housepriceLabel = new Label();
	@FXML
	private Label lblmortgagePayment = new Label();
	@FXML
	private TextField txtIncome;
	@FXML
	private TextField txtExpense;
	@FXML
	private TextField txtCreditScore;
	@FXML
	private TextField txtHouseCost;
	@FXML
	private ComboBox<String> cmbTerm;
	@FXML
	private Button goButton;


    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MortgageController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void Mortgagecalculation() {
    	lblmortgagePayment.setVisible(false);
    	Double income = Double.parseDouble(this.txtIncome.getText());
    	Double Expense = Double.parseDouble(this.txtExpense.getText());
    	int creditScore = Integer.parseInt(this.txtCreditScore.getText());
    	Double costOfHouse = Double.parseDouble(this.txtHouseCost.getText());
    	Double term = Double.parseDouble(this.cmbTerm.getValue().toString());
    	Double interestRate = RateDAL.getRate(creditScore);
    	
    	int term1 = 0;
        if (this.cmbTerm.getSelectionModel().getSelectedIndex() == 0){
        	term1 = 15;
        }
        else if(this.cmbTerm.getSelectionModel().getSelectedIndex() == 1){
        	term1 = 30;
        }
		int houseLoan = 0;
		int housepayment = (int) ch.makery.address.model.Rate.getPayment(creditScore, houseLoan, term1);
    	
    	if(housepayment <= (income * 0.36) 
    			&& housepayment <= (income + (-1*Expense )) * 0.28) {
    		DecimalFormat deciValue = new DecimalFormat("1.23");
    		String Mortgage = deciValue.format(housepayment);
    		
    		this.lblmortgagePayment.setText("This house is perfect!");
    		lblmortgagePayment.setVisible(true);
    		this.lblmortgagePayment.setText("You should pay" + Mortgage + "a month");
    	} else {
    		this.lblmortgagePayment.setText("this house is way to much");
    		System.out.println("Your mortgage payment must be less than" + income * 0.36);
    		System.out.println("Your Mortgage payment must be less than" + (income + (-1)*Expense ) * 0.28);
    	}
 
   }
   
}