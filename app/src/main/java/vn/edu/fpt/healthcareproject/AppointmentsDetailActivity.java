package vn.edu.fpt.healthcareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AppointmentsDetailActivity extends AppCompatActivity {

    private ListView listViewAppointments;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_detail);

        listViewAppointments = findViewById(R.id.listViewAppointments);
        db = new Database(this, "healthcare", null, 1);

        // Lấy username từ Intent (giả sử username được truyền từ màn hình trước)
        String username = getIntent().getStringExtra("username");

        if (username != null) {
            // Lấy danh sách các cuộc hẹn của người dùng
            Cursor cursor = db.getAppointmentsByUsername(username);

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Không có cuộc hẹn nào.", Toast.LENGTH_SHORT).show();
            } else {
                // Hiển thị danh sách cuộc hẹn trong ListView
                String[] fromColumns = {"fullname", "date", "time", "fees"};
                int[] toViews = {R.id.textViewFullName, R.id.textViewDate, R.id.textViewTime, R.id.textViewFees};
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        this,
                        R.layout.list_item_appointment,
                        cursor,
                        fromColumns,
                        toViews,
                        0
                );
                listViewAppointments.setAdapter(adapter);
            }
        } else {
            Toast.makeText(this, "Lỗi: Không tìm thấy người dùng.", Toast.LENGTH_SHORT).show();
        }
    }
}