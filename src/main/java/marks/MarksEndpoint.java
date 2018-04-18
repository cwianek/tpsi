package marks;

import common.Responses;
import models.Mark;
import models.Student;
import students.StudentsService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("marks")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MarksEndpoint {

    @Context
    UriInfo uriInfo;

    private MarksService marksService = MarksService.getInstance();

    @GET
    public Response getMarks(){
        return Responses.ok(marksService.getAllMarks());
    }

    @PUT
    @Path("{id}")
    public Response updateMark(@PathParam("id") long id, Mark newMark){
        Mark currentMark = marksService.getMark(id);
        if(currentMark == null)
            return Responses.notFound();
        marksService.updateMark(currentMark,newMark);
        return Responses.ok();
    }

    @DELETE
    @Path("{id}")
    public Response deleteMark(@PathParam("id") long id) {
        Mark mark = marksService.getMark(id);
        if(mark == null)
            return Responses.notFound();
        marksService.deleteMark(mark);
        return Responses.noContent();
    }
}
