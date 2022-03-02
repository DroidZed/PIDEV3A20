package tn.nebulagaming.services;

import java.sql.Date;
import tn.nebulagaming.interfaces.IService;
import java.util.List;
import tn.nebulagaming.models.UserOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import tn.nebulagaming.models.UserOrderCombined;

/**
 *
 * @author Aymen
 */
public class UserOrderService extends ServiceMotherClass implements IService<UserOrder> {

    public UserOrderService() {

	this.TABLE_NAME = "tbl_userorder";
    }

    @Override
    public List<UserOrder> getAll() {

	List<UserOrder> userOrders = new ArrayList<>();

	String sql = "SELECT * FROM " + this.TABLE_NAME;

	try {
	    ResultSet res = conn.prepareStatement(sql).executeQuery();

	    while (res.next()) {

		userOrders.add(new UserOrder(
			res.getInt(1),
			res.getDate(2),
			res.getDate(3),
			res.getInt(4),
			res.getInt(5),
			res.getString(6),
			res.getInt(7)
		));
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return userOrders;
    }

    @Override
    public void update(UserOrder t, int orderNumber) {

	String sql = "UPDATE " + TABLE_NAME + " SET createdDTM = ?, idStatusOrder = ?, orderAddress = ? where numberOrder = ?";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setDate(1, t.getCreatedDTM());
	    stm.setInt(2, t.getIdStatusOrder());
	    stm.setString(3, t.getOrderAddress());
	    stm.setInt(4, orderNumber);

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public void updateOnPayment(UserOrder t, int orderNumber) {
	String sql = "UPDATE " + TABLE_NAME + " SET payDTM = ?, idPayType = ? where numberOrder = ?";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setDate(1, t.getPayDTM());
	    stm.setInt(2, t.getIdPayType());
	    stm.setInt(3, orderNumber);

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    @Override
    public void create(UserOrder t) {

	String sql = "INSERT INTO " + TABLE_NAME + "(createdDTM, idPayType, orderAddress, idUser) VALUES (?, ?, ?, ?)";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setDate(1, Date.valueOf(LocalDate.now()));
	    stm.setInt(2, t.getIdPayType());
	    stm.setString(3, t.getOrderAddress());
	    stm.setInt(4, t.getIdUser());

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    public void createWithoutPayment(UserOrder t) {
	String sql = "INSERT INTO " + TABLE_NAME + " (`createdDTM`, `orderAddress`, `idUser`) VALUES (?, ?, ?);";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setDate(1, Date.valueOf(LocalDate.now()));
	    stm.setString(2, t.getOrderAddress());
	    stm.setInt(3, t.getIdUser());

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    List<UserOrder> sortBy(int idUser, String criteria) {

	List<UserOrder> list = this.getOfUser(idUser);

	switch (criteria) {

	    case "CREATION_DATE":
		list = list.stream().sorted((o1, o2) -> o1.getCreatedDTM().compareTo(o2.getCreatedDTM())).collect(Collectors.toList());
		break;

	    case "PAID_DATE":
		list = list.stream().sorted((o1, o2) -> o1.getPayDTM().compareTo(o2.getPayDTM())).collect(Collectors.toList());
		break;
	    default:
		break;
	}
	return list;
    }

    public UserOrder getOne(int numberOrder, int idUser) {

	return this.getOfUser(idUser)
		.stream()
		.filter(c -> c.getNumberOrder() == numberOrder)
		.findFirst().get();
    }

    public List<UserOrder> getOfUser(int idUser) {

	return this.getAll().stream()
		.filter(f -> f.getIdUser() == idUser)
		.collect(Collectors.toList());
    }

    public List<UserOrder> paginateUserOrders(int idUser, int pageNum, int pageSize) {

	int SKIP_COUNT = (pageNum - 1) * pageSize;

	int LIMIT_COUNT = SKIP_COUNT != 0 ? pageSize / SKIP_COUNT : 7;

	return this.getOfUser(idUser).stream()
		.skip(SKIP_COUNT)
		.limit(LIMIT_COUNT).collect(Collectors.toList());
    }

    public List<UserOrderCombined> getAssociated() {

	String sql = "SELECT numberOrder, nameUser, uo.createdDTM, uo.payDTM, st.statusOrder FROM tbl_userorder AS uo JOIN tbl_user AS u USING(idUser) JOIN tbl_statusorder st using(idStatusOrder) ORDER BY nameUser";

	List<UserOrderCombined> userOrders = new ArrayList<>();

	try {
	    ResultSet res = conn.prepareStatement(sql).executeQuery();

	    while (res.next()) {

		userOrders.add(new UserOrderCombined(
			res.getInt(1),
			res.getDate(3),
			res.getDate(4),
			res.getString(2),
			res.getString(5)
		));
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return userOrders;
    }
}
