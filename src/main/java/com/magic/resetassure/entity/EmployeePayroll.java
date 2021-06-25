package com.magic.resetassure.entity;

import java.time.LocalDate;

public class EmployeePayroll {

    private String name;
    private double salary;
    private LocalDate startDate;
    private String gender;

    public EmployeePayroll(String name, double salary, LocalDate startDate, String gender) {
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "EmployeePayroll{" +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", gender='" + gender + '\'' +
                '}';
    }
}
