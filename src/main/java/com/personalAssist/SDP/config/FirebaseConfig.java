package com.personalAssist.SDP.config;

import java.io.FileInputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {
	
	@PostConstruct
	public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service-account.json");

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Initialized");
            }

        } catch (Exception e) {
        	System.err.println("❌ Firebase init failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
