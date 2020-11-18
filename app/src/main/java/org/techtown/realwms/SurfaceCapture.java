package org.techtown.realwms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SurfaceCapture extends AppCompatActivity implements AutoPermissionsListener {
    CameraSurfaceView cameraView;
    private Camera camera = null;
    private Button button7;
    String consignee;
    String bl;
    String cont;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_capture);
        Bundle extras=getIntent().getExtras();

        consignee=extras.getString("consignee");
        bl=extras.getString("bl");
        cont=extras.getString("cont");

        FrameLayout previewFrame = findViewById(R.id.previewFrame);
        cameraView = new CameraSurfaceView(this);
        previewFrame.addView(cameraView);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takepicture();
            }
        });


        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }

    public void takepicture() {
      cameraView.capture(new Camera.PictureCallback() {
      public void onPictureTaken(byte[] data, Camera camera) {

          int w = camera.getParameters().getPictureSize().width;
          int h = camera.getParameters().getPictureSize().height;
          WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
          int rotation = manager.getDefaultDisplay().getRotation();
          Matrix matrix = new Matrix();
          matrix.postRotate(90);

          Bitmap src = BitmapFactory.decodeByteArray(data, 0, data.length);

//         Bitmap dest = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);


         Bitmap dest=  Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
          //Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

//          Bitmap dest = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(60);
        tPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tPaint.setColor(Color.BLACK);
        tPaint.setStyle(Paint.Style.FILL);
        cs.drawBitmap(src, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        cs.drawText(timeStamp, 20f, height+20f, tPaint);
        cs.drawText(consignee, 20f, height+100f, tPaint);
        cs.drawText(cont, 20f, height+180f, tPaint);
        cs.drawText(bl, 20f, height+260f, tPaint);
        cs.drawText("입고사진", 20f, height+340f, tPaint);

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final File myDir = new File(strSDpath + "/Fine");

        if (!myDir.exists())
        {  myDir.mkdirs();  }

        try {
            dest.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(myDir.getPath() + "/" + timeStamp + ".jpg")));
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ myDir.getPath() + "/" + timeStamp+ ".jpg")));
            //SurfaceCapture.toast message(SurfaceCapture.this, "IMG SAVE :: " + stDir + timeStamp + ".png");
            camera.startPreview();
        } catch (FileNotFoundException e)
        {
           e.printStackTrace();
        }

    }});
        }
    public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();
            setCameraOrientation();
            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mHolder.getSurface() == null) {
                return;
            }
            try {
                camera.stopPreview();
            } catch (Exception e) {
            }
            try {
                camera.setPreviewDisplay(mHolder);
                camera.startPreview();
            } catch (Exception e) {

            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
                  }

        public void capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
            } else {
            }
        }

        public void setCameraOrientation() {
            if (camera == null) {
                return;
            }

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();

            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0: degrees = 0; break;
                case Surface.ROTATION_90: degrees = 90; break;
                case Surface.ROTATION_180: degrees = 180; break;
                case Surface.ROTATION_270: degrees = 270; break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            camera.setDisplayOrientation(result);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

}
