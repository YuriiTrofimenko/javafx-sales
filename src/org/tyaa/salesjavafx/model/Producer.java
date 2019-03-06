/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.salesjavafx.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author student
 */
public class Producer {
    
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty country;

    public Producer() {}

    public Producer(String name, String country) {
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
    }

    public Producer(Integer id, String name, String country) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public StringProperty countryProperty() {
        return country;
    }
}
