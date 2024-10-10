package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

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
import model.User;

public class Register extends Application {
	
	BorderPane bp;
	VBox vb;
	HBox hb;
	Stage stage;
	Scene scene;
	Label registerLbl, emailLbl,usernameLbl,passwordLbl,confirmpwLbl,phoneNLbl,genderLbl,addressLbl;
	TextField emailTf,usernameTf,phoneNTf;
	PasswordField passwordPf, confirmpwPf;
	RadioButton femaleRb,maleRb;
	CheckBox termCb;
	TextArea addressCb;
	Button registerBtn;
	MenuBar menu;
	Menu register;
	MenuItem login;
	ToggleGroup gender;
	
	
	
	
	public void initialize() {
		bp = new BorderPane();
		vb = new VBox();
		hb = new HBox();
		registerLbl = new Label("Register");
		emailLbl = new Label("Email: ");
		usernameLbl = new Label("Username: ");
		passwordLbl = new Label("Password: ");
		confirmpwLbl = new Label("Confirm Password: ");
		phoneNLbl = new Label("Phone Number: ");
		genderLbl = new Label("Gender: ");
		addressLbl = new Label("Address: ");
		termCb = new CheckBox("i Agree to Term & Condition");
		femaleRb = new RadioButton("Female");
		maleRb = new RadioButton("Male");
		emailTf = new TextField("Input email must end with @hoodie.com");
		usernameTf = new TextField("Input an unique username");
		phoneNTf = new TextField("example +62081234567890");
		passwordPf = new PasswordField();
		confirmpwPf = new PasswordField();
		addressCb = new TextArea("Input address");
		registerBtn = new Button("Register");
		menu = new MenuBar();
		register = new Menu("Register");
		login = new MenuItem("Login");
		
		
		
		vb.getChildren().addAll(registerLbl,emailLbl,emailTf,usernameLbl,usernameTf,passwordLbl,passwordPf,confirmpwLbl,confirmpwPf,phoneNLbl,phoneNTf,genderLbl,hb,addressLbl,addressCb,termCb,registerBtn);
		hb.getChildren().addAll(maleRb,femaleRb);
		
		register.getItems().add(login);
		menu.getMenus().add(register);
		
		gender = new ToggleGroup();
		gender.getToggles().addAll(maleRb,femaleRb);
		
		
		
		
		bp.setTop(menu);
		bp.setCenter(vb);
		bp.setBottom(registerBtn);
		
		
		scene = new Scene(bp, 600, 600);
	}
	
	public void handling() {
		
		login.setOnAction(e-> {
			try {
				new Login().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		registerBtn.setOnAction(e -> {
			String id = "US" + String.format("%03d", (int) (Math.random() * 1000));
            String email = emailTf.getText();
            String username = usernameTf.getText();
            String password = passwordPf.getText();
            String phoneNumber = phoneNTf.getText();
            String address = addressCb.getText();
            String gender = ((RadioButton) this.gender.getSelectedToggle()).getText();
            String role = "User";

            register(id, email, username, password, phoneNumber, address, gender,role);
            
            try {
				new Login().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
	}
	
	public void register(String id, String e, String u, String p, String pn, String a, String g,String r) {
	    Connect con = Connect.getInstance();
	    String confirmPassword = confirmpwPf.getText();
	    if (e.endsWith("@hoohdie.com")) {
	        if (isValidUsername(u)) {
				if (p.length() >= 5 && p.equals(confirmPassword)) {
	                if (pn.length() == 14 && pn.startsWith("+62")) {
	                    if (!a.isEmpty()) {
	                        if (g != null) {
	                            if (termCb.isSelected()) {
	                                con.registerUser("INSERT INTO user (UserID, Email, Username, Password, PhoneNumber, Address, Gender,Role) VALUES(?,?,?,?,?,?,?,?)",
	                                        new User(id,e,u,p,pn,a,g, r));
	                                showAlert("Registration Successful!", "User registered successfully");
	                                clearFields();
	                            } else {
	                                showAlert("Error", "Please agree to the Terms & Conditions");
	                            }
	                        } else {
	                            showAlert("Error", "Gender Must be selected");
	                        }
	                    } else {
	                        showAlert("Error", "Address must be filled");
	                    }
	                } else {
	                    showAlert("Error", "Phone number length must be 14 characters and starts with +62");
	                }
	            } else {
	                showAlert("Error", "Password should be at least 5 characters and match Confirm Password");
	            }
	        } else {
	            showAlert("Error", "Username already taken");
	        }
	    } else {
	        showAlert("Error", "Email should end with @hoohdie.com");
	    }
	    loadRegistrationPage();
	}
	
	private void clearFields() {
        emailTf.clear();
        usernameTf.clear();
        passwordPf.clear();
        confirmpwPf.clear();
        phoneNTf.clear();
        addressCb.clear();
    }

	private boolean isValidUsername(String u) {
	    Connect con = Connect.getInstance();
	    String query = "SELECT * FROM user WHERE Username = '" + u + "'";

	    try {
	        ResultSet resultSet = con.selectData(query);
	        return !resultSet.next(); // Return true if username doesn't exist
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public void style() {
		registerLbl.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,40));
		emailLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		usernameLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		passwordLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		confirmpwLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		phoneNLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		genderLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		addressLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		BorderPane.setAlignment(registerBtn, Pos.BOTTOM_RIGHT);
		BorderPane.setMargin(registerBtn, new Insets(20));
		bp.setMargin(vb, new Insets(20));
		
		vb.setSpacing(5);
	}
	
	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        loadRegistrationPage();
	}
	
	private void loadRegistrationPage() {
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
