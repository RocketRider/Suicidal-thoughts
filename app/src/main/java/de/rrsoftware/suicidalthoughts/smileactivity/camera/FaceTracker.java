package de.rrsoftware.suicidalthoughts.smileactivity.camera;

import android.util.Log;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class FaceTracker extends Tracker<Face> {

    /**
     * Update the position/characteristics of the face
     */
    @Override
    public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
        //TODO
        Log.e("Test", String.valueOf(face.getIsSmilingProbability()));
    }

}