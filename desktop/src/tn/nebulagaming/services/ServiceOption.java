/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.nebulagaming.models.Option;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceOption implements IServiceNewsfeed<Option> {

    Connection cnx;

    public ServiceOption() {
	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void add(Option option) {
	try {
	    String query = "INSERT INTO tbl_option (statusOptions,contentOption,idPost) VALUES ('" + option.getStatusOption() + "' , '" + option.getContentOption() + "','" + option.getIdPost() + "')";
	    Statement st = cnx.createStatement();
	    st.executeUpdate(query);
	    System.out.println("Option added with success !");

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    @Override
    public void delete(int id) {
	// TODO
    }

    @Override
    public void update(Option option) {
	// TODO
    }

    @Override
    public List<Option> display() {
	return null; // TODO
    }

    public List<String> getOptionsByQuiz(int idPost) {
	List<String> listOption = new ArrayList<>();

	try {
	    String query = "SELECT contentOption FROM tbl_option WHERE idPost =" + idPost;
	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    while (rs.next()) {
		listOption.add(rs.getString(1));
	    }

	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
	return listOption;
    }

}
