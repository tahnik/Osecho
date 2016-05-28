/**
 * Created by Tahnik Mustasin on 20/05/2016.
 */


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class Main {
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("en-us");
        configuration.setDictionaryPath("dict/6270.dic");
        //configuration.setLanguageModelPath("lang/6270.lm");
        configuration.setGrammarPath("./");
        configuration.setUseGrammar(true);
        configuration.setGrammarName("dialog");
        configuration.setSampleRate(16000);

        AudioRecorder audioRecorder = new AudioRecorder();

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                configuration);
        InputStream stream = new FileInputStream(new File("speech.wav"));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis().toLowerCase());
        }
        recognizer.stopRecognition();
    }
}
