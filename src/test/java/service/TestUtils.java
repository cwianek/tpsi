package service;

import dao.IDao;
import dao.MongoDao;
import models.Student;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestUtils {
    public static Student createNewStudent(String name, String surname, String date) throws ParseException {
        IDao mongoDao = TestMongoDao.getInstance();
        DateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        return mongoDao.saveStudent(new Student(name,surname,format.parse(date)));
    }
}
