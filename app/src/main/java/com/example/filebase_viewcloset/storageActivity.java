package com.example.filebase_viewcloset;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class storageActivity {

    private FirebaseStorage storage;
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();

    // Create a reference with an initial file path and name
    StorageReference pathReference = storageRef.child("images/stars.jpg");

    // Create a reference to a file from a Google Cloud Storage URI
    StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");

    // Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
    StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");


}
