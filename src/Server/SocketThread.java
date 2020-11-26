package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Mutual.*;


public class SocketThread extends Thread {

    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private AccountService accountService;

    public SocketThread(Socket socket, AccountService accountService) throws IOException {
        this.socket = socket;
        this.accountService = accountService;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        boolean notFinished = true;
        boolean notLogined = true;
        while (notFinished) {
            try {
                Operation operation = (Operation) objectInputStream.readObject();
                switch (operation) {
                    case REGISTER:
                        Account account = (Account) objectInputStream.readObject();
                        accountService.Register(account);
                        break;
                    case LOGIN:
                        String name = (String) objectInputStream.readObject();
                        String password = (String) objectInputStream.readObject();
                        Account loginAccount = accountService.Login(name,password);
                        objectOutputStream.writeObject(accountService.getLoginResult());
                        notLogined = false;
                        break;
                    case GET_INFO:
                        if (notLogined) {
                            objectOutputStream.writeObject("You can't see account info if you are not logined");
                            objectOutputStream.flush();
                            objectOutputStream.reset();
                            break;
                        }
                        else {
                            String ageGetterName = (String) objectInputStream.readObject();
                            objectOutputStream.writeObject(accountService.ageById(ageGetterName));
                            objectOutputStream.flush();
                            objectOutputStream.reset();
                            break;
                        }
                    case EXIT:
                        objectOutputStream.close();
                        objectInputStream.close();
                        socket.close();
                        notFinished = false;
                        break;
                    case GET_SUM_OF_AGES:
                        int sumOfAges = accountService.GetAgeOfAllAccount();
                        objectOutputStream.writeObject(sumOfAges);
                        objectOutputStream.flush();
                        objectOutputStream.reset();
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
