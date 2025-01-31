package com.example.contactsapp.viewmodel

import android.app.Application
import android.provider.ContactsContract.Contacts
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.dao.ContactsDatabase
import com.example.contactsapp.model.Contact
import com.example.contactsapp.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel (application: Application) :AndroidViewModel(application) {

    // δημιουργούμε μια μεταβλητή που θα περιέχει την λίστα όλων των επαφών
    val allContacts : LiveData<List<Contact>>
    val repository : ContactsRepository

    // αρψικοποιούμε το dao, repository και την allContacts μεταβλητή
    init {
        val dao = ContactsDatabase.getDatabase(application).getContactsDao()
        repository = ContactsRepository(dao)
        allContacts = repository.allContacts
    }

    // μέθοδος διαγραφής
    fun deleteContact (contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }

    // ενημέρωση
    fun updateContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(contact)
    }

    // προσθήκη μιας επαφής
    fun addContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }
}
