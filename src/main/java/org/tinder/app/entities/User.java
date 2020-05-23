package org.tinder.app.entities;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User implements Identifiable {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String imgUrl;
    private String position;
    private String gender;


    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String name, String surname, String imgUrl){
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.imgUrl = imgUrl;
    }

    public User(String login, String password, String name, String surname) {
        this(login, password);
        this.name = name;
        this.surname = surname;
    }

    public User(String login, String password, String name, String surname, String imgUrl) {
        this(login, password, name, surname);
        this.imgUrl = imgUrl;
    }

    public User(String login, String password, String name, String surname, String imgUrl, String position) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.imgUrl = imgUrl;
        this.position = position;
    }

    public User(int id, String login, String name, String surname, String imgUrl, String position) {
        this.id=id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.imgUrl = imgUrl;
        this.position = position;
    }

    public User(int id, String name, String surname, String login, String imgUrl, String position, String gender) {
        this.id=id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.imgUrl = imgUrl;
        this.position = position;
        this.gender = gender;
    }

    public User(String login, String password, String name, String surname, String image, String position, String gender) {
        this.login = login;
        this.password=password;
        this.name = name;
        this.surname = surname;
        this.imgUrl = image;
        this.position = position;
        this.gender = gender;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
