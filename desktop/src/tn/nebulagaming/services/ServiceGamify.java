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
import static java.util.Comparator.naturalOrder;
import java.util.HashMap;
import java.util.List;
import static java.util.stream.Collectors.toList;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceGamify {

    ServiceAnswer sa;
    ServicePost sp;
    ServiceParticipation spp;
    Connection cnx;

    public ServiceGamify() {

	sa = new ServiceAnswer();
	sp = new ServicePost();
	spp = new ServiceParticipation();
	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    public List<Integer> LeastProcessTime(int idPost) {
	int idUserLeastProcessTime = 00;
	HashMap<Integer, Integer> map = new HashMap<>();
	List<Integer> userIDs = null;
	try {
	    String query = "SELECT\n"
		    + "  tbl_answerpost.idUser,\n"
		    + "  tbl_answerpost.answeredDTM,\n"
		    + "  tbl_post.postedDTM,\n"
		    + "  TIMESTAMPDIFF(second ,tbl_post.postedDTM , tbl_answerpost.answeredDTM) AS difference \n"
		    + "FROM tbl_post , tbl_answerpost where tbl_post.idPost = tbl_answerpost.idPost AND tbl_answerpost.idPost =" + idPost + " AND tbl_post.typePost ='Quiz'";
	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    while (rs.next()) {
		map.put(rs.getInt(1), rs.getInt(4));
	    }

	    List<Integer> list = new ArrayList<>(map.values());
	    list.sort(naturalOrder());
	    int min = list.get(0);
	    userIDs = map.entrySet().stream()
		    .filter(e -> e.getValue() == min)
		    .map(e -> e.getKey())
		    .collect(toList());

	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}

	return userIDs;
    }

    public List<Integer> getUserAnsweredCorrectly(int idPost) {
	List<Integer> usersAnsweredCorrectly = new ArrayList<>();
	try {
	    String query = "SELECT\n"
		    + "  tbl_answerpost.idUser ,\n"
		    + "  tbl_answerpost.idOption as answer , \n"
		    + "  tbl_post.idCorrectAnswer as correctAnswer  \n"
		    + "FROM tbl_post , tbl_answerpost where tbl_post.idPost = tbl_answerpost.idPost AND tbl_answerpost.idPost =" + idPost;
	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(query);

	    if (rs.next() == false) {
		System.out.println("Aucun user n'a répondu à ce poste !");
	    } else {
		while (rs.next()) {
		    if (rs.getInt("answer") == rs.getInt("correctAnswer")) {
			usersAnsweredCorrectly.add(rs.getInt("idUser"));
		    }
		}
	    }
	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
	return usersAnsweredCorrectly;
    }

    public int interactionComment(int idUser) {
	int countComment = 0;
	try {
	    String query = "SELECT COUNT(idComment) as nbComment FROM tbl_comment where idUser=" + idUser;
	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    if (rs.next() == false) {
		System.out.print("Aucun commentaire !");
	    } else {
		countComment = rs.getInt("nbComment");
	    }
	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
	return countComment;
    }

    /*public int levelUp (int idUser) {
        
    }*/
}
