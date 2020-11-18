package org.techtown.realwms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Cargo> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    CargoAdapter cargoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Cargo");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cargo cargo = snapshot.getValue(Cargo.class);
                    arrayList.add(cargo);
                }
                cargoAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException()));

            }
        });




        cargoAdapter = new CargoAdapter(arrayList, this);
        recyclerView.setAdapter(cargoAdapter);
        textView = findViewById(R.id.textView);
        textView.setText("입고 예정건수" + "   " + arrayList.size());

        cargoAdapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(CargoAdapter.ViewHolder holder, View view, int position) {

                Cargo item = cargoAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getBl(), Toast.LENGTH_LONG).show();

                Intent intent=new Intent(MainActivity.this,IncargoInformations.class);

                intent.putExtra("consignee",item.getConsignee());
                intent.putExtra("cargo_v",item.getCargo_v());
                intent.putExtra("pqty",item.getPqty());
                intent.putExtra("qty",item.getQty());
                intent.putExtra("bl",item.getBl());
                intent.putExtra("des",item.getDes());
                intent.putExtra("cont",item.getCont());
                intent.putExtra("seal",item.getSeal());

                startActivity(intent);}





        });





        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Equipments.class);
                startActivity(intent);

                }

            });
        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Etc.class);
                startActivity(intent);
            }
        });
        Button button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,OutSourcing.class);
               startActivity(intent);
            }
        });
        Button exam_pic=findViewById(R.id.exam_pic);
        exam_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CaptureExam.class);
                startActivity(intent);
            }
        });

        }
    }
