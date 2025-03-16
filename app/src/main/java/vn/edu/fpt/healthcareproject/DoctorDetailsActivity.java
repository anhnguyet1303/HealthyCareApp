package vn.edu.fpt.healthcareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView tv;
    Button btn;

    private String[][] doctorDetails1 =
            {
                    {"Doctor Name: Sarah Lee", "Hospital Address: Kolkata", "Exp: 6yrs", "Mobile: 0945678901", "700"},
                    {"Doctor Name: David Miller", "Hospital Address: Hyderabad", "Exp: 4yrs", "Mobile: 0956789012", "550"},
                    {"Doctor Name: Laura Wilson", "Hospital Address: Pune", "Exp: 9yrs", "Mobile: 0967890123", "850"},
                    {"Doctor Name: James Taylor", "Hospital Address: Ahmedabad", "Exp: 12yrs", "Mobile: 0978901234", "1200"},
                    {"Doctor Name: Emma Brown", "Hospital Address: Jaipur", "Exp: 2yrs", "Mobile: 0989012345", "450"}
            };
    private String[][] doctorDetails2 =
            {
                    {"Doctor Name: Olivia Green", "Hospital Address: Lucknow", "Exp: 5yrs", "Mobile: 0990123456", "650"},
                    {"Doctor Name: William Harris", "Hospital Address: Kanpur", "Exp: 8yrs", "Mobile: 0911234567", "750"},
                    {"Doctor Name: Sophia Clark", "Hospital Address: Nagpur", "Exp: 11yrs", "Mobile: 0922345678", "950"},
                    {"Doctor Name: Daniel Lewis", "Hospital Address: Indore", "Exp: 3yrs", "Mobile: 0933456789", "500"},
                    {"Doctor Name: Mia Walker", "Hospital Address: Surat", "Exp: 7yrs", "Mobile: 0944567890", "800"}
            };
    private String[][] doctorDetails3 =
            {
                    {"Doctor Name: Ethan Martinez", "Hospital Address: Bhopal", "Exp: 6yrs", "Mobile: 0955678901", "700"},
                    {"Doctor Name: Ava Robinson", "Hospital Address: Patna", "Exp: 4yrs", "Mobile: 0966789012", "550"},
                    {"Doctor Name: Noah Hall", "Hospital Address: Vadodara", "Exp: 9yrs", "Mobile: 0977890123", "850"},
                    {"Doctor Name: Isabella Young", "Hospital Address: Coimbatore", "Exp: 12yrs", "Mobile: 0988901234", "1200"},
                    {"Doctor Name: Liam King", "Hospital Address: Visakhapatnam", "Exp: 2yrs", "Mobile: 0999012345", "450"}
            };
    private String[][] doctorDetails4 =
            {
                    {"Doctor Name: Charlotte Scott", "Hospital Address: Kochi", "Exp: 5yrs", "Mobile: 0910123456", "600"},
                    {"Doctor Name: Benjamin Adams", "Hospital Address: Thiruvananthapuram", "Exp: 7yrs", "Mobile: 0921234567", "800"},
                    {"Doctor Name: Amelia Green", "Hospital Address: Ludhiana", "Exp: 10yrs", "Mobile: 0932345678", "1000"},
                    {"Doctor Name: Lucas Baker", "Hospital Address: Agra", "Exp: 3yrs", "Mobile: 0943456789", "500"},
                    {"Doctor Name: Harper Nelson", "Hospital Address: Varanasi", "Exp: 8yrs", "Mobile: 0954567890", "900"}
            };
    private String [][]doctorDetails5 =
            {
                    {"Doctor Name: John Smith","Hospital Address: Pimpri","Exp: 5yrs","Mobile: 0978235682", "600"},
                    {"Doctor Name: Alice Johnson", "Hospital Address: Mumbai", "Exp: 7yrs", "Mobile: 0987654321", "800"},
                    {"Doctor Name: Robert Brown","Hospital Address: Delhi","Exp: 10yrs","Mobile: 0912345678", "1000"},
                    {"Doctor Name: Emily Davis","Hospital Address: Bangalore","Exp: 3yrs","Mobile: 0923456789","500"},
                    {"Doctor Name: Michael Wilson", "Hospital Address: Chennai", "Exp: 8yrs", "Mobile: 0934567890", "900"}
            };
    String[][] doctorDetails ={};
    ArrayList list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.texViewDDTitle);
        btn = findViewById(R.id.buttonDDBack);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Family Physician")==0){
            doctorDetails = doctorDetails1;
        }
        else if(title.compareTo("Dietician")==0){
            doctorDetails = doctorDetails2;
        }
        else if(title.compareTo("Dentist")==0){
            doctorDetails = doctorDetails3;
        }
        else if(title.compareTo("Surgeon")==0){
            doctorDetails = doctorDetails4;
        }
        else{
            doctorDetails = doctorDetails5;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });
        list = new ArrayList<>();
        for(int i = 0; i<doctorDetails.length; i++ ){
            item = new HashMap<String,String>();
            item.put("line1",doctorDetails[i][0]);
            item.put("line2",doctorDetails[i][1]);
            item.put("line3",doctorDetails[i][2]);
            item.put("line4",doctorDetails[i][3]);
            item.put("line5","Cons Fees: "+doctorDetails[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1",title);
                it.putExtra("text2", doctorDetails[i][0]);
                it.putExtra("text3", doctorDetails[i][1]);
                it.putExtra("text4", doctorDetails[i][3]);
                it.putExtra("text5", doctorDetails[i][4]);
                startActivity(it);
            }
        });
    }
}