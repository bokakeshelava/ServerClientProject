package Client;

import Mutual.Account;
import Mutual.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);

        Socket socket = new Socket("localhost",8080);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        while (true) {
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Get Account Information");
            System.out.println("4.Get sum of ages for all users");
            System.out.println("5.Exit");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("Your name:");
                    String name = scanner.nextLine();
                    System.out.println("Your Lastname:");
                    String lastName = scanner.nextLine();
                    System.out.println("Your age:");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.println("Your ID:");
                    long id = Long.parseLong(scanner.nextLine());
                    System.out.println("Your Password:");
                    String password = scanner.nextLine();
                    Account account = new Account(name,lastName,age,id,password);
                    objectOutputStream.writeObject(Operation.REGISTER);
                    objectOutputStream.writeObject(account);
                    objectOutputStream.flush();
                    objectOutputStream.reset();
                    break;
                case "2":
                    System.out.println("Your name:");
                    String loginName = scanner.nextLine();
                    System.out.println("Your password:");
                    String loginPassword = scanner.nextLine();
                    objectOutputStream.writeObject(Operation.LOGIN);
                    objectOutputStream.writeObject(loginName);
                    objectOutputStream.writeObject(loginPassword);
                    System.out.println(objectInputStream.readObject());
                    objectOutputStream.flush();
                    objectOutputStream.reset();
                    break;
                case "3":
                    System.out.println("Name of person whose age by id you want to get:");
                    String ageGetterName = scanner.nextLine();
                    objectOutputStream.writeObject(Operation.GET_INFO);
                    objectOutputStream.writeObject(ageGetterName);
                    Map<Integer,Integer> ageById = (Map<Integer, Integer>) objectInputStream.readObject();
                    System.out.println(ageById);
                    objectOutputStream.flush();
                    objectOutputStream.reset();
                    break;
                case "4":
                    System.out.println("age sum of all users is: ");
                    objectOutputStream.writeObject(Operation.GET_SUM_OF_AGES);
                    int sumOfAges = (int) objectInputStream.readObject();
                    System.out.println(sumOfAges);
                    objectOutputStream.flush();
                    objectOutputStream.reset();
                case "5":
                    objectOutputStream.writeObject(Operation.EXIT);
                    objectOutputStream.close();
                    objectInputStream.close();
                    socket.close();
                    break;
                default:
                    System.out.println("Wrong option");
            }
        }
    }
}
