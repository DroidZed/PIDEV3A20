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
import java.util.stream.Collectors;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServicePost implements IService<Post> {
 
    Connection cnx = GlobalConfig.getInstance().getCnx() ;

    @Override
    public void add(Post post) {
        try {
        String query = "INSERT INTO tbl_post (postedDTM , titlePost,descPost,statusPost,typePost,photoPost,idUser) VALUES ('" +post.getPostedDTM()+"','" +post.getTitlePost()+"','"+post.getDescPost()+"','"+post.getStatusPost()+"','"+post.getTypePost()+"','"+post.getPhotoPost()+"','"+post.getIdOwnerUser()+"')"; 
        Statement st = cnx.createStatement();
        st.executeUpdate(query) ;
        System.out.println("Post added with success !");

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int idPost) {
         try {
        String query = "DELETE FROM tbl_post WHERE idPost =" +idPost; 
        Statement st = cnx.createStatement();
        st.executeUpdate(query) ;
        System.out.println("Post deleted with success !");

    }catch (SQLException e) {
        System.err.println(e.getMessage());
    } 
    }

    @Override
    public void update(Post post) {
    PreparedStatement st ;
     try {
        String query = "UPDATE tbl_post SET titlePost = ?,descPost = ? , photoPost = ? , statusPost = ?  WHERE idPost =?"; 
        st = cnx.prepareStatement(query);
        st.setString (1,post.getTitlePost()) ;
        st.setString (2,post.getDescPost()) ;
        st.setString (3,post.getPhotoPost()) ;
        st.setInt (4,post.getStatusPost()) ;
        st.setInt(5,post.getIdPost());
        st.executeUpdate() ;
        System.out.println("Post updated with success !");

    }catch (SQLException e) {
        Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE , null , e);
    }
    
    }

    @Override
    public List<Post> display() {
        List<Post> listPost = new ArrayList<>(); 

        try {
            
            /* SELECT tbl_user.nameUser , tbl_post.idPost , tbl_post.postedDTM , tbl_post.titlePost , tbl_post.descPost , tbl_post.statusPost , tbl_post.photoPost , tbl_post.idUser FROM tbl_post JOIN tbl_user ON tbl_post.idUser = tbl_user.idUser WHERE typePost = 'Post' */
           String query = "SELECT idPost , postedDTM , titlePost , descPost  , statusPost , photoPost , idUser FROM tbl_post WHERE typePost = 'Post'"; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           while (rs.next()) {
               listPost.add(new Post(
                                   rs.getInt(1) ,
                                   rs.getDate(2),
                                   rs.getString(3),
                                   rs.getString(4),     
                                   rs.getInt(5),
                                   rs.getString(6) ,
                                   rs.getInt(7))) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return listPost ; 
    }
    
    public void search (List<Post> postList ,String keyWord) {

        List<Post> searchResult = postList.stream().filter(e -> e.getTitlePost().contains(keyWord) == true).collect(Collectors.toList());
        if ( !searchResult.isEmpty()) {
            for (Post post : searchResult) {
                System.err.println(post);
            }
        }
        else {
                System.err.println("Aucun résultat pour le mot clé "+keyWord);
        }
    
    }
    
    public void filterByStatusPost (List<Post> postList ,int statusPost) {

        List<Post> searchResult = postList.stream().filter(e -> e.getStatusPost() == statusPost).collect(Collectors.toList());
        if ( !searchResult.isEmpty()) {
            for (Post post : searchResult) {
                System.err.println(post);
            }
        }
        else {
                System.err.println("Aucune poste avec le statut "+statusPost);
        }   
    }
    
}
