import marks.MarksEndpoint;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import students.StudentsEndpoint;
import subjects.SubjectsEndpoint;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Server {

    public static void main(String[] args){
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(StudentsEndpoint.class, SubjectsEndpoint.class, MarksEndpoint.class);
        config.packages("org.glassfish.jersey.examples.linking", "com.fasterxml.jackson.jaxrs").register(DeclarativeLinkingFeature.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }
}
