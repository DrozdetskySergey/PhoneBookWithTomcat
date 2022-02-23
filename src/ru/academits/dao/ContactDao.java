package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ContactDao {
    private final List<Contact> contactList = new ArrayList<>();
    private final AtomicInteger lastContactId = new AtomicInteger(0);

    public ContactDao() {
        Contact contact = new Contact();

        contact.setId(getNewId());
        contact.setName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("89123456789");

        contactList.add(contact);
    }

    private int getNewId() {
        return lastContactId.addAndGet(1);
    }

    public synchronized List<Contact> getAllContacts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return contactList;
        }

        String searchTermToLowerCase = searchTerm.toLowerCase().trim();

        return contactList.stream()
                .filter(contact ->
                        contact.getName().toLowerCase().contains(searchTermToLowerCase)
                        || contact.getLastName().toLowerCase().contains(searchTermToLowerCase)
                        || contact.getPhone().toLowerCase().contains(searchTermToLowerCase))
                .collect(Collectors.toList());
    }

    public synchronized void add(Contact contact) {
        contact.setId(getNewId());

        contactList.add(contact);
    }

    private boolean remove(int id) {
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

    public synchronized boolean removeAll(int[] idArray) {
        boolean isRemoved = false;

        for (int id : idArray) {
            isRemoved = isRemoved | remove(id);
        }

        return isRemoved;
    }
}
