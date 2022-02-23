package ru.academits;

import ru.academits.coverter.ContactsConverter;
import ru.academits.coverter.ContactValidationConverter;
import ru.academits.coverter.IdArrayConverter;
import ru.academits.dao.ContactDao;
import ru.academits.service.ContactService;

/**
 * Created by Anna on 14.06.2017.
 */
public class PhoneBook {

    public static ContactDao contactDao = new ContactDao();

    public static ContactService phoneBookService = new ContactService();

    public static ContactsConverter contactsConverter = new ContactsConverter();

    public static IdArrayConverter idArrayConverter = new IdArrayConverter();

    public static ContactValidationConverter contactValidationConverter = new ContactValidationConverter();
}
