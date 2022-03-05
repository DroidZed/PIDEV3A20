/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.utils;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.swing.MapView;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author SuperNova
 */
public class MapHandler extends MapView {
    
 private Map map ;
 private double llat , llong;

public  MapHandler (String nName){
    JFrame frame = new JFrame(nName);
    setOnMapReadyHandler(new MapReadyHandler(){
    
        @Override
        public void onMapReady(MapStatus status){
            if(status==MapStatus.MAP_STATUS_OK){
                
                map=getMap();
                MapOptions mapOptions = new MapOptions();
                MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                mapOptions.setMapTypeControlOptions(controlOptions);
                getInformation(llat,llong);
                map.setOptions(mapOptions);
                map.setCenter(new LatLng (llat,llong));
                map.setZoom(11.0);
                
                
            }
        }

    });
    frame.add(this,BorderLayout.CENTER);
    frame.setSize(700,500);
    frame.setVisible(true);
    
}

    public MapHandler() {
        this.map=map;
    }

    public void getInformation(double passedlat , double passedlong) {
            this.llat=passedlat;
           this.llong=passedlong;  
    }  
}



