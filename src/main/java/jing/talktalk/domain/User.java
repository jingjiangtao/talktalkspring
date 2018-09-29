package jing.talktalk.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "user")
public class User implements Serializable {

    @Id
    private ObjectId Id;
    private String username = "";
    private String password = "";
    private int sumZan = 0;
    private int sumTalk = 0;
    private String signature = "";
    private String avatar = "default.jpg";

    public ObjectId getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSumZan() {
        return sumZan;
    }

    public void setSumZan(int sumZan) {
        this.sumZan = sumZan;
    }

    public int getSumTalk() {
        return sumTalk;
    }

    public void setSumTalk(int sumTalk) {
        this.sumTalk = sumTalk;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return  this.getId()+" "
                +this.getUsername()+" "
                +this.getPassword()+" "
                +this.getAvatar()+" "
                +this.getSignature()+" "
                +this.getSumTalk()+" "
                +this.getSumZan();
    }
}
