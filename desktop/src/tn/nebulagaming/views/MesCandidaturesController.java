/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.nebulagaming.models.Candidacy;
import tn.nebulagaming.models.Offer;
import tn.nebulagaming.services.ServiceCandidacy;
import tn.nebulagaming.services.ServiceOffer;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author Msi
 */
public class MesCandidaturesController implements Initializable {

    @FXML
    private TableView<Candidacy> tableaucandidature;
    @FXML
    private TableColumn<Candidacy, Integer> idt;
    @FXML
    private TableColumn<Candidacy, java.sql.Date> datect;
    @FXML
    private TableColumn<Candidacy, String> etatt;
    @FXML
    private TableColumn<Candidacy, ImageView> imagecvt;
    @FXML
    private TableColumn<Candidacy, String> titlet;

    private Statement ste;
    private Connection con;
    private final ObservableList<Candidacy> data = FXCollections.observableArrayList();
    ServiceOffer so = new ServiceOffer();
    ServiceCandidacy sc = new ServiceCandidacy();
    @FXML
    private Button btnfaire;
    @FXML
    private Button btnprint;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	Aff();
	btnfaire.setDisable(true);
    }

    public void Aff() {
	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    ste = con.createStatement();
	    data.clear();

	    ResultSet rs = ste.executeQuery("select * from tbl_Candidacy where idUser=" + 1);
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

		data.add(f);
	    }
	} catch (Exception e) {
	    //Logger.getLogger(tab)
	}
	idt.setCellValueFactory(new PropertyValueFactory<>("id"));
	datect.setCellValueFactory(new PropertyValueFactory<>("CandidacyDTM"));
	titlet.setCellValueFactory(new PropertyValueFactory<>("title"));
	etatt.setCellValueFactory(new PropertyValueFactory<>("etat"));
	imagecvt.setCellValueFactory(new PropertyValueFactory<>("img"));

	tableaucandidature.setItems(data);
    }

    @FXML
    private void OnClickFaireTest(ActionEvent event) throws IOException {
	ObservableList<Candidacy> all, Single;
	all = tableaucandidature.getItems();
	Single = tableaucandidature.getSelectionModel().getSelectedItems();
	Candidacy A = Single.get(0);
	Candidacy tmp = sc.getCandidacy(A.getId());
	System.out.println(tmp);

	FXMLLoader loader = new FXMLLoader(getClass().getResource("Test.fxml"));
	Parent root = loader.load();

	TestController tc = loader.getController();
	tc.setvalues(tmp.getIdOffer(), 1);
	btnfaire.getScene().setRoot(root);

    }

    @FXML
    private void fairetest(MouseEvent event) {

	ObservableList<Candidacy> all, Single;
	all = tableaucandidature.getItems();
	Single = tableaucandidature.getSelectionModel().getSelectedItems();
	Candidacy A = Single.get(0);
	if (A.getEtat().equals("Faire le test")) {
	    btnfaire.setDisable(false);

	} else {
	    btnfaire.setDisable(true);
	}

    }

    @FXML
    private void supprimer(ActionEvent event) {
	ObservableList<Candidacy> all, Single;
	all = tableaucandidature.getItems();
	Single = tableaucandidature.getSelectionModel().getSelectedItems();
	Candidacy A = Single.get(0);
	sc.supprimer(A);
	Aff();
    }

    @FXML
    private void imprimer(ActionEvent event) {

	PrinterJob job = PrinterJob.createPrinterJob();

	Node root = this.tableaucandidature;

	if (job != null) {
	    job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
	    Printer printer = job.getPrinter();
	    PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
	    boolean success = job.printPage(pageLayout, root);
	    if (success) {
		job.endJob();
	    }
	}
    }

}
