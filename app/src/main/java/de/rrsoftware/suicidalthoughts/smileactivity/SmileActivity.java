package de.rrsoftware.suicidalthoughts.smileactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.questions.ui.QuestionsActivity;
import de.rrsoftware.suicidalthoughts.smileactivity.camera.CameraUtil;
import de.rrsoftware.suicidalthoughts.smileactivity.camera.CameraView;

public class SmileActivity extends AppCompatActivity {
    private CameraView cameraView;
    private TextView smileText;
    private CameraUtil util;
    private int smileStage = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile);
        smileText = (TextView) findViewById(R.id.textBottom);

        cameraView = (CameraView) findViewById(R.id.cameraView);
        util = new CameraUtil(this, cameraView);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        final int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            util.createCameraSource(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT));
        } else {
            util.requestCameraPermission();
        }
    }


    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        final int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            util.startCameraSource();
        }
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        util.release();
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final boolean result = util.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!result) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Face Tracker sample")
                    .setMessage(R.string.no_camera_permission)
                    .setPositiveButton(R.string.ok, listener)
                    .show();
        }
    }

    public void setSmileProbability(final float probability) {
        if (smileStage == 0 && probability > 0.2) {
            smileStage = 1;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    smileText.setText(R.string.smile_bottom_2);
                }
            });
        }
        if (smileStage == 1 && probability > 0.5) {
            smileStage = 2;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    smileText.setText(R.string.smile_bottom_3);
                }
            });
        }
        if (probability > 0.7) {
            smileStage = 3;
            done();
        }
    }

    private void done() {
        Intent intent = new Intent(this, QuestionsActivity.class);
        startActivity(intent);
        finish();
    }

}
