package brian.example.boot.jms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person implements Serializable {
    private String name;
    private Date createdDateTime;
    private Date receivedDateTime;

}
