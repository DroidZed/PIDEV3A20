/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.views;

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
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.models.Domain;
import tn.nebulagaming.services.ServiceDomain;
import tn.nebulagaming.services.ServiceOffer;

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
    private TableColumn<Domain, Integer> idt;
    @FXML
    private TableColumn<Domain, String> nomt;
    @FXML
    private TableColumn<Domain, String> desct;

    private Statement ste;
    private Connection con;
    private final ObservableList<Domain> data = FXCollections.observableArrayList();
    ServiceDomain sd = new ServiceDomain();
    @FXML
    private TableView<Domain> tableDomain;
    @FXML
    private BarChart<String, Number> barChart;

    ServiceOffer so = new ServiceOffer();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	Aff();
    }

    public void Stat() throws SQLException {
	XYChart.Series<String, Number> series = new XYChart.Series<>();
	series.setName("Répartition des Demandes");
	con = GlobalConfig.getInstance().getCONNECTION();
	ste = con.createStatement();
	ResultSet res = ste.executeQuery("select * from tbl_Domain");
	while (res.next()) {
	    series.getData().add(new XYChart.Data<>(res.getString(2), so.calculer(res.getInt(1))));
	}
	barChart.getData().addAll(series);

    }

    public void Aff() {
	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    ste = con.createStatement();
	    data.clear();

	    ResultSet res = ste.executeQuery("select * from tbl_Domain");
	    while (res.next()) {
		Domain f = new Domain(res.getInt(1), res.getString(2), res.getString(3));
		data.add(f);
	    }

	} catch (Exception e) {
	    //Logger.getLogger(tab)
	}

	idt.setCellValueFactory(new PropertyValueFactory<>("id"));
	nomt.setCellValueFactory(new PropertyValueFactory<>("name"));
	desct.setCellValueFactory(new PropertyValueFactory<>("desc"));

	tableDomain.setItems(data);
	tableDomain.setEditable(true);
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
	if (Validchamp(nominput) && Validchamp(descinput)) {
	    Domain d = new Domain(nominput.getText(), descinput.getText());
	    sd.ajouter(d);

	    Aff();
	} else {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("ERROR");
	    alert.setHeaderText(null);
	    alert.setContentText("Verifiez les champs!");

	    alert.showAndWait();
	}
    }

    @FXML
    private void Supprimer(ActionEvent event) {
	tableDomain.setItems(data);

	ObservableList<Domain> allDomains, SingleDomain;
	allDomains = tableDomain.getItems();
	SingleDomain = tableDomain.getSelectionModel().getSelectedItems();
	Domain A = SingleDomain.get(0);
	sd.supprimer(A);
	SingleDomain.forEach(allDomains::remove);
	Aff();

    }

    @FXML
    private void Change_Nom(TableColumn.CellEditEvent event) throws SQLException {
	Domain tab_DomaineSelected = tableDomain.getSelectionModel().getSelectedItem();
	tab_DomaineSelected.setName(event.getNewValue().toString());
	sd.modifier(tab_DomaineSelected);

    }

    @FXML
    private void Change_Description(TableColumn.CellEditEvent event) throws SQLException {
	Domain tab_DomaineSelected = tableDomain.getSelectionModel().getSelectedItem();
	tab_DomaineSelected.setDesc(event.getNewValue().toString());
	sd.modifier(tab_DomaineSelected);

    }

    private boolean Validchamp(TextField T) {
	return !T.getText().isEmpty() && T.getLength() > 2;
    }

}
