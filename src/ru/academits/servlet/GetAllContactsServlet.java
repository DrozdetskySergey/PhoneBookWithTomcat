package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.ContactsConverter;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllContactsServlet extends HttpServlet {
    private final ContactService phoneBookService = PhoneBook.phoneBookService;
    private final ContactsConverter contactsConverter = PhoneBook.contactsConverter;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (OutputStream responseStream = resp.getOutputStream()) {
            String searchTerm = req.getParameter("term");

            if (searchTerm == null) {
                searchTerm = "";
            }

            List<Contact> contactList = phoneBookService.getAllContacts(searchTerm);

            String contactListJson = contactsConverter.convertToJson(contactList);

            responseStream.write(contactListJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("error in GetAllContactsServlet GET: ");
            e.printStackTrace();
        }
    }
}
