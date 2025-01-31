package com.example.contactsapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsapp.model.Contact

@Database(entities = arrayOf(Contact::class), version = 2, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun getContactsDao(): ContactsDao

    companion object {
        // αποτροπή πολλαπλών οντοτήτων για τη δημιουργία της βάσης δεδομένων
        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            // εάν  INSTANCE δεν είναι null, return INSTANCE
            // δημιουργία της βάσης δεδομένων
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    "contacts_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
