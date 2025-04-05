package chat.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles communication with a single client on the server side.
 * Responsible for reading, broadcasting messages, and cleanup.
 */
public class ClientHandler implements Runnable {
    public static final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    /**
     * Creates a handler for the connected client socket.
     *
     * @param socket the client's socket
     */
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            if (this.clientUsername == null || this.clientUsername.trim().isEmpty()) {
                this.clientUsername = "Anonymous";
            }
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
        } catch (IOException e) {
            closeEverything();
        }
    }

    /**
     * Listens for messages from the client and broadcasts them to others.
     */
    @Override
    public void run() {
        String messageFromClient;

        while (!socket.isClosed()) {
            try {
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient == null) break;
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                break;
            }
        }
        closeEverything();
    }

    /**
     * Broadcasts a message to all connected clients.
     *
     * @param messageToSend the message to broadcast
     */
    public void broadcastMessage(String messageToSend) {
        if (messageToSend == null || messageToSend.isEmpty()) return;

        System.out.println("Broadcasting message: " + messageToSend);

        synchronized (clientHandlers) {
            clientHandlers.removeIf(client -> client.socket.isClosed()); // Usuwamy rozłączonych klientów

            for (ClientHandler clientHandler : clientHandlers) {
                try {
                    if (!clientHandler.clientUsername.equals(this.clientUsername)) {
                        clientHandler.bufferedWriter.write(messageToSend);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    clientHandler.closeEverything();
                }
            }
        }
    }

    /**
     * Removes the client from the active list and notifies others.
     */
    public synchronized void removeClientHandler() {
        synchronized (clientHandlers) {
            broadcastMessage("SERVER: " + clientUsername + " has left the chat");
            clientHandlers.remove(this);
        }
    }

    /**
     * Closes resources and removes the client handler.
     */
    public void closeEverything() {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            removeClientHandler();
        }
    }
}
