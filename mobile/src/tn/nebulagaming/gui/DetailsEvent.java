/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package tn.nebulagaming.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import tn.nebulagaming.entities.Comment;
import tn.nebulagaming.entities.Event;
import tn.nebulagaming.entities.Participation;
import tn.nebulagaming.services.ServiceComment;
import tn.nebulagaming.services.ServiceParticipation;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class DetailsEvent extends BaseForm {
 Form current;
    public DetailsEvent(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Event");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(
                sl
        ));

        /*TextField username = new TextField("sandeep");
        username.setUIID("TextFieldBlack");
        addStringValue("Username", username);

        TextField email = new TextField("sandeep@gmail.com", "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);
        
        TextField password = new TextField("sandeep", "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Password", password);

        CheckBox cb1 = CheckBox.createToggle(res.getImage("on-off-off.png"));
        cb1.setUIID("Label");
        cb1.setPressedIcon(res.getImage("on-off-on.png"));
        CheckBox cb2 = CheckBox.createToggle(res.getImage("on-off-off.png"));
        cb2.setUIID("Label");
        cb2.setPressedIcon(res.getImage("on-off-on.png"));
        
        addStringValue("Facebook", FlowLayout.encloseRightMiddle(cb1));
        addStringValue("Twitter", FlowLayout.encloseRightMiddle(cb2));*/
    }

    public DetailsEvent(Resources res, Event event , Form previous) {
        super("Newsfeed", BoxLayout.y()); 
        current=this; //Back 
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Event");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage(event.getPhotoPost());
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        add(LayeredLayout.encloseIn(
                sl
        ));

        Label label = new Label(event.getTitlePost());
        addLabel("Title Event", label);

        Label label2 = new Label(event.getDescPost());
        addLabel("Description Event", label2);

        Label label3 = new Label(event.getAddressEvent());
        addLabel("Address Event", label3);

        Label label4 = new Label(String.valueOf(event.getNbTicketAvailable()));
        addLabel("Number of Tickets", label4);

        Button btn = new Button("Participate");
        addButton("", btn);
        Button btnMap = new Button("View On Map");
        addButton("", btnMap);
        btnMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                    MapForm mf = new MapForm (event , current);     
            }

        });
        
        TextComponent comment = new TextComponent();
        
        comment.setUIID("TextAreaComment");
        addTextComponent("Add Comment : ", comment);

        Button btnComment = new Button("Submit");
        addButton("", btnComment);
        
        Button btnBack = new Button ("Go Back") ; 
        btnBack.addActionListener (e -> {
            previous.showBack () ; 
        }) ; 
        addButton("", btnBack);
        

        btnComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                handleEventComment(btnComment, event.getIdPost(), comment);
            }

        });
        handleEventParticipate(btn, event.getIdPost());
        //

    }

    private void addLabel(String s, Label label) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).add(BorderLayout.CENTER, label));

    }

    private void addTextField(String s, TextField text) {
        add(BorderLayout.west(new Label(s, "")).add(BorderLayout.CENTER, text));
        add(createLineSeparator(0xeeeeee));
    }
    
    private void addTextComponent (String s, TextComponent text) {
        add(BorderLayout.west(new Label(s, "")).add(BorderLayout.CENTER, text));
        add(createLineSeparator(0xeeeeee));
    }

    private void addButton(String s, Button btn) {
        add(BorderLayout.west(new Label(s, "")).add(BorderLayout.CENTER, btn));
        add(createLineSeparator(0xeeeeee));
    }

    private void handleEventParticipate(Button btn, int idPost) {
        ServiceParticipation sp = new ServiceParticipation();
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("actionPerformed");
                Participation participation = new Participation(idPost, 2, 2);
                boolean spResult = sp.addParticipation(participation);
                if (spResult) {
                    Dialog d = new Dialog("Success");
                    TextArea ta = new TextArea("Participation added successfully ! ", 3, 10);
                    ta.setUIID("PopupBody");
                    ta.setEditable(false);
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, ta);
                    d.showPopupDialog(btn);
                } else {
                    Dialog d = new Dialog("Error");
                    TextArea ta = new TextArea("Participation was not added ! ", 3, 10);
                    ta.setUIID("PopupBody");
                    ta.setEditable(false);
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, ta);
                    d.showPopupDialog(btn);
                }
            }
        });
    }

    private void handleEventComment(Button btnComment, int idPost, TextComponent commentComp ) {
        ServiceComment sc = new ServiceComment();
        btnComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Validator valComment = new Validator();
                valComment.addConstraint(commentComp, new LengthConstraint(2));
                
                
                Comment comment = new Comment(idPost, 2, commentComp.getText());
                boolean spResult = sc.addComment(comment);
                if (spResult) {
                    Dialog d = new Dialog("Success");
                    TextArea ta = new TextArea("Your comment was added successfully ! ", 3, 10);
                    ta.setUIID("PopupBody");
                    ta.setEditable(false);
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, ta);
                    d.showPopupDialog(btnComment);
                } else {
                    Dialog d = new Dialog("Error");
                    TextArea ta = new TextArea("Comment was not added ! ", 3, 10);
                    ta.setUIID("PopupBody");
                    ta.setEditable(false);
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, ta);
                    d.showPopupDialog(btnComment);
                }
            }
        });
        
        /* valComment = new Validator();
        valComment.addConstraint(commentContent, new LengthConstraint(2));*/
    }

}
