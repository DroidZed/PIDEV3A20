package tn.nebulagaming.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tn.nebulagaming.models.ProductByCategory;
import tn.nebulagaming.models.WishList;

/**
 *
 * @author Aymen
 */
public class WishListService extends ServiceMotherClass{

    public WishListService() {

	TABLE_NAME = "tbl_wishlist";
    }

 
    public List<WishList> getAll() {

	List<WishList> wishList = new ArrayList<>();

	String sql = "SELECT * FROM " + TABLE_NAME;

	try {
	    ResultSet res = conn.prepareStatement(sql).executeQuery();

	    while (res.next()) {

		wishList.add(new WishList(
			res.getInt(1),
			res.getInt(2),
			res.getInt(3)
		));
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return wishList;
    }


    public void create(WishList wishList) {

	String sql = "INSERT INTO " + TABLE_NAME + " (idUser, idProduct) VALUES (?, ?)";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, wishList.getIdUser());
	    stm.setInt(2, wishList.getIdProduct());

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }


    public void delete(int idProd, int idUser) {

	String sql = "DELETE FROM " + TABLE_NAME + " where idProduct = ? and idUser = ?";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, idProd);
	    stm.setInt(2, idUser);

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public List<WishList> getOfUser(int idUser) {

	return this.getAll()
		.stream()
		.filter(wl -> wl.getIdUser() == idUser)
		.collect(Collectors.toList());
    }

    public List<ProductByCategory> paginateProducts(int idUser, int pageNum, int pageSize) {

	int SKIP_COUNT = (pageNum - 1) * pageSize;

	int LIMIT_COUNT = SKIP_COUNT != 0 ? pageSize / SKIP_COUNT : 7;

	return this.groupByCategory(idUser)
		.stream()
		.skip(SKIP_COUNT)
		.limit(LIMIT_COUNT).collect(Collectors.toList());
    }

    public List<ProductByCategory> groupByCategory(int idUser) {

	List<ProductByCategory> wishItemsByCategory = new ArrayList<>();

	String sql = "select wl.idProduct, nameProduct, priceProduct, nameCategory from " + TABLE_NAME + " wl left outer join tbl_product prod on wl.idProduct = prod.idProduct right outer JOIN tbl_category cat on prod.idCategory = cat.idCategory where wl.idUser = ? order by nameCategory";

	try {

	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, idUser);

	    ResultSet res = stm.executeQuery();

	    while (res.next()) {

		wishItemsByCategory.add(
			new ProductByCategory(res.getInt(1), res.getString(2), res.getFloat(3), res.getString(4))
		);
	    }

	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}

	return wishItemsByCategory;
    }
}
