package jing.talktalk.domain;

import org.springframework.data.mongodb.core.aggregation.DateOperators;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private String replyUser = "";
    private String replyToUser = "";
    private String replyContent = "";
    private Date replyTime;

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(String replyToUser) {
        this.replyToUser = replyToUser;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "replyUser='" + replyUser + '\'' +
                ", replyToUser='" + replyToUser + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", replyTime=" + replyTime +
                '}';
    }
}
