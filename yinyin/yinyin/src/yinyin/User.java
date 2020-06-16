package yinyin;

import java.awt.Image;
import java.util.ArrayList;
public class User {
	public final int FRIENDLISTNUMBER = 0;
	private ArrayList<Friend> friendList;
	public User()
	{
		friendList = new ArrayList<Friend>();
	}
	// ��Ӻ��ѣ����û�ID��
	public void AddFriend(String number)
	{
		Friend friend = new Friend(number);
		friendList.add(friend);
	} 
	
	// ��Ӻ��ѣ�ȫ��Ϣ��
	public void AddFriend(String num, String name, String remark, String imagePath)
	{
		Friend friend = new Friend(num, name,remark,imagePath);
		friendList.add(friend);
	}
	
	// ��ȡ�����б�
	public ArrayList<Friend> GetFriendList()
	{
		return friendList;
	}
	
	// ɾ������
	public void RemoveFriend(String num)
	{
		for (Friend friend : friendList) 
		{
			if(friend.getNumber() == num)
				friendList.remove(friend);
		}
	}
}
