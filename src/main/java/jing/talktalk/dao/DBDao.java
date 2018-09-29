package jing.talktalk.dao;

import jing.talktalk.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class DBDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    //增加用户
    public int insertOneUser(User user){
        mongoTemplate.insert(user);
        return 1;
    }
    //删除用户
    public int deleteOneUser(String username){
        mongoTemplate.remove(query(where("username").is(username)), "user");
        return 1;
    }

    //修改用户
    public int updatePassword(User user){

        Query query = new Query(where("username").is(user.getUsername()));
        Update update = new Update();
        update.set("password", user.getPassword());

        mongoTemplate.updateFirst(query, update, User.class);

        return 1;
    }

    //根据用户名查找用户
    public ArrayList<User> findUserByName(String username){
        ArrayList<User> result = (ArrayList<User>) mongoTemplate.find(query(where("username").is(username)), User.class);
        return result;
    }

    //根据用户名和密码查找用户
    public ArrayList<User> findUserByNamePwd(String username, String password){
        ArrayList<User> result = (ArrayList<User>)mongoTemplate.find(query(where("username").is(username)
                                                                     .and("password").is(password)),
                                                                     User.class);
        return result;
    }

    //查找所有用户
    public ArrayList<User> findAllUsers(){
        ArrayList<User> result = (ArrayList<User>) mongoTemplate.findAll(User.class, "user");
        return result;
    }

}
