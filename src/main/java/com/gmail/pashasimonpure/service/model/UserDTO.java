package com.gmail.pashasimonpure.service.model;

public class UserDTO {

    private Integer id;
    private String name;
    private String password;
    private String address;
    private String telephone;
    private Integer age;
    private Boolean isActive;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        return "UserDTO { " + id + ", " +
                name + ", " +
                password + ", " +
                isActive + ", " +
                age + ", " +
                address + ", " +
                telephone + " }";
    }

}