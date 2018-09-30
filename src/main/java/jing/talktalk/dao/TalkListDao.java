package jing.talktalk.dao;

import jing.talktalk.domain.TalkList;
import jing.talktalk.domain.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class TalkListDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    //添加说说
    public void insertOneTalk(TalkList talkList){
        mongoTemplate.insert(talkList);
    }
    //删除说说
    public void deleteOneTalk(ObjectId id){
        mongoTemplate.remove(query(where("id").is(id)), "talkList");
    }

    //修改说说
    public void updateTalk(TalkList talkList){
        mongoTemplate.save(talkList);
    }

    //根据Id查找说说
    public ArrayList<TalkList> findTalkById(ObjectId id){
        return (ArrayList<TalkList>) mongoTemplate.find(query(where("id").is(id)), TalkList.class);
    }


    //查找所有说说
    public ArrayList<TalkList> findAllTalks(){
        return (ArrayList<TalkList>) mongoTemplate.findAll(TalkList.class, "talkList");
    }
}
