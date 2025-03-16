package vn.edu.fpt.healthcareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton,timeButton,btnBooking,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextAppFullName);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppContactNumber);
        ed4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBooking = findViewById(R.id.buttonBookAppointment);
        btnBack = findViewById(R.id.buttonBack);

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Cons Fees: "+fees);

        //datePicker
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        //timePicker
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class));
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các trường nhập liệu
                String fullName = ed1.getText().toString();
                String address = ed2.getText().toString();
                String contactNumber = ed3.getText().toString();
                String fees = ed4.getText().toString();
                String selectedDate = dateButton.getText().toString();
                String selectedTime = timeButton.getText().toString();

                // Kiểm tra xem các trường có được nhập đầy đủ không
                if (fullName.isEmpty() || address.isEmpty() || contactNumber.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                    // Hiển thị thông báo lỗi nếu thiếu thông tin
                    showErrorDialog("Vui lòng điền đầy đủ thông tin.");
                    return;
                }

                // Lưu thông tin đặt lịch vào cơ sở dữ liệu
                saveAppointment(fullName, address, contactNumber, selectedDate, selectedTime, fees);
            }
        });
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1+1;
                dateButton.setText(i2+"/"+i1+"/"+"/"+i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }
    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeButton.setText(i+":"+i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins =cal.get(Calendar.MINUTE);
        int style =AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this,style, timeSetListener,hrs,mins,true);
    }

    // Phương thức lưu thông tin đặt lịch
    private void saveAppointment(String fullName, String address, String contactNumber, String selectedDate, String selectedTime, String fees) {
        // Khởi tạo đối tượng Database
        Database db = new Database(this, "healthcare", null, 1);

        // Lấy username của người dùng hiện tại (giả sử username được lưu trong SharedPreferences)
        String username = getSharedPreferences("shared_prefs", MODE_PRIVATE).getString("username", "");

        // Lưu thông tin đặt lịch vào cơ sở dữ liệu
        db.addAppointment(username, fullName, address, contactNumber, selectedDate, selectedTime, fees);

        // Hiển thị thông báo thành công
        showSuccessDialog("Đặt lịch thành công!");
    }

    // Phương thức hiển thị thông báo lỗi
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Lỗi") // Tiêu đề của dialog
                .setMessage(message) // Nội dung thông báo
                .setPositiveButton("OK", null) // Nút OK để đóng dialog
                .show(); // Hiển thị dialog
    }

    // Phương thức hiển thị thông báo thành công
    private void showSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Thành công") // Tiêu đề của dialog
                .setMessage(message) // Nội dung thông báo
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Chuyển hướng về màn hình chính (HomeActivity)
                        startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
                        finish(); // Đóng màn hình hiện tại
                    }
                })
                .show(); // Hiển thị dialog
    }
}