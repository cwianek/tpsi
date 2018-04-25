package service;

import common.Utils;
import dao.IDao;
import dao.MongoDao;
import models.Mark;
import models.Student;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class MarksService {
    private IDao dao = MongoDao.getInstance();
    private static MarksService marksService;
    private MarksService() {}

    public List<Mark> getMarks(int index) {
        Student student = dao.getStudent(index);
        return student.getMarkList();
    }

    public Mark getMark(int index, int id) {
        return dao.getStudent(index)
                .getMarkList()
                .stream()
                .filter(mark -> mark.getId() == id)
                .findFirst().orElse(null);
    }

    public void updateMark(int index, Mark newMark) {
        Student student = dao.getStudent(index);
        Mark currentMark = null;
        for(Mark mark :student.getMarkList()){
            if(mark.getId() == newMark.getId()){
                currentMark = mark;
            }
        }
        int markIndex = student.getMarkList().indexOf(currentMark);
        student.getMarkList().set(markIndex,newMark);
        dao.updateStudent(student);
    }

    public Mark addMark(int index, Mark mark) {
        Student student = dao.getStudent(index);
        mark.setId(getNextMarkId(student));
        student.getMarkList().add(mark);
        dao.updateStudent(student);
        return mark;
    }

    private int getNextMarkId(Student student){
        int max = 0;
        for(Mark mark: student.getMarkList()){
            if(mark.getId() > max)
                max = mark.getId();
        }
        return max+1;
    }

    public void deleteMark(int index, int id) {
        Student student = dao.getStudent(index);
        Mark markToDelete = null;
        for(Mark mark: student.getMarkList()){
            if(mark.getId() == id){
                markToDelete = mark;
            }
        }
        student.getMarkList().remove(markToDelete);
        dao.updateStudent(student);
    }

    public URI getMarkPath(Mark mark, UriInfo uriInfo) {
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        return ub.path("/"+mark.getId()).build();
    }

    public static synchronized MarksService getInstance() {
        if (marksService == null) {
            marksService = new MarksService();
        }
        return marksService;
    }
}

