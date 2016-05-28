import javax.sound.sampled.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Tahnik Mustasin on 25/05/2016.
 */
public class AudioRecorder {
    String AudioFilePath = "speech.wav";
    File wavFile = new File(AudioFilePath);
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;

    AudioFormat getAudioFormat(){
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }
    public AudioRecorder(){
    }
    void startRecording(){
        AudioFormat format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        line.start();

        AudioInputStream stream = new AudioInputStream(line);

        try {
            AudioSystem.write(stream, fileType, wavFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void stopRecording(){
        line.stop();
        line.close();
    }


    void LowPassFilter(){
    }

    public static void main(String args[]){
        final AudioRecorder audioRecorder = new AudioRecorder();

        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                audioRecorder.stopRecording();
            }
        });

        stopper.start();
        audioRecorder.startRecording();
        audioRecorder.LowPassFilter();
    }
}
