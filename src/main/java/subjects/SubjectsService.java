package subjects;

import lombok.Getter;
import models.Subject;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SubjectsService {

    @Getter  private List<Subject> subjects;
    private static SubjectsService subjectsService;

    public static synchronized SubjectsService getInstance(){
        if(subjectsService == null){
            subjectsService = new SubjectsService();
        }
        return subjectsService;
    }

    private SubjectsService(){
        subjects = new ArrayList<>();
        Subject math = new Subject("MATH","MATH_TEACHER");
        Subject ai = new Subject("AI","AI_TEACHER");
        Subject tpd = new Subject("TPD","TPD_TEACHER");
        Subject wf = new Subject("WF","WF_TEACHER");

        subjects.add(math);
        subjects.add(ai);
        subjects.add(tpd);
        subjects.add(wf);
    }

    public Subject getSubject(long id){
        for(Subject subject: subjects){
            if(subject.getId() == id){
                return subject;
            }
        }
        return null;
    }

    public boolean addSubject(Subject subject){
        return subjects.add(subject);
    }

    public URI getSubjectPath(Subject subject, UriInfo uriInfo){
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        return ub.path(subject.getId()+"").build();
    }

    public void updateSubject(Subject currentSubject, Subject newSubject){
        int index = subjects.indexOf(currentSubject);
        Long subjectId = currentSubject.getId();
        newSubject.setId(subjectId);
        subjects.set(index,newSubject);
    }

    public void deleteSubject(Subject subject){
        this.subjects.remove(subject);
    }

}
