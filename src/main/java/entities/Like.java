package entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Like {
    private int userId;
    private int likedUserId;

    public Like(int likedUserId) {
        this.likedUserId = likedUserId;
    }
}
