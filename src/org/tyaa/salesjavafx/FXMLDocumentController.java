/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.salesjavafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.CustomTextField;
import org.tyaa.salesjavafx.model.Product;

/**
 *
 * @author student
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private CustomTextField titleCustomTextField;
    @FXML
    private CustomTextField priceCustomTextField;
    @FXML
    private CustomTextField quantityCustomTextField;
    
    @FXML
    private TableView productTableView;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    @FXML
    private TableColumn quantityTableColumn;
    
    private ObservableList<Product> products;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Создание пустого списка
        products = FXCollections.observableArrayList();
        //Привязка свойства id из объектов типа Product
        //к колонке таблицы
        titleTableColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("title")
        );
        priceTableColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("price")
        );
        quantityTableColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("quantity")
        );
        
        productTableView.setItems(products);
        
        //products.add(new Product("t1", 100500.99, 1));
    }

    @FXML
    private void onAddProductButtonClick(){
        products.add(
                new Product(
                        titleCustomTextField.getText()
                        , Double.valueOf(priceCustomTextField.getText())
                        , Integer.valueOf(quantityCustomTextField.getText()))
        );
    }
}
