package com.example.contactsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.model.Contact

class ContactRVAdapter(
    val context: Context,
    val contactClickDeleteInterface: ContactClickDeleteInterface,
    val contactClickInterface: ContactClickInterface
) :
    RecyclerView.Adapter<ContactRVAdapter.ViewHolder>() {

    // Μεταβλητή για την λίστα των contacts
    private val allContacts = ArrayList<Contact>()

    // Δημιουργία εσωτερικής κλάσης view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Αρχικοποίηση μεταβλητών
        // εμφάνιση επαφών στη λίστα.
        val contactIV = itemView.findViewById<TextView>(R.id.idΙVContact)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Εμφάνιση του contact_rv_item xml layout για κάθε αντικείμενο στη λίστα.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Προσθήκη δεδομένων στο view holder.
        holder.contactIV.setText(allContacts.get(position).contactName)
        // Προσθήκη του click listener για το κουμπί διαγραφής.
        holder.deleteIV.setOnClickListener {
            // interface μεθοδος για την διαγραφή.
            contactClickDeleteInterface.onDeleteIconClick(allContacts.get(position))
        }

        // Προσθήκη του click listener για το viewholder.
        holder.itemView.setOnClickListener {
            // Interface μεθοδος για ενημέρωση.
            contactClickInterface.onContactClick(allContacts.get(position))
        }
    }

    override fun getItemCount(): Int {
        // υλοποίηση της getItemCount μέθοδου που απαιτείται απο την RecyclerView.Adapter κλάση.
        return allContacts.size
    }

    // Μέθοδος για την ενημέρωση της λίστας μετά απο αλλαγές.
    fun updateList(newList: List<Contact>) {
        // καθαρισμός της λίστας
        allContacts.clear()
        // Χρήση ενημερωμένης λίστας για τον adapter.
        allContacts.addAll(newList)
        // Ειδοποίηση τον adapter για τις αλλαγές στην λίστα.
        notifyDataSetChanged()
    }
}

interface ContactClickDeleteInterface {
    // Δήλωση της abstract μεθοδο διαγραφής
    fun onDeleteIconClick(contact: Contact)
}

interface ContactClickInterface {
    // Δήλωση της μεθόδου ενημέρωσης της RecyclerView
    fun onContactClick(contact: Contact)
}