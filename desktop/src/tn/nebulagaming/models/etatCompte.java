/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tn.nebulagaming.models;


public class etatCompte {
    public enum etat {
    active, inactive, banned, restricted;

        String getName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        void setName(etat etatCompte) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        

        
    }
    private int id;
    private etat name;

    public etatCompte(int id, etat name) {
        this.id = id;
        this.name = name ;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public etat getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(etat name) {
        this.name = name;
    }
    
    
}
