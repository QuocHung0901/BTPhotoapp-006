package com.example.btphotoapp_006;

public class Student {

    private String id;
    private String name;
    private String email;
    private String telephone;

    public Student() {
    }

    public Student(
            String id,
            String name,
            String email,
            String telephone
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}