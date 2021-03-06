package yinyin;

import java.sql.*;
import java.util.Vector;
/**数据库基本操作类*/
public class Database {
	private String name;
	private String password;
	private String remark;
	private String number;
	private Connection con=null;
	
	public void setName(String name) {
		this.name=name;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	public void setRemark(String remark) {
		this.remark=remark;
	}
	
	public String getPassword() {
		return this.password;
	}
	public String getName() {
		return this.name;
	}
	public String getNumber() {
		return this.number;
	}
	public String getRemark() {
		return this.remark;
	}
	
	
	public int Connect(String db) {
		int flag=0;
		String url="jdbc:mysql://39.102.32.226:3308/"+db+"?allowPublicKeyRetrieval=true"
          		+ "&useSSL=false&serverTimezone=UTC";
        String n="";
        String p="";
        
        String driver="com.mysql.jdbc.Driver";
         //连接
         try {
        	//System.out.println(url);
        	con=DriverManager.getConnection(url, n, p);
            con.setAutoCommit(true);  
            flag=1;
	} catch (SQLException e) {
			flag=0;
			System.out.println("连接失败");
	}
         return flag;
    }
	
	public void Create(String order,String db) {
		try {
			int flag=Connect(db);
			if(flag==1) {
				PreparedStatement p = con.prepareStatement(order);   //会抛出异常
		        p.execute();
			}
			
		} catch (Exception e) {
			System.out.println("创建表失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				System.out.println("数据库关闭失败");
			}
		}
	}
	
	public void AddColumn(String order,String db) {
		try {
			int flag=Connect(db);
			if(flag==1) {
				PreparedStatement p = con.prepareStatement(order);   //会抛出异常
		        p.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("增加列失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				System.out.println("数据库关闭失败");
			}
		}
	}
	
	public void InsertOrder(String order,String db) {
		try {
			int flag=Connect(db);
			if(flag==1) {
				PreparedStatement p = con.prepareStatement(order);   //会抛出异常
		        p.executeUpdate();
			}
			
		} catch (Exception e) {
			System.out.println("插入数据失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("数据库关闭失败");
			}
		}
	}
	
	public void DelOrder(String order,String db) {
		try {
			int flag=Connect(db);
			if(flag==1) {
				PreparedStatement p = con.prepareStatement(order);   //会抛出异常
		        p.executeUpdate();
			}
			
		} catch (Exception e) {
			System.out.println("删除数据失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				System.out.println("数据库关闭失败");
			}
		}
	}
	
	public void UpdateOrder(String order,String db) {
		try {
			int flag=Connect(db);
			if(flag==1) {
				PreparedStatement p = con.prepareStatement(order);   //会抛出异常
		        p.executeUpdate();
			}
			
		} catch (Exception e) {
			System.out.println("更新数据失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("数据库关闭失败");
			}
		}
	}
	
	public void QueryInfo(String order,String db) {
		try {
			int flag=Connect(db);
			if(flag==1) {
				PreparedStatement p = con.prepareStatement(order);
	            //p.setString(1, "1");
	            ResultSet rs=p.executeQuery();
	            //把查询的所有数据读出来
	            String[] info=new String[5];
	            
	            while (rs.next()) {
	                info[0]=rs.getString(1);
	                info[1]=rs.getString(2);
	                info[2]=rs.getString(3);
	                
	            }
	            setNumber(info[0]);
	            setName(info[1]);
	            if(db=="audio_user")
	            {
	            	setPassword(info[2]);}
	            else {
	            	setRemark(info[2]);
				}
	            
			}
		} catch (Exception e) {
			System.out.println("查询用户信息失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				System.out.println("数据库关闭失败");
			}
		}
	}
	
	public Vector<String> QueryP(String order,String db) {
		Vector<String> vector=new Vector<String>();
		try {
			int flag=Connect(db);
			if(flag==1) {
	            PreparedStatement p = con.prepareStatement(order);
	            ResultSet rs=p.executeQuery();
	            //把查询的所有数据读出来
	            while (rs.next() ) {
	                vector.add(rs.getString(1));
	            }
			}
		} catch (Exception e) {
			System.out.println("查询歌单信息失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				System.out.println("数据库关闭失败");
			}
		}
		return vector;
	}

	/**查询歌曲表和好友表，返回的二维vector*/
	public Vector<Vector<String>> QueryS_F(String order,String number,String name,String db) {
		Vector<Vector<String>> content=new Vector<Vector<String>>();
		String sub=null;
		if(db.equals("audio_friend"))
		{
			sub="f";
		}
		else if (db.equals("audio_songs")) {
			sub="s";
		}
		else if(db.equals("audio_playlist")) {
			sub="p";
		}
		try {
			int flag=Connect(db);
			if(flag==1) {
				//先查询数据库一共多少列
				String sql="select count(*) from information_schema.COLUMNS where TABLE_SCHEMA='"+db+"' and table_name='"+sub+number+name+"'";
	            System.out.println(sql);
				PreparedStatement p = con.prepareStatement(sql);   //会抛出异常
	            ResultSet rs=p.executeQuery();
	            int column=0;
	            while (rs.next()) {
	            	System.out.println(rs.getString(1));
					column=Integer.parseInt(rs.getString(1));
				}
	            System.out.println("一共有"+column);
				p = con.prepareStatement(order);
	            rs=p.executeQuery();
	            //把查询的所有数据读出来
	            while (rs.next()) {
	            	Vector<String> item=new Vector<String>();
	               for(int i=1;i<=column;i++)
	               {
	            	   item.add(rs.getString(i));
	               }
	               content.add(item);
	            }
			}
		} catch (Exception e) {
			System.out.println("查询信息失败");
		}
		finally {
			try {
       		 con.close();
			} catch (Exception e2) {
				System.out.println("数据库关闭失败");
			}
		}
		return content;
	}
	
}


