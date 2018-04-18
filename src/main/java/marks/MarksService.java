package marks;
import models.Mark;
import models.Student;
import models.Subject;
import students.StudentsService;
import subjects.SubjectsService;

import java.util.*;

public class MarksService {
    private static MarksService marksService;
    private static SubjectsService subjectsService = SubjectsService.getInstance();
    private static StudentsService studentsService = StudentsService.getInstance();

    public List<Mark> getAllMarks(){
        List<Mark> marks = new ArrayList<>();
        for(Student student: studentsService.getStudentList()){
            marks.addAll(student.getMarkList());
        }
        return marks;
    }

    public void updateMark(Mark currentMark, Mark newMark) {
        String value = newMark.getValue();
        Long subjectId = newMark.getSubject().getId();
        if(!value.equals(""))
            currentMark.setValue(value);
        if(subjectId != null) {
            Subject newSubject = subjectsService.getSubject(subjectId);
            currentMark.setSubject(newSubject);
        }
    }

    public static synchronized MarksService getInstance(){
        if(marksService == null){
            marksService = new MarksService();
        }
        return marksService;
    }

    public Mark getMark(Long id){
        Mark m = null;
        for(Mark mark: marksService.getAllMarks()){
            if(mark.getId().longValue() == id.longValue())
                m = mark;
        }
        return m;
    }

    public void deleteMark(Mark mark) {
        getAllMarks().remove(mark);
    }
}