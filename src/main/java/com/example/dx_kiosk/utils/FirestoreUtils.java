package com.example.dx_kiosk.utils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirestoreUtils {

    @Autowired
    private Firestore firestore;

    public String saveData(String collectionName, String documentId, Map<String, Object> data) {
        try {
            DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
            ApiFuture<WriteResult> writeResult = documentReference.set(data);
            return writeResult.get().getUpdateTime().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getData(String collectionName, String documentId) {
        try {
            DocumentReference documentReference = firestore.collection(collectionName).document(documentId);
            return documentReference.get().get().getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
