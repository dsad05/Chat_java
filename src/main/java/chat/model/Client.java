package chat.model;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final int PORT = 33290;
    private static final String HOST = "localhost";
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final String username;
    private boolean isRunning = true;
    private MessageListener messageListener;

    public Client(String username) {
        this.username = username;
        try {
            this.socket = new Socket(HOST, PORT);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(username);

            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            closeEverything();
        }
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
        listenForMessage();
    }

    public void sendMessage(String message) {
        try {
            if (socket.isConnected() && isRunning) {
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("Message sent: " + message);
                javafx.application.Platform.runLater(() -> messageListener.onMessageSent(message));
            }
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
            closeEverything();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String message;
            while (!socket.isClosed()) {
                try {
                    message = bufferedReader.readLine();
                    if (message == null) {
                        closeEverything();
                        break;
                    }
                    if (messageListener != null) {
                        String finalMessage = message;
                        System.out.println("Received: " + finalMessage);
                        javafx.application.Platform.runLater(() -> messageListener.onMessageReceived(finalMessage));
                    }
                } catch (IOException e) {
                    closeEverything();
                    break;
                }
            }
        }).start();
    }

    public synchronized void closeEverything() {
        if (!isRunning) return;
        isRunning = false;
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
