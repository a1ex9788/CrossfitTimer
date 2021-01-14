/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacio;

import accesoBD.AccesoBD;
import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Alejandro
 */
public class TemporitzadorCrossfit extends Application {
    
    private static AccesoBD baseDeDades;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLTemporitzadorCrossfit.fxml"));
        
        Scene scene = new Scene(root);
        
        File imageFile = new File("src/imatges/icono.png"); 
        String fileLocation = imageFile.toURI().toString(); 
        Image icono = new Image(fileLocation);
        stage.getIcons().add(icono);
        
        stage.setOnCloseRequest(a -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle(null);
            confirm.setHeaderText("Salvar els canvis");
            confirm.setContentText("Vol que els canvis es guarden en la base de dades??\n\n");

            ButtonType acceptar = new ButtonType("SI");
            ButtonType cancellar = new ButtonType("NO");
            confirm.getButtonTypes().setAll(acceptar, cancellar);

            Optional<ButtonType> resposta = confirm.showAndWait();

            if (resposta.isPresent() && resposta.get() == acceptar) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Temporitzador Crossfit");
                alerta.setHeaderText(null);
                alerta.setContentText("L'aplicació està guardant els canvis. Aquest procés pot tardar uns minuts.\n\n");
                alerta.getButtonTypes().clear();
                alerta.show();
                baseDeDades.salvar();
                
                Platform.exit();
            }
        });

        stage.setTitle("Temporitzador Crossfit");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void setBD(AccesoBD a) {
        baseDeDades = a;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
