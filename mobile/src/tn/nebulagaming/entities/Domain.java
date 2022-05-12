/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.entities;

/**
 *
 * @author User
 */
public class Domain {
    
    private int id;
    private String name;
    private String description;

    public Domain() {
    }

    public Domain(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Domain(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "domain{" + "name=" + name + ", description=" + description + '}';
    }
    
    
    
}
