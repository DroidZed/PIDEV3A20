/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.utils;

/**
 *
 * @author ASUS
 */
public class SingletonUser {

    private static SingletonUser instance = null;

    private int idUser = 0;
    
    private String username;

    private RoleEnum role;

    private SingletonUser() {
	
    }


    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public RoleEnum getRole() {
	return role;
    }

    public void setRole(RoleEnum role) {
	this.role = role;
    }

    public int getIdUser() {
	return idUser;
    }

    public void setIdUser(int idUser) {
	this.idUser = idUser;
    }

    public static SingletonUser getInstance() {
	
	if (instance == null) {
		instance = new SingletonUser();
	}

	return instance;
	
    }

    @Override
     public String toString()
     {
	return this.username;
	}
}
