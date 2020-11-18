package org.techtown.realwms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class OutSourcing extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE =101 ;
    String name;
    private String imageFilePath;
    TextView textView30;
    TextView textView31;
    Integer[] items={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    CheckBox checkBox10;
    CheckBox checkBox9;
    EditText editText2;
    RecyclerView recyclerView;
    Picture_FineAdapter madapter;
    int pictureCount = 0;
    ArrayList<Picture_Fine> pictures;
    String timeStamp = new SimpleDateFormat("yyyy_MM_dd,E요일").format(new Date());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_out_sourcing);
         recyclerView = findViewById(R.id.container2);
         LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
         recyclerView.setLayoutManager(layoutManager);
         madapter = new Picture_FineAdapter();
         recyclerView.setAdapter(madapter);


            textView30=findViewById(R.id.textView30);
            textView31=findViewById(R.id.textView31);
            spinner1=findViewById(R.id.spinner1);
            spinner2=findViewById(R.id.spinner2);
            spinner3=findViewById(R.id.spinner3);
            checkBox9=findViewById(R.id.checkBox9);
            checkBox10=findViewById(R.id.checkBox10);
            editText2=findViewById(R.id.editText2);
        ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(
                this,android.R.layout.simple_spinner_item,items );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner1.setTag(items[position]);
                                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner1.setTag("");
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner2.setTag(items[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner1.setTag("");
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner3.setTag(items[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner1.setTag("");
            }
        });
        Button button14=findViewById(R.id.button14);
               button14.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String str_check9="";
                String str_check10="";
                if(checkBox9.isChecked()){str_check9+=checkBox9.getText().toString();}
                if(checkBox10.isChecked()){str_check10+=checkBox10.getText().toString();}
                int sp1=spinner1.getSelectedItem().hashCode();
                int sp2=spinner2.getSelectedItem().hashCode();
                int sp3=spinner3.getSelectedItem().hashCode();
                String ed2="";
                ed2=editText2.getText().toString();
                int tTl=sp1+sp2+sp3;
                String to=Integer.toString(tTl);
                textView31.setText(timeStamp+"  용역출근 현황"+"("+"총"+to+"명 출근 )");
                textView30.setText(str_check9+""+sp1+"("+"명"+")"+","+str_check10+""+sp2+"("+"명"+")"+
                        ed2+""+sp3+"("+"명"+")");
                }});

               Button button10=findViewById(R.id.button10);
               button10.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       if (intent.resolveActivity(getPackageManager()) != null) {
                           File photoFile = null;
                           try {
                               photoFile = createImageFile();
                           } catch (IOException e) {
                           }
                           if (photoFile != null) {
                               Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "org.techtown.realwms.provider", photoFile);
                               intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                               startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);}
                       }
                   }});
              Button button12=findViewById(R.id.button12);
         button12.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ArrayList<Picture_Fine> result = queryAllPictures();
                 madapter.setItems(result);
                 madapter.notifyDataSetChanged();}
         });
         Button button13=findViewById(R.id.button13);
         button13.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getShareIntent();}});
}

    private void getShareIntent() {
        ArrayList<Uri> uris = new ArrayList<Uri>();
        for (Object file : madapter.mCheckedList) {
            File fileName=new File(String.valueOf(file));
            Uri uri1=FileProvider.getUriForFile(getApplicationContext(), "org.techtown.realwms.provider", fileName);
            uris.add(uri1);}
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE); //전송 메소드를 호출합니다. Intent.ACTION_SEND
        intent.setType("image/*"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris); //사진의 Uri를 가지고 옵니다.
        startActivity(Intent.createChooser(intent, "Choose")); //Activity를 이용하여 호출 합니다.
    }

    private ArrayList<Picture_Fine> queryAllPictures() {
        ArrayList<Picture_Fine> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);
        for (int i = 0; i < 30; i++) {
            cursor.moveToNext();
            String path = cursor.getString(columnDataIndex);
            String displayName = cursor.getString(columnNameIndex);
            String outDate = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue() * 1000L));
            if (!TextUtils.isEmpty(path)) {
                Picture_Fine info = new Picture_Fine(path, displayName, addedDate);
                result.add(info);}
            pictureCount++;}
        for (Picture_Fine info : result) {
            Log.d("MainActivity", info.toString()); }
        return result; }
    private File createImageFile() throws IOException{
         String imageFileName=timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir );
        imageFilePath = image.getAbsolutePath();
        return image;}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            android.media.ExifInterface exif = null;
            try {exif = new android.media.ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();}
            int exifOrientation;
            int exifDegree;
            if (exif != null) {
                exifOrientation = exif.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, android.media.ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else { exifDegree = 0; }
            bitmap = (rotate(bitmap, exifDegree));
            Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String name;
            name=textView30.getText().toString();
            Canvas cs = new Canvas(dest);
            Paint tPaint = new Paint();
            tPaint.setTextSize(120);
            tPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tPaint.setColor(Color.WHITE);
            tPaint.setStyle(Paint.Style.FILL);
            cs.drawBitmap(bitmap, 0f, 0f, null);
            float height = tPaint.measureText("yY");
            cs.drawText(timeStamp, 20f, height + 20f, tPaint);

            cs.drawText(name, 20f, height + 180f, tPaint);
            final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
            final File myDir = new File(strSDpath + "/Fine");
            if (!myDir.exists()) {
                myDir.mkdirs(); }
            try {
                dest.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(myDir.getPath() + "/" + name + "(" + timeStamp + ")" + ".jpg")));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + myDir.getPath() + "/" + name + "(" + timeStamp + ")" + ".jpg")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }}}
    private int exifOrientationToDegress(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
