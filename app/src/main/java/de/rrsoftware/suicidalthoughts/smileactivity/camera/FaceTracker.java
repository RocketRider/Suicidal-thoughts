package de.rrsoftware.suicidalthoughts.smileactivity.camera;

import android.util.Log;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class FaceTracker extends Tracker<Face> {

    /**
     * Start tracking the detected face
     */
    @Override
    public void onNewItem(int faceId, Face item) {
        //TODO
    }

    /**
     * Update the position/characteristics of the face
     */
    @Override
    public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
        //TODO
        Log.e("Test", detectionResults.toString());
    }

    /**
     * face was not detected. This can happen for
     * intermediate frames temporarily (e.g., if the face was momentarily blocked from
     * view).
     */
    @Override
    public void onMissing(FaceDetector.Detections<Face> detectionResults) {
        //TODO
    }

    /**
     * Called when the face is assumed to be gone for good.
     */
    @Override
    public void onDone() {
        //TODO
    }
}