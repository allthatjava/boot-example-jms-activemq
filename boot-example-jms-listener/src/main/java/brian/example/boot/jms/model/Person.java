package brian.example.boot.jms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    private String name;

    @Override
    public String toString(){
        return String.format("Email{name=%s, body=%s}", getName());
    }
}
