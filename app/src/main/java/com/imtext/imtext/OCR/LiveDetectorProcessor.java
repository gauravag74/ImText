package com.imtext.imtext.OCR;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

public class LiveDetectorProcessor implements Detector.Processor<TextBlock> {

    private BoundingBoxes<LiveDetections> mBoundingBoxes;

    LiveDetectorProcessor(BoundingBoxes<LiveDetections> ocrBoundingBoxes) {
        mBoundingBoxes = ocrBoundingBoxes;
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mBoundingBoxes.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            LiveDetections graphic = new LiveDetections(mBoundingBoxes, item);
            mBoundingBoxes.add(graphic);
        }
    }

    @Override
    public void release() {
        mBoundingBoxes.clear();
    }
}
