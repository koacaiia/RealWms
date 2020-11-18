package org.techtown.realwms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureExam extends AppCompatActivity {
    File file;
    String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_exam);

        Button pic_exam=findViewById(R.id.pic_exam);
        pic_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePicture();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public File takePicture() throws IOException {
        if(file==null){
            File stroagrDir= Environment.getExternalStorageDirectory();
            String fileName="test";
            file=File.createTempFile(fileName,".jpg" , new File(stroagrDir + "/FINE"));}
        Uri uri= FileProvider.getUriForFile(this,"org.techtown.realwms.provider",file);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        filepath=file.getAbsolutePath();
        startActivityForResult(intent,101);
        return file;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101&&resultCode==RESULT_OK){
            Toast.makeText(this, filepath, Toast.LENGTH_SHORT).show();
            Bitmap bitmap= BitmapFactory.decodeFile(filepath);
            Bitmap dest=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
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
            cs.drawText(timeStamp, 20f, height + 180f, tPaint);
            final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
            final File myDir = new File(strSDpath + "/Fine");
                      if(!myDir.exists()){
                myDir.mkdirs();}
            try {
                dest.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(new File(myDir.getPath()+"/"+timeStamp+".jpg")));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + myDir.getPath() + "/" + timeStamp + ".jpg")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        file.delete();

    }
}
