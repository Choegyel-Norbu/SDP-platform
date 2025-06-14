package com.personalAssist.SDP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);
    private final Environment env;

    public FirebaseConfig(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = buildFirebaseOptions();
                FirebaseApp.initializeApp(options);
                logger.info("✅ Firebase Initialized Successfully");
            }
        } catch (Exception e) {
            logger.error("❌ Firebase initialization failed", e);
            throw new IllegalStateException("Failed to initialize Firebase", e);
        }
    }

    private FirebaseOptions buildFirebaseOptions() throws Exception {
        InputStream credentialsStream;
        
        if (isProduction()) {
            String encodedCreds = env.getProperty("FIREBASE_CONFIG_BASE64");
            if (encodedCreds == null || encodedCreds.isEmpty()) {
                throw new IllegalStateException("Missing FIREBASE_CREDENTIALS_BASE64 environment variable");
            }
            credentialsStream = new ByteArrayInputStream(
                Base64.getDecoder().decode(encodedCreds)
            );
        } else {
            credentialsStream = getClass().getResourceAsStream("/firebase-service-account.json");
            if (credentialsStream == null) {
                throw new IllegalStateException("Firebase credentials file not found in resources");
            }
        }
        
        return FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(credentialsStream))
            .build();
    }

    private boolean isProduction() {
        return env.getActiveProfiles().length > 0 && 
               Arrays.stream(env.getActiveProfiles())
                     .anyMatch(env -> env.equalsIgnoreCase("prod"));
    }
}