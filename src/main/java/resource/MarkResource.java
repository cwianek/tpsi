package resource;

import common.Responses;
import models.Mark;
import service.MarksService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MarkResource {
    private MarksService marksService = MarksService.getInstance();
    private int index;

    MarkResource(int index){
        this.index = index;
    }

    @GET
    public Mark getMark(@PathParam("id") int id){
        Mark mark = marksService.getMark(index,id);
        if(mark == null) throw new NotFoundException();
        return mark;
    }

    @PUT
    public Response updateMark(@PathParam("id") int id, Mark newMark) {
        newMark.setId(id);
        marksService.updateMark(index, newMark);
        return Responses.ok();
    }

    @DELETE
    public Response deleteMark(@PathParam("id") int id){
        marksService.deleteMark(index,id);
        return Responses.noContent();
    }
}
