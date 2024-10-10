package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.Connect;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {
	
	BorderPane bp;
	VBox vb;
	HBox hb1;
	HBox hb2;

	Scene scene;
	Stage stage;
	
	Button loginBtn;
	TextField usernameTf;
	PasswordField passwordPf;
	Label loginLbl, usernameLbl, passwordLbl;
	MenuBar menuBar;
	Menu login;
	MenuItem regis;
	
	
	public void initialize() {
		bp = new BorderPane();
		vb = new VBox();
		hb1 = new HBox();
		hb2 = new HBox();
		
		
		loginBtn = new Button("Login");
		usernameTf = new TextField();
		passwordPf = new PasswordField();
		loginLbl = new Label("Login");
		usernameLbl = new Label("Username: ");
		passwordLbl = new Label("Password: ");
		menuBar = new MenuBar();
		login = new Menu("Login");
		regis = new MenuItem("Register");
		
		login.getItems().add(regis);
		menuBar.getMenus().add(login);
		
		
		vb.getChildren().addAll(loginLbl,hb1,hb2,loginBtn);
		hb1.getChildren().addAll(usernameLbl,usernameTf);
		hb2.getChildren().addAll(passwordLbl,passwordPf);
		
		bp.setTop(menuBar);
		bp.setCenter(vb);
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void handling() {
	    Connect con = Connect.getInstance();

	    loginBtn.setOnAction(e -> {
	        try {
	            ResultSet rs = con.selectData("SELECT * FROM user ");

	            while (rs.next()) {
	                String u = rs.getString("Username");
	                String p = rs.getString("Password");

	                if (usernameTf.getText().equals(u) && passwordPf.getText().equals(p)) {
	                    String role = rs.getString("Role");
	                    if ("Admin".equals(role)) {
	                        try {
								new EditProduct().start(stage);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                    } else if ("User".equals(role)) {
	                        try {
	                            new HomeScreen().start(stage);
	                        } catch (Exception e1) {
	                            e1.printStackTrace();
	                        }
	                    } else {
	                        showAlert();
	                    }
	                    return; 
	                }
	            }

	            showAlert();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            showAlert();
	        }
	    });

	    regis.setOnAction(e -> {
	        try {
	            new Register().start(stage);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            showAlert();
	        }
	    });
	}

		
		
	
	public void style() {
		vb.setAlignment(Pos.CENTER);
		hb1.setAlignment(Pos.CENTER);
		hb2.setAlignment(Pos.CENTER);
		loginLbl.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,50));
		VBox.setMargin(loginLbl, new Insets(0, 0, 20, 0));
	
		
	}

	public static void main(String[] args) {
		launch(args);

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
	
	 

 private void showAlert() {
	 Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error");
     alert.setHeaderText("Error");
     alert.setContentText("Wrong Credential");
     alert.show();
     
     
	 }
}
