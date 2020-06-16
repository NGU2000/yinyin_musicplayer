package yinyin;

import java.io.*;
import java.util.Vector;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

public class GetSongInfo {
	MP3File mp3File;
	String title,artist,album,year,stime;//文件夹目录
	long time = 0;
	String path = "../music/";
	public GetSongInfo() 
	{
	}
	
	public MP3File getMp3File()
	{
		return mp3File;
	}
	public String getTitle()
	{
		return title;
	}
	public String getArtist() {
		return artist;
	}
	public String getAlbum() {
		return album;
	}
	public String getYear() {
		return year;
	}
	public String getTime() {
		return stime;
	}
	/**获取mp3文件信息   我先注释了读取海报*/
	public void GetInformation(File file)throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException
	{
		mp3File = new MP3File(file);
		Tag tag = mp3File.getTag();
		artist = tag.getFirst(FieldKey.ARTIST);
		album =  tag.getFirst(FieldKey.ALBUM);
		year = tag.getFirst(FieldKey.YEAR);
		title = tag.getFirst(FieldKey.TITLE);
		MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
		time = audioHeader.getTrackLength();
		int min = (int)time/60;
		int sec = (int)time%60;
		stime = Integer.toString(min)+":"+Integer.toString(sec);
		AbstractID3v2Tag tagid3v2 = mp3File.getID3v2Tag();
		AbstractID3v2Frame frame = (AbstractID3v2Frame) tagid3v2.getFrame("APIC");
		FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
		/*byte [] imageData = body.getImageData();
		FileOutputStream outputStream = new FileOutputStream("../post/"+title + ".jpg");
		outputStream.write(imageData);
		outputStream.close();*/
		
	}
	
	/**返回指定path下所有音频文件的歌曲名，歌手名，默认下载1、分享为0，获取专辑名，时长*/
	public Vector<Vector<String>> returnInfo(String path) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException
	{
		Vector<Vector<String>> content=new Vector<Vector<String>>();
		File file= new File(path);
		File[] tempList = file.listFiles();
		for(File f:tempList)
		{
			GetInformation(f);
			Vector<String> Item=new Vector<String>();
			 Item.add(getTitle());
			 Item.add(getArtist());
			 Item.add("1");
			 Item.add("0");
			 Item.add(getAlbum());
			 Item.add(getTime());
			 content.add(Item);}
		return content;
	}
	public int getLength()
	{
		return (int)time;
	}
}
