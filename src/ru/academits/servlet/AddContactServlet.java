package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.ContactsConverter;
import ru.academits.coverter.ContactValidationConverter;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;
import ru.academits.service.ContactValidation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AddContactServlet extends HttpServlet {
    private final ContactService phoneBookService = PhoneBook.phoneBookService;
    private final ContactsConverter contactsConverter = PhoneBook.contactsConverter;
    private final ContactValidationConverter contactValidationConverter = PhoneBook.contactValidationConverter;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (OutputStream responseStream = resp.getOutputStream()) {

            String newContactJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Contact newContact = contactsConverter.convertFormJson(newContactJson);

            ContactValidation contactValidation = phoneBookService.addContact(newContact);

            if (!contactValidation.isValid()) {
                resp.setStatus(400);
            }

            String contactValidationJson = contactValidationConverter.convertToJson(contactValidation);
            responseStream.write(contactValidationJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("error in AddContactServlet POST: ");
            e.printStackTrace();
        }
    }
}
