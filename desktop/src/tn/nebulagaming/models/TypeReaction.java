/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SuperNova
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TypeReaction {
    
    @Getter private int idTypeReact ; 
    @Getter @Setter private String typeReact ; 
    @Getter @Setter private String iconReact ; 
 
     public TypeReaction (String typeReact , String iconReact) {
        this.typeReact = typeReact ;
        this.iconReact = iconReact ; 
    }
}
