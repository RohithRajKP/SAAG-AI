package com.saagsystems.saag_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
//import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    TextView showUspeak, dateView;
    Button help;
    public static String module = "";
    ImageButton speak;
    String command = "saag";
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    String welcome, date;
    String weatherText;
    FragmentManager f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f = getFragmentManager();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        welcome = "Welcome to SAAG INDIA  what can i do for u today ? ";
        //Grabbing References
        showUspeak = (TextView) findViewById(R.id.textViewShow);
        help = (Button) findViewById(R.id.buttonHelp);
        speak = (ImageButton) findViewById(R.id.imageButtonSpeak);
        tts = new TextToSpeech(MainActivity.this, this);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        date = df.format(Calendar.getInstance().getTime());
        dateView = (TextView) findViewById(R.id.textView7Date);
        dateView.setText(date);

        showUspeak.setText(welcome);
        tts.speak(welcome, TextToSpeech.QUEUE_FLUSH, null);

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Prompt speech input
                promptSpeechInput();
                check = true;


            }
        });
    }
    @Override
    public void onDestroy() {
        // Shuts Down TTS
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showUspeak.setText(result.get(0));

                    //Speak out
                    speakOut();

                }
                break;
            }

        }
    }

    //Speak Out
    private void speakOut() {

        String text = showUspeak.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        command = text;

        //Launch Module
        if (check) {
            launchModule(command);
        }
    }

    private void launchModule(String commandTolaunch) {
        switch (commandTolaunch) {
            case Commands.mailModule:
                Toast.makeText(getBaseContext(), "Mail Module", Toast.LENGTH_SHORT).show();
//                Intent intentm = new Intent(MainActivity.this, GmailModule.class);
//                startActivity(intentm);
                break;
            case Commands.callModule:
                Toast.makeText(getBaseContext(), "Call Module", Toast.LENGTH_SHORT).show();
//                Intent intentc = new Intent(MainActivity.this, PhoneModule.class);
//                startActivity(intentc);
                break;
            case Commands.emergencyModule:
                Toast.makeText(getBaseContext(), "Emergency Module", Toast.LENGTH_SHORT).show();
//                Intent intente = new Intent(MainActivity.this, MapModule.class);
//                intente.putExtra(Commands.EMERGENCY, true);
                //startActivity(intente);
                break;
            case Commands.locModule:
                Toast.makeText(getBaseContext(), "Location Module", Toast.LENGTH_SHORT).show();
                Intent intentl = new Intent(MainActivity.this, Needs.class);
                startActivity(intentl);
                break;
            case Commands.musicModule:
//                Toast.makeText(getBaseContext(), "Music Module", Toast.LENGTH_SHORT).show();
//                Intent intentmu = new Intent(MainActivity.this, Music.class);
//                startActivity(intentmu);
                break;
            case Commands.DATE:
               /* display_frag d = new display_frag();
                Bundle bundle = new Bundle();
                bundle.putString(Commands.DATE, date);
                d.setArguments(bundle);
                d.show(getFragmentManager(), "sss");*/
                break;
            case Commands.TIME:
             /*  display_frag d2 = new display_frag();
                Bundle bundle2 = new Bundle();
                bundle2.putString(Commands.DATE, date);
                d2.setArguments(bundle2);
                d2.show(getFragmentManager(), "sss");*/
                break;
            case Commands.remmodule:
//                Toast.makeText(getBaseContext(), "Reminder Module", Toast.LENGTH_SHORT).show();
//                Intent intentr = new Intent(MainActivity.this, ReminderModule.class);
//                startActivity(intentr);
                break;
            case Commands.helpModule:
                module = "main";
                Toast.makeText(getBaseContext(), "Help Module", Toast.LENGTH_SHORT).show();
//                HelpFrag frag = new HelpFrag();
//                frag.show(f, null);
                break;
            case Commands.noteModule:
                Toast.makeText(getBaseContext(), "Note Module", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, NoteModule.class);
//                startActivity(intent);
                break;
            case Commands.weather:
               /* display_frag d1 = new display_frag();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Commands.DATE, weatherText);
                d1.setArguments(bundle1);
                d1.show(getFragmentManager(), "sss");*/
                break;
            default:
                try {
                    Intent intents = new Intent(Intent.ACTION_WEB_SEARCH);
                    intents.putExtra(SearchManager.QUERY, commandTolaunch);
                    startActivity(intents);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;
        }
    }

}
