package resource;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import common.Responses;
import models.Mark;
import models.Student;
import models.Subject;
import service.StudentsService;

import java.util.List;
import java.util.Set;

@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StudentResource {
    private StudentsService studentsService = StudentsService.getInstance();

    @Context
    UriInfo uriInfo;

    @GET
    public Student getStudent(@PathParam("index") int index) {
        return studentsService.getStudent(index);
    }

    @PUT
    public Response updateStudent(@PathParam("index") int index, Student student) {
        studentsService.updateStudent(index, student);
        return Responses.ok();
    }

    @DELETE
    public Response deleteSubject(@PathParam("index") int index) {
        Student student = studentsService.getStudent(index);
        if (student == null)
            return Responses.notFound();
        studentsService.deleteStudent(student);
        return Responses.noContent();
    }

    @Path("/marks")
    public MarksResource getStudentMarks(@PathParam("index") int index) {
        return new MarksResource(index);
    }

    @Path("/subjects")
    public Response getStudentSubjects(@PathParam("index") int index) {
        Student student = studentsService.getStudent(index);
        if (student == null)
            return Responses.notFound();
        return Responses.ok(new GenericEntity<Set<Subject>>(studentsService.getStudentSubjects(student)) {
        });
    }


}
