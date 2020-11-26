package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket serverSocket = new ServerSocket(8080);
        AccountService accountService = new AccountService();




        while (true) {
            Socket socket = serverSocket.accept();
            SocketThread socketThread = new SocketThread(socket,accountService);
            socketThread.start();
        }
    }


}
