package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Hoodie;
import model.Transaction;

public class Cart extends Application {
	
	BorderPane bp;
	GridPane gp;
	VBox vb1,vb2;
	ScrollPane sp;
	HBox hb;
	Stage stage;
	Scene scene;
	MenuBar menu;
	Menu accountM, userM;
	MenuItem logoutMi, homeMi,cartMi,historyMi;
	Label hoodieLbl,hoodieDLbl,hoodieIdLbl,hoodieNameLbl,hoodiePriceLbl,hoodieQuantityLbl,hoodieTpLbl,contactInfoLbl,emailLbl,phoneLbl,addressLbl,cartTotalPLbl, totalPriceLbl;
	TableView<Transaction> productTableView;

	
	Button removeBtn,checkoutBtn;	
	private boolean dataLoaded = false;
	
	private final ObservableList<Transaction> transactionList= FXCollections.observableArrayList();
	
	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		vb1 = new VBox();
		vb2 = new VBox();
		sp = new ScrollPane();
		hb = new HBox();
		menu = new MenuBar();
		accountM = new Menu("Account");
		userM = new Menu("User");
		logoutMi = new MenuItem("Logout");
		homeMi = new MenuItem("Home");
		cartMi = new MenuItem("Cart");
		historyMi = new MenuItem("History");
		hoodieLbl = new Label("Ho-odie");
		hoodieDLbl = new Label("Hoodie Detail");
		 productTableView = new TableView<>();
		hoodieIdLbl = new Label("Select an item from the table...");
	    hoodieNameLbl = new Label("");
	    hoodiePriceLbl = new Label("");
	    hoodieQuantityLbl = new Label("");
		hoodieTpLbl = new Label("Total Price");
		contactInfoLbl = new Label("Contact Information");
		emailLbl = new Label("Email:dummy@hoohdie.com ");
		phoneLbl = new Label("Phone:+6212345678901 ");
		addressLbl = new Label("Address:Jl. dummy ");
		cartTotalPLbl = new Label("Cart's Total Price: ");
		totalPriceLbl = new Label ("Total Price: ");
		checkoutBtn = new Button("Checkout");
		removeBtn = new Button("Remove from cart");
		setTable();
		loadDataFromDatabase();
		mouseEvent();
		
		
		bp.setTop(menu);
		userM.getItems().addAll(homeMi,cartMi,historyMi);
		accountM.getItems().add(logoutMi);
		
		menu.getMenus().addAll(accountM,userM);
		
	
		bp.setCenter(gp);
		gp.setAlignment(Pos.BASELINE_CENTER.CENTER);
		bp.setLeft(vb1);
		vb2.setAlignment(Pos.CENTER_LEFT);
		
		
		gp.add(vb2,1 , 0);
		hb.getChildren().addAll(totalPriceLbl,hoodieTpLbl);
		vb1.getChildren().addAll(hoodieLbl,productTableView/*listview*/);
		vb2.getChildren().addAll(hoodieDLbl,hoodieIdLbl,hoodieNameLbl,hoodiePriceLbl,hoodieQuantityLbl,hb,removeBtn,contactInfoLbl,emailLbl,phoneLbl,addressLbl,cartTotalPLbl,checkoutBtn);
		
		
		
		scene = new Scene(bp, 600, 600);
		
		
		
	}
	

	
	private void setTable() {
	    Connect con = Connect.getInstance();

	    TableColumn<Transaction, String> idCol = new TableColumn<>("Hoodie ID");
	    idCol.setCellValueFactory(new PropertyValueFactory<>("hoodieId"));
	    idCol.setMinWidth(sp.getWidth() / 2);
	    idCol.setResizable(false);

	    TableColumn<Transaction, String> nameCol = new TableColumn<>("Hoodie Name");
	    nameCol.setCellValueFactory(new PropertyValueFactory<>("hoodieName"));
	    nameCol.setMinWidth(sp.getWidth() / 2);
	    nameCol.setResizable(false);

	    TableColumn<Transaction, Integer> quantityCol = new TableColumn<>("Quantity");
	    quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityHoodie"));
	    quantityCol.setMinWidth(sp.getWidth() / 2);
	    quantityCol.setResizable(false);

	    TableColumn<Transaction, Double> totalPCol = new TableColumn<>("Total Price");
	    totalPCol.setCellValueFactory(new PropertyValueFactory<>("totalPHoodie"));
	    totalPCol.setMinWidth(sp.getWidth() / 2);
	    totalPCol.setResizable(false);

	    productTableView.getColumns().addAll(idCol, nameCol, quantityCol, totalPCol);
	}

	
	private void loadDataFromDatabase() {
	    Connect con = Connect.getInstance();
	    ResultSet rs = con.selectData("SELECT hoodie.HoodieID, HoodieName, Quantity, HoodiePrice FROM transactiondetail JOIN hoodie ON hoodie.HoodieID = transactiondetail.HoodieID");

	    double cartTotalPrice = 0.0;

	    try {
	        while (rs.next()) {
	            String hoodieId = rs.getString("HoodieID");
	            String hoodieName = rs.getString("HoodieName");
	            int quantityHoodie = rs.getInt("Quantity");
	            double priceHoodie = rs.getDouble("HoodiePrice");

	            boolean found = false;
	            for (Transaction transaction : transactionList) {
	                if (transaction.getHoodieId().equals(hoodieId)) {
	                    transaction.setHoodieName(hoodieName);
	                    transaction.setQuantityHoodie(quantityHoodie);
	                    transaction.getTotalPHoodie();
	                    found = true;
	                    break;
	                }
	            }

	            if (!found) {
	                Transaction transactions = new Transaction(hoodieId, hoodieName, priceHoodie, quantityHoodie);
	                transactionList.add(transactions);
	            }

	            cartTotalPrice += priceHoodie * quantityHoodie; 
	        }

	        productTableView.setItems(transactionList);
	        dataLoaded = true;

	        cartTotalPLbl.setText("Cart's Total Price: " + cartTotalPrice);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void removeProductFromCart(Transaction selectedTransaction) {
	    Connect con = Connect.getInstance();
	    String hoodieId = selectedTransaction.getHoodieId();
	    String Query = "DELETE FROM transactiondetail WHERE HoodieID = '" + hoodieId + "'";

	    try {
	        con.deleteHoodie(Query);

	      
	        transactionList.remove(selectedTransaction);

	    
	        productTableView.setItems(transactionList);

	   
	        double cartTotalPrice = transactionList.stream()
	                .mapToDouble(transaction -> transaction.getPriceHoodie() * transaction.getQuantityHoodie())
	                .sum();
	        cartTotalPLbl.setText("Cart's Total Price: " + cartTotalPrice);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



	

	private void mouseEvent() {
	    productTableView.setOnMouseClicked(event -> {
	        if (event.getClickCount() == 1 && !productTableView.getSelectionModel().isEmpty()) {
	            Transaction selectedTransaction = productTableView.getSelectionModel().getSelectedItem();
	            if (selectedTransaction != null) {
	                hoodieIdLbl.setText("ID: " + selectedTransaction.getHoodieId());
	                hoodieNameLbl.setText("Name: " + selectedTransaction.getHoodieName());
	                hoodiePriceLbl.setText("Price: " + selectedTransaction.getPriceHoodie());
	                hoodieQuantityLbl.setText("Quantity: " + selectedTransaction.getQuantityHoodie());

	                double totalPrice = selectedTransaction.getPriceHoodie() * selectedTransaction.getQuantityHoodie();
	                totalPriceLbl.setText("Total Price: " + totalPrice);
	            }
	        }
	    });
	    
	    removeBtn.setOnAction(event -> {
	        Transaction selectedTransaction = productTableView.getSelectionModel().getSelectedItem();
	            removeProductFromCart(selectedTransaction);
	            try {
					new Cart().start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    });
	    
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
	
	    

	    checkoutBtn.setOnAction(event -> {
	        if (transactionList.isEmpty()) {
	            Alert emptyCartAlert = new Alert(Alert.AlertType.ERROR);
	            emptyCartAlert.setTitle("Error");
	            emptyCartAlert.setHeaderText(null);
	            emptyCartAlert.setContentText("Empty Cart.");
	            emptyCartAlert.showAndWait();
	        } else {
	            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	            confirmationAlert.setTitle("Payment Confirmation");
	            confirmationAlert.setHeaderText(null);
	            confirmationAlert.setContentText("Proceed with payment?");
	        }
	    });

	    
	}


	public void alert() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Item Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item to remove.");
        alert.showAndWait();
	}
	

	public void style() {
		
	}

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		style();
		
		
		
		this.stage = primaryStage;
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setTitle("hO-Ohdie");
		
		 if (!dataLoaded) {
	            loadDataFromDatabase();
	            dataLoaded = true;
	        }
		
	}

}
