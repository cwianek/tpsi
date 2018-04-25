package resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import common.Responses;
import models.Student;
import service.StudentsService;

@Path("students")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class StudentsResource {

    @Context
    UriInfo uriInfo;

    private StudentsService studentsService = StudentsService.getInstance();

    @GET
    public List<Student> getStudents() {
        return studentsService.getStudentList();
    }
    
    @POST
    public Response addStudent(Student student){
        URI uri = studentsService.addStudent(student, uriInfo);
        return Responses.created(uri);
    }

    @Path("/{index}")
    public StudentResource getStudent(@PathParam("index") int index) {
        Student student = studentsService.getStudent(index);
        if(student == null)
            throw new NotFoundException();
        return new StudentResource();
    }

}
