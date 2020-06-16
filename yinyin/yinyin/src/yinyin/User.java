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
	// 添加好友（仅用户ID）
	public void AddFriend(String number)
	{
		Friend friend = new Friend(number);
		friendList.add(friend);
	} 
	
	// 添加好友（全信息）
	public void AddFriend(String num, String name, String remark, String imagePath)
	{
		Friend friend = new Friend(num, name,remark,imagePath);
		friendList.add(friend);
	}
	
	// 获取好友列表
	public ArrayList<Friend> GetFriendList()
	{
		return friendList;
	}
	
	// 删除好友
	public void RemoveFriend(String num)
	{
		for (Friend friend : friendList) 
		{
			if(friend.getNumber() == num)
				friendList.remove(friend);
		}
	}
}
