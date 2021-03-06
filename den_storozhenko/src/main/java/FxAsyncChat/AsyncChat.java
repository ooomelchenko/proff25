package FxAsyncChat;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;


public class AsyncChat extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);

        BorderPane borderPane = new BorderPane();

        TextField ipText = new TextField("localhost");
        TextField portText = new TextField("30000");
        Button connectButton = new Button("Connect");
        FlowPane connect = new FlowPane(ipText, portText, connectButton);
        borderPane.setTop(connect);

        TextArea chatText = new TextArea();
        chatText.setEditable(false);
        borderPane.setCenter(chatText);

        TextField messageText = new TextField();
        Button sendMessageButton = new Button("Send");

        Text errorText = new Text(" ");
        errorText.setFill(Color.RED);
        FlowPane message = new FlowPane(messageText,sendMessageButton, errorText);
        borderPane.setBottom(message);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("AsyncChat by Denst");
        primaryStage.setScene(scene);
        primaryStage.show();

        ipText.setMinWidth(scene.getWidth() * 0.4);
        portText.setMinWidth(scene.getWidth() * 0.4);
        connectButton.setMinWidth(scene.getWidth() * 0.2);

        messageText.setMinWidth(scene.getWidth() * 0.9);
        sendMessageButton.setMinWidth(scene.getWidth() * 0.1);

        Thread mainThread = new Thread(new NewServer(30000, chatText));
        mainThread.start();
        final Client[] client = new Client[1];
        connectButton.setOnMouseClicked(event -> {
            try {
               client[0] = new Client(ipText.getText(), Integer.parseInt(portText.getText()), messageText);
                errorText.setText("Client created.");
            } catch (IOException e) {
                errorText.setText("Client doesn't created.");
            }
        });

        sendMessageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (client[0]!= null && client[0].isAvaible()) {
                        client[0].send();
                    } else{
                        errorText.setText("Client doesn't exist.");
                    }
                    messageText.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        messageText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().toString().equals("ENTER")) {
                    try {
                        if (client[0] != null && client[0].isAvaible()) {
                            client[0].send();
                        } else {
                            errorText.setText("Client isn't exist.");
                        }
                        messageText.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorText.setText(" ");
                }
            }
        });

        primaryStage.setOnCloseRequest(event -> {
            mainThread.interrupt();
        });
    }

}
