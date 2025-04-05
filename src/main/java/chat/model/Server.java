package chat.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Chat server that listens for client connections and starts handlers.
 */
public class Server {
    private static final int PORT = 33290;
    private ServerSocket server;

    /**
     * Initializes the server socket on a predefined port.
     */
    public Server() {
        try{
            server = new ServerSocket(PORT);
            System.out.println("Server started. Server Port: " + getPort());
            System.out.println("Waiting for connection...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the port number the server is listening on.
     *
     * @return the local port number
     */
    public int getPort() {
        return server.getLocalPort();
    }

    /**
     * Accepts client connections and starts their handler threads.
     */
    private void initConnections() {
        while (!server.isClosed()) {
            try {
                Socket socket = server.accept();

                System.out.println("New client connected: " + socket.getRemoteSocketAddress());
                new Thread(new ClientHandler(socket)).start();
            } catch (IOException e) {
                System.out.println("Error accepting client connection.");
                closeServer();
            }
        }
    }

    /**
     * Closes the server socket.
     */
    private void closeServer() {
        try {
            if (server != null) server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Entry point of the server application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.initConnections();
    }
}
