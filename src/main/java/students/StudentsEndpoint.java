package students;

import common.MarkValues;
import common.Responses;
import models.Mark;
import models.Student;
import models.Subject;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import subjects.SubjectsService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Set;

@Path("students")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class StudentsEndpoint {

    @Context
    UriInfo uriInfo;

    private StudentsService studentsService = StudentsService.getInstance();

    @GET
    public List<Student> getStudents() {
        return studentsService.getStudentList();
    }

    @GET
    @Path("{index}")
    public Response getStudent(@PathParam("index") Long index) {
        Student student = studentsService.getStudent(index);
        return student == null ? Responses.notFound() : Responses.ok(student);
    }

    @GET
    @Path("{index}/marks")
    public Object getStudentMarks(@PathParam("index") Long index){
        Student student = studentsService.getStudent(index);
        if(student == null){
            return Responses.notFound();
        }else{
            return Responses.ok(new GenericEntity<List<Mark>>(student.getMarkList()) {});
        }
    }

    @GET
    @Path("{index}/subjects")
    public Response getStudentSubjects(@PathParam("index") Long index){
        Student student = studentsService.getStudent(index);
        if(student == null)
            return Responses.notFound();
        return Responses.ok(new GenericEntity<Set<Subject>>(studentsService.getStudentSubjects(student)) {});
    }

    @POST
    public Response addStudent(Student student){
        studentsService.addStudent(student);
        return Responses.created(studentsService.getStudentPath(student,uriInfo));
    }

    @POST
    @Path("{index}")
    public Response addMark(@PathParam("index") Long index, Mark mark){
        Student student = studentsService.getStudent(index);
        if(MarkValues.getMarkValue(mark.getValue()).equals("-1")){
            return Responses.notFound();
        }
        if(student == null)
            return Responses.notFound();
        Mark addedMark = studentsService.addStudentMark(student,mark);
        return Responses.created(studentsService.getMarkPath(student,addedMark,uriInfo));
    }

    @PUT
    @Path("{index}")
    public Response updateStudent(@PathParam("index") Long index, Student student){
        Student currentStudent = studentsService.getStudent(index);
        if(currentStudent == null)
            return Responses.notFound();

        studentsService.updateStudent(currentStudent,student);
        return Responses.ok();
    }

    @PUT
    @Path("{index}/marks/{id}")
    public Response updateStudentMark(@PathParam("index") Long index,@PathParam("id") long id, Mark newMark){
        Student currentStudent = studentsService.getStudent(index);
        if(currentStudent == null)
            return Responses.notFound();
        Mark studentMark = studentsService.getStudentMark(currentStudent,id);
        if(studentMark == null)
            return Responses.notFound();
        studentsService.updateStudentMark(currentStudent, studentMark,newMark);
        return Responses.ok();
    }

    @DELETE
    @Path("{index}")
    public Response deleteSubject(@PathParam("index") long index){
        Student student = studentsService.getStudent(index);
        if(student == null)
            return Responses.notFound();
        studentsService.deleteStudent(student);
        return Responses.noContent();
    }

    @DELETE
    @Path("{index}/marks/{id}")
    public Response deleteMark(@PathParam("index") long index, @PathParam("id") long id){
        Student student = studentsService.getStudent(index);
        if(student == null)
            return Responses.notFound();
        Mark mark = studentsService.getStudentMark(student,id);
        if(mark == null)
            return Responses.notFound();
        studentsService.deleteStudentMark(student,mark);
        return Responses.noContent();
    }

}
