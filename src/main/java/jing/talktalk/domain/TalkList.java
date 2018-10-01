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
    private String idString = "";
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

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public ArrayList<Comment> getCommentContent() {
        return commentContent;
    }

    public ArrayList<String> getTalkImages() {
        return talkImages;
    }

    @Override
    public String toString() {
        return "TalkList{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", talkContent='" + talkContent + '\'' +
                ", time=" + time +
                ", zanNum=" + zanNum +
                ", zanPerson=" + zanPerson +
                ", commentNum=" + commentNum +
                ", commentContent=" + commentContent +
                ", talkImages=" + talkImages +
                '}';
    }
}
