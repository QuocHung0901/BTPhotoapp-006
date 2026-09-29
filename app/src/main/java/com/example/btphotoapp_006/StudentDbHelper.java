package com.example.btphotoapp_006;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "student_database.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENT =
            "students";

    private static final String COLUMN_ID =
            "id";

    private static final String COLUMN_NAME =
            "name";

    private static final String COLUMN_EMAIL =
            "email";

    private static final String COLUMN_TELEPHONE =
            "telephone";


    public StudentDbHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable =
                "CREATE TABLE "
                        + TABLE_STUDENT
                        + " ("
                        + COLUMN_ID
                        + " TEXT PRIMARY KEY, "
                        + COLUMN_NAME
                        + " TEXT, "
                        + COLUMN_EMAIL
                        + " TEXT, "
                        + COLUMN_TELEPHONE
                        + " TEXT"
                        + ")";

        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL(
                "DROP TABLE IF EXISTS "
                        + TABLE_STUDENT
        );

        onCreate(db);
    }


    // =====================================================
    // SAVE STUDENT
    // =====================================================

    public boolean saveStudent(
            Student student
    ) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_ID,
                student.getId()
        );

        values.put(
                COLUMN_NAME,
                student.getName()
        );

        values.put(
                COLUMN_EMAIL,
                student.getEmail()
        );

        values.put(
                COLUMN_TELEPHONE,
                student.getTelephone()
        );

        /*
         * Nếu ID đã tồn tại:
         * dữ liệu sẽ được thay thế.
         */
        long result =
                db.insertWithOnConflict(
                        TABLE_STUDENT,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                );

        db.close();

        return result != -1;
    }


    // =====================================================
    // LOAD STUDENT THEO ID
    // =====================================================

    public Student getStudentById(
            String id
    ) {

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_STUDENT,

                        null,

                        COLUMN_ID + " = ?",

                        new String[]{
                                id
                        },

                        null,
                        null,
                        null
                );

        Student student = null;

        if (cursor.moveToFirst()) {

            String studentId =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_ID
                            )
                    );

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_NAME
                            )
                    );

            String email =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_EMAIL
                            )
                    );

            String telephone =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_TELEPHONE
                            )
                    );

            student =
                    new Student(
                            studentId,
                            name,
                            email,
                            telephone
                    );
        }

        cursor.close();

        db.close();

        return student;
    }
}