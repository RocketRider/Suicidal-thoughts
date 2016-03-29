package de.rrsoftware.suicidalthoughts.smileactivity.camera;

import android.util.Log;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import de.rrsoftware.suicidalthoughts.smileactivity.SmileActivity;

public class FaceTracker extends Tracker<Face> {
    private final SmileActivity activity;

    public FaceTracker(final SmileActivity activity) {
        this.activity = activity;
    }

    /**
     * Update the position/characteristics of the face
     */
    @Override
    public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
        activity.setSmileProbability(face.getIsSmilingProbability());
    }

}