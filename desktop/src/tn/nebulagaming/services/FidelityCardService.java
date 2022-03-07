package tn.nebulagaming.services;

import java.sql.Date;
import tn.nebulagaming.interfaces.IService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tn.nebulagaming.models.FidelityCard;

/**
 *
 * @author Aymen Dhahri
 */
public class FidelityCardService extends ServiceMotherClass {

    public FidelityCardService() {

	TABLE_NAME = "tbl_fidcard";
    }

    private List<FidelityCard> getAll() {

	String sql = "SELECT * FROM " + TABLE_NAME;

	List<FidelityCard> fidCards = new ArrayList<>();

	try {
	    ResultSet res = conn.prepareStatement(sql).executeQuery();

	    while (res.next()) {

		fidCards.add(new FidelityCard(
			res.getInt(1),
			res.getInt(2),
			res.getDate(3),
			res.getInt(4),
			res.getInt(5)
		));
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return fidCards;
    }

    public void create(FidelityCard t) {

	String sql = "INSERT INTO" + TABLE_NAME
		+ "(nbPointsFid, createdDTM, idCardType, idUser) VALUES (?, ?, ?, ?)";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, t.getIdUser());
	    stm.setDate(2, Date.valueOf(LocalDate.now()));
	    stm.setInt(3, t.getIdCardType());
	    stm.setInt(4, t.getIdUser());

	    stm.executeUpdate();

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    public FidelityCard getOfUser(int idUser) {

	return this.getAll().stream()
		.filter(f -> f.getIdUser() == idUser)
		.collect(Collectors.toList())
		.get(0);
    }

    public void upgradeCardType(FidelityCard fd) {

	String sql = "UPDATE " + TABLE_NAME + " set idCardType = ? Where idFidelityCard = ?";

	try {
	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, fd.getIdCardType());
	    stm.setInt(2, fd.getIdFidelityCard());

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation success !!");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public String getCardTypeById(int idCardType) {

	String cardType = "";

	try {
	    String sql = "SELECT cardType FROM tbl_cardtype where idCardType = ?";

	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, idCardType);

	    ResultSet res = stm.executeQuery();

	    if (res.next()) {

		cardType = res.getString(1);
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return cardType;
    }

    public void addPoints(int idUser, int nbPoints) {

	try {

	    FidelityCard fd = this.getOfUser(idUser);

	    String sql = "UPDATE " + TABLE_NAME + " set nbPointsFid = ? Where idFidelityCard = ?";

	    PreparedStatement stm = conn.prepareStatement(sql);

	    stm.setInt(1, fd.getNbPointsFid());
	    stm.setInt(2, fd.getIdCardType());
	    stm.setInt(3, fd.getIdFidelityCard());

	    if (stm.executeUpdate() == 1) {
		System.out.println("Operation success !!");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }
}
