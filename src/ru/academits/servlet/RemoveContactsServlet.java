package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.ContactConverter;
import ru.academits.coverter.IdArrayConverter;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveContactsServlet extends HttpServlet {
    private final ContactService phoneBookService = PhoneBook.phoneBookService;
    private final IdArrayConverter idArrayConverter = PhoneBook.idArrayConverter;
    private final ContactConverter contactConverter = PhoneBook.contactConverter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (OutputStream responseStream = resp.getOutputStream()) {

            String idArrayJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int[] idArray = idArrayConverter.convertFormJson(idArrayJson);

            if (!phoneBookService.removeContacts(idArray)) {
                resp.setStatus(400);
            }

            List<Contact> contactList = phoneBookService.getAllContacts();

            String contactListJson = contactConverter.convertToJson(contactList);

            responseStream.write(contactListJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("error in RemoveContactsServlet POST: ");
            e.printStackTrace();
        }
    }
}
