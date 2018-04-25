package resource;
import common.Responses;
import models.Mark;
import service.MarksService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MarksResource {
    private MarksService marksService = MarksService.getInstance();
    private int index;

    MarksResource(int index) {
        this.index = index;
    }

    @GET
    public List<Mark> getStudentMarks() {
        return marksService.getMarks(index);
    }

    @POST
    public Response addMark(Mark mark, @Context UriInfo uriInfo) {
        marksService.addMark(index, mark);
        return Responses.created(marksService.getMarkPath(mark, uriInfo));
    }

    @Path("/{id}")
    public MarkResource getMarkResource(@PathParam("id") int id) {
        return new MarkResource(index);
    }
}
