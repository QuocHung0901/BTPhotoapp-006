package com.example.btphotoapp_006;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtTelephone;

    private Button btnSaveShared;
    private Button btnLoadShared;

    private Button btnSaveSQLite;
    private Button btnLoadSQLite;

    private StudentDbHelper dbHelper;

    private static final String PREF_NAME =
            "StudentPreferences";

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_main
        );


        // =====================================================
        // ÁNH XẠ VIEW
        // =====================================================

        edtId =
                findViewById(
                        R.id.edtId
                );

        edtName =
                findViewById(
                        R.id.edtName
                );

        edtEmail =
                findViewById(
                        R.id.edtEmail
                );

        edtTelephone =
                findViewById(
                        R.id.edtTelephone
                );


        btnSaveShared =
                findViewById(
                        R.id.btnSaveShared
                );

        btnLoadShared =
                findViewById(
                        R.id.btnLoadShared
                );

        btnSaveSQLite =
                findViewById(
                        R.id.btnSaveSQLite
                );

        btnLoadSQLite =
                findViewById(
                        R.id.btnLoadSQLite
                );


        // =====================================================
        // SQLITE HELPER
        // =====================================================

        dbHelper =
                new StudentDbHelper(this);


        // =====================================================
        // SAVE SHARED PREFERENCES
        // =====================================================

        btnSaveShared.setOnClickListener(
                view -> saveSharedPreferences()
        );


        // =====================================================
        // LOAD SHARED PREFERENCES
        // =====================================================

        btnLoadShared.setOnClickListener(
                view -> loadSharedPreferences()
        );


        // =====================================================
        // SAVE SQLITE
        // =====================================================

        btnSaveSQLite.setOnClickListener(
                view -> saveSQLite()
        );


        // =====================================================
        // LOAD SQLITE
        // =====================================================

        btnLoadSQLite.setOnClickListener(
                view -> loadSQLite()
        );
    }


    // =========================================================
    // LẤY STUDENT TỪ FORM
    // =========================================================

    private Student getStudentFromForm() {

        String id =
                edtId
                        .getText()
                        .toString()
                        .trim();

        String name =
                edtName
                        .getText()
                        .toString()
                        .trim();

        String email =
                edtEmail
                        .getText()
                        .toString()
                        .trim();

        String telephone =
                edtTelephone
                        .getText()
                        .toString()
                        .trim();

        return new Student(
                id,
                name,
                email,
                telephone
        );
    }


    // =========================================================
    // HIỂN THỊ STUDENT LÊN FORM
    // =========================================================

    private void showStudent(
            Student student
    ) {

        edtId.setText(
                student.getId()
        );

        edtName.setText(
                student.getName()
        );

        edtEmail.setText(
                student.getEmail()
        );

        edtTelephone.setText(
                student.getTelephone()
        );
    }


    // =========================================================
    // KIỂM TRA FORM
    // =========================================================

    private boolean validateForm() {

        if (edtId
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtId.setError(
                    "Please enter ID"
            );

            edtId.requestFocus();

            return false;
        }


        if (edtName
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtName.setError(
                    "Please enter name"
            );

            edtName.requestFocus();

            return false;
        }


        if (edtEmail
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtEmail.setError(
                    "Please enter email"
            );

            edtEmail.requestFocus();

            return false;
        }


        if (edtTelephone
                .getText()
                .toString()
                .trim()
                .isEmpty()) {

            edtTelephone.setError(
                    "Please enter telephone"
            );

            edtTelephone.requestFocus();

            return false;
        }


        return true;
    }


    // =========================================================
    // 1. SAVE SHARED PREFERENCES
    // =========================================================

    private void saveSharedPreferences() {

        if (!validateForm()) {
            return;
        }

        Student student =
                getStudentFromForm();


        SharedPreferences preferences =
                getSharedPreferences(
                        PREF_NAME,
                        MODE_PRIVATE
                );


        SharedPreferences.Editor editor =
                preferences.edit();


        editor.putString(
                "id",
                student.getId()
        );

        editor.putString(
                "name",
                student.getName()
        );

        editor.putString(
                "email",
                student.getEmail()
        );

        editor.putString(
                "telephone",
                student.getTelephone()
        );


        editor.apply();


        Toast.makeText(
                this,
                "Saved to SharedPreferences",
                Toast.LENGTH_SHORT
        ).show();
    }


    // =========================================================
    // 2. LOAD SHARED PREFERENCES
    // =========================================================

    private void loadSharedPreferences() {

        SharedPreferences preferences =
                getSharedPreferences(
                        PREF_NAME,
                        MODE_PRIVATE
                );


        String id =
                preferences.getString(
                        "id",
                        ""
                );

        String name =
                preferences.getString(
                        "name",
                        ""
                );

        String email =
                preferences.getString(
                        "email",
                        ""
                );

        String telephone =
                preferences.getString(
                        "telephone",
                        ""
                );


        if (id.isEmpty()) {

            Toast.makeText(
                    this,
                    "No SharedPreferences data",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        Student student =
                new Student(
                        id,
                        name,
                        email,
                        telephone
                );


        showStudent(student);


        Toast.makeText(
                this,
                "Loaded from SharedPreferences",
                Toast.LENGTH_SHORT
        ).show();
    }


    // =========================================================
    // 3. SAVE SQLITE
    // =========================================================

    private void saveSQLite() {

        if (!validateForm()) {
            return;
        }


        Student student =
                getStudentFromForm();


        boolean success =
                dbHelper.saveStudent(
                        student
                );


        if (success) {

            Toast.makeText(
                    this,
                    "Saved to SQLite",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Save SQLite failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // =========================================================
    // 4. LOAD SQLITE
    // =========================================================

    private void loadSQLite() {

        String id =
                edtId
                        .getText()
                        .toString()
                        .trim();


        /*
         * Load SQLite cần ID để biết
         * muốn lấy sinh viên nào.
         */
        if (id.isEmpty()) {

            edtId.setError(
                    "Enter ID to load from SQLite"
            );

            edtId.requestFocus();

            return;
        }


        Student student =
                dbHelper.getStudentById(
                        id
                );


        if (student == null) {

            Toast.makeText(
                    this,
                    "Student not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        showStudent(student);


        Toast.makeText(
                this,
                "Loaded from SQLite",
                Toast.LENGTH_SHORT
        ).show();
    }
}