package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Transaction;
import model.TransactionHeader;

public class History extends Application {
	BorderPane bp;
	GridPane gp;
	VBox vb1,vb2;
	ScrollPane sp;
	Stage stage;
	Scene scene;
	MenuBar menu;
	Menu accountM, userM;
	MenuItem logoutMi, homeMi,cartMi,historyMi;
	TableView<Transaction> productTableView;
	TableView<TransactionHeader> transactionUser;
	
	private final ObservableList<Transaction> transactionList= FXCollections.observableArrayList();
	private final ObservableList<TransactionHeader> transactionUList= FXCollections.observableArrayList();
	
	private void initialize() {
		
		bp = new BorderPane();
		gp = new GridPane();
		vb1 = new VBox();
		vb2 = new VBox();
		sp = new ScrollPane();
		menu = new MenuBar();
		accountM = new Menu("Account");
		userM = new Menu("User");
		logoutMi = new MenuItem("Logout");
		homeMi = new MenuItem("Home");
		cartMi = new MenuItem("Cart");
		historyMi = new MenuItem("History");
		 productTableView = new TableView<>();
		 transactionUser = new TableView<>();
		 loadDataFromDatabase();
		 bp.setTop(menu);
		 menu.getMenus().addAll(accountM,userM);
			userM.getItems().addAll(homeMi,cartMi,historyMi);
			accountM.getItems().add(logoutMi);
		
		 bp.setCenter(gp);
			
			vb2.setAlignment(Pos.CENTER_LEFT);
			
			loadDataFromDatabase();
			setTable1();
			setTable2();
			gp.add(vb1, 0, 0);
			gp.add(vb2,1 , 0);
			
			vb1.getChildren().addAll(transactionUser);
			vb2.getChildren().addAll(productTableView);
			
			scene = new Scene(bp, 600, 600);
	}

	private void calculateTotalTransactionPrice(TransactionHeader selectedTransaction) {
	    double total = 0;
	    for (Transaction transaction : transactionList) {
	    	
	    }
	    selectedTransaction.setTotalTransactionPrice(total);
	}

	private void setTable1() {
	    Connect con = Connect.getInstance();

	    TableColumn<TransactionHeader, String> transactionIdCol = new TableColumn<>("Transaction ID");
	    transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

	    TableColumn<TransactionHeader, String> userIdCol = new TableColumn<>("User ID");
	    userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

	    transactionUser.getColumns().addAll(transactionIdCol, userIdCol);

	    transactionUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            calculateTotalTransactionPrice(newSelection);
	            updateDetailsTable(newSelection.getTransactionID());
	        } else {
	            updateDetailsTable(null);
	        }
	    });
	}
	
	private void updateDetailsTable(String transactionID) {
		
	}

	
	
	private void setTable2() {
	    Connect con = Connect.getInstance();


	    TableColumn<Transaction, String> idCol = new TableColumn<>("Hoodie ID");
	    idCol.setCellValueFactory(new PropertyValueFactory<>("hoodieId")); 
	    TableColumn<Transaction, String> nameCol = new TableColumn<>("Hoodie Name");
	    nameCol.setCellValueFactory(new PropertyValueFactory<>("hoodieName"));
	    TableColumn<Transaction, Integer> quantityCol = new TableColumn<>("Quantity");
	    quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityHoodie")); 
	    TableColumn<Transaction, Double> totalPCol = new TableColumn<>("Total Price");
	    totalPCol.setCellValueFactory(new PropertyValueFactory<>("totalPHoodie")); 

	    productTableView.getColumns().addAll( idCol, nameCol, quantityCol, totalPCol);
	}

	public void setMenuAction() {
		logoutMi.setOnAction(e->{
		
				try {
					new Login().start(stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		});
		
		homeMi.setOnAction(e->{
			try {
				new HomeScreen().start(stage);
			} catch (Exception el) {
				// TODO: handle exception
				el.printStackTrace();
			}
			
		});
		
		cartMi.setOnAction(e->{
			try {
				new Cart().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		historyMi.setOnAction(e->{
			new History().start(stage);
		});
	}
	
	private void loadDataFromDatabase() {
	    Connect con = Connect.getInstance();
	    ResultSet rs = con.selectData("SELECT transactiondetail.TransactionID, UserID, hoodie.HoodieID, HoodieName, Quantity, HoodiePrice FROM transactiondetail JOIN hoodie ON hoodie.HoodieID = transactiondetail.HoodieID JOIN transactionheader ON transactionheader.TransactionID = transactiondetail.TransactionID");

	    try {
	        while (rs.next()) {
	            String hoodieId = rs.getString("HoodieID");
	            String hoodieName = rs.getString("HoodieName");
	            int quantityHoodie = rs.getInt("Quantity");
	            double priceHoodie = rs.getDouble("HoodiePrice");
	            String transactionID = rs.getString("TransactionID");
	            String userID = rs.getString("UserID");
	            TransactionHeader transactionth = new TransactionHeader(transactionID, userID);
	            transactionUList.add(transactionth);
	            Transaction transactions = new Transaction(hoodieId, hoodieName, priceHoodie, quantityHoodie);
	            transactionList.add(transactions);
	        }

	        productTableView.setItems(transactionList);
	        transactionUser.setItems(transactionUList);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	}

   
    @Override
    public void start(Stage primaryStage) {
    	initialize();
      setMenuAction();
        
        this.stage = primaryStage;
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setTitle("hO-Ohdie");
    }

    

   

    private static class TransactionDetail {
       
    }
}
