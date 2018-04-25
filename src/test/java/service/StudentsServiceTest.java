package service;

import dao.IDao;
import models.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentsServiceTest {

    IDao mongoDao;
    @Before
    public void setUp() throws Exception {
        mongoDao = TestMongoDao.getInstance();
    }

    @Test
    public void addStudentTest() throws Exception {
            String name = "TestStudentName";
            String surname = "TestStudentSurname";
            String date = "01-01-1994";
            Student student = TestUtils.createNewStudent(name,surname,date);
            int index = student.getIndex();

            Student mongoStudent = mongoDao.getStudent(index);
            assertTrue(mongoStudent.getIndex() == student.getIndex());
            assertTrue(mongoStudent.getName().equals(name));
            assertTrue(mongoStudent.getSurname().equals(surname));
            assertTrue(mongoStudent.getSurname().equals(surname));
    }

    @Test
    public void updateStudentTest() throws Exception {
        String name = "TestStudentName";
        String surname = "TestStudentSurname";
        String date = "01-01-1994";
        Student student = TestUtils.createNewStudent(name,surname,date);
        int index = student.getIndex();

        Student mongoStudent = mongoDao.getStudent(index);
        String changedName = "ChangedStudentName";
        mongoStudent.setName(changedName);
        mongoDao.updateStudent(mongoStudent);

        Student updatedStudent = mongoDao.getStudent(index);
        assertTrue(updatedStudent.getName().equals(changedName));
        assertTrue(updatedStudent.getSurname().equals(surname));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        String name = "TestStudentName";
        String surname = "TestStudentSurname";
        String date = "01-01-1994";
        Student student = TestUtils.createNewStudent(name,surname,date);
        int index = student.getIndex();
        mongoDao.delete(student);

        Student deletedStudent = mongoDao.getStudent(index);
        assertNull(deletedStudent);
    }



}