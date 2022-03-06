/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jdbc.jdbc;
import models.candidacy;
import models.domain;
import models.offer;
import services.serviceCandidacy;
import services.serviceOffer;

/**
 * FXML Controller class
 *
 * @author Msi
 */
public class MesCandidaturesController implements Initializable {

    @FXML
    private TableView<candidacy> tableaucandidature;
    @FXML
    private TableColumn<candidacy, Integer> idt;
    @FXML
    private TableColumn<candidacy, java.sql.Date> datect;
    @FXML
    private TableColumn<candidacy, String> etatt;
    @FXML
    private TableColumn<candidacy, ImageView> imagecvt;
    @FXML
    private TableColumn<candidacy, String> titlet;

    
    private Statement ste;
    private Connection con;
    private final ObservableList<candidacy> data = FXCollections.observableArrayList();
    serviceOffer so = new serviceOffer();
    serviceCandidacy sc = new serviceCandidacy();
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
    
    public void Aff(){
            try {
            con = jdbc.getInstance().getCnx();
            ste = con.createStatement();
            data.clear();

            ResultSet rs = ste.executeQuery("select * from tbl_candidacy where idUser="+1);
            while(rs.next()){
                candidacy f = new candidacy(rs.getInt(1),rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                offer off = so.getById(f.getIdOffer());
                f.setTitle(off.getTitleOffer());
                
                File file = new File(f.getImageCV());
                Image image = new Image(file.toURI().toString());
                
                ImageView imageView =new ImageView(image);
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
            datect.setCellValueFactory(new PropertyValueFactory<>("candidacyDTM"));
            titlet.setCellValueFactory(new PropertyValueFactory<>("title"));
            etatt.setCellValueFactory(new PropertyValueFactory<>("etat"));
            imagecvt.setCellValueFactory(new PropertyValueFactory<>("img"));
          
            tableaucandidature.setItems(data);
           }
    
    @FXML
    private void OnClickFaireTest(ActionEvent event) throws IOException {
        ObservableList<candidacy> all,Single ;
             all=tableaucandidature.getItems();
             Single=tableaucandidature.getSelectionModel().getSelectedItems();
             candidacy A = Single.get(0);
             candidacy tmp = sc.getCandidacy(A.getId());
             System.out.println(tmp);
             
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Test.fxml"));
        Parent root = loader.load();
        
        TestController tc =loader.getController();
        tc.setvalues(tmp.getIdOffer(),1);
        btnfaire.getScene().setRoot(root);

    }

    @FXML
    private void fairetest(MouseEvent event) {
                     
        ObservableList<candidacy> all,Single ;
             all=tableaucandidature.getItems();
             Single=tableaucandidature.getSelectionModel().getSelectedItems();
             candidacy A = Single.get(0);
             if(A.getEtat().equals("Faire le test"))
             {
                 btnfaire.setDisable(false);
                 
             }else{
                 btnfaire.setDisable(true);
             }

    }

    @FXML
    private void supprimer(ActionEvent event) {
        ObservableList<candidacy> all,Single ;
        all=tableaucandidature.getItems();
        Single=tableaucandidature.getSelectionModel().getSelectedItems();
        candidacy A = Single.get(0);
        sc.supprimer(A);
        Aff();
    }

    @FXML
    private void imprimer(ActionEvent event) {
        
        
        PrinterJob job = PrinterJob.createPrinterJob();
       
        Node root= this.tableaucandidature;
        
        if(job != null)
        {
            job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
            Printer printer = job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            boolean success = job.printPage(pageLayout, root);
            if(success)
                {
                    job.endJob();
                }
        }
    }
    
}
