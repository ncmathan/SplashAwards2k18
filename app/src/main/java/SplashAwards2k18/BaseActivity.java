package SplashAwards2k18;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Result;
import ai.kitt.snowboy.SnowboyDetect;


public class BaseActivity extends AppCompatActivity {
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
        //setupXiaoBaiButton();
        setupAsr();
        textToSpeech=new TextToSpeech(this,null);
        setupNlu();
        setupHotword();
        // TODO: Start Hotword
        startHotword();
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
                if (texts==null||texts.isEmpty()){
                    System.out.println("Please try again");
                }
                else{
                    String text=texts.get(0);
                    System.out.println("Detected text:\n\n"+text);
                    startNlu(text);
                }
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


    public void startTts(String speech) {
        // TODO: Start TTS
        textToSpeech.speak(speech,TextToSpeech.QUEUE_FLUSH,null);
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
        String clientAccessToken = "6b99e3fa8978439dabee6c5ef6fcd800";
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
                    if(speech.toLowerCase().contains("opening")){
                        if(speech.toLowerCase().contains("fairprice")){
                            Intent intent=new Intent(BaseActivity.this,Shopping.class);
                            intent.putExtra("num",1);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("coldstorage")){
                            Intent intent=new Intent(BaseActivity.this,Shopping.class);
                            intent.putExtra("num",2);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("redmart")){
                            Intent intent=new Intent(BaseActivity.this,Shopping.class);
                            intent.putExtra("num",3);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("shopping list")){
                            Intent intent=new Intent(BaseActivity.this,Shopping.class);
                            intent.putExtra("num",4);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("recipe")){
                            Intent intent=new Intent(BaseActivity.this,RecipeActivity.class);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("calories")){
                            Intent intent=new Intent(BaseActivity.this,CaloriesActivity.class);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("appointment status")){
                            Intent intent=new Intent(BaseActivity.this,Appointment.class);
                            intent.putExtra("num",2);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("comments")){
                            Intent intent=new Intent(BaseActivity.this,Appointment.class);
                            intent.putExtra("num",1);
                            startActivity(intent);
                        }
                        else if(speech.toLowerCase().contains("favourite")){
                            Intent intent=new Intent(BaseActivity.this,RecipeActivity.class);
                            intent.putExtra("num",1);
                            startActivity(intent);
                        }
                    }
                    else if(speech.toLowerCase().contains("searching recipes containing")){
                        Intent intent=new Intent(BaseActivity.this,RecipeListActivity.class);
                        intent.putExtra("string",speech.substring(29));
                        System.out.println("string is:\n\n"+speech.substring(28));
                        startActivity(intent);
                    }

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
        File model = new File(snowboyDirectory, "alexa_02092017.umdl");
        File common = new File(snowboyDirectory, "common.res");

        // TODO: Set Sensitivity
        snowboyDetect = new SnowboyDetect(common.getAbsolutePath(), model.getAbsolutePath());
        snowboyDetect.setSensitivity("0.3");
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



    @Override
    public void onDestroy(){
        //Close the Text to Speech Library
        if(textToSpeech != null) {

            textToSpeech.stop();
            textToSpeech.shutdown();
            Log.d("base activity", "TTS Destroyed");
        }
        super.onDestroy();
        shouldDetect=false;
        if(speechRecognizer!=null)
        {
            speechRecognizer.destroy();
        }

    }
    
}


