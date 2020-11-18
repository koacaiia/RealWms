package org.techtown.realwms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IncargoInformations extends AppCompatActivity {
    TextView textView12;
    TextView textView14;
    TextView textView16;
    TextView textView18;
    TextView textView20;
    TextView textView22;
    TextView textView24;
    TextView textView26;
    String consignee;
    String cargo_v;
    String pqty;
    String qty;
    String bl;
    String des;
    String cont;
    String seal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incargo_informations);

//         String consignee="";
//         String cargo_v="";
//         String pqty="";
//         String qty="";
//         String bl="";
//         String des="";
//         String cont="";
//         String seal="";

         Bundle extras=getIntent().getExtras();
         consignee=extras.getString("consignee");
         cargo_v=extras.getString("cargo_v");
         pqty=extras.getString("pqty");
         qty=extras.getString("qty");
         bl=extras.getString("bl");
         des=extras.getString("des");
         cont=extras.getString("cont");
         seal=extras.getString("seal");

        textView12 = findViewById(R.id.textView12);
        textView14 = findViewById(R.id.textView14);
        textView16 = findViewById(R.id.textView16);
        textView18 = findViewById(R.id.textView18);
        textView20 = findViewById(R.id.textView20);
        textView22 = findViewById(R.id.textView22);
        textView24 = findViewById(R.id.textView24);
        textView26 = findViewById(R.id.textView26);
        textView12.setText(consignee);
        textView14.setText(cargo_v);
        textView16.setText(des);
        textView18.setText(cont);
        textView20.setText(seal);
        textView22.setText(bl);
        textView24.setText(pqty);
        textView26.setText(qty);

        Button button4=findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IncargoInformations.this,SurfaceCapture.class);

                intent.putExtra("consignee",consignee);
                intent.putExtra("cont",cont);
                intent.putExtra("bl",bl);
//                intent.putExtra("cargo_v",cargo_v);
//                intent.putExtra("pqty",pqty);
//                intent.putExtra("qty",qty);
//                intent.putExtra("des",des);
//                intent.putExtra("seal",seal);
                startActivity(intent);




            }

        });

        Button button6=findViewById(R.id.button6);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IncargoInformations.this,SurfaceView_Wms.class);

                intent.putExtra("consignee",consignee);
                intent.putExtra("cont",cont);
                intent.putExtra("bl",bl);
//                intent.putExtra("cargo_v",cargo_v);
//                intent.putExtra("pqty",pqty);
//                intent.putExtra("qty",qty);
//                intent.putExtra("des",des);
//                intent.putExtra("seal",seal);
                startActivity(intent);




            }

        });




    }
}
