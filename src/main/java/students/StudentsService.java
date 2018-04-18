package students;
import common.MarkValues;
import lombok.Getter;
import models.Mark;
import models.Student;
import models.Subject;
import subjects.SubjectsService;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.*;

public class StudentsService {
    @Getter  private List<Student> studentList = new ArrayList<>();
    private static SubjectsService subjectsService = SubjectsService.getInstance();
    private static StudentsService studentsService;
    private StudentsService(){
        for(int i =0;i<10;i++) {
            Student student = new Student("Name"+i, "Surname"+i, new Date());
            studentList.add(student);
            for(int j = 0;j<10;j++){
                int size = subjectsService.getSubjects().size();
                Mark mark = new Mark(MarkValues.getMarkValue((j%3+2) +""),subjectsService.getSubject(j%size));
                addStudentMark(student,mark);
            }
        }
    }

    public Student getStudent(Long index){
        for(Student student: studentList){
            if(student.getIndex().longValue() == index.longValue()){
                return student;
            }
        }
        return null;
    }

    public boolean addStudent(Student student){
        return this.studentList.add(student);
    }

    public void updateStudent(Student currentStudent, Student newStudent){
        int index = studentList.indexOf(currentStudent);
        Long studentIndex = currentStudent.getIndex();
        newStudent.setIndex(studentIndex);
        getStudentList().set(index,newStudent);
    }

    public static synchronized StudentsService getInstance(){
        if(studentsService == null){
            studentsService = new StudentsService();
        }
        return studentsService;
    }

    public URI getStudentPath(Student student, UriInfo uriInfo){
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        return ub.path(student.getIndex().toString()).build();
    }

    public Set<Subject> getStudentSubjects(Student student){
        Set<Subject> studentSubjects = new HashSet<Subject>();
        for(Mark mark: student.getMarkList()){
            studentSubjects.add(mark.getSubject());
        }
        return studentSubjects;
    }

    public Mark addStudentMark(Student student, Mark mark){
        student.getMarkList().add(mark);
        return mark;
    }

    public void deleteStudent(Student student){
        this.studentList.remove(student);
    }

    public Mark getStudentMark(Student student, long id){
        for(Mark m: student.getMarkList()){
            if(m.getId() == id){
                return m;
            }
        }
        return null;
    }

    public void deleteStudentMark(Student student, Mark mark){
        student.getMarkList().remove(mark);
    }

    public URI getMarkPath(Student student, Mark mark, UriInfo uriInfo) {
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        return ub.path(student.getIndex().toString()+"/"+mark.getId()).build();
    }

    public void updateStudentMark(Student student, Mark currentMark, Mark newMark) {
        int index = student.getMarkList().indexOf(currentMark);
        Long markId = currentMark.getId();
        newMark.setId(markId);
        student.getMarkList().set(index,newMark);
    }
}

