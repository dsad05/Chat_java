package chat.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 33290;
    private ServerSocket server;


    public Server() {
        try{
            server = new ServerSocket(PORT);
            System.out.println("Server started. Server Port: " + getPort());
            System.out.println("Waiting for connection...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return server.getLocalPort();
    }

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

    private void closeServer() {
        try {
            if (server != null) server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.initConnections();
    }
}
