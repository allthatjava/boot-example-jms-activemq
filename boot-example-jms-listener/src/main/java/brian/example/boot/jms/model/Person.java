package brian.example.boot.jms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person implements Serializable {
    private String name;

    @Override
    public String toString(){
        return String.format("Email{name=%s, body=%s}", getName());
    }
}
