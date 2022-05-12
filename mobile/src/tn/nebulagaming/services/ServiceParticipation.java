/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import tn.nebulagaming.entities.Participation;
import tn.nebulagaming.utils.SingletonUser;
import tn.nebulagaming.utils.Statics;

/**
 *
 * @author SuperNova
 */
public class ServiceParticipation {

    private ConnectionRequest request;
    public boolean resultOK;
    private SingletonUser singUser = SingletonUser.getInstance();

    public ServiceParticipation() {
        request = new ConnectionRequest();
    }

    public boolean addParticipation(Participation participation) {
        System.out.println(participation);
        System.out.println("********");
        String url = Statics.BASE_URL + "/wsparticipations/participate";

        request.setUrl(url);
        request.setPost(true);
        request.setRequestBody("{\n"
                + "        \"idPost\": " + participation.getIdPost() + ",\n"
                + "        \"idPayType\": 2,\n"
                + "        \"idUser\": "+singUser.getIdUser()+"\n"
                + "}");
        request.addRequestHeader("Content-Type", "application/json");
        request.setHttpMethod("POST");

	System.out.println(request.getRequestBody());

        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = request.getResponseCode() == 200; //Code HTTP 200 OK
                
                request.removeResponseListener(this);
                System.out.println(request.getResponseCode() + "----" + request.getResponseData());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOK;
    }

}
