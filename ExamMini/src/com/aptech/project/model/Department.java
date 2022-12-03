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
public class Department {

    private ObjectProperty<Integer> id;
    private StringProperty name;

    public Department() {
        id = new SimpleObjectProperty<>();
        name = new SimpleStringProperty();
    }

    public Department(ObjectProperty<Integer> id, StringProperty name) {
        this.id = id;
        this.name = name;
    }

   public Integer getId(){
       return this.id.get();
   }
   public void setId(int id){
       this.id.set(id);
   }
   public String getName(){
       return this.name.get();
   }
   public void setName(String name){
        this.name.set(name);
   }
   public ObjectProperty<Integer> getIdProperty(){
       return this.id;
   }
   public StringProperty getNameProperty(){
       return this.name;
   }

    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name=" + name + '}';
    }

    
    
    
    
}
