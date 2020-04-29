package server;

public class Server_main {
    public static void main(String[] args) {
        Rest_Controller server = new Rest_Controller();

        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
