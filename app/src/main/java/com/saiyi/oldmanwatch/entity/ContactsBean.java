package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/10/19
 * @Describe
 */
public class ContactsBean {
    private String mac;
    private List<Contacts> contacts;

    public class Contacts {
        private String phone;
        private String name;
        private String first;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        @Override
        public String toString() {
            return "ContactsBean{" +
                    "phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", first='" + first + '\'' +
                    '}';
        }
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ContactsBean{" +
                "mac='" + mac + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
