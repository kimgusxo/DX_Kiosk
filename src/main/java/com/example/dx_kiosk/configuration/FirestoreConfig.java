package com.example.dx_kiosk.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirestoreConfig {

    @Bean
    public Firestore firestore() throws IOException {
        // resources 디렉토리에서 서비스 계정 파일 로드
        InputStream serviceAccount = getClass()
                .getClassLoader()
                .getResourceAsStream("firebase-service-account.json");

        if (serviceAccount == null) {
            throw new IllegalStateException("firebase-service-account.json 파일을 찾을 수 없습니다.");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build(); // databaseURL 없이 Firestore만 사용 가능

        // Firebase 앱 초기화
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        return FirestoreClient.getFirestore();
    }

}
