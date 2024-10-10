package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.User;


public class EditProduct extends Application{
	
	Scene scene;
	Stage stage;
	ScrollPane sp;
	BorderPane bp;
	GridPane gp;
	VBox vb1,vb2,vb3 ,vb4;
	HBox hb1,hb2,hb3, hb4;
	TableView<Hoodie> productTableView;
	Label editProductLbl,updatedelLbl,insertLbl,hoodieIdLbl,nameHoodieLbl,priceHoodieLbl,nameLbl,pricelbl;
	TextField priceHoodieTf, nameTf,priceTf;
	Button updateBtn, deleteBtn, insertBtn;
	MenuBar menu;
	Menu accountM,adminM;
	MenuItem logoutMi,editPMi;
	 private final ObservableList<Hoodie> hoodieList = FXCollections.observableArrayList();

	
	private void initialize() {
		editProductLbl = new Label("Edit Product");
		updatedelLbl = new Label("Update & Delete Hoodie(s)");
		insertLbl = new Label("Insert Hoodie");
		hoodieIdLbl = new Label("Hoodie ID: ");
		nameHoodieLbl = new Label("Name: ");
		priceHoodieLbl = new Label("Price: ");
		nameLbl = new Label("Name: ");
		pricelbl = new Label("Price: ");
		priceHoodieTf = new TextField();
		nameTf = new TextField("Input name");
		priceTf = new TextField("Input price");
		updateBtn = new Button("Update Price");
		deleteBtn = new Button("Delete Hoodie");
		menu = new MenuBar();
		accountM = new Menu("Account");
		adminM = new Menu("Admin");
		editPMi = new MenuItem("Edit Product");
		logoutMi = new MenuItem("Logout");
		insertBtn = new Button("Insert");
		 productTableView = new TableView<>();
		 hb1 = new HBox();
		 hb2 = new HBox();
		 hb3 = new HBox();
		 hb4 = new HBox();
		sp = new ScrollPane();
		bp = new BorderPane();
		gp = new GridPane();
		vb1 = new VBox();
		vb2 = new VBox();
		vb3 = new VBox();
		vb4 = new VBox();
		
		setTable();
		loadDataFromDatabase();
		bp.setCenter(gp);
		bp.setTop(menu);
		gp.add(vb1, 0, 0);
		gp.add(vb4, 1, 0);
		
		hb2.getChildren().addAll(updateBtn,deleteBtn);
		hb3.getChildren().addAll(nameLbl,nameTf);
		hb4.getChildren().addAll(pricelbl,priceTf);
		productTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	displayTransactionDetails(newValue);
            }
    });
		
		menu.getMenus().addAll(accountM,adminM);
		adminM.getItems().add(editPMi);
		accountM.getItems().add(logoutMi);
		vb1.getChildren().addAll(editProductLbl,productTableView);
		vb2.getChildren().addAll(updatedelLbl);
		vb3.getChildren().addAll(insertLbl,hb3,hb4,insertBtn);
		vb4.getChildren().addAll(vb2,vb3);
		scene = new Scene(bp, 600, 600);
	}
	
	private void displayTransactionDetails(Hoodie selectedHoodie) {
        hoodieIdLbl.setText("ID: " + selectedHoodie.getHoodieId());
        nameHoodieLbl.setText("Name: " + selectedHoodie.getHoodieName());
       

        
        vb2.getChildren().removeAll(hoodieIdLbl,nameHoodieLbl,priceHoodieLbl,priceHoodieTf,hb2);

        vb2.getChildren().addAll(hoodieIdLbl,nameHoodieLbl,priceHoodieLbl,priceHoodieTf,hb2);
    }
	private void style() {

	}
	
	private void setTable() {
		Connect con = Connect.getInstance();
		
		TableColumn<Hoodie, String> idCol = new TableColumn<>("Hoodie ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("hoodieId"));
		idCol.setMinWidth(sp.getWidth()/2);
		idCol.setResizable(false);
		
		TableColumn<Hoodie, String> nameCol = new TableColumn<>("Hoodie Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("hoodieName"));
		nameCol.setMinWidth(sp.getWidth()/2);
		nameCol.setResizable(false);
		
		TableColumn<Hoodie, Double> priceCol = new TableColumn<>("Hoodie Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("priceHoodie"));
		priceCol.setMinWidth(sp.getWidth()/2);
		priceCol.setResizable(false);
		
		productTableView.getColumns().addAll(idCol,nameCol,priceCol);

		
	}
	
	private void loadDataFromDatabase() {
        Connect con = Connect.getInstance();
        ResultSet rs = con.selectData("SELECT * FROM Hoodie");

        try {
            while (rs.next()) {
                String hoodieId = rs.getString("HoodieID");
                String hoodieName = rs.getString("HoodieName");
                double price = rs.getDouble("HoodiePrice");

                Hoodie hoodie = new Hoodie(hoodieId, hoodieName, price);
                hoodieList.add(hoodie);
            }

            productTableView.setItems(hoodieList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private void updatePrice(double p,String i,String n) {
		Connect con = Connect.getInstance();
		String query = "UPDATE Hoodie SET HoodiePrice = ? WHERE HoodieID = ? AND HoodieName = ? ";
		
		con.updateHoodie(query,p, i, n);
	}
	
	public void register(String hid, String hn, double hp) {
	    Connect con = Connect.getInstance();
	
	   

	                        if (hn.length()>=5) {
	                            if (hp >0) {
	                                con.registerHoodie("INSERT INTO `hoodie` (`HoodieID`, `HoodieName`, `HoodiePrice`) VALUES(?,?,?)",
	                                        new Hoodie(hid,hn,hp));
	                                showAlert("Registration Successful!", "Hoodie registered successfully");
	                                clearFields();
	                            } else {
	                                showAlert("Error", "Hoodie Price must be more than 0");
	                            }
	                        } else {
	                            showAlert("Error", "Hoodie Name length must be 5 characters");
	                        }
	            
	                
	    loadEditProductPage();
	}
	private void clearFields() {
        
        nameTf.clear();
        priceTf.clear();
    }
	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        loadEditProductPage();
	}
	
	private void handling() {
		updateBtn.setOnAction(e->{
			 double newPrice = Double.parseDouble(priceHoodieTf.getText());
	            updatePrice(newPrice,hoodieIdLbl.getText(),nameHoodieLbl.getText());
		});
		logoutMi.setOnAction(e->{
			try {
				new Login().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		deleteBtn.setOnAction(event -> {
            Hoodie selectedHoodie = productTableView.getSelectionModel().getSelectedItem();
                removeProductFromCart(selectedHoodie);
                try {
                    new EditProduct().start(stage);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        });
		
		insertBtn.setOnAction(event ->{
			
            String hoodieId = "HO" + String.format("%03d", (int) (Math.random() * 1000));
        	String hoodieName= nameTf.getText();
         	double priceHoodie =  Double.parseDouble(priceTf.getText());

            register(hoodieId,hoodieName,priceHoodie);
            
            try {
				new EditProduct().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	
	private void removeProductFromCart(Hoodie selectedHoodie) {
        Connect con = Connect.getInstance();
        String hoodieId = selectedHoodie.getHoodieId();
        String Query = "DELETE FROM Hoodie WHERE HoodieID = '" + hoodieId + "'";

        try {
            con.deleteHoodie(Query);

            hoodieList.remove(selectedHoodie);

            productTableView.setItems(hoodieList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void loadEditProductPage() {
	    initialize();
	    style();
	    handling();
	    stage.setScene(scene);
	    stage.setTitle("hO-Ohdie");
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		style();
		handling();
		this.stage = primaryStage;
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setTitle("hO-Ohdie");
		
		
	}
	
}
