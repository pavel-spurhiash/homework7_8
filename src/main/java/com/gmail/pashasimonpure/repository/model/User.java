package com.gmail.pashasimonpure.repository.model;

public class User {

    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private Boolean isActive;

    private UserInformation information;

    public UserInformation getInformation() {
        return information;
    }

    public void setInformation(UserInformation information) {
        this.information = information;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User { " + id + ", " + name + ", " + password + ", " + age + ", " + isActive + ", " + information + " }";
    }

}
