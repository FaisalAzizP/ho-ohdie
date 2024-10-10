package main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.*;

public class PaymentPop extends Application {

	
    private int transactionIndex = 0; // Track transaction index
    
    

    @Override
    public void start(Stage primaryStage) {
        // Components
        Label confirmationLabel = new Label("Are you sure you want to proceed with payment?");
        Button makePaymentButton = new Button("Make Payment");
        Button cancelButton = new Button("Cancel");

        // Event handling for Make Payment button
        makePaymentButton.setOnAction(e -> {
            boolean paymentSuccessful = processPayment(); // Call method to process payment

            if (paymentSuccessful) {
                displaySuccessAlert(); // Display success alert if payment is successful
                // Redirect user to History Scene
                // Replace the following line with your code to navigate to the History Scene
                System.out.println("Redirecting to History Scene...");
            }
        });

        // Event handling for Cancel button
        cancelButton.setOnAction(e -> {
            // Close the window if Cancel is clicked
            primaryStage.close();
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(confirmationLabel, makePaymentButton, cancelButton);

        // Window setup
        Window paymentConfirmationWindow = new Window("Payment Confirmation");
        paymentConfirmationWindow.setContentPane(layout);
        paymentConfirmationWindow.setPrefSize(300, 150);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        paymentConfirmationWindow.setMovable(false);

        // Show the window
        Scene scene = new Scene(paymentConfirmationWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkout");
        primaryStage.show();
    }

    // Method to process payment and perform necessary actions
    private boolean processPayment() {
        // Simulating payment processing and database insertion
        String transactionID = generateTransactionID();
        System.out.println("Processing payment... Transaction ID: " + transactionID);

        // Simulated success/failure of payment
        boolean paymentSuccessful = true; // Change based on actual payment processing

        if (paymentSuccessful) {
            // Simulated clearing of user's cart
            System.out.println("Clearing user's cart...");
        }

        return paymentSuccessful;
    }

    // Method to generate Transaction ID
    private String generateTransactionID() {
        transactionIndex++; // Increment transaction index
        String transactionIndexFormatted = String.format("%03d", transactionIndex);
        return "TR" + transactionIndexFormatted;
    }

    // Method to display a success alert
    private void displaySuccessAlert() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Payment Successful");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Payment successful! Redirecting to History Scene.");
        successAlert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
