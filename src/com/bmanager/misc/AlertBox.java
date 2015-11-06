package com.bmanager.misc;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

//A Wrapper class to the javafx alert dialog
public class AlertBox {

    /***
     * Displays an alert box
     * @param title The text to set the Alert box to
     * @param headerText The header text to set the Alert box to
     * @param contentText The content text to set the Alert box to
     */
    public static void show(String title, String headerText, String contentText) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UTILITY);

        alert.showAndWait();
    }
}