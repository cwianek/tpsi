package models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import common.Utils;
import lombok.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import students.StudentsEndpoint;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name="student")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
public class Student {

    @InjectLinks({
            @InjectLink(resource = StudentsEndpoint.class, rel = "parent", method = "getStudents",style = InjectLink.Style.ABSOLUTE),
            @InjectLink(resource = StudentsEndpoint.class, rel = "self", method = "getStudent", style = InjectLink.Style.ABSOLUTE)
            //@InjectLink(resource = rest.StudentResource.class, rel = "create", method = "createStudent", style = InjectLink.Style.ABSOLUTE),
            //@InjectLink(resource = rest.StudentResource.class, rel = "update", method = "updateStudent", style = InjectLink.Style.ABSOLUTE),
            //@InjectLink(resource = rest.StudentResource.class, rel = "delete", method = "deleteStudent", style = InjectLink.Style.ABSOLUTE)
    })
    @XmlElement(name = "links")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    @Setter @Getter List<Link> links;

    @JsonIgnore @XmlTransient @Setter @Getter private Long index;
    @Setter @Getter private String name;
    @Setter @Getter private String surname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Setter @Getter private Date birthdate;
    @JsonIgnore @XmlTransient @Setter @Getter private List<Mark> markList;

    public Student(){
        this.index = Utils.getStudentIndex();
        this.markList = new ArrayList<>();
    }

    public Student(String name, String surname, Date birthdate) {
        this();
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }
}
