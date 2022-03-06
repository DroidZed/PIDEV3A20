/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tn.nebulagaming.models.Streaming;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class StreamHomeController implements Initializable {

    @FXML
    private WebView web;
    Streaming stream;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }    

    void iniializeFxml(Streaming s) {
        stream=s;
        System.out.println(stream.getLink());
          final WebEngine Web=web.getEngine();
        String urlweb=stream.getLink();
        Web.load(urlweb);
    }

    void showData(Streaming s) {
        
    }
    
}
