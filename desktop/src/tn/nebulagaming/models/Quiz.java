/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;

/**
 *
 * @author SuperNova
 */
public class Quiz extends Post {

    private String correctAnswer;

    //Add Quiz
    public Quiz(String titlePost, String descPost, int statusPost, String typePost, Date startDTM, Date endDTM, int idOwnerUser) {
	super(titlePost, descPost, statusPost, typePost, startDTM, endDTM, idOwnerUser);
    }

    public Quiz(int idPost, Date postedDTM, String titlePost, String descPost, int statusPost, Date startDTM, Date endDTM, int idOwnerUser, String correctAnswer) {
	super(idPost, postedDTM, titlePost, descPost, statusPost, startDTM, endDTM, idOwnerUser);
	this.correctAnswer = correctAnswer;
    }

    public Quiz(int idPost, String titlePost, String descPost, int statusPost, Date startDTM, Date endDTM, String correctAnswer) {
	super(idPost, titlePost, descPost, statusPost, startDTM, endDTM);
	this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
	return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
	this.correctAnswer = correctAnswer;
    }
}
