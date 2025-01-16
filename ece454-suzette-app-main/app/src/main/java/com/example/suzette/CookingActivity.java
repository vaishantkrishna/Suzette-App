package com.example.suzette;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suzette.virtualassistant.Message;
import com.example.suzette.virtualassistant.MessageAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CookingActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private static final String TAG = "CookingActivity";
    private static final String API_KEY = "test";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    RecyclerView recyclerView;
    String rec;
    String system;
    boolean first;
    EditText messageEditText;
    MaterialButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    List<JSONObject> messageHistory;
    private MediaPlayer mediaPlayer;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
            .build();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        first = true;
        setContentView(R.layout.activity_cooking_activity);
        messageList = new ArrayList<>();
        Intent intent = getIntent();
        system = intent.getStringExtra("secondPrompt");
         rec = intent.getStringExtra("firstPrompt");
        recyclerView = findViewById(R.id.recycler_view);

        messageEditText = findViewById(R.id.editTextUserInput);
        sendButton = findViewById(R.id.buttonSend);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        if(first){

            messageEditText.setText("");
            callAPI("help me cook " + rec);

        }
        sendButton.setOnClickListener((v)->{
            String question = messageEditText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);

        });

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "Playback completed");
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, "Media Player error: " + what + ", " + extra);
                return false;
            }
        });

        // Text the chatbot will say to the user.
        //speak("");

        // Request audio recording permission
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
          //  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        //}

        // Set up button click listener
        // speechToTextButton.setOnClickListener(v -> startSpeechToText());

    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        //okhttp
        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));
        if (messageHistory == null) {
            messageHistory = new ArrayList<>();
        }


// Create a user message
        JSONObject userMessage = new JSONObject();
        try {
            userMessage.put("role", "user");
            userMessage.put("content", question);  // Your question content
            messageHistory.add(userMessage);  // Add to messages array
        } catch (JSONException e) {
            e.printStackTrace();  // Handle error
        }
        if(first) {
            first = false;
// Create a system message
            JSONObject systemMessage = new JSONObject();
            try {
                systemMessage.put("role", "system");
                systemMessage.put("content", system);  // System message content
                messageHistory.add(systemMessage);  // Add to messages array
            } catch (JSONException e) {
                e.printStackTrace();  // Handle error
            }
        }


        long timerInMilliseconds = extractTimerDuration(question);

        JSONArray messages = new JSONArray();
        for (JSONObject message : messageHistory) {
            messages.put(message);
        }
        Log.d("timer", ""+timerInMilliseconds);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-4");
            jsonBody.put("messages", messages);  // Correctly structured messages array
            jsonBody.put("max_tokens", 4000);
            jsonBody.put("temperature", 0);
            JSONArray tools = new JSONArray();
            JSONObject functionTool = new JSONObject();

            if (timerInMilliseconds > 0) {
                showCustomTimerDialog(timerInMilliseconds);
                /*
                functionTool.put("type", "function");

                JSONObject functionDetails = new JSONObject();
                functionDetails.put("name", "showCustomTimerDialog");
                functionDetails.put("description", "Start a countdown timer with a specified duration");

                JSONObject parameters = new JSONObject();
                parameters.put("type", "object");

                JSONObject properties = new JSONObject();
                properties.put("time_in_ms", new JSONObject().put("type", "integer"));

                parameters.put("properties", properties);
                parameters.put("required", new JSONArray().put("time_in_ms"));

                functionDetails.put("parameters", parameters);

                functionTool.put("function", functionDetails);


                tools.put(functionTool);


                jsonBody.put("tools", tools);


// Adding function parameters
                JSONObject functionArgs = new JSONObject();
                functionArgs.put("time_in_ms", timerInMilliseconds);

                */
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() == null) {
                    addResponse("Failed to load response: empty body");
                    return;
                }

                String responseBodyString = response.body().string();

                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        JSONArray choices = jsonObject.getJSONArray("choices");

                        // Validate if the expected structure is correct
                        if (choices.length() > 0 && choices.getJSONObject(0).has("message")) {
                            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                            messageHistory.add(message);
                            if (message.has("content")) {
                                String content = message.getString("content");
                                addResponse(content.trim());
                            } else {
                                addResponse("Failed to parse response: 'content' field missing");
                            }
                        } else {
                            addResponse("Failed to parse response: 'message' object missing");
                        }
                    } else {
                       addResponse("Failed to load response: " + responseBodyString);
                    }
                } catch (JSONException e) {
                    addResponse("Failed to parse response: " + e.getMessage());
                }

            }
        });

    }
    private long extractTimerDuration(String userInput) {
        // Simple regex to extract a number and a time unit from the user's input
        Pattern pattern = Pattern.compile("(\\d+)\\s*(seconds?|minutes?|hours?|secs?|mins?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {
            int timeValue = Integer.parseInt(matcher.group(1));
            String timeUnit = matcher.group(2).toLowerCase();

            switch (timeUnit) {
                case "sec":
                case "secs":
                case "second":
                case "seconds":
                    return timeValue * 1000; // Convert to milliseconds
                case "min":
                case "mins":
                case "minute":
                case "minutes":
                    return timeValue * 60 * 1000; // Convert to milliseconds
                case "hour":
                case "hours":
                    return timeValue * 60 * 60 * 1000; // Convert to milliseconds
                default:
                    return 0;
            }
        }

        return 0; // Return zero if no valid timer duration is found
    }

    /**
     * Use this to play the output wav file generated by the media player.
     *
     * @param text
     */
    private void speak(String text) {
        try {
            String filePath = getExternalFilesDir(null) + "/output.wav";
            //OpenAITextAndSpeech.convertTextToSpeech(text, filePath);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();

            // Put here where to reset the text.
            // chatTextView.setText(text);
        } catch (IOException e) {
            Log.e(TAG, "Error playing audio: " + e.getMessage());
        }
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Speech recognition not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    String spokenText = result.get(0);

                    // Put the text box where the speech should be displayed
                    //chatTextView.setText(spokenText);
                    Log.d(TAG, "Spoken Text: " + spokenText);
                    convertSpeechToText(spokenText); // Call the method to convert speech to text
                }
            }
        }
    }

    private void convertSpeechToText(String spokenText) {
        // Call the method from OpenAITextAndSpeech class to convert speech to text
        // Replace "output.wav" with the appropriate file path where the speech is stored
        String filePath = getExternalFilesDir(null) + "/output.wav";
        try {
            String transcribedText = OpenAITextAndSpeech.convertSpeechToText(filePath);
            // Use the transcribed text for further processing or display
            Log.d(TAG, "Transcribed Text: " + transcribedText);
        } catch (IOException e) {
            Log.e(TAG, "Error converting speech to text: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. You can start speech recognition.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Speech recognition cannot be initiated.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Create a temporary Timer view as instructed by User
     * The idea is that the User will command Suzette to set a timer. We can parse the time out of
     * message and set a Timer on the backend.
     *
     * @param timeInMillis : Set time requested in milliseconds
     */
    private void showCustomTimerDialog(long timeInMillis) {
        CustomTimerDialog timerDialog = new CustomTimerDialog(this, timeInMillis);
        timerDialog.show(); // Show the dialog
    }


}

