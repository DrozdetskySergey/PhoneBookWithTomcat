var happy = happy || {};

happy.get = function (url, data) {
    return $.get(url, data);
}

happy.post = function (url, data) {
    return $.post({
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data)
    });
}

Vue.component("modal", {
    template: "#modal-template"
});

new Vue({
    el: "#app",

    data: {
        isDisabledDeleteButton: true,
        showModal: 0, // 0(disabled), 1, 2, 3.
        hasAllChecked: false,
        contacts: [], // { id, lastName, name, phone }
        searchTerm: "",
        idList: [],
        contactId: 0,
        lastName: "",
        name: "",
        phone: "",
        isLastNameInvalid: false,
        isNameInvalid: false,
        isPhoneInvalid: false,
    },

    created: function () {
        this.loadContacts();
    },

    methods: {
        clearSearchTerm: function () {
            this.searchTerm = "";

            this.loadContacts();
        },

        loadContacts: function () {
            var currentThis = this;

            happy.get("/phonebook/get", {term: this.searchTerm}).done(function (contacts) {
                currentThis.contacts = JSON.parse(contacts);
            }).fail(function () {
                alert("Не удалось загрузить контакты!");
            });
        },

        addContact: function () {
            this.showModal = 0;

            var request = {
                lastName: this.lastName,
                name: this.name,
                phone: this.phone
            };

            var currentThis = this;

            happy.post("/phonebook/add", request).done(function (response) {
                var contactValidation = JSON.parse(response);

                if (!contactValidation.valid) {
                    alert(contactValidation.error);

                    return;
                }

                currentThis.loadContacts();

                currentThis.lastName = "";
                currentThis.name = "";
                currentThis.phone = "";
            }).fail(function () {
                alert("Не удалось добавить контакт!");
            });
        },

        removeContact: function () {
            this.showModal = 0;

            this.removeContactsFromPhoneBook([this.contactId]);
        },

        removeContacts: function () {
            this.showModal = 0;

            if (!this.searchTerm) {
                this.removeContactsFromPhoneBook(this.idList);

                return;
            }

            var actualContactsIdList = this.contacts.map(function (contact) {
                return contact.id;
            });

            var idList = this.idList.filter(function (id) {
                return actualContactsIdList.indexOf(id) !== -1;
            });

            this.removeContactsFromPhoneBook(idList);
        },

        removeContactsFromPhoneBook: function (idList) {
            if (idList.length === 0) {
                return;
            }

            var currentThis = this;

            happy.post("/phonebook/remove", idList).done(function () {
                currentThis.idList = currentThis.idList.filter(function (id) {
                    return idList.indexOf(id) === -1;
                });

                if (currentThis.idList.length === 0) {
                    currentThis.hasAllChecked = false;
                    currentThis.isDisabledDeleteButton = true;
                }

                currentThis.loadContacts();
            }).fail(function () {
                alert("Не удалось удалить контакт(ы)!");
            });

        },

        checkNewContactValid: function () {
            var phone = this.phone.replace(/\D/g, "");

            if (!this.lastName) {
                this.isLastNameInvalid = true;
            }

            if (!this.name) {
                this.isNameInvalid = true;
            }

            if (phone.length !== 11) {
                this.isPhoneInvalid = true;
            }

            if (this.isLastNameInvalid || this.isNameInvalid || this.isPhoneInvalid) {
                return;
            }

            this.phone = phone;

            this.showModal = 3;
        }
    },

    watch: {
        hasAllChecked: function (newValue) {
            this.idList.splice(0, this.idList.length);

            if (newValue) {
                var currentThis = this;

                this.contacts.forEach(function (contact) {
                    currentThis.idList.push(contact.id);
                });
            }
        },

        idList: function (newValue) {
            this.isDisabledDeleteButton = newValue.length === 0;
        },

        lastName: function (newValue) {
            if (newValue.length > 0) {
                this.isLastNameInvalid = false;
            }
        },

        name: function (newValue) {
            if (newValue.length > 0) {
                this.isNameInvalid = false;
            }
        },

        phone: function (newValue) {
            if (newValue.length > 10) {
                this.isPhoneInvalid = false;
            }
        }
    }
});