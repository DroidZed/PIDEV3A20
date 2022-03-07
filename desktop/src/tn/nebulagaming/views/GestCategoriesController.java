/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.nebulagaming.models.Categories;
import tn.nebulagaming.services.ServiceCategories;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import tn.nebulagaming.services.ServiceProducts;

/**
 * FXML Controller class
 *
 * @author anony
 */
public class GestCategoriesController implements Initializable {

    ServiceCategories Sc = new ServiceCategories();
    ServiceProducts Sp = new ServiceProducts();

    @FXML
    private TextField tfnomcat;
    @FXML
    private Button retour;

    @FXML
    private Button AddCat;

    @FXML
    private Button EditCat;

    @FXML
    private Button DeleteCat;

    @FXML
    private TableView<Categories> tblCat;

    @FXML
    private TableColumn<Categories, String> col_nomcat;

    ObservableList<Categories> list;

    int index = -1;
    @FXML
    private Label error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list = FXCollections.observableArrayList(Sc.afficher());

        col_nomcat.setCellValueFactory(new PropertyValueFactory<>("nameCategory"));

        tblCat.setItems(list);
        // TODO

        tblCat.setRowFactory(tv -> {

            TableRow<Categories> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (!row.isEmpty()) {
                    tfnomcat.setText(tblCat.getSelectionModel().getSelectedItem().getNameCategory());
                }
            });

            return row;
        });
    }

    //Ajouter 
    @FXML
    void AddCat(ActionEvent event) throws IOException {
        if (tfnomcat.getText().isEmpty()) {
            error.setText("Tu dois saisir un nom de categorie Valide");
        } else {
            Sc.ajouter(new Categories(tfnomcat.getText()));
            list = FXCollections.observableArrayList(Sc.afficher());
            Sp.Notificationmanager(0);
            UpdateTable();
        }
    }

    //Supp
    @FXML
    void DeleteCat(ActionEvent event) {
        Categories cat = tblCat.getSelectionModel().getSelectedItem();
        Sc.supprimer(cat);
        Sp.Notificationmanager(1);
        list.remove(cat);
    }

    //Modifier
    @FXML
    void EditCat(ActionEvent event) {
        if (tfnomcat.getText().isEmpty())
        { error.setText("Tu dois saisir un nom de categorie valide");
        }else {
        Categories cat = tblCat.getSelectionModel().getSelectedItem();
        cat.setNameCategory(tfnomcat.getText());
        Sc.modifier(cat);
        Sp.Notificationmanager(2);
        tblCat.refresh();
    }
    }

    //Return Home
    @FXML
    void returnb(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Mettre a jour le table aprés ajouter un eleem
    public void UpdateTable() {
        list = FXCollections.observableArrayList(Sc.afficher());

        col_nomcat.setCellValueFactory(new PropertyValueFactory<>("nameCategory"));

        tblCat.setItems(list);
        // TODO

        tblCat.setRowFactory(tv -> {

            TableRow<Categories> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (!row.isEmpty()) {
                    tfnomcat.setText(tblCat.getSelectionModel().getSelectedItem().getNameCategory());
                }
            });

            return row;
        });
    }
}
