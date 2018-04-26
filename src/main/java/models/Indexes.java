package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;

@Entity
@NoArgsConstructor
public class Indexes {
    @Getter
    @Setter
    private int studentIndex;

    @Getter
    @Setter
    private int markId;

}
