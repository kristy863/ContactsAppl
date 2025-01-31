package com.example.contactsapp.repository

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.contactsapp.dao.ContactsDao
import com.example.contactsapp.model.Contact


class ContactsRepository(private val contactsDao: ContactsDao) {

    // δημιουργία μεταβλητής για την λίστα των contacts
    val allContacts: LiveData<List<Contact>> = contactsDao.getAllContacts()

    // δημιουργία insert μεθόδου για τη πρόσθεση επαφής στη βάση δεδομένων
    suspend fun insert(contact: Contact) {
        contactsDao.insert(contact)
    }

    // διαγραφή επαφής απο την βάση δεδομένων.
    suspend fun delete(contact: Contact){
        contactsDao.delete(contact)
    }

    // ενημέρωση επαφής στη βάση δεδομένων.
    suspend fun update(contact: Contact){
        contactsDao.update(contact)
    }
}
