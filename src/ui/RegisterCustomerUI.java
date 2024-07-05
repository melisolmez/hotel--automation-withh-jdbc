package ui;

import model.User;
import service.user.DefaultUserService;

import java.sql.SQLException;
import java.util.Scanner;

public class RegisterCustomerUI {

    private static final DefaultUserService userService= new DefaultUserService();

    public static void registerCustomer() throws SQLException {
        String name,surname,TC,tel;
        Scanner sc= new Scanner(System.in);
        System.out.println("****** KAYIT *********");
        System.out.println("Lütfen adınızı giriniz:");
        name=sc.nextLine();
        System.out.println("Lütfen soyadınızı giriniz:");
        surname=sc.nextLine();
        System.out.println("Lütfen TC kimlik numaranızı giriniz:");
        TC=sc.nextLine();
        System.out.println("Lütfen telefon numaranızı giriniz:");
        tel=sc.nextLine();

        User user= new User();
        user.setName(name);
        user.setSurname(surname);
        user.setTel(tel);
        user.setTC(TC);

        userService.register(user);
    }

}
