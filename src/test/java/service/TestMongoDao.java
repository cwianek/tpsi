package service;

import dao.IDao;
import dao.MongoDao;

public class TestMongoDao extends MongoDao {
    @Override
    public String getMongoName() {
        return "TPSI_TEST";
    }

    public synchronized static IDao getInstance(){
        if(mongoDao == null){
            mongoDao = new TestMongoDao();
        }
        return mongoDao;
    }
}
