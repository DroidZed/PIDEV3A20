/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tn.nebulagaming.interfaces.IService;
import tn.nebulagaming.models.OrderLine;
import static tn.nebulagaming.services.ServiceMotherClass.conn;

/**
 *
 * @author Aymen Dhahri
 */
public class OrderLineService extends ServiceMotherClass implements IService<OrderLine> {

    public OrderLineService() {

	TABLE_NAME = "tbl_orderline";
    }

    @Override
    public List<OrderLine> getAll() {

	String sql = "SELECT * FROM " + this.TABLE_NAME;

	List<OrderLine> orderLines = new ArrayList<>();

	try {
	    ResultSet res = conn.prepareStatement(sql).executeQuery();

	    while (res.next()) {

		orderLines.add(new OrderLine(
			res.getInt(1),
			res.getInt(2),
			res.getInt(3)
		));
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return orderLines;
    }

    @Override
    public void update(OrderLine t, int idOrderLine) {

	String sql = "UPDATE " + this.TABLE_NAME 
	+ " SET quantOrdLine = ? where idOrderLine = ?";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, t.getQuantOrdLine());
	    stm.setInt(2, idOrderLine);

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    @Override
    public void create(OrderLine t) {

	String sql = "INSERT INTO" 
	+ this.TABLE_NAME + "(quantOrdLine, idProduct, numberOrder) VALUES (?, ?, ?)";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, t.getQuantOrdLine());
	    stm.setInt(2, t.getIdProduct());
	    stm.setInt(3, t.getNumberOrder());

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation complete !");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public OrderLine getById(int id) {
	return this.getAll()
	.stream()
	.filter(o -> o.getIdOrderLine() == id)
	.collect(Collectors.toList())
	.get(0);
    }

    public float calculateTotal(int numOrder) {

	String sql = "select p.priceProduct * quantOrdLine from tbl_orderline o left outer join tbl_product as p on p.idProduct = o.idProduct where numberOrder = ? order by numberOrder";

	List<Float> sums = new ArrayList<>();

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, numOrder);

	   ResultSet res = stm.executeQuery();

	    while (res.next()) {

		sums.add(res.getFloat(1));
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return sums.stream().reduce(0.0f, (a, b) -> a + b);
    }

}
