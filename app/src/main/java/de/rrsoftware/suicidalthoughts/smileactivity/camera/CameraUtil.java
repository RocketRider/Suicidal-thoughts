package de.rrsoftware.suicidalthoughts.smileactivity.camera;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.smileactivity.SmileActivity;

public class CameraUtil {
    private static final String LOGTAG = "CameraUtil";
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static final int RC_HANDLE_GMS = 9001;

    private final SmileActivity activity;
    private CameraSource cameraSource;
    private final CameraView view;

    public CameraUtil(final SmileActivity activity, final CameraView view) {
        this.activity = activity;
        this.view = view;
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    public void requestCameraPermission() {
        Log.w(LOGTAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(activity, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(view, R.string.permission_camera,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }


    private FaceDetector createFaceDetector() {
        Context context = activity.getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new FaceTrackerFactory(activity))
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(LOGTAG, "Face detector dependencies are not yet available.");
        }
        return detector;
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    public void createCameraSource(final boolean tryFrontCam) {

        Context context = activity.getApplicationContext();
        FaceDetector detector = createFaceDetector();

        final CameraSource.Builder builder = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setRequestedFps(30.0f)
                .setAutoFocusEnabled(true);

        //prefer the front camera
        if (tryFrontCam) {
            builder.setFacing(CameraSource.CAMERA_FACING_FRONT);
        } else {
            builder.setFacing(CameraSource.CAMERA_FACING_BACK);
        }
        cameraSource = builder.build();

        if (tryFrontCam) {
            try {
                cameraSource.start();
                cameraSource.stop();
            } catch (SecurityException e) {
                Log.e(LOGTAG, "No permission!");
            } catch (Exception e) {
                //If the front cam does not work create back cam!
                cameraSource.stop();
                cameraSource.release();
                detector.release();
                cameraSource = null;
                createCameraSource(false);
            }
        }
    }


    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    public void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                activity.getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(activity, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                view.start(cameraSource);
            } catch (IOException e) {
                Log.e(LOGTAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    public void release() {
        if (cameraSource != null) {
            cameraSource.release();
            cameraSource = null;
        }
    }

    public boolean onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(LOGTAG, "Got unexpected permission result: " + requestCode);
            return false;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(LOGTAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT));
            return true;
        }

        Log.e(LOGTAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        return false;
    }

}
