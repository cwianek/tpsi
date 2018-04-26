package dao;

import models.Mark;
import models.Student;
import models.Subject;
import org.bson.types.ObjectId;

import java.util.List;

public interface IDao {
        List<Student> getStudents();
        Student getStudent(int index);
        Student saveStudent(Student student);
        void updateStudent(Student student);

        List<Subject> getSubjects();
        Subject getSubject(ObjectId id);
        Subject saveSubject(Subject subject);
        void updateSubject(Subject subject);

        void delete(Object object);
        int getNextStudentIndex();
        int getNextMarkId();
}
