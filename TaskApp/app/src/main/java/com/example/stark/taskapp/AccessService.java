package com.example.stark.taskapp;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

public class AccessService extends AccessibilityService {

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db;
    static int count=0;
    LocationTrack locationTrack;
    double longitude = 0.00;
    double latitude = 0.00;
    String check;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        //String uid = admin.firestore().collection().id;

        db = FirebaseFirestore.getInstance();

        accessibilityEvent.getPackageName();
        Log.d("check1", accessibilityEvent.getPackageName().toString());
        String docId = String.valueOf(count);

        DocumentReference docRef = db.collection("Packages").document(docId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        check = document.getData().get("package name").toString();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        if(!accessibilityEvent.getPackageName().toString().equals(check))
        {
            count++;
        }


        locationTrack = new LocationTrack(AccessService.this);


        if (locationTrack.canGetLocation()) {

            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

        }

        Map<String, Object> pack = new HashMap<>();
        pack.put("package name", accessibilityEvent.getPackageName().toString());
        pack.put("latitude", latitude);
        pack.put("longitude", longitude);
        pack.put("confirm", true);

        db.collection("Packages").document(docId)
                .set(pack)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    @Override
    public void onInterrupt() {

    }
}
