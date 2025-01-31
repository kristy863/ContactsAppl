package com.example.contactsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Όνομα πίνακα
@Entity(tableName = "contactsTable")

// Όνομα των στηλών του πίνακα και μορφή των attributes
class Contact(@ColumnInfo(name = "name") val contactName:String,
              @ColumnInfo(name = "surname") val contactSurname:String,
              @ColumnInfo(name = "number") val contactNumber: String,
              @ColumnInfo(name = "email") val contactEmail: String
) {

    // ορισμός του πρωταρχικού κλειδιού του πίνακα και δήλωση id
    @PrimaryKey(autoGenerate = true) var id = 0
}

