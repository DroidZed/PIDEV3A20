/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.gui;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author anony
 */
public class EditProduct extends BaseForm {
    


    Form current;

    public EditProduct(Form prev) {

	current = this;

	setLayout(BoxLayout.y());

	setTitle("Add Product");

	getToolbar().addMaterialCommandToLeftBar("Back", '0', e -> {
	    prev.showBack();
	});

    }
}
