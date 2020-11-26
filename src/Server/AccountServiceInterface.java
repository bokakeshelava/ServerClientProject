package Server;

import Mutual.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface AccountServiceInterface {

    void Register(Account account) throws IOException;

    Account Login(String name, String password) throws IOException;

    Map<Long,Integer> ageById(String name) throws IOException;

    boolean ValidateLogin(Account account,String password);

    int GetAgeOfAllAccount() throws FileNotFoundException;

}
