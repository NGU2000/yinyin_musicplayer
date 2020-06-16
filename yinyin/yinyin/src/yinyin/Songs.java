package yinyin;

import java.util.Vector;

//����������,������ʱ����Ӧ�����������ݿ�ĸ��£�ɾ����������Ӹ��������ظ������������
/**����������*/
public class Songs {
	private Database dbsong=new Database();
	private String db="audio_songs";
	
	/**����������*/
	public void CreateSongList(String number,String playlist_name) {
		String order="CREATE TABLE s"+number+playlist_name+"(song_name VARCHAR(80))";
		dbsong.Create(order, db);
		String add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"singer_name VARCHAR(80)";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"download BOOLEAN";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"share BOOLEAN";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"ר���� VARCHAR(80)";
		dbsong.AddColumn(add, db);
		add="ALTER TABLE s"+number+playlist_name+" ADD COLUMN "+"ʱ��  VARCHAR(80)";
		dbsong.AddColumn(add, db);
	}
	
	/**����download ��update������*/
	public void UpdateD_S(String number,String playlist_name,String choose,String flag,String song_name) {
		String order="UPDATE s"+number+playlist_name+
				" SET "+choose+"='"+flag+"' WHERE song_name='"+song_name+"'";
		dbsong.UpdateOrder(order, db);
		System.out.println(order);
	}
	
	/**ɾ���������и���*/
	public void DelSong(String number,String playlist_name,String song_name) {
		String order="DELETE FROM s"+number+playlist_name+"  WHERE song_name='"+song_name+"'";
		dbsong.DelOrder(order, db);
	}
	/**�ƶ�����*/
	public void MoveSong(String number,String old_playlist_name,String new_playlist_name,String song_name,String singer_name,String download,String share,String album,String time) {
		//���ھ͸赥ɾ��
		DelSong(number, old_playlist_name, song_name);
		//���¸赥�м���
		String order="INSERT INTO s"+number+new_playlist_name+
				" VALUES('"+song_name+"','"+singer_name+"','"+download+"','"+share+"','"+album+"',"+time+"')";
		dbsong.InsertOrder(order, db);
	}
	
	/**��ĳ���赥�м������*/
	public void AddSong(String number,String playlist_name,String song_name,String singer_name,String album,String time) {
		String order="INSERT INTO s"+number+playlist_name+"  VALUES('"+song_name+"','"+singer_name+"','1','0'"+",'"+album+"','"+time+"')";
		System.out.println(order);
		dbsong.InsertOrder(order, db);
	}
	
	/**��ȡĳ�û�ĳ�赥��ȫ���ĸ���*/
	public Vector<Vector<String>>  getALLsongs(String number,String playlist_name) {
		String order="SELECT * FROM s"+number+playlist_name;
		Vector<Vector<String>> content=dbsong.QueryS_F(order, number,playlist_name, db);
		return content;
	}
}
