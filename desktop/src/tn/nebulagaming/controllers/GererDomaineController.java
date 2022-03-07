/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jdbc.jdbc;
import models.domain;
import services.serviceDomain;
import services.serviceOffer;

/**
 * FXML Controller class
 *
 * @author Msi
 */
public class GererDomaineController implements Initializable {

    @FXML
    private TextField nominput;
    @FXML
    private TextField descinput;
    @FXML
    private TableColumn<domain, Integer> idt;
    @FXML
    private TableColumn<domain, String> nomt;
    @FXML
    private TableColumn<domain, String> desct;

    private Statement ste;
    private Connection con;
    private final ObservableList<domain> data = FXCollections.observableArrayList();
    serviceDomain sd = new serviceDomain();
    @FXML
    private TableView<domain> tabledomain;
    @FXML
    private BarChart<String, Number> barChart;
    
    serviceOffer so = new serviceOffer();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Aff();
    }  
    public void Stat() throws SQLException{
                XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Répartition des Demandes");
            con = jdbc.getInstance().getCnx();
            ste = con.createStatement();
            ResultSet res = ste.executeQuery("select * from tbl_domain");
            while(res.next()){
            series.getData().add(new XYChart.Data<>(res.getString(2), so.calculer(res.getInt(1))));
            }        
        barChart.getData().addAll(series);

    }

       public void Aff(){
                        try {
            con = jdbc.getInstance().getCnx();
            ste = con.createStatement();
            data.clear();

            ResultSet res = ste.executeQuery("select * from tbl_domain");
            while(res.next()){
                domain f= new domain(res.getInt(1),res.getString(2),res.getString(3));
                data.add(f);
            }

        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
               
            
            idt.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomt.setCellValueFactory(new PropertyValueFactory<>("name"));
            desct.setCellValueFactory(new PropertyValueFactory<>("desc"));
            
            tabledomain.setItems(data);
            tabledomain.setEditable(true);
            nomt.setCellFactory(TextFieldTableCell.forTableColumn());
            desct.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            Stat();
                    } catch (SQLException ex) {
            Logger.getLogger(GererDomaineController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void ajouter(ActionEvent event) {
        if(Validchamp(nominput) && Validchamp(descinput))
        {
        domain d = new domain(nominput.getText(),descinput.getText());
        sd.ajouter(d);
        
        Aff();
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");

        alert.showAndWait();
        }
    }

    @FXML
    private void Supprimer(ActionEvent event) {
                tabledomain.setItems(data);

             ObservableList<domain> alldomains,Singledomain ;
             alldomains=tabledomain.getItems();
             Singledomain=tabledomain.getSelectionModel().getSelectedItems();
             domain A = Singledomain.get(0);
             sd.supprimer(A);
             Singledomain.forEach(alldomains::remove);
             Aff();

    }

    @FXML
    private void Change_Nom(TableColumn.CellEditEvent event) throws SQLException {
     domain tab_domaineSelected = tabledomain.getSelectionModel().getSelectedItem();
     tab_domaineSelected.setName(event.getNewValue().toString());
     sd.modifier(tab_domaineSelected);

    }

    @FXML
    private void Change_Description(TableColumn.CellEditEvent event) throws SQLException {
     domain tab_domaineSelected = tabledomain.getSelectionModel().getSelectedItem();
     tab_domaineSelected.setDesc(event.getNewValue().toString());
     sd.modifier(tab_domaineSelected);

    }

                private boolean Validchamp(TextField T){
        return !T.getText().isEmpty() && T.getLength() > 2;
    }

    
}
