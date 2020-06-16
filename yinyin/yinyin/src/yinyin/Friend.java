package yinyin;

public class Friend {
	private Database dbfriend=new Database();
	private String db="audio_friend";
	
	// 好友账号
	private String number;
	// 好友用户名
	private String name;
	// 好友备注
	private String remark;
	// 好友头像
	private String imagePath;
	
	// 构造函数（全信息）
	public Friend(String num, String n, String r, String i) 
	{
		this.number = num;
		this.name = n;
		this.remark = r;
		this.imagePath = i;
	}
	
	// 构造函数（仅账号）
	public Friend(String num)
	{
		number = num;
		name = null;
		remark = null;
		imagePath = null;
	}
	
	// 构造函数（无参数）
	public Friend()
	{
		number =null;
		name = null;
		remark = null;
		imagePath = null;
	}
	
	public void setNumber(String num)
	{
		if(num != null)
			this.number = num;
	}
	
	public String getNumber()
	{
		return number;
	}
	
	public void SetName(String n)
	{
		if(n != null)
			name = n;
	}
	
	public String GetName()
	{
		return name;
	}
	
	public void SetRemark(String r)
	{
		if(r != null)
			remark = r;
	}
	
	public String GetRemark()
	{
		return remark;
	}
	
	public void SetImagePath(String i)
	{
		if(i != null)
			imagePath = i;
	}
	
	public String GetImagePath()
	{
		return imagePath;
	}
	
	public void CreateFriendList(String number)
	{
		String order="CREATE TABLE f"+number+"(number VARCHAR(80))";
		dbfriend.Create(order, db);
		String add="ALTER TABLE f"+number+" ADD COLUMN name VARCHAR(80)";
		dbfriend.AddColumn(add, db);
		add="ALTER TABLE f"+number+" ADD COLUMN remark  VARCHAR(80)";
		dbfriend.AddColumn(add, db);
	}
}
