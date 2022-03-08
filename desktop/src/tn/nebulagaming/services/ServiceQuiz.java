/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.nebulagaming.models.Quiz;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceQuiz implements IServiceNewsfeed<Quiz> {

    Connection cnx;

    public ServiceQuiz() {

	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void add(Quiz quiz) {

	try {
	    String query = "INSERT INTO tbl_post (postedDTM , titlePost,descPost,statusPost,startDTM,endDTM,typePost,idUser) VALUES ('" + quiz.getPostedDTM() + "','" + quiz.getTitlePost() + "','" + quiz.getDescPost() + "','" + quiz.getStatusPost() + "','" + quiz.getStartDTM() + "','" + quiz.getEndDTM() + "','" + quiz.getTypePost() + "','" + quiz.getIdOwnerUser() + "')";
	    Statement st = cnx.createStatement();
	    st.executeUpdate(query);
	    System.out.println("Quiz added with success !");

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public void assignCorrectAnswer(Quiz quiz, String correctAnswer) {
	PreparedStatement st;
	try {
	    String query = "UPDATE tbl_post SET correctAnswer = ?  WHERE idPost =?";
	    st = cnx.prepareStatement(query);
	    st.setString(1, correctAnswer);
	    st.setInt(2, quiz.getIdPost());
	    st.executeUpdate();
	    System.out.println("Answer assigned to Quiz !");

	} catch (SQLException e) {
	    Logger.getLogger(ServiceQuiz.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void delete(int idPost) {
	try {
	    String query = "DELETE FROM tbl_post WHERE idPost =" + idPost;
	    Statement st = cnx.createStatement();
	    st.executeUpdate(query);
	    System.out.println("Quiz deleted with success !");

	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
    }

    @Override
    public void update(Quiz quiz) {
	PreparedStatement st;
	try {
	    String query = "UPDATE tbl_post SET titlePost = ?,descPost = ? , statusPost = ? , startDTM = ? , endDTM= ? , correctAnswer = ? WHERE idPost =?";
	    st = cnx.prepareStatement(query);
	    st.setString(1, quiz.getTitlePost());
	    st.setString(2, quiz.getDescPost());
	    st.setInt(3, quiz.getStatusPost());
	    st.setDate(4, quiz.getStartDTM());
	    st.setDate(5, quiz.getEndDTM());
	    st.setString(6, quiz.getCorrectAnswer());
	    st.setInt(7, quiz.getIdOwnerUser());
	    st.executeUpdate();
	    System.out.println("Quiz updated with success !");

	} catch (SQLException e) {
	    Logger.getLogger(ServiceQuiz.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public List<Quiz> display() {
	List<Quiz> listQuiz = new ArrayList<>();

	try {

	    String query = "SELECT idPost , postedDTM , titlePost , descPost  , statusPost ,startDTM , endDTM  , idUser , correctAnswer FROM tbl_post WHERE typePost = 'Quiz'";
	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    while (rs.next()) {
		listQuiz.add(new Quiz(
			rs.getInt(1),
			rs.getDate(2),
			rs.getString(3),
			rs.getString(4),
			rs.getInt(5),
			rs.getDate(6),
			rs.getDate(7),
			rs.getInt(8),
			rs.getString(9)));
	    }
	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
	return listQuiz;
    }

}
