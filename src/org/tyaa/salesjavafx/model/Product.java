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
public class Product {
    
    private IntegerProperty id;
    private StringProperty title;
    private DoubleProperty price;
    private IntegerProperty quantity;
    private IntegerProperty producerId;

    public Product() {}

    public Product(String title, Double price, Integer quantity, Integer producerId) {
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.producerId = new SimpleIntegerProperty(producerId);
    }

    public Product(Integer id, String title, Double price, Integer quantity, Integer producerId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.producerId = new SimpleIntegerProperty(producerId);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
    
    public IntegerProperty producerIdProperty() {
        return producerId;
    }
}
