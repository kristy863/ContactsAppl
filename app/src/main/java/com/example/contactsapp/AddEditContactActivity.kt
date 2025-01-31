package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.model.Contact
import com.example.contactsapp.viewmodel.ContactsViewModel

class AddEditContactActivity : AppCompatActivity() {
    // Δημιουργία μεταβλητών για τα UI components.
    lateinit var contactNameEdit: EditText
    lateinit var contactSurnameEdit: EditText
    lateinit var contactNumberEdit: EditText
    lateinit var contactEmailEdit: EditText
    lateinit var saveBtn: Button

    lateinit var viewModel: ContactsViewModel
    var contactID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        // Αρχικοποίηση του view model class.
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ContactsViewModel::class.java)

        // Αρχικοποίηση των μεταβλητών των UI components.
        contactNameEdit = findViewById(R.id.idEditContactName)
        contactSurnameEdit = findViewById(R.id.idEditContactSurname)
        contactNumberEdit = findViewById(R.id.idEditContactNumber)
        contactEmailEdit = findViewById(R.id.idEditContactEmail)
        saveBtn = findViewById(R.id.idBtn)

        // Λήψη δεδομένων μέσω του intent
        val contactType = intent.getStringExtra("contactType")
        if (contactType.equals("Edit")) { // Αν ο τύπος ειναι ενημέρωση.
            // Θέτουμε τα δεδομένα στα edit text.
            val contactName = intent.getStringExtra("contactName")
            val contactSurname = intent.getStringExtra("contactSurname")
            val contactNumber = intent.getStringExtra("contactNumber")
            val contactEmail= intent.getStringExtra("contactEmail")
            contactID = intent.getIntExtra("contactId", -1)
            saveBtn.setText("Update Contact")
            contactNameEdit.setText(contactName)
            contactSurnameEdit.setText(contactSurname)
            contactNumberEdit.setText(contactNumber)
            contactEmailEdit.setText(contactEmail)

        } else { // Προσθήκη επαφής
            saveBtn.setText("Save Contact")
        }

        // Click listener του save button.
        saveBtn.setOnClickListener {
            val contactName = contactNameEdit.text.toString()
            val contactSurname = contactSurnameEdit.text.toString()
            val contactNumber = contactNumberEdit.text.toString()
            val contactEmail = contactEmailEdit.text.toString()

            // Έλεγχος του τύπου contact, αν ειναι ενημέρωση ή προσθήκη.
            if (contactType.equals("Edit")) {
                if (contactName.isNotEmpty() && contactSurname.isNotEmpty() && contactNumber.isNotEmpty() && contactEmail.isNotEmpty()) {
                    val updatedContact = Contact(contactName, contactSurname, contactNumber, contactEmail )
                    updatedContact.id = contactID
                    viewModel.updateContact(updatedContact)
                    Toast.makeText(this, "Contact Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (contactName.isNotEmpty() && contactSurname.isNotEmpty() && contactNumber.isNotEmpty() && contactEmail.isNotEmpty()) {
                    viewModel.addContact(Contact(contactName, contactSurname, contactNumber, contactEmail ))
                    Toast.makeText(this, "$contactName Added", Toast.LENGTH_LONG).show()
                }
            }
            // MainActivity.
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}

