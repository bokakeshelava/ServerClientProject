package Server;

import Mutual.Account;

import java.io.*;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class AccountService implements AccountServiceInterface {

    private static final String DIRECTORY = "Accounts";

    private String loginResult;


    @Override
    public void Register(Account account) throws IOException {
        String path = DIRECTORY + "/" + account.getName() + ".txt";
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(account);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account Login(String name, String password) throws IOException {
        Account account;
        String path = DIRECTORY + "/" + name + ".txt";
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            account = (Account) objectInputStream.readObject();
            if (ValidateLogin(account,password)) {
                loginResult = "Succesful Login to:" + account.getName();
                return account;
            }
            else {
                loginResult = "wrong login and password, your account does not exist";
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Wrong login and password, your account doesnot exist");
        }
        return null;
    }

    @Override
    public Map<Long, Integer> ageById(String name) throws IOException {
        Map<Long, Integer> ageById = new HashMap<>();
        String path = DIRECTORY + "/" + name + ".txt";
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            Account account = (Account) objectInputStream.readObject();
            ageById.put(account.getId(), account.getAge());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ageById;
    }

    @Override
    public boolean ValidateLogin(Account account, String password) {
        return account.getPassword().equals(password);
    }

    @Override
    public int GetAgeOfAllAccount() throws FileNotFoundException {
        int ageSum = 0;
        String path;
        File file = new File(DIRECTORY);
        File[] directory = file.listFiles();
        for (File acc : directory) {
            path = DIRECTORY + "/" + acc.getName();
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
                Account account = (Account) inputStream.readObject();
                ageSum += account.getAge();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ageSum;
    }


    String getLoginResult() { return loginResult;}
}
