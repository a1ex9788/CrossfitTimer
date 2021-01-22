/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import accesoBD.AccesoBD;
import aplicacio.TemporitzadorCrossfit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;
import modelo.SesionTipo;


/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class FXMLTemporitzadorCrossfitController implements Initializable {

    @FXML
    private TableView<Grupo> taulaGrups;
    @FXML
    private TableColumn<Grupo, String> codiGrup;
    @FXML
    private TableColumn<Grupo, String> descripcioGrup;
    @FXML
    private GridPane gridAuxSessions;
    @FXML
    private ImageView auxFonsSessions;
    @FXML
    private GridPane gridAuxGrafiques;
    @FXML
    private ImageView auxFonsGrafiques;
    @FXML
    private TilePane contenidorSesions;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridAuxCrono;
    @FXML
    private ImageView auxFonsCrono;
    @FXML
    private ComboBox<Grupo> buscadorGrup;
    @FXML
    private TableView<SesionM> taulaGrafiques;
    @FXML
    private TableColumn<SesionM, String> dataSessio;
    @FXML
    private TableColumn<SesionM, String> duracioSessio;
    @FXML
    private TableColumn<SesionM, String> sessioTipusSessio;
    @FXML
    private TableColumn<SesionM, Boolean> checkBoxSessio;
    @FXML
    private LineChart<String, Number> grafica;
    @FXML
    private NumberAxis eixY;
    @FXML
    private CategoryAxis eixX;
    @FXML
    private Rectangle fonsTransparent;
    @FXML
    private ListView<SesionTipo> llistaSessionsTipus;
    @FXML
    private ComboBox<Grupo> grupElegit;
    @FXML
    private ComboBox<String> tipusDeSessio;
    @FXML
    private GridPane eleccioSessio;
    @FXML
    private VBox creacioNovaSessioTipus;
    @FXML
    private Text error0;
    @FXML
    private Text error1;
    @FXML
    private Text error2;
    @FXML
    private TextField nom;
    @FXML
    private TextField nombreExercici;
    @FXML
    private TextField nombreCircuit;
    @FXML
    private Text error3;
    @FXML
    private Text error4;
    @FXML
    private Text error5;
    @FXML
    private Text error6;
    @FXML
    private TextField minEscalfament;
    @FXML
    private TextField segEscalfament;
    @FXML
    private TextField minExercici;
    @FXML
    private TextField segExercici;
    @FXML
    private TextField minDescansExercici;
    @FXML
    private TextField segDescansExercici;
    @FXML
    private TextField minDescansCircuit;
    @FXML
    private TextField segDescansCircuit;
    @FXML
    private Text textError;
    @FXML
    private Label tWork;
    @FXML
    private Label tTranscorregut;
    @FXML
    private Label tRestant;
    @FXML
    private Label nExercici;
    @FXML
    private Label nCircuit;
    @FXML
    private ImageView imatgePararReanudar;
    @FXML
    private HBox hBoxAuxResize;
    @FXML
    private Button restartButton;
    @FXML
    private StackPane paneFiSessio;
    @FXML
    private Text tDuracioSessio;
    @FXML
    private Rectangle fonsTransparent2;
    @FXML
    private Tab tab2;
    @FXML
    private Tab tab3;
    @FXML
    private Button eixirButton;
    @FXML
    private Text paneDescans;
    @FXML
    private VBox paneNExer;
    
    
    private ObservableList<Grupo> grups;
    private ObservableList<SesionM> sesions;
    private ObservableList<SesionTipo> sesionsTipus;
    private AccesoBD baseDeDades;
    private Gym gimnas;
    
    private Sesion sesionActual;
    private Grupo grupActual;
    private final StringProperty minISeg = new SimpleStringProperty("00:00");
    private final StringProperty tTrans = new SimpleStringProperty("00:00:00");
    private final StringProperty tRest = new SimpleStringProperty("00:00:00");
    private final StringProperty numEx = new SimpleStringProperty("0/0");
    private final StringProperty numCir = new SimpleStringProperty("0/0");
    
    private MeuServei service;
    private MeuaTasca tascaTempsTrans;
    private final BooleanProperty fiSessio = new SimpleBooleanProperty(false);
    private boolean primeraVegada = true;
    private boolean roig = true; //Animació roig/blanc
    
    private final BooleanProperty enDescans = new SimpleBooleanProperty(false);
    
    private XYChart.Series<String, Number> serieTTreball = new XYChart.Series<>();
    private XYChart.Series<String, Number> serieTDescans = new XYChart.Series<>();
    private XYChart.Series<String, Number> serieTTotal = new XYChart.Series<>();
    
    AudioClip sorollFiFase = new AudioClip((new File("src/sorolls/pitidet.mp3")).toURI().toString());
    AudioClip sorollFiSessio = new AudioClip((new File("src/sorolls/aplausos.mp3")).toURI().toString());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        baseDeDades = AccesoBD.getInstance();
        gimnas = baseDeDades.getGym();
        TemporitzadorCrossfit.setBD(baseDeDades);
        
        sesionsTipus = FXCollections.observableArrayList(gimnas.getTiposSesion());
        
        //CRONO
        grupElegit.converterProperty().bind(buscadorGrup.converterProperty());
        grupElegit.itemsProperty().bind(buscadorGrup.itemsProperty());
        grupElegit.valueProperty().addListener(a -> {
            if (grupElegit.getValue() != null) {
                ArrayList<Sesion> sesions = grupElegit.getValue().getSesiones();
                if (sesions.size() > 0) {
                    tipusDeSessio.setValue(null);
                    tipusDeSessio.setValue("Aprofitar sessió tipus");
                    llistaSessionsTipus.getSelectionModel().select(0);
                }
            }
        });
        
        tipusDeSessio.getItems().addAll("Aprofitar sessió tipus", "Crear nova sessió tipus");
        
        llistaSessionsTipus.setItems(sesionsTipus);
        llistaSessionsTipus.setCellFactory(a -> new CellAux());
        
        tipusDeSessio.valueProperty().addListener(a -> {
            if (tipusDeSessio.getValue() != null) {
                if(tipusDeSessio.getValue().equals("Aprofitar sessió tipus")) {
                    textError.setVisible(false);
                    llistaSessionsTipus.setDisable(false);
                    llistaSessionsTipus.setVisible(true);
                    creacioNovaSessioTipus.setVisible(false);
                }
                else {
                    textError.setVisible(false);
                    error0.setVisible(false);
                    error1.setVisible(false);
                    error2.setVisible(false);
                    error3.setVisible(false);
                    error4.setVisible(false);
                    error5.setVisible(false);
                    error6.setVisible(false);

                    creacioNovaSessioTipus.setVisible(true);
                    llistaSessionsTipus.setVisible(false);
                }
            }
        });
        //SERVICE
        tWork.textProperty().bind(minISeg);
        tTranscorregut.textProperty().bind(tTrans);
        tRestant.textProperty().bind(tRest);
        nExercici.textProperty().bind(numEx);
        nCircuit.textProperty().bind(numCir);
        
        tWork.textProperty().addListener(a->{
            if (pintarDeRoig(tWork.getText())) {
                if (roig) {
                    tWork.setTextFill(Paint.valueOf("Red"));
                    sorollFiFase.play();
                }
                else { tWork.setTextFill(Paint.valueOf("White")); }
                roig = !roig;
            } else {
                tWork.setTextFill(Paint.valueOf("White"));
            }
        });
        
        
        tascaTempsTrans = new MeuaTasca();
        tascaTempsTrans.setStringProperty(tTrans);
        service = new MeuServei();
        service.setStringPropertiesAux(minISeg, tTrans, tRest, numEx, numCir, tascaTempsTrans, fiSessio, enDescans);
        
        
        fiSessio.addListener(a -> {
            if (fiSessio.getValue()) {
                String s = tTranscorregut.getText();
                String s1 = s.substring(0,2) + "h ";
                if (s1.charAt(0) == '0') { s1 = s1.substring(1); }
                String s2 = s.substring(3, 5) + "m ";
                if (s2.charAt(0) == '0') { s2 = s2.substring(1); }
                String s3 = s.substring(6,8) + "s";
                if (s3.charAt(0) == '0') { s3 = s3.substring(1); }
                tDuracioSessio.setText(s1 + s2 + s3);
                paneFiSessio.setVisible(true);
                
                sorollFiSessio.play();
            }
        });
        
        enDescans.addListener(a -> {
            if (enDescans.getValue()) {
                paneDescans.setVisible(true);
                paneNExer.setVisible(false);
            } else {
                paneDescans.setVisible(false);
                paneNExer.setVisible(true);
            }
        });
        

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
            } else if (segDescansCircuit.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        segDescansExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segDescansExercici.getText().length() == 2) {
                keyEvent.consume();
            } else if (segDescansExercici.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        segEscalfament.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segEscalfament.getText().length() == 2) {
                keyEvent.consume();
            } else if (segEscalfament.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        segExercici.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"1234567890".contains(keyEvent.getCharacter()) || segExercici.getText().length() == 2) {
                keyEvent.consume();
            } else if (segExercici.getText().length() == 0 && "6789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        
        //SESIONS TIPUS           
        if(gimnas.getTiposSesion().isEmpty()) {
            contenidorSesions.getChildren().add(new Label("Encara no hi han sessions tipus"));
        } else {
            for (int i = 0; i < gimnas.getTiposSesion().size(); i++) {
                SesionTipo s = gimnas.getTiposSesion().get(i);
                creaTaula(s);
            }
        }
        
        //GRUPS
        grups = FXCollections.observableArrayList(gimnas.getGrupos());
        taulaGrups.setItems(grups);
        taulaGrups.setEditable(true);
        
        codiGrup.setCellValueFactory(new PropertyValueFactory("codigo"));
        codiGrup.setCellFactory(TextFieldTableCell.forTableColumn());
        codiGrup.setOnEditCommit(a -> {
            Grupo g = a.getRowValue();
            g.setCodigo(a.getNewValue());
            
            buscadorGrup.getItems().clear();
            buscadorGrup.getItems().addAll(gimnas.getGrupos());
        });
        
        descripcioGrup.setCellValueFactory(new PropertyValueFactory("descripcion"));
        descripcioGrup.setCellFactory(TextFieldTableCell.forTableColumn());
        descripcioGrup.setOnEditCommit(a -> {
            Grupo g = a.getRowValue();
            g.setDescripcion(a.getNewValue());
            
            buscadorGrup.getItems().clear();
            buscadorGrup.getItems().addAll(gimnas.getGrupos());
        });
        
        //GRÀFIQUES
        buscadorGrup.getItems().addAll(gimnas.getGrupos());
        buscadorGrup.setConverter(new StringConverter<Grupo>() {
            @Override
            public String toString(Grupo p) {
                return p.getCodigo();
            }

            @Override
            public Grupo fromString(String s) {
                for (int i = 0; i < gimnas.getGrupos().size(); i++) {
                    Grupo g = gimnas.getGrupos().get(i);
                    if (g.getCodigo().equals(s)) { return g; }
                }
                return null; //mai es va a aplegar a esta instrucció
            }
        });
        buscadorGrup.valueProperty().addListener(a -> {
            serieTTreball.getData().clear();
            serieTDescans.getData().clear();
            serieTTotal.getData().clear();
            Grupo g = buscadorGrup.getValue();
            if (g != null) {
                sesions.clear();
                for (int i = 0; i < g.getSesiones().size(); i++) {
                    Sesion s = g.getSesiones().get(i);
                    sesions.add(new SesionM(s.getFecha(), s.getDuracion(), s.getTipo()));
                }
            }
        });
        
        
        sesions = FXCollections.observableArrayList();
        taulaGrafiques.setItems(sesions);
        taulaGrafiques.setEditable(true);
         
        dataSessio.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getFecha().getDayOfMonth()
                + "/" + a.getValue().getFecha().getMonthValue()
                + "/" + a.getValue().getFecha().getYear()));
        duracioSessio.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getDuracion().getSeconds()/60 + "' " + a.getValue().getDuracion().getSeconds()%60 + "\""));
        sessioTipusSessio.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getTipo().getCodigo()));
        
        
        serieTTreball.setName("Treball total");
        serieTDescans.setName("Descans total");
        serieTTotal.setName("Temps real");  
        grafica.getData().addAll(serieTTreball, serieTDescans, serieTTotal);
        checkBoxSessio.setCellFactory(column -> new CheckBoxTableCell<>());
        checkBoxSessio.setCellValueFactory(cellData -> {
            SesionM cellValue = cellData.getValue();
            BooleanProperty property = cellValue.getSeleccionat();
            
            // Add listener to handler change
            property.addListener((observable, oldValue, newValue) -> {
                cellValue.setSeleccionat(newValue);
                LocalDateTime dia = cellValue.getFecha();
                String nom = dia.getDayOfMonth() + "/" + dia.getMonthValue() + "/" + dia.getYear() + " - " + cellValue.getTipo().getCodigo();
                if (newValue) {
                    SesionTipo st = cellValue.getTipo();
                    int tExercici = st.getT_ejercicio() * st.getNum_ejercicios() * st.getNum_circuitos();
                    int tDescans = (st.getNum_ejercicios() - 1) * st.getD_ejercicio() + (st.getNum_circuitos() - 1) * st.getD_circuito();
                    long tReal = cellValue.getDuracion().getSeconds(); //en minuts
                    
                    
                    serieTTreball.getData().add(new XYChart.Data<>(nom, tExercici/60.0));
                    
                    serieTDescans.getData().add(new XYChart.Data<>(nom, tDescans/60.0));
                      
                    serieTTotal.getData().add(new XYChart.Data<>(nom, tReal/60.0));
                } else {
                    for (int i = 0; i < serieTTreball.getData().size(); i++) {
                        if (serieTTreball.getData().get(i).getXValue().equals(nom)) { 
                            serieTTreball.getData().remove(i);
                            serieTDescans.getData().remove(i);
                            serieTTotal.getData().remove(i);
                            break;
                        }
                    }
                }
            });

            return property;
        });
        
        
        eixX.setLabel("Sessions");
        eixY.setLabel("Temps(min)");
        
                
        //FONS CRONO
        auxFonsCrono.fitHeightProperty().bind(gridAuxCrono.heightProperty());
        auxFonsCrono.fitWidthProperty().bind(gridAuxCrono.widthProperty());
        
        fonsTransparent.heightProperty().bind(gridAuxCrono.heightProperty());
        fonsTransparent.widthProperty().bind(gridAuxCrono.widthProperty());
        
        fonsTransparent2.heightProperty().bind(gridAuxCrono.heightProperty());
        fonsTransparent2.widthProperty().bind(gridAuxCrono.widthProperty());
        
        //FONS SESSIONS
        auxFonsSessions.fitHeightProperty().bind(gridAuxSessions.heightProperty());
        auxFonsSessions.fitWidthProperty().bind(gridAuxSessions.widthProperty());
        
        //FONS GRAFIQUES
        auxFonsGrafiques.fitHeightProperty().bind(gridAuxGrafiques.heightProperty());
        auxFonsGrafiques.fitWidthProperty().bind(gridAuxGrafiques.widthProperty());
        
        //RESIZE TAULA GRUPS
        codiGrup.prefWidthProperty().bind(taulaGrups.widthProperty().divide(3).subtract(1));
        descripcioGrup.prefWidthProperty().bind(taulaGrups.widthProperty().divide(3).multiply(2).subtract(1));
        
        //RESIZE TAULA GRAFIQUES
        dataSessio.prefWidthProperty().bind(taulaGrafiques.widthProperty().multiply(2).divide(7));
        duracioSessio.prefWidthProperty().bind(taulaGrafiques.widthProperty().multiply(2).divide(7));
        sessioTipusSessio.prefWidthProperty().bind(taulaGrafiques.widthProperty().multiply(2).divide(7));
        checkBoxSessio.prefWidthProperty().bind(taulaGrafiques.widthProperty().divide(7).subtract(3));
        
        
        //RESIZE TAB1
        gridAuxCrono.heightProperty().addListener(a -> {
            if (gridAuxCrono.getHeight() != 0) {
                tWork.setFont(Font.font(gridAuxCrono.getHeight()*3/5));
                tTranscorregut.setFont(Font.font(gridAuxCrono.getHeight()/9));
                tRestant.setFont(Font.font(gridAuxCrono.getHeight()/9));
            }
        });
        
        //RESIZE TAB2
        gridAuxSessions.widthProperty().addListener(a -> {
            taulaGrups.setPrefWidth((gridAuxSessions.getWidth() - 3*80)/2);
            hBoxAuxResize.setSpacing(gridAuxSessions.getWidth() / 15);
        });
        
        //RESIZE TAB3
        gridAuxGrafiques.widthProperty().addListener(a -> {
            grafica.setPrefWidth((gridAuxGrafiques.getWidth() - 3*80)*2/3);
        });
    }
    
    private boolean pintarDeRoig(String s) {
        boolean res = false;
        int s1 = Integer.parseInt(s.substring(0, 2));
        int s2 = Integer.parseInt(s.substring(3));
        if (s1 == 0 && s2 <= 5) { res = true; }
        return res;
    }
    
    private void creaTaula(SesionTipo s) {
        if (sesionsTipus.isEmpty()) { contenidorSesions.getChildren().clear(); }
        
        ObservableList<Parell> llista = FXCollections.observableArrayList();
        TableView<Parell> taula = new TableView<>();
        TableColumn<Parell, String> tPare = new TableColumn<>(s.getCodigo().toUpperCase());
        TableColumn<Parell, String> t1 = new TableColumn<>();
        t1.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getS1()));
        TableColumn<Parell, String> t2 = new TableColumn<>();
        t2.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getS2()));
        t2.setStyle("-fx-alignment: CENTER_RIGHT");
        tPare.getColumns().addAll(t1, t2);
        taula.getColumns().add(tPare);
        taula.setItems(llista);

        taula.getStylesheets().add("/vista/CSSTaules.css");

        t1.prefWidthProperty().bind(contenidorSesions.widthProperty().divide(4).multiply(3).subtract(1));
        t2.prefWidthProperty().bind(contenidorSesions.widthProperty().divide(4).subtract(1));
        contenidorSesions.prefWidthProperty().bind(scrollPane.widthProperty().subtract(16));
        contenidorSesions.setVgap(40);

        llista.add(new Parell("Nombre d'exercicis", s.getNum_ejercicios() + " "));
        llista.add(new Parell("Nombre de circuits", s.getNum_circuitos() + " "));
        
        llista.add(new Parell("Temps d'escalfament", tornaMinSegBe(s.getT_calentamiento()/60, s.getT_calentamiento()%60)));
        llista.add(new Parell("Temps d'exercici", tornaMinSegBe(s.getT_ejercicio()/60, s.getT_ejercicio()%60)));
        llista.add(new Parell("Temps de descans/exercici", tornaMinSegBe(s.getD_ejercicio()/60, s.getD_ejercicio()%60)));
        llista.add(new Parell("Temps de descans/circuit", tornaMinSegBe(s.getD_circuito()/60, s.getD_circuito()%60)));
        
        contenidorSesions.getChildren().add(taula);
    }
    
    private String tornaMinSegBe(int m, int s) {
        String res = "";
        if(m != 0) { res += m + "' "; }
        if(s != 0) { res += s + "\" "; }
        if("".equals(res)) res = "0\" ";
        return res;
    }
    
    @FXML
    private void afegirGrup(ActionEvent event) {
        Grupo g = new Grupo();
        int aux = grups.size() + 1;
        g.setCodigo("Grup " + aux);
        g.setDescripcion("Nova descripció");
        grups.add(g);
        gimnas.getGrupos().add(g);
        taulaGrups.getSelectionModel().select(g);
        
        Alert fi = new Alert(Alert.AlertType.INFORMATION);
        fi.setTitle("Afegir grup");
        fi.setHeaderText("Nou grup creat correctament");
        fi.setContentText("Introduisca el codi i descripció d'aquest directament en la taula");
        ButtonType acceptar2 = new ButtonType("Acceptar");
        fi.getButtonTypes().setAll(acceptar2);
        fi.showAndWait();
        
        buscadorGrup.getItems().clear();
        buscadorGrup.getItems().addAll(gimnas.getGrupos());
    }

    @FXML
    private void afegirSessioTipus(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLNovaSessioTipus.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Afegir Sessio Tipus");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        SesionTipo st = new SesionTipo();
        ((FXMLNovaSessioTipusController) loader.getController()).setSesioTipus(st);

        stage.showAndWait();

        if (((FXMLNovaSessioTipusController) loader.getController()).haGuardat()) {
            boolean yaEsta = false;
            SesionTipo aux;
            for (int i = 0; i < sesionsTipus.size(); i++) {
                aux = sesionsTipus.get(i);
                if (aux.getCodigo().equals(st.getCodigo())) {
                    yaEsta = true;
                }
            }

            if (yaEsta) {
                Alert fi = new Alert(Alert.AlertType.ERROR);
                fi.setTitle("Error");
                fi.setHeaderText(null);
                fi.setContentText("Ja existeix una sessió tipus amb codi '"
                        + st.getCodigo() + "' a la llista");
                ButtonType acceptar = new ButtonType("Acceptar");
                fi.getButtonTypes().setAll(acceptar);
                fi.showAndWait();
            } else {
                //Inserim la sessió en ordre alfabètic
                int i = 0;
                if (gimnas.getTiposSesion().size() > 0) {
                    while (i < gimnas.getTiposSesion().size()
                            && gimnas.getTiposSesion().get(i).getCodigo().compareTo(st.getCodigo()) < 0) { i++; }
                }
                
                creaTaula(st);
                
                gimnas.getTiposSesion().add(i, st);
                sesionsTipus.add(st);
                
                Alert fi = new Alert(Alert.AlertType.INFORMATION);
                fi.setTitle("Nova sessió tipus");
                fi.setHeaderText(null);
                fi.setContentText("La sessió tipus'" + st.getCodigo() + " "
                        + "' ha sigut creada correctament");
                ButtonType acceptar2 = new ButtonType("Acceptar");
                fi.getButtonTypes().setAll(acceptar2);
                fi.showAndWait();
            }
        }
    }

    @FXML
    private void començarSessio(ActionEvent event) {

        if(grupElegit.getValue() == null) {
            textError.setText("Selecciona grup");
            textError.setVisible(true);
        } else if(tipusDeSessio.getValue() == null) {
            textError.setText("Selecciona sessió");
            textError.setVisible(true);
        }
        else if (tipusDeSessio.getValue().equals("Aprofitar sessió tipus")) {
            if(llistaSessionsTipus.getSelectionModel().getSelectedItem() == null) {
                textError.setText("Selecciona sessió tipus");
                textError.setVisible(true);
            } else {
                eleccioSessio.setVisible(false);
                
                
                iniciaSessio(llistaSessionsTipus.getSelectionModel().getSelectedItem(), false);
            }
        } else {
            //AGAFAR ELS VALORS DONATS EN ELS TEXT FIELD
            String n = nom.getText().trim(), nExer = nombreExercici.getText(), nCir = nombreCircuit.getText().trim(),
                    minExer = minExercici.getText().trim(), minDescansExer = minDescansExercici.getText().trim(), minEscalf = minEscalfament.getText().trim(),
                    segExer = segExercici.getText().trim(), segDescansExer = segDescansExercici.getText().trim(), segEscalf = segEscalfament.getText().trim(),
                    minDescansCir = minDescansCircuit.getText().trim(),
                    segDescansCir = segDescansCircuit.getText().trim();

            //COMPROBAR QUE ELS CAMPS ESTAN TOTS PLENS
            if (n.equals("") || nExer.equals("") || nCir.equals("") || minExer.equals("") || minDescansExer.equals("") || minEscalf.equals("")
                    || segExer.equals("") || segDescansExer.equals("") || segEscalf.equals("") || minDescansCir.equals("") || segDescansCir.equals("")) {
                
                textError.setText("Falten camps per omplir(*)");
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
                eleccioSessio.setVisible(false);

                SesionTipo sesionTipoNova = new SesionTipo();

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

                //Inserim la sessió en ordre alfabètic
                int i = 0;
                if (gimnas.getTiposSesion().size() > 0) {
                    while (i < gimnas.getTiposSesion().size()
                            && gimnas.getTiposSesion().get(i).getCodigo().compareTo(sesionTipoNova.getCodigo()) < 0) {
                        i++;
                    }
                }

                creaTaula(sesionTipoNova);

                gimnas.getTiposSesion().add(i, sesionTipoNova);
                sesionsTipus.add(sesionTipoNova);
                
                
                iniciaSessio(sesionTipoNova, false);
            }
        }
    }
    
    private void iniciaSessio(SesionTipo st, boolean restart) {
        if (!restart) {
            sesionActual = new Sesion();
            sesionActual.setFecha(LocalDateTime.now());
            sesionActual.setTipo(st);
        }
        
        enDescans.setValue(false);
        
        int tCircuit = (st.getD_ejercicio() + st.getT_ejercicio()) * (st.getNum_ejercicios() - 1)
                + st.getT_ejercicio();
        int tTotal = st.getT_calentamiento() + (st.getD_circuito() + tCircuit) * (st.getNum_circuitos() - 1)
                + tCircuit;
        
        service.sesioTotal(tCircuit, tTotal);
        service.sesioAux(sesionActual);
        numEx.setValue("0/" + sesionActual.getTipo().getNum_ejercicios());
        numCir.setValue("0/" + sesionActual.getTipo().getNum_circuitos());

        int auxHores = tTotal/3600;
        tRest.setValue(String.format("%02d:%02d:%02d", auxHores, (tTotal-3600*auxHores)/60, (tTotal-3600*auxHores)%60));
        minISeg.setValue(String.format("%02d:%02d", st.getT_calentamiento()/60, st.getT_calentamiento()%60));
        tTrans.setValue("00:00:00");
        
        grupActual = grupElegit.getValue();
    }

    @FXML
    private void pararReanudar(ActionEvent event) {
        if(primeraVegada) {
            Thread th = new Thread(tascaTempsTrans);
            th.setDaemon(true);
            th.start();
            
            primeraVegada = false;
            
            tab2.setDisable(true);
            tab3.setDisable(true);
        }
        
        if (service.estaParat()) { //Inicia
            File imageFile = new File("src/imatges/parar.png");
            String fileLocation = imageFile.toURI().toString();
            Image im = new Image(fileLocation);
            imatgePararReanudar.setImage(im);
            
            service.start();
            
            service.setParat(false);
            
            restartButton.setDisable(true);
            eixirButton.setDisable(true);
        } else { //Para
            File imageFile = new File("src/imatges/començar.png");
            String fileLocation = imageFile.toURI().toString();
            Image im = new Image(fileLocation);
            imatgePararReanudar.setImage(im);
            
            service.cancel();
            service.reset();
            
            service.setParat(true);
            
            restartButton.setDisable(false);
            eixirButton.setDisable(false);
        }
    }


    @FXML
    private void començarSessioAgain(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Començar de nou");
        confirm.setHeaderText(null);
        confirm.setContentText("Segur que vol començar de de nou la sessió actual?\n\n");

        ButtonType acceptar = new ButtonType("Acceptar");
        ButtonType cancellar = new ButtonType("Cancel·lar");
        confirm.getButtonTypes().setAll(acceptar, cancellar);

        Optional<ButtonType> resposta = confirm.showAndWait();

        if (resposta.isPresent() && resposta.get() == acceptar) {
            File imageFile = new File("src/imatges/començar.png");
            String fileLocation = imageFile.toURI().toString();
            Image im = new Image(fileLocation);
            imatgePararReanudar.setImage(im);
            primeraVegada = true;

            service.cancel();
            tascaTempsTrans.cancel();

            tascaTempsTrans = new MeuaTasca();
            tascaTempsTrans.setStringProperty(tTrans);
            service = new MeuServei();
            service.setStringPropertiesAux(minISeg, tTrans, tRest, numEx, numCir, tascaTempsTrans, fiSessio, enDescans);
            iniciaSessio(sesionActual.getTipo(), true);
            fiSessio.setValue(false);
            tWork.setTextFill(Paint.valueOf("White"));
        }

    }

    @FXML
    private void seguentExercici(ActionEvent event) {
        System.out.println(grupActual.getSesiones().size());
    }

    @FXML
    private void finalitzarSessio(ActionEvent event) {
        sesionActual.setDuracion(service.getDuracio());
        grupActual.getSesiones().add(sesionActual);
        if (buscadorGrup.getValue() == grupActual) {
            buscadorGrup.setValue(null);
            buscadorGrup.setValue(grupActual);
            serieTTreball.getData().clear();
            serieTDescans.getData().clear();
            serieTTotal.getData().clear();
        }
        
        paneFiSessio.setVisible(false);
        
        eleccioSessio.setVisible(true);
        
        File imageFile = new File("src/imatges/començar.png");
        String fileLocation = imageFile.toURI().toString();
        Image im = new Image(fileLocation);
        imatgePararReanudar.setImage(im);
        primeraVegada = true;
        
        service.cancel();
        tascaTempsTrans.cancel();
        
        tascaTempsTrans = new MeuaTasca();
        tascaTempsTrans.setStringProperty(tTrans);
        service = new MeuServei();
        service.setStringPropertiesAux(minISeg, tTrans, tRest, numEx, numCir, tascaTempsTrans, fiSessio, enDescans);
        iniciaSessio(sesionActual.getTipo(), false);
        fiSessio.setValue(false);
        tWork.setTextFill(Paint.valueOf("White"));
        
        tab2.setDisable(false);
        tab3.setDisable(false);
        
        sesionsTipus.remove(sesionActual.getTipo());
        sesionsTipus.add(0, sesionActual.getTipo());
        llistaSessionsTipus.getSelectionModel().select(0);
        
        grupElegit.setValue(null);
        tipusDeSessio.setValue(null);
        llistaSessionsTipus.setDisable(true);
        
        sorollFiSessio.stop();
    }

    @FXML
    private void eixirDeLaSessio(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eixir de la sessió");
        confirm.setHeaderText(null);
        confirm.setContentText("Segur que vol eixir de la sessió actual?\n\n");

        ButtonType acceptar = new ButtonType("Acceptar");
        ButtonType cancellar = new ButtonType("Cancel·lar");
        confirm.getButtonTypes().setAll(acceptar, cancellar);

        Optional<ButtonType> resposta = confirm.showAndWait();

        if (resposta.isPresent() && resposta.get() == acceptar) {
            File imageFile = new File("src/imatges/començar.png");
            String fileLocation = imageFile.toURI().toString();
            Image im = new Image(fileLocation);
            imatgePararReanudar.setImage(im);
            primeraVegada = true;

            service.cancel();
            tascaTempsTrans.cancel();

            tascaTempsTrans = new MeuaTasca();
            tascaTempsTrans.setStringProperty(tTrans);
            service = new MeuServei();
            service.setStringPropertiesAux(minISeg, tTrans, tRest, numEx, numCir, tascaTempsTrans, fiSessio, enDescans);
            iniciaSessio(sesionActual.getTipo(), false);
            fiSessio.setValue(false);
            tWork.setTextFill(Paint.valueOf("White"));

            eleccioSessio.setVisible(true);

            tab2.setDisable(false);
            tab3.setDisable(false);
        }  
        
    }
}


class Parell {
    private String s1;
    private String s2;
    
    
    public Parell() {
        this(null, null);
    }
    
    public Parell(String ss1, String ss2) {
        s1 = ss1;
        s2 = ss2;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }
    
    public void setS1(String ss1) {
        s1 = ss1;
    }
    
    public void setS2(String ss2) {
        s1 = ss2;
    }
}

class SesionM extends Sesion {
    private BooleanProperty seleccionat;
    
    public SesionM (LocalDateTime d, Duration du, SesionTipo s) {
        super();
        setFecha(d);
        setDuracion(du);
        setTipo(s);
        seleccionat = new SimpleBooleanProperty(false);
    }

    public BooleanProperty getSeleccionat() {
        return seleccionat;
    }

    public void setSeleccionat(BooleanProperty seleccionat) {
        this.seleccionat = seleccionat;
    }

    void setSeleccionat(Boolean newValue) {
        this.seleccionat.set(newValue);
    }
}

class CellAux extends ListCell<SesionTipo> {

    @Override
    protected void updateItem(SesionTipo item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (item == null || empty) { setText(null); }
        else { setText(item.getCodigo()); }
    }
    
}

class MeuServei extends Service<Void> {
    private boolean estaParat = true;
    private long tComençament;
    private long tComençament2;
    private long actual;
    private long tParatGlobal;
    private long tParatInterval;
    protected long tSesio;
    protected long tCircuit;
    private Sesion sesioActual;
    private boolean ripSesio = false;
    private int comptador = 0;
    private int comptador2 = 0;
    private int queToca = 0; //0 = calfament, 1 = exercici, 2 = descansExercici, 3 = descansCircuit
    private boolean estaParat2 = true;
    
    StringProperty minISeg;
    StringProperty tTrans;
    StringProperty tRest;
    StringProperty nCircuit;
    StringProperty nExercici;
    MeuaTasca tasca;
    BooleanProperty fiSessio;
    
    BooleanProperty descans;
    
    private Duration d;
    private boolean minISegPara = false;
            
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (!estaParat2) {
                    tComençament = System.currentTimeMillis();
                    tComençament2 = System.currentTimeMillis();
                } else { // estabamos detenidos y nos ponemos en marcha sin cambio de estado
                    Long elapsedTime = System.currentTimeMillis() - actual;
                    tParatGlobal = tParatGlobal + elapsedTime;
                    tParatInterval = tParatInterval + elapsedTime;
                    estaParat2 = false;
                }    
                while (true) {
                    if (isCancelled()) {
                        break;
                    }

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        if (isCancelled()) {
                            break;
                        }
                    }
                    
                    if(ripSesio){
                        //Acava la sessió
                        descans.setValue(false);
                        
                        long tDuracio = tasca.getDuracio() + 1000;
                        tasca.cancel();
                        
                        fiSessio.setValue(true);

                        d = Duration.ofMillis(tDuracio);
                        
                        break;
                    }
                    else{
                    actual = System.currentTimeMillis();
                    Long tTotal = (actual - tComençament) - tParatInterval;
                    Long tTotal2 = (actual - tComençament2) - tParatGlobal;
                    
                    
                    //Per a minISeg
                    Duration duracio2;
                    switch(queToca){
                        case 0:
                            if (sesioActual.getTipo().getT_calentamiento() == 0) { duracio2 = Duration.ofMillis(0); }
                            else duracio2 = Duration.ofMillis(sesioActual.getTipo().getT_calentamiento()*1000 - tTotal);
                            break;
                        case 1:
                            if (sesioActual.getTipo().getT_ejercicio() == 0) { duracio2 = Duration.ofMillis(0); }
                            else duracio2 = Duration.ofMillis(sesioActual.getTipo().getT_ejercicio()*1000 - tTotal);
                            break;
                        case 2:
                            if (sesioActual.getTipo().getD_ejercicio() == 0) { duracio2 = Duration.ofMillis(0); }
                            else duracio2 = Duration.ofMillis(sesioActual.getTipo().getD_ejercicio()*1000 - tTotal);
                            break;
                        case 3:
                            if (sesioActual.getTipo().getD_circuito() == 0) { duracio2 = Duration.ofMillis(0); }
                            else duracio2 = Duration.ofMillis(sesioActual.getTipo().getD_circuito()*1000 - tTotal);
                            break;
                        default:
                            duracio2 = Duration.ofMillis(-1);
                            break;
                    }
                    
                    final long minuts2 = duracio2.toMinutes();
                    final long segons2 = duracio2.minusMinutes(minuts2).getSeconds();
                    final long centesimes = duracio2.minusSeconds(segons2).toNanos() / 10000000;
                    
                    //Per a tRest
                    Duration duracio3 = Duration.ofMillis(tSesio - tTotal2); 
                    final long hores2 = duracio3.toHours();
                    final long minuts3 = duracio3.toMinutes();
                    final long segons3 = duracio3.minusMinutes(minuts3).getSeconds();
                    final long centesimes2 = duracio3.minusSeconds(segons3).toNanos() / 10000000;
                    
                    
                    Platform.runLater(() -> {
                        //minISeg
                        if ((segons2 == 0 && centesimes < 10 )) { //Canvia d'interval
                            if(comptador2 == sesioActual.getTipo().getNum_circuitos()
                                    && comptador == sesioActual.getTipo().getNum_ejercicios()) {
                                minISegPara = true;
                            }
                            tComençament = System.currentTimeMillis();
                            tParatInterval = 0;
                            
                            
                            minISeg.setValue(String.format("%02d:%02d", 0, 0));
                            
                            if(comptador > sesioActual.getTipo().getNum_ejercicios() - 1){
                                queToca = 3;
                                descans.setValue(true);
                                comptador = 0;
                                comptador2++;
                            }else {
                                switch (queToca) {
                                    case 0:
                                        queToca = 1;
                                        descans.setValue(false);
                                        comptador++;
                                        comptador2++;
                                        break;
                                    case 1:
                                        queToca = 2;
                                        descans.setValue(true);
                                        break;
                                    default:
                                        queToca = 1;
                                        descans.setValue(false);
                                        comptador++;
                                        break;
                                }
                            }
                        }else{ 
                            if (!minISegPara) {
                                minISeg.setValue(String.format("%02d:%02d", minuts2, segons2));
                                nCircuit.setValue(comptador2 + "/" + sesioActual.getTipo().getNum_circuitos());
                                nExercici.setValue(comptador + "/" + sesioActual.getTipo().getNum_ejercicios());
                            }
                        }
                        
                        //tRest
                        if ((segons3 == 0 && centesimes2 < 10 )) {
                            tRest.setValue(String.format("%02d:%02d:%02d", 0, 0, 0));
                            ripSesio = true;
                        }else { 
                            tRest.setValue(String.format("%02d:%02d:%02d", hores2, minuts3, segons3));
                        }
                    });
                    }
                }                
                
                return null;
            }
        };
    }
    
    @Override
    protected void cancelled() {
        super.cancelled();
        actual = System.currentTimeMillis();
        estaParat2 = true;
    }
    
    public void sesioTotal(int ca, int sa){
        tSesio = sa*1000;
        tCircuit = ca*1000;
    }
    
    public void sesioAux (Sesion s){
        sesioActual = s;
    }
    public boolean estaParat() {
        return estaParat;
    }
    
    public void setParat(boolean b) {
        estaParat = b;
    }
    
    public void setStringPropertiesAux(StringProperty t1, StringProperty t2, StringProperty t3,
            StringProperty t4, StringProperty t5, MeuaTasca t, BooleanProperty b, BooleanProperty des) {
        minISeg = t1;
        tTrans = t2;
        tRest = t3;
        nExercici = t4;
        nCircuit = t5;
        tasca = t;
        fiSessio = b;
        descans = des;
    }
    
    public Duration getDuracio() {
        return d;
    }
}

class MeuaTasca extends Task<Void> {

    private long tComençament;
    private long actual;
    private long tTotal; 
    
    private StringProperty tTrans;

    @Override
    protected Void call() {
        tComençament = System.currentTimeMillis();

        while (true) {
            if (isCancelled()) {
                break;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                if (isCancelled()) {
                    break;
                }
            }

            actual = System.currentTimeMillis();
            tTotal = actual - tComençament;

            //Per a tTrans
            Duration duracio = Duration.ofMillis(tTotal + 1000);
            final long hores = duracio.toHours();
            final long minuts = duracio.minusHours(hores).getSeconds() / 60;
            final long segons = duracio.minusMinutes(minuts).getSeconds();

            Platform.runLater(() -> {
                //tTrans
                tTrans.setValue(String.format("%02d:%02d:%02d", hores, minuts, segons));
            });
        }

        return null;
    }

    public void setStringProperty(StringProperty t) {
        tTrans = t;
    }
    
    public long getDuracio() {
        return tTotal;
    }
}
