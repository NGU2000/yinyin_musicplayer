package yinyin;

import java.util.Vector;

//创建歌曲表,点击表格时的响应函数，到数据库的更新，删除歌曲，添加歌曲，下载歌曲，分享歌曲
/**歌曲操作类*/
public class Songs {
	private Database dbsong=new Database();
	private String db="audio_songs";
	
	/**创建歌曲表*/
	public void CreateSongList(String number,String playlist_name) {
		String order="CREATE TABLE s"+number+playlist_name+"(song_name VARCHAR(80))";
		dbsong.Create(order, db);
		String add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"singer_name VARCHAR(80)";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"download BOOLEAN";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"share BOOLEAN";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"专辑名 VARCHAR(80)";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"时长  VARCHAR(80)";
		dbsong.AddColumn(add, db);
	}
	
	/**更新download 和update的属性*/
	public void UpdateD_S(String number,String playlist_name,String choose,String flag,String song_name) {
		String order="UPDATE s"+number+playlist_name+
				" SET "+choose+"='"+flag+"' WHERE song_name='"+song_name+"'";
		dbsong.UpdateOrder(order, db);
		System.out.println(order);
	}
	
	/**删除歌曲表中歌曲*/
	public void DelSong(String number,String playlist_name,String song_name) {
		String order="DELETE FROM s"+number+playlist_name+"  WHERE song_name='"+song_name+"'";
		dbsong.DelOrder(order, db);
	}
	/**移动歌曲*/
	public void MoveSong(String number,String old_playlist_name,String new_playlist_name,String song_name,String singer_name,String download,String share,String album,String time) {
		//先在就歌单删除
		DelSong(number, old_playlist_name, song_name);
		//在新歌单中加入
		String order="INSERT INTO s"+number+new_playlist_name+
				" VALUES('"+song_name+"','"+singer_name+"','"+download+"','"+share+"','"+album+"',"+time+"')";
		dbsong.InsertOrder(order, db);
	}
	
	/**在某个歌单中加入歌曲*/
	public void AddSong(String number,String playlist_name,String song_name,String singer_name,String album,String time) {
		String order="INSERT INTO s"+number+playlist_name+"  VALUES('"+song_name+"','"+singer_name+"','1','0'"+",'"+album+"','"+time+"')";
		System.out.println(order);
		dbsong.InsertOrder(order, db);
	}
	
	/**获取某用户某歌单中全部的歌曲*/
	public Vector<Vector<String>>  getALLsongs(String number,String playlist_name) {
		String order="SELECT * FROM s"+number+playlist_name;
		Vector<Vector<String>> content=dbsong.QueryS_F(order, number,playlist_name, db);
		return content;
	}
}
