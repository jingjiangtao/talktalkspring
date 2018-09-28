package jing.talktalk.dao;

import com.mongodb.client.model.geojson.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBDao {


    @Autowired
    private MongoTemplate mongoTemplate;

}
