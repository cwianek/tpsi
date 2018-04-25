package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import common.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.glassfish.jersey.linking.Binding;
import resource.MarkResource;
import resource.MarksResource;
import resource.StudentsResource;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import resource.SubjectResource;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@Embedded
@XmlRootElement(name = "mark")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class Mark {

    @InjectLinks({
            @InjectLink(resource = SubjectResource.class, rel = "subject", bindings = {@Binding(name="id", value="${instance.subject.id}")}),
            @InjectLink(resource = MarksResource.class, rel = "parent"),
            @InjectLink(resource = MarkResource.class, rel = "self")
    })
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    @Getter
    @Setter
    List<Link> links;

    @Setter
    @Getter
    private String value;

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @Reference
    @Setter
    @Getter
    private Subject subject;

    @Setter
    @Getter
    private int id;

    public Mark(String value, Subject subject) {
        this.value = value;
        this.subject = subject;
        this.date = new Date();
    }
}
