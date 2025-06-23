package com.example.landfinder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText etName, etPhone, etDate, etTime;
    Button btnConfirm;
    DatabaseHelper dbHelper;
    int propertyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etDate = findViewById(R.id.et_date);
        etTime = findViewById(R.id.et_time);
        btnConfirm = findViewById(R.id.btn_confirm_appointment);
        dbHelper = new DatabaseHelper(this);

        propertyId = getIntent().getIntExtra("property_id", -1);

        etDate.setOnClickListener(view -> openDatePicker());
        etTime.setOnClickListener(view -> openTimePicker());

        btnConfirm.setOnClickListener(view -> saveAppointment());
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            etTime.setText(hourOfDay + ":" + (minute < 10 ? "0" + minute : minute));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePicker.show();
    }

    private void saveAppointment() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property_id", propertyId);
        values.put("name", name);
        values.put("phone", phone);
        values.put("date", date);
        values.put("time", time);

        long result = db.insert("appointments", null, values);
        if (result != -1) {
            Toast.makeText(this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to book appointment", Toast.LENGTH_SHORT).show();
        }
    }
}
