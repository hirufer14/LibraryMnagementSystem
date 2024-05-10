package com.example.librarysystem;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_MEMBERS = "members";

    // Users table columns
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // Books table columns
    public static final String KEY_BOOK_ID = "book_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_ISBN = "isbn";

    // Members table columns
    public static final String KEY_MEMBER_ID = "member_id";
    public static final String KEY_MEMBER_NAME = "name";
    public static final String KEY_MEMBER_CONTACT = "contact";

    // Create Users table query
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + "("
                    + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_USERNAME + " TEXT,"
                    + KEY_PASSWORD + " TEXT"
                    + ")";

    // Create Books table query
    private static final String CREATE_BOOKS_TABLE =
            "CREATE TABLE " + TABLE_BOOKS + "("
                    + KEY_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_TITLE + " TEXT,"
                    + KEY_AUTHOR + " TEXT,"
                    + KEY_ISBN + " TEXT"
                    + ")";

    // Create Members table query
    private static final String CREATE_MEMBERS_TABLE =
            "CREATE TABLE " + TABLE_MEMBERS + "("
                    + KEY_MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_MEMBER_NAME + " TEXT,"
                    + KEY_MEMBER_CONTACT + " TEXT"
                    + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create required tables
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_BOOKS_TABLE);
        db.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        // Create tables again
        onCreate(db);
    }

    // CRUD Operations for Users

    public long registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);

        long newRowId = db.insert(TABLE_USERS, null, values);
        db.close();

        return newRowId;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean loginSuccessful = false;

        try {
            String[] projection = {KEY_USER_ID};
            String selection = KEY_USERNAME + " = ? AND " + KEY_PASSWORD + " = ?";
            String[] selectionArgs = {username, password};

            cursor = db.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null);
            loginSuccessful = cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return loginSuccessful;
    }

    // CRUD Operations for Books

    public long addBook(String title, String author, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_ISBN, isbn);

        long newRowId = db.insert(TABLE_BOOKS, null, values);
        db.close();

        return newRowId;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {KEY_BOOK_ID + " AS _id", KEY_TITLE, KEY_AUTHOR, KEY_ISBN};
        return db.query(TABLE_BOOKS, projection, null, null, null, null, null);
    }

    public Cursor getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {KEY_TITLE, KEY_AUTHOR, KEY_ISBN};
        String selection = KEY_BOOK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};
        return db.query(TABLE_BOOKS, projection, selection, selectionArgs, null, null, null);
    }

    public int updateBook(int bookId, String title, String author, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_ISBN, isbn);

        String selection = KEY_BOOK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};

        int rowsAffected = db.update(TABLE_BOOKS, values, selection, selectionArgs);
        db.close();

        return rowsAffected;
    }

    // CRUD Operations for Members

    public long addMember(String name, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_NAME, name);
        values.put(KEY_MEMBER_CONTACT, contact);

        long newRowId = db.insert(TABLE_MEMBERS, null, values);
        db.close();

        return newRowId;
    }

    public Cursor getAllMembers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {KEY_MEMBER_ID + " AS _id", KEY_MEMBER_NAME, KEY_MEMBER_CONTACT};
        return db.query(TABLE_MEMBERS, projection, null, null, null, null, null);
    }
    public Cursor getMemberById(int memberId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {KEY_MEMBER_NAME, KEY_MEMBER_CONTACT};
        String selection = KEY_MEMBER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(memberId)};
        return db.query(TABLE_MEMBERS, projection, selection, selectionArgs, null, null, null);
    }



    public int updateMember(int memberId, String name, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_NAME, name);
        values.put(KEY_MEMBER_CONTACT, contact);

        String selection = KEY_MEMBER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(memberId)};

        int rowsAffected = db.update(TABLE_MEMBERS, values, selection, selectionArgs);
        db.close();

        return rowsAffected;
    }
}

