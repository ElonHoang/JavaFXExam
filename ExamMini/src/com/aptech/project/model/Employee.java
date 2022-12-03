/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.project.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Administrator
 */
public class Employee {

    private ObjectProperty<Integer> id;
    private StringProperty name;
    private StringProperty address;
    private ObjectProperty<Integer> salary;
    private ObjectProperty<Department> department;

    public Employee() {
        id = new SimpleObjectProperty<>();
        name = new SimpleStringProperty();
        address = new SimpleStringProperty();
        salary = new SimpleObjectProperty<>();
        department = new SimpleObjectProperty<>();
    }

    public Employee(ObjectProperty<Integer> id, StringProperty name,
            StringProperty address, ObjectProperty<Integer> salary,
            ObjectProperty<Department> department) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.department = department;
    }

    public Integer getId() {
        return this.id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return this.address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public Integer getSalary() {
        return this.salary.get();
    }

    public void setSalary(Integer salary) {
        this.salary.set(salary);
    }

    public Department getDepartment() {
        return this.department.get();
    }

    public void setDepartment(Department department) {
        this.department.set(department);
    }

    public ObjectProperty<Integer> getIdProperty() {
        return id;
    }

    public StringProperty getAddressProperty() {
        return address;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public ObjectProperty<Integer> getSalaryProperty() {
        return salary;
    }

    public ObjectProperty<Department> getDepartmentProperty() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", address=" + address + ", salary=" + salary + ", department=" + department + '}';
    }
    

}
