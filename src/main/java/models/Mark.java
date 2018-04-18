package models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import common.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlRootElement(name="mark")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class Mark {
    @Setter @Getter private String value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Setter @Getter private Date date;
    @XmlTransient @JsonIgnore @Setter @Getter private Subject subject;
    @JsonIgnore @XmlTransient @Setter @Getter private Long id;

    public Mark(String value, Subject subject) {
        this.value = value;
        this.subject = subject;
        this.date = new Date();
        this.id = Utils.getMarkId();
    }
}
