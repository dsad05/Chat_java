package chat.model;

import java.io.*;
import java.net.Socket;
import javafx.application.Platform;

/**
 * Represents a chat client that connects to the server.
 * Handles sending and receiving messages over a socket connection.
 */
public class Client {
    private static final int PORT = 33290;
    private static final String HOST = "localhost";

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final String username;
    private boolean isRunning = true;
    private MessageListener messageListener;

    /**
     * Creates a new client instance and connects to the server using the given username.
     * @param username the username for the chat session
     */
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

    /**
     * Sets the listener to handle incoming and sent messages.
     * Starts listening for incoming messages.
     * @param listener the message listener
     */
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
        listenForMessage();
    }

    /**
     * Sends a message to the server.
     * @param message the message to send
     */
    public void sendMessage(String message) {
        try {
            if (socket.isConnected() && isRunning) {
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("Message sent: " + message);
                Platform.runLater(() -> messageListener.onMessageSent(message));
            }
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
            closeEverything();
        }
    }

    /**
     * Starts a background thread to listen for messages from the server.
     */
    public void listenForMessage() {
        Thread listenThread = new Thread(() -> {
            String message;
            while (!socket.isClosed()) {
                try {
                    message = bufferedReader.readLine();
                    if (message == null) {
                        break;
                    }
                    if (messageListener != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> messageListener.onMessageReceived(finalMessage));
                    }
                } catch (IOException e) {
                    break;
                }
            }
            closeEverything();
        });
        listenThread.setDaemon(true);
        listenThread.start();
    }

    /**
     * Closes the client connection and all associated resources.
     * Waits briefly for the listener thread to finish.
     */
    public synchronized void closeEverything() {
        if (!isRunning) return;

        sendMessage(username + " has left the chat");
        isRunning = false;
        System.exit(0);
    }

}
