package com.example.sivercare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AppDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USER_TYPE = "user_type";

    public static final String TABLE_FACILITIES = "facilities";
    public static final String COLUMN_FACILITY_ID = "facility_id";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_DISTRICT = "district";
    public static final String COLUMN_SUBDISTRICT = "subdistrict";
    public static final String COLUMN_FACILITY_NAME = "facility_name";
    public static final String COLUMN_ADMIN_ID = "admin_id";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_USER_TYPE + " TEXT);";

    private static final String CREATE_TABLE_FACILITIES =
            "CREATE TABLE " + TABLE_FACILITIES + " (" +
                    COLUMN_FACILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CITY + " TEXT, " +
                    COLUMN_DISTRICT + " TEXT, " +
                    COLUMN_SUBDISTRICT + " TEXT, " +
                    COLUMN_FACILITY_NAME + " TEXT, " +
                    COLUMN_ADMIN_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_ADMIN_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_FACILITIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACILITIES);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getId());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_NAME, user.getName());

        if (user instanceof Staff) {
            values.put(COLUMN_USER_TYPE, "직원");
        } else if (user instanceof Admin) {
            values.put(COLUMN_USER_TYPE, "관리자");
        }

        return db.insert(TABLE_USERS, null, values);
    }

    public long addFacility(Facility facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, facility.getCity());
        values.put(COLUMN_DISTRICT, facility.getDistrict());
        values.put(COLUMN_SUBDISTRICT, facility.getSubdistrict());
        values.put(COLUMN_FACILITY_NAME, facility.getName());
        values.put(COLUMN_ADMIN_ID, getUserId(facility.getAdmin().getId()));

        return db.insert(TABLE_FACILITIES, null, values);
    }

    public Facility getFacility(String city, String district, String subdistrict, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FACILITIES,
                new String[]{COLUMN_FACILITY_ID, COLUMN_ADMIN_ID},
                COLUMN_CITY + "=? AND " + COLUMN_DISTRICT + "=? AND " + COLUMN_SUBDISTRICT + "=? AND " + COLUMN_FACILITY_NAME + "=?",
                new String[]{city, district, subdistrict, name},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int facilityId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FACILITY_ID));
            int adminId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_ID));
            cursor.close();

            Admin admin = getAdminById(adminId);
            List<Staff> staffs = getStaffsByFacilityId(facilityId);
            return new Facility(city, district, subdistrict, name, admin, staffs, facilityId);
        } else {
            return null;
        }
    }

    private Admin getAdminById(int adminId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_NAME},
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(adminId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            cursor.close();
            return new Admin(id, password, phone, email, name);
        } else {
            return null;
        }
    }

    private List<Staff> getStaffsByFacilityId(int facilityId) {
        List<Staff> staffs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_NAME},
                COLUMN_USER_TYPE + "=?",
                new String[]{"직원"},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                staffs.add(new Staff(id, password, phone, email, name));
            }
            cursor.close();
        }
        return staffs;
    }

    public void addStaffToFacility(int facilityId, Staff staff) {
        addUser(staff);
        // 추가적인 로직이 필요하다면 여기에 작성합니다.
    }

    private int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            cursor.close();
            return userId;
        } else {
            return -1; // User not found
        }
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount > 0;
    }

    public String getUserType(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_TYPE},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String userType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_TYPE));
            cursor.close();
            return userType;
        }

        return null;
    }
}
