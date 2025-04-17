package com.example.texteditorapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class TextEditorApp extends Application {
    TextArea textArea = new TextArea();
    FileChooser fileChooser = new FileChooser();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Editor");

        // MenuBar
        MenuBar menuBar = new MenuBar();

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");

        // Edit Menu
        Menu editMenu = new Menu("Edit");
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");

        // File Menu Actions
        newFile.setOnAction(e -> textArea.clear());
        openFile.setOnAction(e -> openFile(primaryStage));
        saveFile.setOnAction(e -> saveFile(primaryStage));
        exit.setOnAction(e -> primaryStage.close());

        // Edit Menu Actions
        cut.setOnAction(e -> textArea.cut());
        copy.setOnAction(e -> textArea.copy());
        paste.setOnAction(e -> textArea.paste());

        fileMenu.getItems().addAll(newFile, openFile, saveFile, new SeparatorMenuItem(), exit);
        editMenu.getItems().addAll(cut, copy, paste);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // Layout
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(textArea);

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(Stage stage) {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException ex) {
                showAlert("Error", "Failed to open file.");
            }
        }
    }

    private void saveFile(Stage stage) {
        // Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            // Ensure the file has a .txt extension
            if (!file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException ex) {
                showAlert("Error", "Failed to save file.");
            }
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

