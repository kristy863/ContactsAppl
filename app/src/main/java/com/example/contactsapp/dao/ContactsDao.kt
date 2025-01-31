package com.example.contactsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactsapp.model.Contact

// annotation για την dao class.
@Dao
interface ContactsDao {

    // Μέθοδος insert για εισαγωγή στη βάση δεδομένων.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact : Contact)

    // Μέθοδος delete.
    @Delete
    suspend fun delete(contact: Contact)

    // Μέθοδος αναζήτησης

    @Query("Select * from contactsTable order by id ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    // Μέθοδος ενημέρωσης μιας επαφής
    @Update
    suspend fun update(contact: Contact)

}
