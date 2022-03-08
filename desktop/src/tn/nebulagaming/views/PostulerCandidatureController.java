/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.views;

import tn.nebulagaming.models.Pdf;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.models.Candidacy;
import tn.nebulagaming.models.Domain;
import tn.nebulagaming.models.Offer;
import tn.nebulagaming.services.ServiceCandidacy;
import tn.nebulagaming.services.ServiceDomain;
import tn.nebulagaming.services.ServiceOffer;

/**
 * FXML Controller class
 *
 * @author Msi
 */
public class PostulerCandidatureController implements Initializable {

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
    private TextField affiche;
    @FXML
    private Button uploadbutton;
    @FXML
    private Label Alert;

    private Statement ste;
    private Connection con;
    private final ObservableList<Offer> data = FXCollections.observableArrayList();

    ServiceOffer so = new ServiceOffer();
    ServiceDomain sd = new ServiceDomain();
    ServiceCandidacy sc = new ServiceCandidacy();
    @FXML
    private Button btnpostuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	Aff();
    }

    public void Aff() {
	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    ste = con.createStatement();
	    data.clear();

	    ResultSet rs = ste.executeQuery("select * from tbl_offer");
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

    @FXML
    private void LoadData(MouseEvent event) {
	ObservableList<Offer> alloffers, Singledoffer;
	alloffers = tableoffre.getItems();
	Singledoffer = tableoffre.getSelectionModel().getSelectedItems();
	Offer A = Singledoffer.get(0);
	Candidacy a = sc.getById(1, A.getIdOffer());
	if (a == null) {
	    Alert.setText("");
	    btnpostuler.setDisable(false);
	} else {
	    Alert.setText("Demande déja envoyé");
	    btnpostuler.setDisable(true);
	}

    }

    @FXML
    private void Uploadfile(ActionEvent event) {
	FileChooser fc = new FileChooser();
	String path = fc.showOpenDialog(uploadbutton.getScene().getWindow()).getPath();
	affiche.setText(path);
    }

    @FXML
    private void postuler(ActionEvent event) throws IOException {
	if (affiche.getText().equals("")) {
	    Alert.setText("Saisir Votre CV");
	} else {
	    Alert.setText("");

	    File f = new File(affiche.getText());
	    ObservableList<Offer> alloffers, Singledoffer;
	    alloffers = tableoffre.getItems();
	    Singledoffer = tableoffre.getSelectionModel().getSelectedItems();
	    Offer A = Singledoffer.get(0);
	    System.out.println(affiche.getText());

	    int session = GlobalConfig.getInstance().getSession();

	    Candidacy d = new Candidacy(A.getIdOffer(), session, affiche.getText(), "En Cours");
	    Files.copy(Paths.get(affiche.getText()), Paths.get("C:\\Users\\User\\Desktop\\" + f.getName()), REPLACE_EXISTING);

	    sc.ajouter(d);

	    Aff();

	}

    }

    @FXML
    private void PDFbtn(ActionEvent event) throws FileNotFoundException, SQLException, DocumentException {
	ObservableList<Offer> all, Single;
	all = tableoffre.getItems();
	Single = tableoffre.getSelectionModel().getSelectedItems();
	Offer A = Single.get(0);

	Pdf p = new Pdf();
	p.add("Offer" + A.getIdOffer(), A.getTitleOffer(), A.getDescOffer(), A.getTypeoffer(), A.getStartDTM().toString(), A.getEndDTM().toString(), String.valueOf(A.getSalaryOffer()), A.getRegionOffer(), A.getAddressOffer(), A.getDomain());

    }

}
