package yinyin;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.io.*;

/**登录注册类，直接运行，登录成功后直接显示主界面*/
public class Login_Sign extends JFrame {

	private JPanel contentPane;
	private JTextField edit_name;
	private JTextField edit_num;
	private JTextField edit_password;
	private String number="1";//记录用户索引,，默认是第一个用户
	Friend friend=new Friend();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Sign frame = new Login_Sign();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public void setNumber(String number)
	{
		this.number=number;
	}
	
	public String getNumber()
	{
		return this.number;
	}
	
	public Login_Sign() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 426, 431);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel name = new JLabel("\u6635\u79F0");
		name.setFont(new Font("仿宋", Font.BOLD, 18));
		name.setBounds(34, 107, 74, 33);
		contentPane.add(name);
		
		JLabel password = new JLabel("\u8D26\u53F7");
		password.setFont(new Font("仿宋", Font.BOLD, 18));
		password.setBounds(34, 168, 54, 28);
		contentPane.add(password);
		
		JLabel lblNewLabel_2 = new JLabel("\u5BC6\u7801");
		lblNewLabel_2.setFont(new Font("仿宋", Font.BOLD, 18));
		lblNewLabel_2.setBounds(34, 224, 54, 26);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u5934\u50CF");
		lblNewLabel_3.setFont(new Font("仿宋", Font.BOLD, 18));
		lblNewLabel_3.setBounds(170, 10, 74, 66);
		contentPane.add(lblNewLabel_3);
		
		//判断初始存放用户信息的目录是否有文件，如果有，直接显示最后注册的那个人
		String[] s=new String[3]; //user_name,user_num,user_password;
		//先获得该目录下所有文件
		List<String> files = new ArrayList<String>();
        File file = new File("../audioPlayer/user");
        File[] tempList = file.listFiles();
        if(tempList.length != 0)
        {
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
        //默认第一个为最小，往后修改
        String f=files.get(0);
        File one=new File(f);
        for(int i=1;i<files.size();i++)
        {
        	File two=new File(files.get(i));
        	if(one.lastModified()<two.lastModified())
        	{
        		one=two;
        	}
        }
        //锁定目前最新注册的用户
		file=new File(one.getPath());
		try {
			if(file.exists())
			{
			BufferedReader br = new BufferedReader(new InputStreamReader(  
	                new FileInputStream(file)));  
			s[0]=one.getPath().substring(one.getPath().lastIndexOf("\\")+1);
			s[0]=s[0].substring(0, s[0].lastIndexOf("."));
			int i=1;
	        for (String line = br.readLine(); line != null; line = br.readLine()) {  
	            s[i]=line;
	            i+=1;
	        }  
	        br.close();
	        System.out.println("ok");}
		} catch (Exception e) {
			System.out.println(e);
		}
        }
        else {
			
		}
		
		edit_name = new JTextField(s[0]);
		edit_name.setFont(new Font("仿宋", Font.BOLD, 18));
		edit_name.setBounds(108, 109, 266, 31);
		contentPane.add(edit_name);
		edit_name.setColumns(10);
		
		edit_num = new JTextField(s[1]);
		edit_num.setFont(new Font("仿宋", Font.BOLD, 18));
		edit_num.setColumns(10);
		edit_num.setBounds(108, 165, 266, 31);
		contentPane.add(edit_num);
		
		edit_password = new JTextField(s[2]);
		edit_password.setFont(new Font("仿宋", Font.BOLD, 18));
		edit_password.setColumns(10);
		edit_password.setBounds(108, 221, 266, 29);
		contentPane.add(edit_password);
		
		JButton btn_login= new JButton("\u767B\u5F55");
		btn_login.setFont(new Font("仿宋", Font.BOLD, 18));
		btn_login.setBounds(44, 304, 93, 31);
		contentPane.add(btn_login);
		
		JButton btn_sign = new JButton("\u6CE8\u518C");
		btn_sign.setFont(new Font("仿宋", Font.BOLD, 18));
		btn_sign.setBounds(281, 304, 93, 31);
		contentPane.add(btn_sign);
		
		btn_sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//连接数据库
				Database signDatabase=new Database();
				//获取输入内容
				String name=edit_name.getText();
				String number=edit_num.getText();
				String password=edit_password.getText();
				//账号/昵称/密码/好友列表名/歌单
				String sql="INSERT INTO user_info VALUES('"+number+"','"+name+"','"
				+password+"','"+number+"','"+number+"')";
				//用户信息注册
				String db="audio_user";
				signDatabase.InsertOrder(sql,db);
				//构建歌单表----歌单默认构造  本地音乐/我的收藏
				PlaylistFunc plfFunc=new PlaylistFunc();
				plfFunc.CreatePlaylist(number);
				
				//同时创建歌曲表
				Songs ssign=new Songs();
				ssign.CreateSongList(number, "本地歌单");
				ssign.CreateSongList(number, "我的收藏");
				
				//构建好友表(number name remark)
				friend.CreateFriendList(number);
			
				//创建本地用户登录表
				String path=name+".txt";
				File file=new File("../audioPlayer");
				if(!file.exists()){//如果文件夹不存在
					file.mkdir();//创建文件夹
				}
				file=new File("../audioPlayer/user");
				if(!file.exists()){
					file.mkdir();
				}
				File f=new File("../audioPlayer/user",path);
				try {
					if (!f.exists())
					{f.createNewFile();
						FileWriter fw=new FileWriter(f);
						BufferedWriter bw= new BufferedWriter(fw);
						bw.write(number);
						bw.newLine();
						bw.write(password);
						bw.flush();
						bw.close();}
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("写入用户数据失败");
				}
			}
		});
		
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//连接数据库
				Database signDatabase=new Database();
				//获取屏幕内容
				String name=edit_name.getText();
				String number=edit_num.getText();
				String password=edit_password.getText();
				String sql="SELECT * FROM  user_info WHERE number='"+number+"'";
				String db="audio_user";
				signDatabase.QueryInfo(sql,db);
				if (number.equals(signDatabase.getNumber()) && 
						password.equals(signDatabase.getPassword()))
				{
					setNumber(number);
					System.out.println("登录时用户是"+getNumber());
					JOptionPane.showMessageDialog(null, "登录成功");
					MainWindow pl = new MainWindow(number);
					pl.setVisible(true);
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "登录失败");
				}
				
		}
	});
		JLabel lblNewLabel_4 = new JLabel("\u65B0\u7528\u6237\u70B9\u51FB");
		lblNewLabel_4.setFont(new Font("仿宋", Font.BOLD, 18));
		lblNewLabel_4.setBounds(281, 267, 93, 26);
		contentPane.add(lblNewLabel_4);
	}
}
