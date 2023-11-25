package com.example.gestioncontactsfinal.dao;

import android.provider.BaseColumns;

public final class ContactsContract {
        private ContactsContract () {}
        public static class Contact implements BaseColumns {
            public static final String TABLE_NAME = "Contact";
            public static final String COLUMN_NAME_NOM = "Nom";
            public static final String COLUMN_NAME_PRENOM = "Prenom";
            public static final String COLUMN_NAME_SERVICE = "Service";
            public static final String COLUMN_NAME_EMAIL = "Email";
            public static final String COLUMN_NAME_TEL = "Tel";
            public static final String COLUMN_NAME_FAVORITE = "fav";
        }
        public static class User implements BaseColumns {
            public static final String TABLE_NAME = "User";
            public static final String COLUMN_NAME_FIRSTNAME = "FirstName";
            public static final String COLUMN_NAME_LASTNAME = "LastName";
            public static final String COLUMN_NAME_PASSWORD = "Password";
            public static final String COLUMN_NAME_EMAIL = "Email";
            public static final String COLUMN_NAME_BIRTH = "BirthDate";
        }


}




