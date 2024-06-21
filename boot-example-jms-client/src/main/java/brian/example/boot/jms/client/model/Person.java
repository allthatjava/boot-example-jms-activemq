package brian.example.boot.jms.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    private String name;
    private Date createdDateTime;
    private Date receivedDateTime;
}
