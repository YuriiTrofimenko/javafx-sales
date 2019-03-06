/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.salesjavafx;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.tyaa.salesjavafx.model.Producer;
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
    private ComboBox producerComboBox;

    @FXML
    private TableView productTableView;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    @FXML
    private TableColumn quantityTableColumn;
    @FXML
    private TableColumn producerTableColumn;

    private ObservableList<Product> products;
    private ObservableList<Producer> producers;
    private ValidationSupport validationSupport;
    private List<ValidationMessage> validationMessageList;
    private Boolean isValidationDecorationOn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /* TableView */
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
        producerTableColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("producerId")
        );
        
        producerTableColumn.setCellFactory(((column) -> {
            return  new TableCell<Product, Integer>(){
                @Override
                protected void updateItem(Integer producerId, boolean empty)
                {
                    super.updateItem(producerId, empty);
                    if (producerId == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        
                        Optional<Producer> producerOptional =
                                producers.stream()
                                    .filter((p) -> {
                                            return p.idProperty().getValue().equals(producerId);
                                        })
                                    .findFirst();
                        
                        if (producerOptional.isPresent()) {
                            setText(producerOptional.get().nameProperty().getValue());
                        } else {
                            setText("Unknown producer");
                            setTextFill(Color.RED);
                        }
                    }
                }
                
            };
        }));

        productTableView.setItems(products);
        
        /* Form inputs */
        
        producerComboBox.setConverter(new StringConverter<Producer>() {
            @Override
            public String toString(Producer producer) {
                return producer.nameProperty().getValue()
                        + " ("
                        + producer.countryProperty().getValue()
                        + ")";
            }
            @Override
            public Producer fromString(String string) {
                return null;
            }
        });
        
        producers = FXCollections.observableArrayList();
        producerComboBox.setItems(producers);
        producers.add(new Producer(1, "Producer 1", "Ukraine"));
        producers.add(new Producer(2, "Producer 2", "USA"));
        
        //producerComboBox

        /* Validation */
        
        isValidationDecorationOn = false;
        
        ValueExtractor.addObservableValueExtractor(
                c -> c instanceof CustomTextField,
                c -> ((CustomTextField) c).textProperty());
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(true);
        validationSupport.registerValidator(
                titleCustomTextField,
                Validator.createEmptyValidator("Название товара обязательно"));
        validationSupport.registerValidator(
                priceCustomTextField,
                Validator.createRegexValidator("Введите число от 0 до 99999 в формате #####.00", "[0-9]{1,5}[\\.][0-9]{2}", Severity.ERROR));

        ChangeListener lostFoucusListener
                = (ChangeListener<Boolean>) (ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
                    if (newPropertyValue) {
                        //System.out.println("Textfield on focus");
                    } else {
                        //System.out.println("Textfield out focus");
                        validate();
                    }
                };

        titleCustomTextField.focusedProperty().addListener(lostFoucusListener);
        priceCustomTextField.focusedProperty().addListener(lostFoucusListener);

        //products.add(new Product("t1", 100500.99, 1));
    }

    @FXML
    private void onAddProductButtonClick() {

        if (validationMessageList != null) {
            if (validationMessageList.isEmpty()) {
                Producer producer =
                    (Producer)producerComboBox.getSelectionModel().getSelectedItem();
                Integer producerId =
                        (producer != null)
                        ? producer.idProperty().getValue()
                        : 0;
                products.add(
                        new Product(
                                titleCustomTextField.getText(),
                                Double.valueOf(priceCustomTextField.getText()),
                                Integer.valueOf(quantityCustomTextField.getText()),
                                producerId
                        )
                );
            } else {
                String errorsString = "";
                for (ValidationMessage message : validationMessageList) {
                    errorsString += "поле \""
                            + ((CustomTextField) message.getTarget()).getPromptText()
                            + "\": "
                            + message.getText()
                            + ". ";
                }

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Товар не добавлен");
                alert.setContentText(errorsString);
                alert.showAndWait();
            }
        } else {
            System.out.println("Validation support is not inialized");
        }
        
        producerComboBox.getSelectionModel().clearSelection();
    }

    private void validate() {

        validationMessageList
                = (List<ValidationMessage>) validationSupport
                        .getValidationResult()
                        .getMessages();

        if (!isValidationDecorationOn) {
            validationSupport.initInitialDecoration();
            isValidationDecorationOn = true;
        }
    }
}
