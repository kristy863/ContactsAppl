package com.example.contactsapp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.model.Contact
import com.example.contactsapp.viewmodel.ContactsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


// Η κλάση κληρονομεί απο τις NoteClickDeleteInterface και NoteClickInterface !
class MainActivity : AppCompatActivity(), ContactClickInterface, ContactClickDeleteInterface {

    // Δήλωση μεταβλητών για το recycler view, edit text, button and viewmodel.
    lateinit var viewModel: ContactsViewModel
    lateinit var contactsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Αρχικοποίηση μεταβλητών των views
        contactsRV = findViewById(R.id.contactsRV)
        addFAB = findViewById(R.id.idFAB)

        // Ορισμός layout
        // manager για το recycler view.
        contactsRV.layoutManager = LinearLayoutManager(this)

        // Αρχικοποίηση του Adapter
        val contactRVAdapter = ContactRVAdapter(this, this, this)

        // Ορισμός του adapter για το recycler view
        contactsRV.adapter = contactRVAdapter

        // Αρχικοποίηση view model
        // Χρήση AndroidViewModelFactory
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ContactsViewModel::class.java)

        // Μέθοδος getAllContacts() από το view model class
        viewModel.allContacts.observe(this, Observer { list ->
            list?.let {
                // Με αλλαγή στη λίστα των επαφών γίνεται ανανέωση του adapter
                contactRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            // Ο click listener για το FAButton για την προσθήκη μιας νέας επαφής.
            // Το intent οδηγεί σε AddEditNoteActivity για την προσθήκη μιας νέας επαφής.
            val intent = Intent(this, AddEditContactActivity::class.java)
            startActivity(intent)
        }
    }

    // Υλοποίηση της μεθόδου
    override fun onContactClick(contact: Contact) {
        // Το intent οδηγεί στην AddEditNoteActivity για την ενημέρωση μιας επαφής.
        // Εισαγωγή στοιχείων της επαφής που έγινε το κλικ και ενημέρωση της.
        val intent = Intent(this@MainActivity, AddEditContactActivity::class.java)
        intent.putExtra("contactType", "Edit")
        intent.putExtra("contactName", contact.contactName)
        intent.putExtra("contactSurname", contact.contactSurname)
        intent.putExtra("contactNumber", contact.contactNumber)
        intent.putExtra("contactEmail", contact.contactEmail)
        intent.putExtra("contactId", contact.id)
        startActivity(intent)
    }

    override fun onDeleteIconClick(contact: Contact) {
        // Κλήση μεθόδου deleteNote από το view model class για την διαγραφή μιας επαφής.
        viewModel.deleteContact(contact)
        // Εμφάνιση toast message
        Toast.makeText(this, "${contact.contactName} Deleted", Toast.LENGTH_LONG).show()
    }
}
