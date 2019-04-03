package com.gradesscompany.smarthouse.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @SuppressWarnings("FieldCanBeLocal")
    private final int PREF_WIDTH = 550;

    @SuppressWarnings("FieldCanBeLocal")
    private final int PREF_HEIGHT = 300;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale locale = new Locale("ru");
        ResourceBundle bundle = ResourceBundle.getBundle("com.gradesscompany.smarthouse.main.resources.Locale", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/fxml/server_gui.fxml"), bundle);
        Parent root = loader.load();
        Scene scene = new Scene(root, PREF_WIDTH, PREF_HEIGHT);
        primaryStage.setTitle("Smart house server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
