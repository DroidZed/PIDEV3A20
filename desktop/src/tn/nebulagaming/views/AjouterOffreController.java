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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.models.Candidacy;
import tn.nebulagaming.models.Domain;
import tn.nebulagaming.models.Offer;
//import org.controlsfx.control.Notifications;
import tn.nebulagaming.services.ServiceCandidacy;
import tn.nebulagaming.services.ServiceDomain;
import tn.nebulagaming.services.ServiceOffer;
import tray.notification.NotificationType;
import static tray.notification.NotificationType.SUCCESS;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Msi
 */
public class AjouterOffreController implements Initializable {

    @FXML
    private ComboBox<String> typeOfferCombo;
    ObservableList<String> listoffre = FXCollections.observableArrayList("StagePFE", "StageInitiation", "EmploiCDD", " EmploiCDI", " Alternance", " Freelance");
    ObservableList<String> listregion = FXCollections.observableArrayList("Tunis", "Ariana", "Ben Arous", " Sfax", " Nabeul");
    ObservableList<String> listDomain = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> ComboDomain;
    @FXML
    private ComboBox<String> ComboRegion;
    @FXML
    private TextField descinput;
    @FXML
    private TextField titreinput;
    @FXML
    private DatePicker datedebinput;
    @FXML
    private DatePicker datefininput;
    @FXML
    private TextField salaireinput;
    @FXML
    private TableView<Offer> tableoffre;
    @FXML
    private TableColumn<Offer, Integer> idt;
    @FXML
    private TableColumn<Offer, String> titret;
    @FXML
    private TableColumn<Offer, String> desct;
    @FXML
    private TableColumn<Offer, String> typet;
    @FXML
    private TableColumn<Offer, java.sql.Date> datedebt;
    @FXML
    private TableColumn<Offer, java.sql.Date> datefint;
    @FXML
    private TableColumn<Offer, Float> salairet;
    @FXML
    private TableColumn<Offer, String> regiont;
    @FXML
    private TableColumn<Offer, String> domaint;
    @FXML
    private TableColumn<Offer, String> adresset;

    @FXML
    private Label idmodif;

    private Statement ste;
    private Connection con;
    private final ObservableList<Offer> data = FXCollections.observableArrayList();
    private final ObservableList<Candidacy> dataCand = FXCollections.observableArrayList();

    ServiceOffer so = new ServiceOffer();
    ServiceDomain sd = new ServiceDomain();
    @FXML
    private TextField adresseinput;
    @FXML
    private TextField recherche;
    @FXML
    private TableView<Candidacy> tableaucandidature;
    @FXML
    private TableColumn<Candidacy, String> candidaturet;
    @FXML
    private TableColumn<Candidacy, ImageView> imagecvt;
    @FXML
    private TableColumn<Candidacy, java.sql.Date> datet;
    ServiceCandidacy sc = new ServiceCandidacy();
    @FXML
    private Button btnDm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	typeOfferCombo.setItems(listoffre);
	ComboRegion.setItems(listregion);
	try {
	    fillcombo();
	} catch (SQLException ex) {
	    Logger.getLogger(AjouterOffreController.class.getName()).log(Level.SEVERE, null, ex);
	}
	Aff();
	RechercheAV();

    }

    public void fillcombo() throws SQLException {
	ServiceDomain ser = new ServiceDomain();
	List<Domain> list = ser.afficher();
	for (Domain aux : list) {
	    listDomain.add(aux.getName());
	}
	ComboDomain.setItems(listDomain);
    }

    public void Aff() {
	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    ste = con.createStatement();
	    data.clear();

	    ResultSet rs = ste.executeQuery("select * from tbl_offer where idUser=" + 2);
	    while (rs.next()) {
		Offer f = new Offer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11));
		Domain d = sd.getById(f.getIdDomain());
		f.setDomain(d.getName());
		data.add(f);
	    }
	} catch (Exception e) {
	    //Logger.getLogger(tab)
	}
	idt.setCellValueFactory(new PropertyValueFactory<>("idOffer"));
	titret.setCellValueFactory(new PropertyValueFactory<>("titleOffer"));
	desct.setCellValueFactory(new PropertyValueFactory<>("descOffer"));
	typet.setCellValueFactory(new PropertyValueFactory<>("Typeoffer"));
	datedebt.setCellValueFactory(new PropertyValueFactory<>("startDTM"));
	datefint.setCellValueFactory(new PropertyValueFactory<>("endDTM"));
	salairet.setCellValueFactory(new PropertyValueFactory<>("salaryOffer"));
	regiont.setCellValueFactory(new PropertyValueFactory<>("regionOffer"));
	adresset.setCellValueFactory(new PropertyValueFactory<>("addressOffer"));
	domaint.setCellValueFactory(new PropertyValueFactory<>("Domain"));
	tableoffre.setItems(data);
    }

    public void RechercheAV() {
	// Wrap the ObservableList in a FilteredList (initially display all data).
	FilteredList<Offer> filteredData = new FilteredList<>(data, b -> true);

	// 2. Set the filter Predicate whenever the filter changes.
	recherche.textProperty().addListener((observable, oldValue, newValue) -> {
	    filteredData.setPredicate(offertmp -> {
		// If filter text is empty, display all persons.

		if (newValue == null || newValue.isEmpty()) {
		    return true;
		}

		// Compare first name and last name of every person with filter text.
		String lowerCaseFilter = newValue.toLowerCase();

		if (offertmp.getTypeoffer().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		    return true; // Filter matches first name.
		} else if (String.valueOf(offertmp.getDomain()).indexOf(lowerCaseFilter) != -1) {
		    return true;
		} else {
		    return false; // Does not match.
		}
	    });
	});

	// 3. Wrap the FilteredList in a SortedList. 
	SortedList<Offer> sortedData = new SortedList<>(filteredData);

	// 4. Bind the SortedList comparator to the TableView comparator.
	// 	  Otherwise, sorting the TableView would have no effect.
	sortedData.comparatorProperty().bind(tableoffre.comparatorProperty());

	// 5. Add sorted (and filtered) data to the table.
	tableoffre.setItems(sortedData);
    }

    @FXML
    private void ajouter(ActionEvent event) {
	if (Validchamp(titreinput) && Validchamp(descinput) && Validchamp(salaireinput) && Validchamp(adresseinput)) {
	    DatePicker datsort = (DatePicker) datedebinput;
	    String date = (String) datsort.getValue().toString();
	    date = date.substring(0, 4) + '/' + date.substring(5, 7) + '/' + date.substring(8);
	    java.util.Date myDate = new java.util.Date(date);
	    java.sql.Date datedebut = new java.sql.Date(myDate.getTime());

	    DatePicker datsortt = (DatePicker) datefininput;
	    String datee = (String) datsortt.getValue().toString();
	    datee = datee.substring(0, 4) + '/' + datee.substring(5, 7) + '/' + datee.substring(8);
	    java.util.Date myDatee = new java.util.Date(datee);
	    java.sql.Date datefinn = new java.sql.Date(myDatee.getTime());

	    Domain d = sd.getByName(ComboDomain.getValue());
	    Offer of = new Offer(titreinput.getText(), typeOfferCombo.getValue(), descinput.getText(), datedebut, datefinn, Float.parseFloat(salaireinput.getText()), regiont.getText(), adresseinput.getText(), d.getId());
	    of.setIdUser(3);
	    so.ajouter(of);

	    Aff();
	    RechercheAV();

	    TrayNotification tray = new TrayNotification();
	    tray.setTitle("Ajout");
	    tray.setMessage("Ajouter avec succé");
	    tray.setNotificationType(NotificationType.SUCCESS);
	    tray.showAndWait();

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
	tableoffre.setItems(data);
	ObservableList<Offer> alloffers, Singledoffer;
	alloffers = tableoffre.getItems();
	Singledoffer = tableoffre.getSelectionModel().getSelectedItems();
	Offer A = Singledoffer.get(0);
	so.supprimer(A);
	Singledoffer.forEach(alloffers::remove);
	Aff();
	RechercheAV();
    }

    @FXML
    private void Modifier(ActionEvent event) {
	if (Validchamp(titreinput) && Validchamp(descinput) && Validchamp(salaireinput) && Validchamp(adresseinput)) {
	    DatePicker datsort = (DatePicker) datedebinput;
	    String date = (String) datsort.getValue().toString();
	    date = date.substring(0, 4) + '/' + date.substring(5, 7) + '/' + date.substring(8);
	    java.util.Date myDate = new java.util.Date(date);
	    java.sql.Date datedebut = new java.sql.Date(myDate.getTime());

	    DatePicker datsortt = (DatePicker) datefininput;
	    String datee = (String) datsortt.getValue().toString();
	    datee = datee.substring(0, 4) + '/' + datee.substring(5, 7) + '/' + datee.substring(8);
	    java.util.Date myDatee = new java.util.Date(datee);
	    java.sql.Date datefinn = new java.sql.Date(myDatee.getTime());

	    Domain d = sd.getByName(ComboDomain.getValue());
	    Offer of = new Offer(Integer.valueOf(idmodif.getText()), titreinput.getText(), typeOfferCombo.getValue(), descinput.getText(), datedebut, datefinn, Float.parseFloat(salaireinput.getText()), regiont.getText(), adresseinput.getText(), d.getId());
	    so.modifier(of);

	    Aff();
	    RechercheAV();
	} else {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("ERROR");
	    alert.setHeaderText(null);
	    alert.setContentText("Verifiez les champs!");

	    alert.showAndWait();
	}

    }

    @FXML
    private void LoadModif(MouseEvent event) {
	ObservableList<Offer> alloffers, Singledoffer;
	alloffers = tableoffre.getItems();
	Singledoffer = tableoffre.getSelectionModel().getSelectedItems();
	Offer A = Singledoffer.get(0);

	AffCandidature(A.getIdOffer());

	idmodif.setText(Integer.toString(A.getIdOffer()));
	titreinput.setText(A.getTitleOffer());
	typeOfferCombo.setValue(A.getTypeoffer());
	descinput.setText(A.getDescOffer());
	LocalDate localDate = LocalDate.parse(A.getStartDTM().toString());
	datedebinput.setValue(localDate);
	LocalDate localDatee = LocalDate.parse(A.getEndDTM().toString());
	datefininput.setValue(localDatee);
	salaireinput.setText(Float.toString(A.getSalaryOffer()));
	ComboRegion.setValue(A.getRegionOffer());
	adresseinput.setText(A.getAddressOffer());
	Domain d = sd.getById(A.getIdDomain());
	ComboDomain.setValue(d.getName());
    }

    @FXML
    private void Autoriser(ActionEvent event) {

	ObservableList<Candidacy> all, Single;
	all = tableaucandidature.getItems();
	Single = tableaucandidature.getSelectionModel().getSelectedItems();
	Candidacy A = Single.get(0);

	A.setEtat("Faire le test");
	sc.modifier(A);

	AffCandidature(A.getIdOffer());

    }

    @FXML
    private void Refuser(ActionEvent event) {
	ObservableList<Candidacy> all, Single;
	all = tableaucandidature.getItems();
	Single = tableaucandidature.getSelectionModel().getSelectedItems();
	Candidacy A = Single.get(0);

	A.setEtat("Refuser");
	sc.modifier(A);
	AffCandidature(A.getIdOffer());

    }

    public void AffCandidature(int id) {
	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    ste = con.createStatement();
	    dataCand.clear();

	    ResultSet rs = ste.executeQuery("select * from tbl_Candidacy where idOffer=" + id);
	    while (rs.next()) {
		Candidacy f = new Candidacy(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
		Offer off = so.getById(f.getIdOffer());
		f.setTitle(off.getTitleOffer());

		File file = new File(f.getImageCV());
		Image image = new Image(file.toURI().toString());

		ImageView imageView = new ImageView(image);
		imageView.setImage(image);
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);

		f.setImg(imageView);

		dataCand.add(f);
	    }
	} catch (Exception e) {
	    //Logger.getLogger(tab)
	}
	idt.setCellValueFactory(new PropertyValueFactory<>("id"));
	candidaturet.setCellValueFactory(new PropertyValueFactory<>("etat"));
	datet.setCellValueFactory(new PropertyValueFactory<>("CandidacyDTM"));
	imagecvt.setCellValueFactory(new PropertyValueFactory<>("img"));

	tableaucandidature.setItems(dataCand);
    }

    private boolean Validchamp(TextField T) {
	return !T.getText().isEmpty() && T.getLength() > 2;
    }

    @FXML
    private void goToDom(ActionEvent event) {

	
    }

}
