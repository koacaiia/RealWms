package org.techtown.realwms;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class SurfaceView_Wms extends AppCompatActivity implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view__wms);
        Button buttonStartCameraPreview=findViewById(R.id.startcamerapreview);
        Button buttonStopCameraPreview=findViewById(R.id.stopcamerapreview);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        SurfaceView surfaceView=findViewById(R.id.surfaceview);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        buttonStartCameraPreview.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!previewing){
                    camera=Camera.open();
                    try {
                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();
                        previewing=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonStopCameraPreview.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera !=null&&previewing){
                    camera.stopPreview();
                    camera.release();
                    camera=null;
                    previewing=false;
                }
            }
        });


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
