package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactDao {
    private final List<Contact> contactList = new ArrayList<>();
    private final AtomicInteger lastContactId = new AtomicInteger(0);

    public ContactDao() {
        Contact contact = new Contact();

        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");

        contactList.add(contact);
    }

    private int getNewId() {
        return lastContactId.addAndGet(1);
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public void add(Contact contact) {
        contact.setId(getNewId());

        contactList.add(contact);
    }

    public boolean remove(int id) {
        if (id < 1 || id > lastContactId.get()) {
            return false;
        }

        for (int i = contactList.size() - 1; i >= 0; i--) {
            int contactId = contactList.get(i).getId();

            if (contactId == id) {
                contactList.remove(i);

                return true;
            }
        }

        return false;
    }

    public boolean removeAll(int[] idArray) {
        boolean isRemoved = false;

        for (int id : idArray) {
            isRemoved = isRemoved || remove(id);
        }

        return isRemoved;
    }
}
