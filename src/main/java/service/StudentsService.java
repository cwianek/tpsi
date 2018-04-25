package service;
import dao.IDao;
import dao.MongoDao;
import models.Mark;
import models.Student;
import models.Subject;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;

public class StudentsService {
    private IDao dao = MongoDao.getInstance();
    private static StudentsService studentsService;
    private StudentsService(){ }

    public Student getStudent(int index){
        return dao.getStudent(index);
    }

    public List<Student> getStudentList(){
        return dao.getStudents();
    }

    public URI addStudent(Student student, UriInfo uriInfo){
        dao.saveStudent(student);
        return getStudentPath(student, uriInfo);
    }

    public void updateStudent(int index, Student newStudent){
        newStudent.setIndex(index);
        dao.updateStudent(newStudent);
    }

    public URI getStudentPath(Student student, UriInfo uriInfo){
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        return ub.path(student.getIndex() + "").build();
    }

    public Set<Subject> getStudentSubjects(Student student){
        Set<Subject> studentSubjects = new HashSet<Subject>();
        for(Mark mark: student.getMarkList()){
            studentSubjects.add(mark.getSubject());
        }
        return studentSubjects;
    }

    public void deleteStudent(Student student){
        dao.delete(student);
    }

    public static synchronized StudentsService getInstance(){
        if(studentsService == null){
            studentsService = new StudentsService();
        }
        return studentsService;
    }
}

