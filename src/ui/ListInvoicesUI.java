package ui;

import model.Invoices;
import service.invoices.DefaultInvoiceService;

import java.util.List;
import java.util.Scanner;

public class ListInvoicesUI {

    private static final DefaultInvoiceService invoiceService= new DefaultInvoiceService();

    public static void listInvoices(){
        String userTc;

        Scanner sc= new Scanner(System.in);
        System.out.println("Faturanı görmek için lütfen TC kimlik numaranı gir:");
        userTc=sc.nextLine();
        List<Invoices> invoice= invoiceService.listInvoice(userTc);

        for(Invoices invoices: invoice){
            System.out.println("Fatura ID:"+ invoices.getId());
            System.out.println("Rezervasyon ID:"+ invoices.getReservationId());
            System.out.println("Fatura Tarihi:"+ invoices.getInvoiceDate());
            System.out.println("Toplam Tutar:"+ invoices.getTotalAmount());
        }

    }
}
