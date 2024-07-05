package service.invoices;

import model.Invoices;

import java.util.List;

public interface InvoicesService {
    void calculateInvoice(String userTc);
    List<Invoices> listInvoice(String userTc);
}
