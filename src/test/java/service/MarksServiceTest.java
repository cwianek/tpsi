package service;

import dao.MongoDao;
import models.Mark;
import models.Student;
import models.Subject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import resource.MarksResource;

import static org.junit.Assert.*;

public class MarksServiceTest {
    MongoDao mongoDao;
    MarksService marksService;

    @Before
    public void setUp() throws Exception {
        mongoDao = MongoDao.getInstance();
        marksService = MarksService.getInstance();
    }

    @Test
    public void addMark() throws Exception {
        Student student = TestUtils.createNewStudent("Name", "Surname", "03-01-1994");
        int index = student.getIndex();
        String markValue = "3.5";
        Mark mark = addMark(index,markValue);
        assertTrue(mark.getValue().equals(markValue));
    }

    @Test
    public void updateMark() throws Exception {
        Student student = TestUtils.createNewStudent("Name", "Surname", "03-01-1994");
        int index = student.getIndex();
        String markValue = "3.5";
        Mark mark = addMark(index,markValue);

        mark.setDate(null);
        mark.setValue("2.5");
        marksService.updateMark(index,mark);

        Mark updatedMark = marksService.getMark(index,mark.getId());
        assertTrue(updatedMark.getDate() == null);
        assertTrue(updatedMark.getValue().equals("2.5"));
    }

    private Mark addMark(int index, String markValue){
        Subject subject = new Subject(new ObjectId(),"LecturerTest","SubjectTest" );
        Subject mongoSubject = SubjectsService.getInstance().addSubject(subject);
        Mark mark = new Mark(markValue,mongoSubject);

        Mark addedMark = marksService.addMark(index,mark);
        int id = addedMark.getId();

        return marksService.getMark(index,id);
    }

}