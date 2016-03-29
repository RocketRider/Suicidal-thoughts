package de.rrsoftware.suicidalthoughts.smileactivity.camera;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

import de.rrsoftware.suicidalthoughts.smileactivity.SmileActivity;

/**
 * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
 * uses this factory to create face trackers as needed -- one for each individual.
 */
public class FaceTrackerFactory implements MultiProcessor.Factory<Face> {
    private final SmileActivity activity;

    public FaceTrackerFactory(final SmileActivity activity) {
        this.activity = activity;
    }

    @Override
    public Tracker<Face> create(Face face) {
        return new FaceTracker(activity);
    }
}