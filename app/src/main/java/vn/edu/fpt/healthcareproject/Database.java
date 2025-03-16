package vn.edu.fpt.healthcareproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table users(username text, email text, password text)";
        sqLiteDatabase.execSQL(qry1);
        // Tạo bảng appointments

        String qry2 = "create table appointments(_id integer primary key autoincrement, username text, fullname text, address text, contact text, date text, time text, fees text)";
        sqLiteDatabase.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists appointments");

        // Tạo lại bảng mới
        onCreate(sqLiteDatabase);
    }
    public void register (String username,String email, String password){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login(String username, String password){
        int result=0;
        String str[] = new String[2];
        str[0] =username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username = ? and password = ?", str);
        if(c.moveToFirst()){
            result = 1;
        }
        return result;
    }

    // Phương thức thêm đặt lịch
    public void addAppointment(String username, String fullname, String address, String contact, String date, String time, String fees) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("fullname", fullname);
        cv.put("address", address);
        cv.put("contact", contact);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("fees", fees);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("appointments", null, cv);
        db.close();
    }

    public Cursor getAppointmentsByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select _id, username, fullname, address, contact, date, time, fees from appointments where username = ?", new String[]{username});
    }
}
