package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.IdArrayConverter;
import ru.academits.service.ContactService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

public class RemoveContactsServlet extends HttpServlet {
    private final ContactService phoneBookService = PhoneBook.phoneBookService;
    private final IdArrayConverter idArrayConverter = PhoneBook.idArrayConverter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String idArrayJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int[] idArray = idArrayConverter.convertFormJson(idArrayJson);

            if (!phoneBookService.removeContacts(idArray)) {
                resp.setStatus(400);
            }
        } catch (Exception e) {
            System.out.println("error in RemoveContactsServlet POST: ");
            e.printStackTrace();
        }
    }
}
