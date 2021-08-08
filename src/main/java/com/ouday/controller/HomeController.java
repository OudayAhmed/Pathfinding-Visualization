package com.ouday.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;


public class HomeController {

    @FXML
    private FontIcon closeIcon;

    @FXML
    private FontIcon minimizeIcon;

    @FXML
    private JFXComboBox<String> algorithmsCombobox;

    @FXML
    private JFXComboBox<Integer> sizeOfTheCellCombobox;

    @FXML
    private JFXComboBox<String> mazeGenerationAlgorithmsCombobox;

    @FXML
    private JFXComboBox<String> speedCombobox;


    @FXML
    void switchToVisualizer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ouday/view/visualizer.fxml"));
        Parent root = fxmlLoader.load();
        VisualizerController visualizerController = fxmlLoader.getController();
        visualizerController.setVisualizerDetails(algorithmsCombobox.getValue(), sizeOfTheCellCombobox.getValue(), mazeGenerationAlgorithmsCombobox.getValue(), speedCombobox.getValue());
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void closeStage(MouseEvent event) {
        ((Stage) closeIcon.getScene().getWindow()).close();
    }

    @FXML
    void minimizeStage(MouseEvent event) {
        ((Stage) minimizeIcon.getScene().getWindow()).setIconified(true);
    }

}