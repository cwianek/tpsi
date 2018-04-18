package subjects;

import common.Responses;
import models.Subject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("subjects")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class SubjectsEndpoint {

    @Context
    UriInfo uriInfo;

    private SubjectsService subjectsService = SubjectsService.getInstance();

    @GET
    public List<Subject> getSubjects() {
        return subjectsService.getSubjects();
    }

    @GET
    @Path("{id}")
    public Response getSubject(@PathParam("id") Long id) {
        Subject subject = subjectsService.getSubject(id);
        return subject == null ? Responses.notFound() : Responses.ok(subject);
    }

    @POST
    public Response addSubject(Subject subject) {
        subjectsService.addSubject(subject);
        return Responses.created(subjectsService.getSubjectPath(subject, uriInfo));
    }

    @PUT
    @Path("{id}")
    public Response updateSubject(@PathParam("id") long id, Subject subject) {
        Subject currentSubject = subjectsService.getSubject(id);
        if (currentSubject == null)
            return Responses.notFound();

        subjectsService.updateSubject(currentSubject, subject);
        return Responses.ok();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSubject(@PathParam("id") long id){
        Subject subject = subjectsService.getSubject(id);
        if(subject == null)
            return Responses.notFound();
        subjectsService.deleteSubject(subject);
        return Responses.noContent();
    }

}
