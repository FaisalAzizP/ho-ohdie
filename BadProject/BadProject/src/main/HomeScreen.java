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
import model.Cartdata;
import model.Hoodie;
	import model.Transaction;
	
	public class HomeScreen extends Application {
		
		BorderPane bp;
		GridPane gp;
		VBox vb1,vb2;
		ScrollPane sp;
		HBox hb;
		Stage stage;
		Scene scene;
		MenuBar menu;
		Menu accountM, userM,adminM;
		MenuItem logoutMi, homeMi,cartMi,historyMi,editPMi;
		Label hoodieLbl,hoodieDLbl,hoodieIdLbl,hoodieNameLbl,hoodiePriceLbl,hoodieQuantityLbl,hoodieTpLbl;
		ListView<Transaction> list;
	
		Spinner<Integer> quantitySp;
		Button addBtn;
		
		private final ObservableList<Transaction>transactionList= FXCollections.observableArrayList();
		
		
		
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
			editPMi = new MenuItem("Edit");
			adminM = new Menu("Admin");
			hoodieLbl = new Label("Ho-odie");
			hoodieDLbl = new Label("Hoodie Detail");
			hoodieIdLbl = new Label();
			hoodieNameLbl = new Label();
			hoodiePriceLbl = new Label();
			hoodieQuantityLbl = new Label("Quantity");
			hoodieTpLbl = new Label("Total Price");
			quantitySp = new Spinner<>(1,999,1);
			addBtn = new Button("Add to cart");
	
			list = new ListView<>();
			populateListView();
			
			
			bp.setTop(menu);
			userM.getItems().addAll(homeMi,cartMi,historyMi);
			accountM.getItems().add(logoutMi);
			adminM.getItems().add(editPMi);
			menu.getMenus().addAll(accountM,userM);
			
				list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		            if (newValue != null) {
		            	displayTransactionDetails(newValue);
		            }
	        });
			
		
			bp.setCenter(gp);
			gp.setAlignment(Pos.BASELINE_CENTER.TOP_CENTER);
			bp.setLeft(vb1);
			vb2.setAlignment(Pos.CENTER_LEFT);
			
			
			gp.add(vb2,1 , 0);
			hb.getChildren().addAll(quantitySp,hoodieTpLbl);
			vb1.getChildren().addAll(hoodieLbl,list/*listview*/);
			vb2.getChildren().addAll(hoodieDLbl);
			
			
			
			scene = new Scene(bp, 600, 600);
			
		}
		public void register(String uid, String hid,int q) {
		    Connect con = Connect.getInstance();
		
		   

		                       
		    con.registercartd("INSERT INTO `cart` (`HoodieID`, `Quantity`) VALUES(?,?,?)",
		  
	                new Cartdata(uid, hid, q));

	        showAlert("Registration Successful!", "Hoodie registered successfully");
		                        
		            
		                
		    loadHomeScreenPage();
		}
	
		
		private void showAlert(String title, String content) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	        loadHomeScreenPage();
		}
		
		private void loadHomeScreenPage() {
		    initialize();
		    style();
		    setMenuAction();
		    stage.setScene(scene);
		    stage.setTitle("hO-Ohdie");
		}
		public void populateListView() {
		    Connect con = Connect.getInstance();
		    ResultSet rs = con.selectData("SELECT HoodieID, HoodieName ,HoodiePrice FROM Hoodie");
	
		    list.setCellFactory(param -> new ListCell<Transaction>() {
		        @Override
		        protected void updateItem(Transaction item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty || item == null) {
		                setText(null);
		            } else {
		                setText(item.getHoodieId() +"	"+item.getHoodieName());
		            }
		        }
		    });
	
		    try {
		        while (rs.next()) {
		            String hoodieId = rs.getString("HoodieID");
		            String hoodieName = rs.getString("HoodieName");
		            double priceHoodie = rs.getDouble("HoodiePrice");
	
		            Transaction transactions = new Transaction(hoodieId, hoodieName, priceHoodie,0);// Dummy value for price
		            transactionList.add(transactions);
		        }
	
		        list.setItems(transactionList);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	
		private void displayTransactionDetails(Transaction selectedTransaction) {
	        // Display details of the selected Hoodie
	        hoodieIdLbl.setText("ID: " + selectedTransaction.getHoodieId());
	        hoodieNameLbl.setText("Name: " + selectedTransaction.getHoodieName());
	        hoodiePriceLbl.setText("Price: " + selectedTransaction.getPriceHoodie()); 

	        
	        vb2.getChildren().removeAll(hoodieIdLbl,hoodieNameLbl,hoodiePriceLbl,hoodieQuantityLbl,hb,addBtn);

	        vb2.getChildren().addAll(hoodieIdLbl,hoodieNameLbl,hoodiePriceLbl,hoodieQuantityLbl,hb,addBtn);
	    }
	
		
		
	
		public void style() {
			
		}
		
		public void setMenuAction() {
			
			addBtn.setOnAction(event ->{
				
	           
	        	
	         	String userId= "US" + String.format("%03d", (int) (Math.random() * 1000));
	        	String hoodieId= "HO" + String.format("%03d", (int) (Math.random() * 1000));
	        	int quantity = quantitySp.getValue();

	            register(userId,hoodieId,quantity);
	            
	            try {
					new HomeScreen().start(stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		}
	
		
	
		@Override
		public void start(Stage primaryStage) throws Exception {
			initialize();
			style();
			setMenuAction();
			this.stage = primaryStage;
			this.stage.setScene(scene);
			this.stage.show();
			this.stage.setTitle("hO-Ohdie");
			
		}
	
	}
