package yinyin;
//管理歌单

import java.util.Vector;

/**对歌单新建、删除、重命名的功能类*/
public class PlaylistFunc {
//新建   重命名  删除歌单  音友圈权限
	//锁定用户的number
	private String db="audio_playlist";
	private Vector<String> vector=new Vector<String>();
	private Database playlistdb=new Database();
	
	
	//初始化设置本地歌单和我的收藏
	public void CreatePlaylist(String number) {
		String sql="CREATE TABLE p"+number+"(playlist_name VARCHAR(255))";
		playlistdb.Create(sql, "audio_playlist");
		
		sql="INSERT INTO p"+number+" VALUES('本地歌曲')";
		playlistdb.InsertOrder(sql, "audio_playlist");
		
		sql="INSERT INTO p"+number+" VALUES('我的收藏')";
		playlistdb.InsertOrder(sql, "audio_playlist");
	}
	
	//无需对其初始化，因为创建歌单时 就已经加入默认歌单
	//输入用户索引
	public Vector<String> getAlllist(String number)
	{
		String order="SELECT * FROM p"+number;
		this.vector=playlistdb.QueryP(order,db);
		return this.vector;
	}
	
	//传进来用户索引  和想要加入的歌单名字
	public void AddPlaylist(String number,String name) {
		String order="INSERT INTO p"+number+" VALUES('"+name+"')";
		playlistdb.InsertOrder(order, db);
		vector.add(name);
	}
	//删除歌单
	public void DelPlaylist(String number,String name) {
		String order="DELETE FROM p"+number+ "WHERE playlist_name='"+name+"'";
		playlistdb.DelOrder(order, db);
	}
	
	//重命名歌单,用户索引，老名字，新名字
	public void UpdatePlaylist(String number,String nameO,String nameN) {
		String order="UPDATE p"+number+ " SET playlist_name='"+nameN+"' WHERE playlist_name='"+nameO+"'";
		playlistdb.UpdateOrder(order, db);
	}
}
