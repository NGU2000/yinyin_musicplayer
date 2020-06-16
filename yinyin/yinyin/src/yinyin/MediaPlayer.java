package yinyin;

import java.io.File;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MediaPlayer {
	private Player player;
	public MediaPlayer() {
		player = new Player();
	}
	
	//�����Ƿ���ͣ���ṩ��һ����ûָ����Ź���
	public boolean GetPause(){
		return player.isPause();
	}
	
	//�����Ƿ񲥷���ϣ�����һ��ʵ��ѭ��������һ���ȵȷ���
	public boolean IsStop() {
		return player.IsStop();
	}
	
	//�����ַ����ж���File,���õײ㲥����
	public void Play(String string)
	{
		player.play(new File(string));
	}
	
	//�ָ�����
	public void Play() {
		player.replay();
	}
	
	//��ͣ
	public void Pause() {
		player.pause();
	}
	
	//ֹͣ����
	public void Stop() {
		try {
			player.stop();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
