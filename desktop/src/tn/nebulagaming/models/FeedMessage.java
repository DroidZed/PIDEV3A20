/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

/**
 *
 * @author SuperNova
 */
public class FeedMessage {
     String title;
    String description;
    String link;
    String author;
    String guid;
    String pubdate ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public void setPubDate (String pubdate) {
        this.pubdate = pubdate;
    }
    
    public String getPubDate () {
        return this.pubdate ;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        String str = "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link +", pubdate=" + pubdate + ", author=" + author + ", guid=" + guid
                + "]";
        str = str.replaceAll("\\<.*?\\>", "");
        return str;
    } 
}
