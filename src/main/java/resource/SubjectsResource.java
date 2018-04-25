package resource;
import common.Responses;
import models.Mark;
import models.Subject;
import org.bson.types.ObjectId;
import service.SubjectsService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("subjects")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectsResource {
    private SubjectsService subjectsService = SubjectsService.getInstance();

    @GET
    public List<Subject> getSubjects() {
        return subjectsService.getSubjects();
    }

    @POST
    public Response addSubject(Subject subject, @Context UriInfo uriInfo) {
        Subject addedSubject = subjectsService.addSubject(subject);
        return Responses.created(subjectsService.getSubjectPath(addedSubject,uriInfo));
    }

    @Path("/{id}")
    public SubjectResource getCourseResource(@PathParam("id") ObjectId id){
        return new SubjectResource();
    }

}
