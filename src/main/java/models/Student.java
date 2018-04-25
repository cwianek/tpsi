package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import resource.MarksResource;
import resource.StudentsResource;
import resource.SubjectResource;
import resource.SubjectsResource;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Entity
public class Student {

    @InjectLinks({
            @InjectLink(resource = StudentsResource.class, rel = "self",  style = InjectLink.Style.ABSOLUTE),
            @InjectLink(resource = SubjectsResource.class, rel = "subjects", style = InjectLink.Style.ABSOLUTE),
            @InjectLink(resource = MarksResource.class, rel = "marks", style = InjectLink.Style.ABSOLUTE),
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    @Setter
    @Getter
    List<Link> links;

    @Id
    @XmlTransient
    @Setter
    @Getter
    private ObjectId id;

    @Indexed(options = @IndexOptions(unique = true))
    @Setter
    @Getter
    private int index;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Setter
    @Getter
    private Date birthdate;

    @Embedded
    @XmlTransient
    @Setter
    @Getter
    private List<Mark> markList;

    public Student() {
        this.markList = new ArrayList<>();
    }

    public Student(String name, String surname, Date birthdate) {
        this();
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }
}
