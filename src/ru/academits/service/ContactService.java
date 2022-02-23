package ru.academits.service;

import ru.academits.PhoneBook;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;

import java.util.List;


public class ContactService {
    private final ContactDao contactDao = PhoneBook.contactDao;

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.getAllContacts("");

        for (Contact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }

        return false;
    }

    public ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();

        if (contact.getName().isEmpty()) {
            contactValidation.setError("Поле Имя должно быть заполнено.");
        } else if (contact.getLastName().isEmpty()) {
            contactValidation.setError("Поле Фамилия должно быть заполнено.");
        } else if (contact.getPhone().length() != 11) {
            contactValidation.setError("Поле Телефон должно быть длиной 11 символов.");
        } else if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");
        }

        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);

        if (contactValidation.isValid()) {
            contactDao.add(contact);
        }

        return contactValidation;
    }

    public List<Contact> getAllContacts(String searchTerm) {
        return contactDao.getAllContacts(searchTerm);
    }

    public boolean removeContacts(int[] idArray) {
        return contactDao.removeAll(idArray);
    }
}
