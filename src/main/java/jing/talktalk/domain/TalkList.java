package jing.talktalk.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Document(collection = "talkList")
public class TalkList implements Serializable {

    @Id
    private ObjectId id;
    private String username;
    private String avatarPath = "";
    private String talkContent = "";
    private Date time;
    private int zanNum = 0;
    private ArrayList<String> zanPerson;
    private int commentNum = 0;
    private ArrayList<Comment> commentContent;
    private ArrayList<String> talkImages;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }

    public ArrayList<String> getZanPerson() {
        return zanPerson;
    }

    public void setZanPerson(ArrayList<String> zanPerson) {
        this.zanPerson = zanPerson;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public ArrayList<Comment> getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(ArrayList<Comment> commentContent) {
        this.commentContent = commentContent;
    }

    public ArrayList<String> getTalkImages() {
        return talkImages;
    }

    public void setTalkImages(ArrayList<String> talkImages) {
        this.talkImages = talkImages;
    }
}
