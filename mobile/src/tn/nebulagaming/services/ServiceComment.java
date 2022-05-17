/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import tn.nebulagaming.entities.Comment;
import tn.nebulagaming.utils.SingletonUser;
import tn.nebulagaming.utils.Statics;


/**
 *
 * @author SuperNova
 */
public class ServiceComment {

    private ConnectionRequest request;
    public boolean resultOK;
    private SingletonUser singUser = SingletonUser.getInstance();

    public ServiceComment() {
        request = new ConnectionRequest();
    }

    public boolean addComment(Comment comment) {
        System.out.println("********");
        String url = Statics.BASE_URL + "/wscomments/comment";
        System.out.println("===>" + url);

        request.setUrl(url);
        request.setPost(true);
        System.out.println("{\n"
                + "        \"idPost\": " + comment.getIdPost() + ",\n"
                + "        \"idUser\": "+singUser.getIdUser()+" ,\n"
                + "        \"comment\":\"" + comment.getComment() + "\"\n"
                + "}");
        request.setRequestBody("{\n"
                + "        \"idPost\": " + comment.getIdPost() + ",\n"
                + "        \"idUser\": "+singUser.getIdUser()+" ,\n"
                + "        \"comment\":\"" + comment.getComment() + "\"\n"
                + "}");
        request.addRequestHeader("Content-Type", "application/json");
        request.setHttpMethod("POST");

        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = request.getResponseCode() == 200; //Code HTTP 200 OK

                request.removeResponseListener(this);
                System.out.println(request.getResponseCode() + "----" + request.getResponseErrorMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOK;
    }

}
