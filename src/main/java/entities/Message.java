package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Message {
    private int id;
    private int senderId;
    private int receiverId;
    private String messageText;
    private Date time;
    private String status;

    public Message(int id, int senderId, int receiverId, String messageText, Date time) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.time = time;
    }

    public Message(int id, int senderId, int receiverId, String messageText, String status, Date time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.status = status;
        this.time = time;
    }

    public Message(int senderId, int receiverId, String messageText) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
    }

}
