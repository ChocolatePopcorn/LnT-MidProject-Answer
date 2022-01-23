package com.LnT.MidProject;

public class Employee {
    private String id, name, gender, position;
    private int salary;
    
    public Employee(String id, String name, String gender, String position, int salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.salary = salary;
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


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getPosition() {
        return position;
    }


    public void setPosition(String position) {
        this.position = position;
    }


    public int getSalary() {
        return salary;
    }


    public void setSalary(int salary) {
        this.salary = salary;
    }
}
