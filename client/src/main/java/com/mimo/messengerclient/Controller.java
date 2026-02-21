package com.mimo.messengerclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class Controller {

    private int roomId;

    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label usernameRulesLabel;

    @FXML
    private VBox sidebar;
    @FXML
    private Button roomButton1;
    @FXML
    private Button roomButton2;
    @FXML
    private Button roomButton3;
    @FXML
    private Button roomButton4;

    @FXML
    private ScrollPane chatPane;
    @FXML
    private VBox chatBox;
    @FXML
    private Pane messagePane;
    @FXML
    private VBox messageBox;
    @FXML
    private Label messageName;
    @FXML
    private Label messageContent;

    @FXML
    private TextArea messageFieldArea;

    @FXML
    private Label warningLabel;

    @FXML
    private void enteredName() {
        if (usernameField.getText().length() > 3 && usernameField.getText().length() <= 20 && usernameField.getText().charAt(0) != ' ' && usernameField.getText().charAt(usernameField.getText().length() - 1) != ' ' ) {
            usernameLabel.setVisible(false);
            usernameField.setVisible(false);
            usernameRulesLabel.setVisible(false);
            sidebar.setVisible(true);
        }
    }

    @FXML
    private void changeRoomTo1() {
        System.out.println("Changed Room to 1");
        roomId = 1;
        getChatHistory();
        chatPane.setVisible(true);
        messageFieldArea.setVisible(true);
    }
    @FXML
    private void changeRoomTo2() {
        System.out.println("Changed Room to 2");
        roomId = 2;
        getChatHistory();
        chatPane.setVisible(true);
        messageFieldArea.setVisible(true);
    }
    @FXML
    private void changeRoomTo3() {
        System.out.println("Changed Room to 3");
        roomId = 3;
        getChatHistory();
        chatPane.setVisible(true);
        messageFieldArea.setVisible(true);
    }
    @FXML
    private void changeRoomTo4() {
        System.out.println("Changed Room to 4");
        roomId = 4;
        getChatHistory();
        chatPane.setVisible(true);
        messageFieldArea.setVisible(true);
    }

    @FXML
    private void sendMessage() {
        String message = messageFieldArea.getText();

        message = message.substring(0 ,message.length() - 1);
        if (message.contains("\n")) {
            message = message.replaceAll("\n", "<|nl|>");
        }

        String username = usernameField.getText();
        String msgJson = "{\"sender\":\"" + username + "\",\"content\":\"" + message + "\",\"roomId\":" + roomId + "}";
        System.out.println(msgJson);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/messages/send"))
                                .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(msgJson))
                                                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            System.out.println("InputOutput\n" + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Interrupted\n" + e.getMessage());
        }

        getChatHistory();
        messageFieldArea.clear();
    }

    public void createMessage(String username, String content) {
        Pane messagePane = new Pane();
        messagePane.setId("messagePane");
        VBox messageBox = new VBox();
        messageBox.setId("messageBox");
        Label messageName = new Label();
        messageName.setId("messageName");
        Label messageContent = new Label();
        messageContent.setId("messageContent"); //200 character limit

        messageBox.setPrefSize(930, 140);
        messageBox.setLayoutX(7);
        messageBox.setLayoutY(10);
        messageBox.setSpacing(7);
        messagePane.prefWidthProperty().bind(messageBox.widthProperty());
        messagePane.prefHeightProperty().bind(messageBox.heightProperty());
        messageName.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        VBox.setMargin(messageName, new Insets(0, 0, 0, 20));
        messageContent.setWrapText(true);
        messageContent.setPrefWidth(930);
        messageContent.setMaxWidth(930);
        messageContent.setFont(Font.font("Verdana", 23));
        VBox.setMargin(messageContent, new Insets(0, 0, 0, 15));
        messageName.setText(username);
        messageContent.setText(content);
        while (content.contains("<|nl|>")) {
            content = content.replace("<|nl|>", "\n");
        }


        messageBox.getChildren().addAll(messageName, messageContent);
        messagePane.getChildren().add(messageBox);
        chatBox.getChildren().add(messagePane);
        messagePane.setVisible(true);
        messageBox.setVisible(true);
        messageName.setVisible(true);
        messageContent.setVisible(true);
    }

    public void getChatHistory() {
        chatBox.getChildren().clear();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/messages/receive?roomId=" + roomId))
                                .GET()
                                        .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            ObjectMapper objectMapper = new ObjectMapper();
            List<ChatMessageDTO> messages = objectMapper.readValue(
                    response.body(),
                    new TypeReference<List<ChatMessageDTO>>() {}
            );

            Collections.reverse(messages);
            for(ChatMessageDTO chatMessageDTO : messages) {
                createMessage(chatMessageDTO.getUser(), chatMessageDTO.getContent());
            }


        } catch (IOException e) {
            System.out.println("InputOutput\n" + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Interrupted\n" + e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        usernameLabel.setVisible(true);
        usernameField.setVisible(true); //20 character limit;
        usernameRulesLabel.setVisible(true);
        sidebar.setVisible(false);
        chatPane.setVisible(false);
        messageFieldArea.setVisible(false);
        warningLabel.setVisible(false);

        usernameLabel.setLayoutX(430);
        usernameLabel.setLayoutY(345);
        usernameLabel.setPrefSize(340, 100);
        usernameField.setPrefSize(340, 100);
        usernameField.setLayoutX(430);
        usernameField.setLayoutY(455);
        usernameRulesLabel.setLayoutX(430);
        usernameRulesLabel.setLayoutY(565);
        usernameRulesLabel.setText("Username must...\n...be between 3 and 20 characters long\n...not start or end with a space");


        roomButton1.setMinSize(200, 100); //v = width, v1 = height
        roomButton2.setMinSize(200, 100);
        roomButton3.setMinSize(200, 100);
        roomButton4.setMinSize(200, 100);

        sidebar.setSpacing(10);
        sidebar.setMinSize(200, 800);
        sidebar.setLayoutX(10);
        sidebar.setLayoutY(10);


        chatPane.setLayoutX(230);
        chatPane.setLayoutY(10);
        chatPane.setPrefSize(960, 650);
        chatBox.setSpacing(20);
        chatBox.setPrefWidth(940);
        chatBox.setMinHeight(650);
        messageBox.setPrefSize(930, 140);
        messageBox.setLayoutX(7);
        messageBox.setLayoutY(10);
        messageBox.setSpacing(7);
        messagePane.prefWidthProperty().bind(messageBox.widthProperty());
        messagePane.prefHeightProperty().bind(messageBox.heightProperty());
        messageName.setPrefWidth(930);
        messageName.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        VBox.setMargin(messageName, new Insets(0, 0, 0, 20));
        messageContent.setWrapText(true);
        messageContent.setPrefWidth(930);
        messageContent.setFont(Font.font("Verdana", 23));
        messageContent.setMaxWidth(930);
        VBox.setMargin(messageContent, new Insets(0, 0, 0, 15));

        warningLabel.setLayoutY(670);
        warningLabel.setLayoutX(15);
        warningLabel.setPrefSize(200, 170);
        warningLabel.setWrapText(true);

        messageFieldArea.setPrefSize(960, 170);
        messageFieldArea.setLayoutX(230);
        messageFieldArea.setLayoutY(670);
        messageFieldArea.setWrapText(true);
        messageFieldArea.setOnKeyReleased(event -> {
            boolean isCharacLimReached = false;
            boolean isTextLenReached = false;

            if (event.getCode() == KeyCode.ENTER && event.isShiftDown()) { //Check if sending or new line
                System.out.println("New Line!");
                messageFieldArea.appendText("\n");
            }
            if (messageFieldArea.getLength() > 175) { //Check if character limit reached
                System.out.println("Character limit reached!");
                isTextLenReached = true;
                warningLabel.setText("Character limit of 175 reached!");
                warningLabel.setVisible(true);
            }
            if (messageFieldArea.getParagraphs().size() > 5) { //Check if paragraph limit reached
                System.out.println("Paragraph limit reached!");
                isCharacLimReached = true;
                warningLabel.setText("Paragraph limit of 5 reached!");
                warningLabel.setVisible(true);
            }
            if (!isTextLenReached && !isCharacLimReached) {
                warningLabel.setVisible(false);
                if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) { //Sends message if req met
                    System.out.println("Send message!");
                    sendMessage();
                }
            }

        });
    }
}