/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import models.Pdf;
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
import jdbc.jdbc;
import models.candidacy;
import models.domain;
import models.offer;
import services.serviceCandidacy;
import services.serviceDomain;
import services.serviceOffer;

/**
 * FXML Controller class
 *
 * @author Msi
 */
public class PostulerCandidatureController implements Initializable {

    @FXML
    private TableView<offer> tableoffre;
    @FXML
    private TableColumn<offer, Integer> idt;
    @FXML
    private TableColumn<offer, String> titret;
    @FXML
    private TableColumn<offer, String> desct;
    @FXML
    private TableColumn<offer, String> typet;
    @FXML
    private TableColumn<offer, java.sql.Date> datedebt;
    @FXML
    private TableColumn<offer, java.sql.Date> datefint;
    @FXML
    private TableColumn<offer, Float> salairet;
    @FXML
    private TableColumn<offer, String> regiont;
    @FXML
    private TableColumn<offer, String> domaint;
    @FXML
    private TableColumn<offer, String> adresset;

    @FXML
    private TextField affiche;
    @FXML
    private Button uploadbutton;
    @FXML
    private Label Alert;

    private Statement ste;
    private Connection con;
    private final ObservableList<offer> data = FXCollections.observableArrayList();

    serviceOffer so = new serviceOffer();
    serviceDomain sd = new serviceDomain();
    serviceCandidacy sc = new serviceCandidacy();
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
    
    public void Aff(){
                        try {
            con = jdbc.getInstance().getCnx();
            ste = con.createStatement();
            data.clear();

            ResultSet rs = ste.executeQuery("select * from tbl_offer");
            while(rs.next()){
                offer f = new offer(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11));
                domain d = sd.getById(f.getIdDomain());
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
             ObservableList<offer> alloffers,Singledoffer ;
             alloffers=tableoffre.getItems();
             Singledoffer=tableoffre.getSelectionModel().getSelectedItems();
             offer A = Singledoffer.get(0);
             candidacy a = sc.getById(1, A.getIdOffer());
             if(a==null)
             {
             Alert.setText("");
             btnpostuler.setDisable(false);
             }
             else
             {
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
        if(affiche.getText().equals(""))
        {
            Alert.setText("Saisir Votre CV");
        }
        else{
            Alert.setText("");
            
             File f = new File(affiche.getText());
             ObservableList<offer> alloffers,Singledoffer ;
             alloffers=tableoffre.getItems();
             Singledoffer=tableoffre.getSelectionModel().getSelectedItems();
             offer A = Singledoffer.get(0);
             System.out.println(affiche.getText());
             
            candidacy d = new candidacy(A.getIdOffer(), 1, affiche.getText(), "En Cours");
            Files.copy(Paths.get(affiche.getText()),Paths.get("C:\\Users\\User\\Desktop\\"+f.getName()),REPLACE_EXISTING);

            sc.ajouter(d);
        
        Aff();

        }

    }

    @FXML
    private void PDFbtn(ActionEvent event) throws FileNotFoundException, SQLException, DocumentException {
             ObservableList<offer> all,Single ;
             all=tableoffre.getItems();
             Single=tableoffre.getSelectionModel().getSelectedItems();
             offer A = Single.get(0);
             
             Pdf p = new Pdf();
             p.add("Offer" +A.getIdOffer(), A.getTitleOffer(),A.getDescOffer(), A.getTypeoffer(), A.getStartDTM().toString(), A.getEndDTM().toString(), String.valueOf(A.getSalaryOffer()), A.getRegionOffer(), A.getAddressOffer(), A.getDomain());
        
        
    }
    
}
