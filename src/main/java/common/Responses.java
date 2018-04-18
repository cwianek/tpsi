package common;
import models.Student;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

public class Responses {
    public static Response notFound(){
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public static Response ok(Object entity){
        return Response.ok( entity).build();
    }

    public static Response ok(){
        return Response.ok().build();
    }

    public static Response noContent(){
        return Response.noContent().build();
    }

    public static Response created(URI uri){
        return Response.created(uri).build();
    }
}
