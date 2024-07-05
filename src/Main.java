import ui.*;

import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc= new Scanner(System.in);
        int choice;
        menu();
        choice=sc.nextInt();
        while(true) {
            if (choice == 1) {
                adminMenu();
               int choices= sc.nextInt();
               switch (choices){
                   case 1:
                       AddRoomUI.addRoom();
                   case 2:
                       ListAllCustomerMadeReservationsUI.listCustomer();
                   case 3:
                       break;

               }
            }else if(choice==2){
                customerMenu();
                int choices= sc.nextInt();
                switch (choices) {
                    case 1:
                        RegisterCustomerUI.registerCustomer();
                    case 2:
                        customerOperationsMenu();
                        int cho= sc.nextInt();
                        if (cho == 1) {
                            MakeReservationUI.makeReservation();
                        }else if(cho==2){
                            ListCustomerReservationUI.listCustomerReservation();
                        }else if(cho==3){
                            UpdateReservationUI.updateReservation();
                        }else if(cho==4){
                            ListInvoicesUI.listInvoices();
                        }else{
                            System.out.println("Geçersiz seçim");
                        }

                }

            }else{
                System.out.println("Geçersiz seçim");
            }
        }

    }

    private static void menu(){
        System.out.println("****************** UPOD OTELE HOŞ GELDİNİZ ******************");
        System.out.println("Yönetici olarak devam etmek için 1 'e \n Rezervasyon yapmak için 2 'ye basınız");
    }
    private static void adminMenu(){
        System.out.println("****************** SAYIN YÖNETİCİ UPOD OTELE HOŞ GELDİN ******************");
        System.out.println("Oda oluşturmak için 1 'e \n Rezervasyon yapan müşterileri listelemek için 2'ye \n çıkış için 3' e basın");
    }
    private static void customerMenu(){
        System.out.println("****************** SAYIN MÜŞTERİMİZ UPOD OTELE HOŞ GELDİN ******************");
        System.out.println("Lütfen kayıt yapmak için 1'e \n Kaydınız var ise devam etmek için 2'ye basınız ");
    }
    private static void customerOperationsMenu(){
        System.out.println("Yapmak istediğniz işlemi seçiniz:");
        System.out.println("Rezervasyon yapmak için 1'e \n Rezervasyonlarınızı görüntülemek için 2'ye basınız \n Rezervasyonunuzu düzenlemek için 3'e \n Faturanızı görmek için  4' e basınız");
    }
}