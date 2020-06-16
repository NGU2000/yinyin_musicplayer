package yinyin;
//����赥

import java.util.Vector;

/**�Ը赥�½���ɾ�����������Ĺ�����*/
public class PlaylistFunc {
//�½�   ������  ɾ���赥  ����ȦȨ��
	//�����û���number
	private String db="audio_playlist";
	private Vector<String> vector=new Vector<String>();
	private Database playlistdb=new Database();
	
	
	//��ʼ�����ñ��ظ赥���ҵ��ղ�
	public void CreatePlaylist(String number) {
		String sql="CREATE TABLE p"+number+"(playlist_name VARCHAR(255))";
		playlistdb.Create(sql, "audio_playlist");
		
		sql="INSERT INTO p"+number+" VALUES('���ظ���')";
		playlistdb.InsertOrder(sql, "audio_playlist");
		
		sql="INSERT INTO p"+number+" VALUES('�ҵ��ղ�')";
		playlistdb.InsertOrder(sql, "audio_playlist");
	}
	
	//��������ʼ������Ϊ�����赥ʱ ���Ѿ�����Ĭ�ϸ赥
	//�����û�����
	public Vector<String> getAlllist(String number)
	{
		String order="SELECT * FROM p"+number;
		this.vector=playlistdb.QueryP(order,db);
		return this.vector;
	}
	
	//�������û�����  ����Ҫ����ĸ赥����
	public void AddPlaylist(String number,String name) {
		String order="INSERT INTO p"+number+" VALUES('"+name+"')";
		playlistdb.InsertOrder(order, db);
		vector.add(name);
	}
	//ɾ���赥
	public void DelPlaylist(String number,String name) {
		String order="DELETE FROM p"+number+ "WHERE playlist_name='"+name+"'";
		playlistdb.DelOrder(order, db);
	}
	
	//�������赥,�û������������֣�������
	public void UpdatePlaylist(String number,String nameO,String nameN) {
		String order="UPDATE p"+number+ " SET playlist_name='"+nameN+"' WHERE playlist_name='"+nameO+"'";
		playlistdb.UpdateOrder(order, db);
	}
}
