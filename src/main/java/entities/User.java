package entities;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class User {
    private int user_id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String position;
    private String photo_link;

    public User(int user_id, String name, String surname, String email, String position, String photo_link) {
        this.user_id= user_id;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.position=position;
        this.photo_link=photo_link;
    }
}
