/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.SesionTipo;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLNovaSessioTipusController implements Initializable {

    
    @FXML
    private GridPane gridPaneAS;
    @FXML
    private ImageView fonsAS;
    @FXML
    private TextField minEscalfament;
    @FXML
    private TextField minExercici;
    @FXML
    private TextField minDescansExercici;
    @FXML
    private TextField minDescansCircuit;
    @FXML
    private TextField segEscalfament;
    @FXML
    private TextField segExercici;
    @FXML
    private TextField segDescansExercici;
    @FXML
    private TextField segDescansCircuit;
    @FXML
    private TextField nombreExercici;
    @FXML
    private TextField nombreCircuit;
    @FXML
    private TextField nom;
    @FXML
    private Text error0;
    @FXML
    private Text error1;
    @FXML
    private Text error2;
    @FXML
    private Text error3;
    @FXML
    private Text error4;
    @FXML
    private Text error5;
    @FXML
    private Text error6;
    @FXML
    private Text textError;

    
    
    private SesionTipo sesionTipoNova;
    private boolean guarda;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //AJUSTAR IMAGEVIEW AL GRIDPANE
        fonsAS.fitHeightProperty().bind(gridPaneAS.heightProperty());
        fonsAS.fitWidthProperty().bind(gridPaneAS.widthProperty());
        
        //POSSAR ELS ERRORS EN INVISIBLE
        textError.setVisible(false);
        error0.setVisible(false);
        error1.setVisible(false);
        error2.setVisible(false);
        error3.setVisible(false);
        error4.setVisible(false);
        error5.setVisible(false);
        error6.setVisible(false);
        
        //CREACIÓ DE TOTS ELS FILRES PER ALS TEXT FIELD
        nom.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz1234567890: ".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        nombreCircuit.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || nombreCircuit.getText().length() == 2) {
                keyEvent.consume();
            }
        });
        nombreExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || nombreExercici.getText().length() == 2) {
                keyEvent.consume();
            }
        });
        minDescansCircuit.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || minDescansCircuit.getText().length() == 2) {
                keyEvent.consume();
            }
        });
        minDescansExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890:".contains(keyEvent.getCharacter()) || minDescansExercici.getText().length() == 2) {
                keyEvent.consume();
            }
        });
        minEscalfament.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || minEscalfament.getText().length() == 2) {
                keyEvent.consume();
            }
        });
        minExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890:".contains(keyEvent.getCharacter()) || minExercici.getText().length() == 2) {
                keyEvent.consume();
            }
        });
        segDescansCircuit.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segDescansCircuit.getText().length() == 2) {
                keyEvent.consume();
            } else if(segDescansCircuit.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        segDescansExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segDescansExercici.getText().length() == 2) {
                keyEvent.consume();
            } else if(segDescansExercici.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        segEscalfament.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segEscalfament.getText().length() == 2) {
                keyEvent.consume();
            } else if(segEscalfament.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        segExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segExercici.getText().length() == 2) {
                keyEvent.consume();
            } else if(segExercici.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
    }    
    
    public void setSesioTipus(SesionTipo st) {
        sesionTipoNova = st;
    }
    
    public boolean haGuardat() {
        return guarda;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) nom.getScene().getWindow()).close();
    }
    
    @FXML
    private void guardarNovaSessio(ActionEvent event) {

        //AGAFAR ELS VALORS DONATS EN ELS TEXT FIELD
        String n = nom.getText().trim(), nExer = nombreExercici.getText(), nCir = nombreCircuit.getText().trim(),
                minExer = minExercici.getText().trim(), minDescansExer = minDescansExercici.getText().trim(), minEscalf = minEscalfament.getText().trim(),
                segExer = segExercici.getText().trim(), segDescansExer = segDescansExercici.getText().trim(), segEscalf = segEscalfament.getText().trim(),
                minDescansCir = minDescansCircuit.getText().trim(),
                segDescansCir = segDescansCircuit.getText().trim();

        //COMPROBAR QUE ELS CAMPS ESTAN TOTS PLENS
        if (n.equals("") || nExer.equals("") || nCir.equals("") || minExer.equals("") || minDescansExer.equals("") || minEscalf.equals("")
                || segExer.equals("") || segDescansExer.equals("") || segEscalf.equals("") || minDescansCir.equals("") || segDescansCir.equals("")) {

            textError.setVisible(true);

            if (n.equals("")) {
                error0.setVisible(true);
            } else {
                error0.setVisible(false);
            }

            if (nExer.equals("")) {
                error1.setVisible(true);
            } else {
                error1.setVisible(false);
            }

            if (nCir.equals("")) {
                error2.setVisible(true);
            } else {
                error2.setVisible(false);
            }

            if (minExer.equals("") || segExer.equals("")) {
                error4.setVisible(true);
            } else {
                error4.setVisible(false);
            }

            if (minDescansExer.equals("") || segDescansExer.equals("")) {
                error5.setVisible(true);
            } else {
                error5.setVisible(false);
            }

            if (minEscalf.equals("") || segEscalf.equals("")) {
                error3.setVisible(true);
            } else {
                error3.setVisible(false);
            }

            if (minDescansCir.equals("") || segDescansCir.equals("")) {
                error6.setVisible(true);
            } else {
                error6.setVisible(false);
            }
        } else {

            //PASSAR VALORS DONATS A INT
            while (n.startsWith(" ")) {
                n = n.substring(1);
            }
            Integer nExerINT = Integer.parseInt(nExer);
            Integer nCirINT = Integer.parseInt(nCir);
            Integer minExerINT = Integer.parseInt(minExer);
            Integer minDescansExerINT = Integer.parseInt(minDescansExer);
            Integer minEscalfINT = Integer.parseInt(minEscalf);
            Integer minDescansCirINT = Integer.parseInt(minDescansCir);
            Integer segExerINT = Integer.parseInt(segExer);
            Integer segDescansExerINT = Integer.parseInt(segDescansExer);
            Integer segEscalfINT = Integer.parseInt(segEscalf);
            Integer segDescansCirINT = Integer.parseInt(segDescansCir);

            //CREACIÓ SESSIÓ TIPUS NOVA
            //1ºPassar minuts a segons
            int tExer, tEscalf, tDescansExer, tDescansCir;
            tExer = (minExerINT * 60) + segExerINT;
            tEscalf = (minEscalfINT * 60) + segEscalfINT;
            tDescansExer = (minDescansExerINT * 60) + segDescansExerINT;
            tDescansCir = (minDescansCirINT * 60) + segDescansCirINT;

            //2ºGuardar-ho en sesionTipoNova
            sesionTipoNova.setCodigo(n);
            sesionTipoNova.setD_circuito(tDescansCir);
            sesionTipoNova.setD_ejercicio(tDescansExer);
            sesionTipoNova.setNum_circuitos(nCirINT);
            sesionTipoNova.setNum_ejercicios(nExerINT);
            sesionTipoNova.setT_calentamiento(tEscalf);
            sesionTipoNova.setT_ejercicio(tExer);

            guarda = true;

            ((Stage) nom.getScene().getWindow()).close();
        }
    }
    
}

   
    

