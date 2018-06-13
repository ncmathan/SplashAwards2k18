package sg.gowild.SplashAwards2k18;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Result;
import ai.kitt.snowboy.SnowboyDetect;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    // View Variables
    private Button weight;
    private Button calories;
    private LinearLayout healthvitals;
    private LinearLayout recipe;
    private LinearLayout shopping;
    private RelativeLayout appointment;
    // ASR Variables
    private SpeechRecognizer speechRecognizer;
    // TTS Variables
    private TextToSpeech textToSpeech;
    // NLU Variables
    private AIDataService aiDataService;
    // Hotword Variables
    private boolean shouldDetect;
    private SnowboyDetect snowboyDetect;
    static {
        System.loadLibrary("snowboy-detect-android");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Setup Components
        setupViews();
        setupXiaoBaiButton();
        setupAsr();
        setupTts();
        setupNlu();
        setupHotword();
        // TODO: Start Hotword
        startHotword();
    }

    private void setupViews() {
        // TODO: Setup Views
        weight= (Button)findViewById(R.id.weight);
        calories=(Button)findViewById(R.id.calories);
        healthvitals=(LinearLayout)findViewById(R.id.healthvitals);
        recipe=(LinearLayout)findViewById(R.id.recipe);
        shopping=(LinearLayout)findViewById(R.id.shopping);
        appointment=(RelativeLayout)findViewById(R.id.appointment);
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RecipeActivity.class);
                startActivity(intent);

            }
        });
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Shopping.class);
                startActivity(intent);
            }
        });
    }

    private void setupXiaoBaiButton() {
        String BUTTON_ACTION = "com.gowild.action.clickDown_action";

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BUTTON_ACTION);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO: Add action to do after button press is detected
                shouldDetect=false;
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void setupAsr() {
        // TODO: Setup ASR
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                Log.e("asr","error: "+Integer.toString(error));
                startHotword();
            }

            @Override
            public void onResults(Bundle results) {
                List<String> texts = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                /*if (texts==null||texts.isEmpty()){
                    textView.setText("Please try again");
                }
                else{
                    String text=texts.get(0);
                    textView.setText(text);
                    startNlu(text);
                }*/
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    private void startAsr() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // TODO: Set Language
                final Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

                // Stop hotword detection in case it is still running
                shouldDetect = false;

                // TODO: Start ASR
                speechRecognizer.startListening(recognizerIntent);
            }
        };
        Threadings.runInMainThread(this, runnable);
    }

    private void setupTts() {
        // TODO: Setup TTS
        textToSpeech=new TextToSpeech(this,null);
    }

    private void startTts(String text) {
        // TODO: Start TTS
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        // TODO: Wait for end and start hotword
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (textToSpeech.isSpeaking()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e("tts", e.getMessage(), e);
                    }
                }

                startHotword();
            }
        };
        Threadings.runInBackgroundThread(runnable);
    }

    private void setupNlu() {
        // TODO: Change Client Access Token
        String clientAccessToken = "d6fcfeb7b1354580b30ba8412b0793f1";
        AIConfiguration aiConfiguration = new AIConfiguration(clientAccessToken,
                AIConfiguration.SupportedLanguages.English);
        aiDataService = new AIDataService(aiConfiguration);
    }

    private void startNlu(final String text) {
        // TODO: Start NLU
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AIRequest aiRequest=new AIRequest();
                aiRequest.setQuery(text);
                try {
                    AIResponse aiResponse=aiDataService.request(aiRequest);
                    Result result=aiResponse.getResult();
                    Fulfillment fulfillment=result.getFulfillment();
                    String speech=fulfillment.getSpeech();
                    startTts(speech);
                } catch (AIServiceException e) {
                    e.printStackTrace();
                }
            }
        };
        Threadings.runInBackgroundThread(runnable);
    }

    private void setupHotword() {
        shouldDetect = false;
        SnowboyUtils.copyAssets(this);

        // TODO: Setup Model File
        File snowboyDirectory = SnowboyUtils.getSnowboyDirectory();
        File model = new File(snowboyDirectory, "hey_felix.pmdl");
        File common = new File(snowboyDirectory, "common.res");

        // TODO: Set Sensitivity
        snowboyDetect = new SnowboyDetect(common.getAbsolutePath(), model.getAbsolutePath());
        snowboyDetect.setSensitivity("0.4");
        snowboyDetect.applyFrontend(true);
    }

    public void startHotword() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shouldDetect = true;
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

                int bufferSize = 3200;
                byte[] audioBuffer = new byte[bufferSize];
                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.DEFAULT,
                        16000,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize
                );

                if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                    Log.e("hotword", "audio record fail to initialize");
                    return;
                }

                audioRecord.startRecording();
                Log.d("hotword", "start listening to hotword");

                while (shouldDetect) {
                    audioRecord.read(audioBuffer, 0, audioBuffer.length);

                    short[] shortArray = new short[audioBuffer.length / 2];
                    ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortArray);

                    int result = snowboyDetect.runDetection(shortArray, shortArray.length);
                    if (result > 0) {
                        Log.d("hotword", "detected");
                        shouldDetect = false;
                    }
                }

                audioRecord.stop();
                audioRecord.release();
                Log.d("hotword", "stop listening to hotword");
                startAsr();
                // TODO: Add action after hotword is detected
            }
        };
        Threadings.runInBackgroundThread(runnable);
    }

}
