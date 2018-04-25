package models;

import dao.ObjectIdJaxbAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import resource.SubjectResource;
import resource.SubjectsResource;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Entity
@XmlRootElement(name="subject")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class Subject {
    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    @Setter @Getter private ObjectId id;
    @Setter @Getter private String name;
    @Setter @Getter private String lecturer;

    @InjectLinks({
            @InjectLink(resource = SubjectsResource.class, rel = "parent", style = InjectLink.Style.ABSOLUTE),
            @InjectLink(resource = SubjectResource.class, rel = "self", style = InjectLink.Style.ABSOLUTE)
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    @Getter @Setter private List<Link> links;

    public Subject(ObjectId id, String lecturer, String name) {
        this.id = id;
        this.lecturer = lecturer;
        this.name = name;
    }
}
