package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.ContactConverter;
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
    private final ContactConverter contactConverter = PhoneBook.contactConverter;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (OutputStream responseStream = resp.getOutputStream()) {
            List<Contact> contactList = phoneBookService.getAllContacts();

            String contactListJson = contactConverter.convertToJson(contactList);

            responseStream.write(contactListJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("error in GetAllContactsServlet GET: ");
            e.printStackTrace();
        }
    }
}
