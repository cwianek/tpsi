package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="subject")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
public class Subject {
    @Setter @Getter private String name;
    @Setter @Getter private String lecturer;
    @JsonIgnore @XmlTransient @Setter @Getter private Long id;

    public Subject(){
        this.id = Utils.getSubjectId();
    }

    public Subject(String name, String lecturer) {
        this();
        this.name = name;
        this.lecturer = lecturer;
    }
}
