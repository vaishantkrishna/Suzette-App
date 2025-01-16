package com.example.suzette;

import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAITextAndSpeech {
    private static final OkHttpClient client = new OkHttpClient();

    private static final String API_KEY = "test";

    public static void main(String[] args) throws IOException {
        String textToSpeak = "Hello, world!";
        String spokenTextFile = "spoken_text.wav";

        String spokenText = convertTextToSpeech(textToSpeak, spokenTextFile);
        System.out.println("Converted Text-to-Speech: " + spokenText);

        String transcribedText = convertSpeechToText(spokenTextFile);
        System.out.println("Transcribed Speech-to-Text: " + transcribedText);
    }

    public static String convertTextToSpeech(String text, String outputFile) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"text\":\"" + text + "\"}");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/tts")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Assuming the response body contains the audio data
            byte[] audioData = response.body().bytes();

            // Save the audio data to a file
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(audioData);
            }

            return outputFile;
        }
    }

    public static String convertSpeechToText(String audioFile) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "audio", RequestBody.create(MediaType.parse("audio/wav"), new java.io.File(audioFile)))
                .build();

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/ASR/direct")
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }
}
