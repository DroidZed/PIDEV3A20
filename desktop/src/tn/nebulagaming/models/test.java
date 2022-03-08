/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

/**
 *
 * @author hp
 */
public class Test {
   private int idTest;
   private String durationTest;
   private String  questionTest;
   private int nbQuestion;
   private String choix;
   private String question;
   private int idUser;

    public Test(String durationTest, String questionTest, int nbQuestion, String choix, String question) {
        this.durationTest = durationTest;
        this.questionTest = questionTest;
        this.nbQuestion = nbQuestion;
        this.choix = choix;
        this.question = question;
    }


    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public String getDurationTest() {
        return durationTest;
    }

    public void setDurationTest(String durationTest) {
        this.durationTest = durationTest;
    }

    public String getQuestionTest() {
        return questionTest;
    }

    public void setQuestionTest(String questionTest) {
        this.questionTest = questionTest;
    }

    public int getNbQuestion() {
        return nbQuestion;
    }

    public void setNbQuestion(int nbQuestion) {
        this.nbQuestion = nbQuestion;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

@Override
    public String toString() {
      System.out.println("----");
        return "test{" + ", \ndurationTest= " + this.durationTest +", \nnbQuestion=" + this.nbQuestion + ", \nQuestions: " + this.questionTest + this.question+ ", \nchoix=" + this.choix + ",\n----\n"+'}';
    }
}
