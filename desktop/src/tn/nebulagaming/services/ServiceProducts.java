/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tn.nebulagaming.models.Products;
import tn.nebulagaming.utils.GlobalConfig;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import tn.nebulagaming.models.ProductsByCategory;
import org.controlsfx.control.Notifications;
import tn.nebulagaming.utils.JavaMail;

/**
 *
 * @author rayen
 */
public class ServiceProducts implements IProduct<Products> {

    JavaMail jvm = new JavaMail();
    Connection cnx = GlobalConfig.getInstance().getCONNECTION();

    public void ajouter(Products t) {
        try {
            String request
                    = "INSERT INTO tbl_product (nameProduct, priceProduct , qtyProduct , imageProduct  , idCategory , idUser)VALUES(?,?,?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setString(1, t.getNameProduct());
            st.setFloat(2, t.getPriceProduct());
            st.setInt(3, t.getQtyProduct());
            st.setString(4, t.getImageProduct());
            st.setInt(5, t.getIdCategory());
            st.setInt(6, t.getIdUser());

            st.executeUpdate();
            System.out.println("Produit ajouté");
            String Produit = t.getNameProduct();
            jvm.sendTextMail("Nebula Gaming Test", "rayen.bakali@gmail.com", "Votre " + Produit + " est mis en ligne");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimer(Products t) {
        try {
            String request = "DELETE FROM tbl_product WHERE idProduct=?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, t.getIdProduct());
            st.executeUpdate();
            System.out.println("Produit supprimée!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void modifier(Products t) {
        try {
            String request
                    = "UPDATE tbl_product SET nameProduct = ?, priceProduct = ?, QtyProduct = ?, imageProduct = ?, IdCategory = ? WHERE idProduct = ?";
            PreparedStatement st = cnx.prepareStatement(request);

            st.setString(1, t.getNameProduct());
            st.setFloat(2, t.getPriceProduct());
            st.setInt(3, t.getQtyProduct());
            st.setString(4, t.getImageProduct());
            st.setInt(5, t.getIdCategory());
            st.setInt(6, t.getIdProduct());

            st.executeUpdate();
            System.out.println("Produit modifié");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
    }

    @Override
    public List<Products> afficher() {
        List<Products> list = new ArrayList<>();
        try {
            String request = "SELECT* FROM tbl_product";
            PreparedStatement st = cnx.prepareStatement(request);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Products(rs.getInt("idProduct"), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getDate(8)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public Products findByname_Product(String name_Product) {

        return afficher().stream().filter(Products -> name_Product.equals(Products.getNameProduct())).findFirst().get();

    }

    @Override
    public Products findByID_Product(int id) {

        return afficher().stream().filter(e -> e.getIdProduct() == id).findFirst().get();
    }

    @Override
    public List<Products> sortByName() {

        return afficher().stream().sorted((a, b) -> a.getNameProduct().compareTo(b.getNameProduct()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Products> paginateProducts(int pageNum, int pageSize) {

        int SKIP_COUNT = (pageNum - 1) * pageSize;

        int LIMIT_COUNT = 0;

        try {

            LIMIT_COUNT = pageSize / SKIP_COUNT;

        } catch (ArithmeticException e) {
            LIMIT_COUNT = 5;
        }

        return afficher()
                .stream()
                .skip(SKIP_COUNT)
                .limit(LIMIT_COUNT).collect(Collectors.toList());
    }

    public List<ProductsByCategory> getProductsByCategory() {
        List<ProductsByCategory> list = new ArrayList<>();

        try {
            String request = "SELECT IdProduct, nameProduct, priceProduct, QtyProduct, imageProduct, nameCategory FROM tbl_product JOIN tbl_category USING(idCategory)";
            PreparedStatement st = cnx.prepareStatement(request);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new ProductsByCategory(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getFloat(3),
                                rs.getInt(4),
                                rs.getString(5),
                                rs.getString(6)
                        ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }

    public void Notificationmanager(int mode) {
        Notifications not = Notifications.create()
                .graphic(null)
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("clicked on notification");
                    }
                });
        not.darkStyle();
        switch (mode) {
            case 0:

                not.title("Success");
                not.text("Categorie crée");
                not.showInformation();
                break;
            case 1:

                not.title("Success ");
                not.text("Suppression terminer");
                not.showWarning();
                break;
            case 2:

                not.title("Success");
                not.text("Modification terminé");

                not.showInformation();
                break;
            case 3:

                not.text("Produit  ajouté et notifié Par mail");
                not.title("Success");
                not.showConfirm();
                break;
           

            case 4:
                not.text("Code Promo ajouté");
                not.title("Success");
                not.showWarning(); 
                break;
            case 5: 
                not.text("Mettez une entrée valide s'il vous plait");
                not.title("Entrée Non Valide");
                not.showWarning();
                break;
            default:

        }

    }
}
