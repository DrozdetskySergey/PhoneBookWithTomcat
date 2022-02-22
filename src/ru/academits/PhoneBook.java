package ru.academits;

import ru.academits.coverter.ContactConverter;
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

    public static ContactConverter contactConverter = new ContactConverter();

    public static IdArrayConverter idArrayConverter = new IdArrayConverter();

    public static ContactValidationConverter contactValidationConverter = new ContactValidationConverter();
}
