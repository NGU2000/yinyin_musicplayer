package yinyin;

import java.io.File;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MediaPlayer {
	private Player player;
	public MediaPlayer() {
		player = new Player();
	}
	
	//返回是否暂停，提供上一层调用恢复播放功能
	public boolean GetPause(){
		return player.isPause();
	}
	
	//返回是否播放完毕，供上一层实现循环单曲下一曲等等方法
	public boolean IsStop() {
		return player.IsStop();
	}
	
	//传入字符串判断是File,调用底层播放器
	public void Play(String string)
	{
		player.play(new File(string));
	}
	
	//恢复播放
	public void Play() {
		player.replay();
	}
	
	//暂停
	public void Pause() {
		player.pause();
	}
	
	//停止播放
	public void Stop() {
		try {
			player.stop();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
