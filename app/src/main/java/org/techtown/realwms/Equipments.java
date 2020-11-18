package org.techtown.realwms;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Equipments extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;
    File image;
    CheckBox checkBox;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    RadioButton radioButton, radioButton2;
    String name;
    File file;
    String timeStamp;
    TextView textView;
    RecyclerView recyclerView;
    Picture_FineAdapter adapter;
    int pictureCount = 0;
    ArrayList<Picture_Fine> pictures;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipments);
        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.container);
        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Picture_FineAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnPictureItemClickListener() {
            @Override
            public void onItemClick(Picture_FineAdapter.ViewHolder holder, View view, int position) {
                Picture_Fine item = adapter.getItem(position);}
        });
        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Picture_Fine> result = queryAllPictures();
                adapter.setItems(result);
                adapter.notifyDataSetChanged();}
        });
        Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShareIntent();}});
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);

        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        Button button11=findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {photoFile = createFile();} catch (IOException e) {}
                    if (photoFile != null) {
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "org.techtown.realwms.provider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);}}
                String str_radio = "";//String 값 초기화
                if (radioButton.isChecked()) {str_radio += radioButton.getText().toString(); }
                if (radioButton2.isChecked()) {str_radio += radioButton2.getText().toString();}
                String str_check = "";//String 값 초기화
                if (checkBox.isChecked()) {str_check += checkBox.getText().toString();}
                if (checkBox2.isChecked()) {str_check += checkBox2.getText().toString();}
                if (checkBox3.isChecked()) {str_check += checkBox3.getText().toString();}
                if (checkBox4.isChecked()) {str_check += checkBox4.getText().toString();}
                if (checkBox5.isChecked()) {str_check += checkBox5.getText().toString();}
                if (checkBox6.isChecked()) {str_check += checkBox6.getText().toString();}
                if (checkBox7.isChecked()) {str_check += checkBox7.getText().toString();}
                Toast.makeText(getApplicationContext(), "  " + str_radio + "   " + str_check + "    촬영", Toast.LENGTH_SHORT).show();
                name = str_check + str_radio;
            }
        });}
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
        for (Picture_Fine info : result) {Log.d("MainActivity", info.toString()); }
        return result; }
    public File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile( imageFileName,".jpg", storageDir );

        imageFilePath = image.getAbsolutePath();
        return image;}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            Canvas cs = new Canvas(dest);
            Paint tPaint = new Paint();
            tPaint.setTextSize(120);
            tPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tPaint.setColor(Color.BLACK);
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

            }}
        image.delete();}
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
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show(); }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show(); }
    };
        public void getShareIntent() {
            ArrayList<Uri> uris = new ArrayList<Uri>();
            for (Object file : adapter.mCheckedList) {
                    File fileName=new File(String.valueOf(file));
                    Uri uri1=FileProvider.getUriForFile(getApplicationContext(), "org.techtown.realwms.provider", fileName);
                    uris.add(uri1);}
                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE); //전송 메소드를 호출합니다. Intent.ACTION_SEND
                    intent.setType("image/*"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris); //사진의 Uri를 가지고 옵니다.
                    startActivity(Intent.createChooser(intent, "Choose")); //Activity를 이용하여 호출 합니다.
                }

        }
