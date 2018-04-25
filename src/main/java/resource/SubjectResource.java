package resource;

import common.Responses;
import models.Subject;
import org.bson.types.ObjectId;
import service.SubjectsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectResource {
    private SubjectsService subjectsService = SubjectsService.getInstance();

    @GET
    public Response getSubject(@PathParam("id") ObjectId id) {
        Subject subject = subjectsService.getSubject(id);
        return subject == null ? Responses.notFound() : Responses.ok(subject);
    }

    @PUT
    public Response updateSubject(@PathParam("id") ObjectId id, Subject subject) {
        subjectsService.updateSubject(id, subject);
        return Responses.ok();
    }

    @DELETE
    public Response deleteSubject(@PathParam("id") ObjectId id){
        Subject subject = subjectsService.getSubject(id);
        if(subject == null)
            return Responses.notFound();
        subjectsService.deleteSubject(subject);
        return Responses.noContent();
    }



}
