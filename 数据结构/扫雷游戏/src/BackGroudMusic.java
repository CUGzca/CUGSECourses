import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.FileInputStream;
import java.io.IOException;

public class BackGroudMusic {
    public AudioPlayer MusicPlayer=AudioPlayer.player;
    public AudioStream BGM;
    public AudioData MusicData;
    public ContinuousAudioDataStream loop=null;
    BackGroudMusic(){
        try{
            //必须是wav格式的并且少于1M
            BGM=new AudioStream(new FileInputStream("E:/Java Eclipse Project/mineSweeping/music/1.wav"));
            MusicData=BGM.getData();
            loop=new ContinuousAudioDataStream(MusicData);
        }catch(IOException e){
            e.printStackTrace();
        }
        MusicPlayer.start(loop);
    }

}
