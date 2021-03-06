package yinyin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.LockSupport;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Player implements AudioPlayer {
	private boolean IsStop = true;
	private boolean IsPause = false;
	
	AudioInputStream audioInputStream;//文件流
	AudioFormat audioFormat;//文件格
	SourceDataLine sourceDataLine;//输出设备
	Thread playThread;//播放线程
	Thread replayThread;//重复播放
	
	public boolean isPause()
	{
		return IsPause;
	}
	public void setPause(boolean isPause)
	{
		this.IsPause = IsPause;
	}
	public boolean IsStop()
	{
		return IsStop;
	}
	public void init(InputStream file)
	{
		try {
			//停止播放线程
			IsStop = true;
			//获得文件输入流
			try {
				audioInputStream = AudioSystem.getAudioInputStream(file);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//获得文件输入流的音频格式类对象
			audioFormat = audioInputStream.getFormat();
			//转换mp3文件编码
			if(audioFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED)
			{
				audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						audioFormat.getSampleRate(),16,
						audioFormat.getChannels(),
						audioFormat.getChannels()*2,
						audioFormat.getSampleRate(),false);
				audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
			
			}
			//打开输出设备
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,AudioSystem.NOT_SPECIFIED);
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void play(InputStream in) {
		init(in);
		play() ;
	}
	public void play(File file)
	{
		try {
			init(new FileInputStream(file));
			play();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null,"","读取文件出错",JOptionPane.INFORMATION_MESSAGE);		
		}
	}
	public void play()
	{
		if(IsPause == false)
		{
			if(playThread!=null)
			{
				playThread.stop();
				playThread = new PlayThread();
				playThread.start();
			}
			else {
				playThread = new PlayThread();
				playThread.start();
			}
		}
	}
	public void replay()
	{
		replayThread = new RePlayThread();
		replayThread.start();
	}
	public void stop()throws UnsupportedAudioFileException,IOException,LineUnavailableException
	{
		if(playThread!=null)
		{
			playThread.stop();
		//	playThread = new PlayThread();
		}
		if(!(replayThread == null)) {
			replayThread.stop();
		}
	}
	public void pause()
	{
		if(!(replayThread == null)) {
			replayThread.stop();
		}
		IsPause = true;
	}
	class RePlayThread extends Thread
	{
		public void run()
		{
			while(true)
			{
				LockSupport.unpark(playThread);
			}
		}
	}
	public class PlayThread extends Thread{
		byte tempBuffer[] = new byte[2048];
		public void run()
		{
			try {
				IsStop = false;
				int cnt;
				while ((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length))!=-1) {
					if(cnt>0)
					{
						//写入缓存数据
						sourceDataLine.write(tempBuffer, 0, cnt);
					}
					if(IsPause == true)
					{
						LockSupport.park();
					}
				}
				sourceDataLine.drain();
				
				//如果打开则播放完毕后不能再次播放
				sourceDataLine.close();
				IsStop = true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
