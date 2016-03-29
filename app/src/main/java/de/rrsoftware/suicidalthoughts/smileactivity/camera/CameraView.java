package de.rrsoftware.suicidalthoughts.smileactivity.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

public class CameraView extends ViewGroup {
    private static final String LOGTAG = "CameraView";

    private final Context context;
    private final SurfaceView surfaceView;
    private boolean startRequested;
    private boolean surfaceAvailable;
    private CameraSource cameraSource;


    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        startRequested = false;
        surfaceAvailable = false;

        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(surfaceView);
    }

    public void start(CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {
            stop();
        }
        this.cameraSource = cameraSource;
        requestLayout();

        if (this.cameraSource != null) {
            startRequested = true;
            startIfReady();
        }
    }

    public void stop() {
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    private void startIfReady() throws IOException {
        if (startRequested && surfaceAvailable) {
            final int rc = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            if (rc == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
                startRequested = false;
            }
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surface) {
            surfaceAvailable = true;
            try {
                startIfReady();
            } catch (IOException e) {
                Log.e(LOGTAG, "Could not start camera source.", e);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surface) {
            surfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int height = parentHeight / 2;
        int cameraWidth = 320;
        int cameraHeight = 240;
        if (cameraSource != null) {
            Size size = cameraSource.getPreviewSize();
            if (size != null) {
                cameraWidth = size.getWidth();
                cameraHeight = size.getHeight();
            }
        }
        if (!isPortraitMode()) {
            final int temp = cameraWidth;
            cameraWidth = cameraHeight;
            cameraHeight = temp;
        }


        final int width = (int) (((float) cameraHeight) / cameraWidth * height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).layout(0, 0, width, height);
        }

        try {
            startIfReady();
        } catch (IOException e) {
            Log.e(LOGTAG, "Could not start camera source.", e);
        }
    }


    private boolean isPortraitMode() {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }

        Log.d(LOGTAG, "isPortraitMode returning false by default");
        return false;
    }
}
