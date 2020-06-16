package yinyin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabExpander;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import java.awt.Scrollbar;
import java.awt.SystemColor;


public class MainWindow extends JFrame {
	//主面板
	JPanel contentPane;
	public FriendDisplay fd;
	private JPanel FriendPage = new JPanel();
	private User user = new User();
	private static int count=0;
	private JTabbedPane tabbedPane;

	private Vector<JScrollPane> scrollPanes=new Vector<JScrollPane>();
	public Vector<JTable> tables=new Vector<JTable>();
	private PlaylistFunc pl=new PlaylistFunc();
	private Songs songs=new Songs();
	JButton btn_add_pl = new JButton("\u65B0\u5EFA\u6B4C\u5355");
	//详细歌曲信息面板
	private JPanel musicexactInfoJPanel = new JPanel();
	
	//歌词面板
	private JScrollPane lyricJScrollPane = new JScrollPane();
	private JTextPane lyricTextPane = new JTextPane();
	//歌曲海报显示
	private JLabel bigpicJLabel = new JLabel();
	
	//左下角显示歌曲信息面板
	private JPanel musicInfoJPanel = new JPanel();
	private JLabel picJLabel = new JLabel();//显示图片
	private JLabel musicnJLabel = new JLabel();//显示歌曲名
	private JLabel singerJLabel = new JLabel();//显示歌手名
		
	//记录时间
	private int min = 0;
	private int sec = 0;
	
	//记录上一首歌，现在的歌
	private int foreindex = -1;
	private int curindex = -1;
	private int index = -1;
	
	//播放滑块
	private JSlider playSlider = new JSlider();
	
	//音量滑块
	private JSlider voiceJSlider;
	
	//初始化ICON
	private	ImageIcon pauseIcon = new ImageIcon();
	private	ImageIcon playIcon = new ImageIcon();
	private	ImageIcon nextIcon = new ImageIcon();
	private ImageIcon circleIcon = new ImageIcon();
	private ImageIcon foreIcon = new ImageIcon();
	private ImageIcon orderIcon = new ImageIcon();
	private ImageIcon randomIcon = new ImageIcon();
	private ImageIcon createIcon = new ImageIcon();
	private ImageIcon friendIcon = new ImageIcon();
	private ImageIcon backIcon = new ImageIcon();
	
	//播放音乐
	private MediaPlayer mediaplayer = new MediaPlayer();
	//登录的用户
	private static String number="1";
	
	//记录歌曲总数
	private int song_num = 0;
	//存放歌曲信息
	private Object[][] data = new Object[100][5];
	
	//存放歌词信息
	private Object[][] lyric;
	
	//播放按钮
	private JButton playButton = new JButton();
	
	//下一首按钮
	private JButton nextButton = new JButton();
	
	//前一首按钮	
	private JButton foreButton = new JButton();
	
	//返回按钮
	private JButton backButton = new JButton();
	
	//记录播放模式
	private JButton styleButton = new JButton();
	
	//显示时间
	private JLabel totaltimeLabel = new JLabel("0:00");
	private JLabel nowtimeLabel = new JLabel("0:00");
	
	//时间控件
	private TimerActioner timerActioner = new TimerActioner();
	private Timer timer = new Timer(1000,timerActioner);
	
	//判断是否有音乐在缓存中
	private boolean HasMusic = false;
	
	//记录当前的播放状态
	private boolean IsPlay = false;
	
	//设置播放模式
	private int style = 0 ;
	
	
	//开启显示歌词线程
	private Thread showThread = new Highlight();
	
	public static void setNumber(String number){
		number=number;
	}
	public static String getNumber()
	{
		return number;
	}
	
	public void setTabPane(JTabbedPane tabbedPane)
	{
		this.tabbedPane=tabbedPane;
	}
	
	public JTabbedPane getTabpane() {
		return this.tabbedPane=tabbedPane;
	}
	
	public void setTables(Vector<JTable> tables)
	{
		this.tables=tables;
	}
	
	public Vector<JTable> getTables() {
		return this.tables;
	}
	
	public void setScrollpanes(Vector<JScrollPane> scrollPanes)
	{
		this.scrollPanes=scrollPanes;
	}
	
	public Vector<JScrollPane> getScrollpanes() {
		return this.scrollPanes;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(number);
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
	public  MainWindow(String number) {
 {
	 	setNumber(number);
	 	System.out.println("现在登录的用户是："+number);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 30, 1080, 780);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(musicexactInfoJPanel);
		contentPane.add(FriendPage);
		playSlider.setBackground(Color.WHITE);
		//读取好友列表到用户类中
		String db = "audio_friend";
		String order = "SELECT * FROM f1";
		Database database=new Database();
		database.QueryInfo(order, db);
		user.AddFriend(database.getNumber(), database.getName(),database.getRemark(), "../Image/1.jpg");
		
		//将歌单初始化展现
		Vector<String> v=pl.getAlllist(number);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(10, 42, 1040, 626);
		contentPane.add(tabbedPane);
		
		//选项面板响应线程---弹出菜单
		Tabpane_func tf=new Tabpane_func();
		tf.setPane(tabbedPane);
		tf.setNumber(number);
		Thread tt=new Thread(tf);
		tt.start();
		
		JButton FriendBtn = new JButton();
		FriendBtn.setBounds(1005, 0, 45, 30);
		FriendBtn.setBackground(Color.WHITE);
		
		PicToIcon.ManageImageIcon("../Image/friend.png", FriendBtn);
		contentPane.add(FriendBtn);
		JPanel FriendPage = new JPanel();
		
		FriendPage.setVisible(false);
		FriendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				count++;
				if(count%2==0)
				{
					tabbedPane.setVisible(true);
					FriendPage.setVisible(false);
					btn_add_pl.setVisible(true);
				}
				else {
					btn_add_pl.setVisible(false);
					tabbedPane.setVisible(false);
					FriendPage.setVisible(true);
					FriendPage.setBackground(SystemColor.desktop);
					FriendPage.setBounds(10,34,1080,640);
					FriendPage.setLayout(null);
					contentPane.add(FriendPage);
					fd = new FriendDisplay(FriendPage);
					for (Friend friend : user.GetFriendList())
						fd.AddAFriend(friend.GetImagePath(), friend.GetName());
				}
				}
		});
		
		
		
		//新建歌单
		btn_add_pl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//先在数据库加入,在vector中加入
				pl.AddPlaylist(number, "新增歌单");
				scrollPanes.add(new JScrollPane());
				//表格头部要有
				String[] n= {"歌曲","歌手","下载","收藏","专辑名","时长"};
				Vector<String> name=new Vector<String>();
				for(int j=0;j<n.length;j++)
				{
					name.add(n[j]);
				}
				//内容----数据库读取
				//声明动态二维
				Vector<Vector<String>> content=new Vector<Vector<String>>();
				DefaultTableModel dtm=new DefaultTableModel(content,name);
				JTable newTable=new JTable(dtm)
						//防编辑
						{
							public boolean isCellEditable(int row, int column)
							{
								return false;
							}
							};
				tables.add(newTable);
				//给该table绑定监听事件
				Song_table st=new Song_table();
				st.setTable(newTable);
				Thread t=new Thread(st);
				t.start();
				int len1,len2;
				len1=scrollPanes.size()-1;
				len2=tables.size()-1;
				scrollPanes.get(len1).setViewportView(tables.get(len2));
				tabbedPane.addTab(v.get(v.size()-1), null, scrollPanes.get(len1), null);
			}
		});
		btn_add_pl.setBounds(161, 3, 45, 35);
		setButton(btn_add_pl, "../Image/create.png");
		contentPane.add(btn_add_pl);
		
		//展示歌单中的歌曲
		for(int i=0;i<v.size();i++)
		{
			scrollPanes.add(new JScrollPane());
			String[] n= {"歌曲","歌手","下载","收藏","专辑名","时长"};
			Vector<String> name=new Vector<String>();
			for(int j=0;j<n.length;j++)
			{
				name.add(n[j]);
			}
			
			//声明动态二维获取某用户歌单的全部歌曲 
			Vector<Vector<String>> content=new Vector<Vector<String>>();
			System.out.println(v.get(i));
			if(v.get(i).equals("本地歌曲"))
			{
				GetSongInfo getSongInfo=new GetSongInfo();
				try {
					content=getSongInfo.returnInfo(getSongInfo.path);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else {
				content=songs.getALLsongs(number, v.get(i));
			}
			
			//声明一维
			Vector<String>temp=new Vector<String>();
			content.add(temp);
			DefaultTableModel dtm=new DefaultTableModel(content,name);
			JTable item=new JTable(dtm)
			//防编辑
			{
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
				};
			tables.add(item);
			//给每一个table绑定监听函数
			Song_table st=new Song_table();
			st.setNumber(number);
			st.setPlaylist_name(v.get(i));
			st.setTable(item);
			Traverse(item);
			item.addMouseListener(new MouseListener(){
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getButton() == MouseEvent.BUTTON1 && item.getSelectedColumn() == 0)
					{
						//点击初始化
						Traverse(item);
						min = 0;
						sec = 0;
						
						if(HasMusic == true)
						{
						
							timer.start();
							foreindex = curindex;
							curindex = item.getSelectedRow();
						}
						else {
							timer.start();
							curindex = item.getSelectedRow();
						}
						
						//设置变量
						HasMusic = true;
						IsPlay = true;
						
						try {
							
							ShowMusicInfo();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						//打开时间控制器
						if(HasMusic!=false)
						{
						
							mediaplayer.Play("../music/"+item.getValueAt(item.getSelectedRow(),0).toString()+".mp3");
						}
						else {
							mediaplayer.Play("../music/"+item.getValueAt(item.getSelectedRow(),0).toString()+".mp3");
						}
						
						//开启线程
						if(showThread!=null)
						{
							showThread.stop();
							showThread = new Highlight();
							showThread.start();			
						}
						else {
							
							showThread.start();
						}
					
						
						playButton.setIcon(playIcon);
					
						//设置滑块
						playSlider.setMinimum(0);
						playSlider.setMaximum(Integer.parseInt(data[curindex][4].toString()));
						//修改Label
						totaltimeLabel.setText(data[curindex][3].toString());
						nowtimeLabel.setText("0:00");
						
					}
				}
			});
			Thread t=new Thread(st);
			t.start();
			scrollPanes.get(i).setViewportView(tables.get(i));
			tabbedPane.addTab(v.get(i), null, scrollPanes.get(i), null);
		}
		
		//设置按钮位置
		playButton.setBounds( 100, 675, 50, 50);
		nextButton.setBounds( 160, 680, 40, 40);
		foreButton.setBounds(40, 680, 40, 40);
		styleButton.setBounds(230,685,30,30);
		backButton.setBounds(20,20,40,40);
		playIcon =  changeIcon("../Image/play.png",playButton );
		circleIcon = changeIcon("../Image/circle.png", styleButton);
		orderIcon = changeIcon("../Image/order.png", styleButton);
		pauseIcon =  changeIcon("../Image/pause.png",playButton );
		randomIcon = changeIcon("../Image/random.png",styleButton);
		backIcon = changeIcon("../Image/back.png", backButton);
		//设置按钮相关情况
		setButton(nextButton, "../Image/next.png");
		setButton(playButton, "../Image/play.png");
		setButton(foreButton,"../Image/fore.png");
		setButton(styleButton,"../Image/circle.png");
		backButton.setIcon(backIcon);
		//播放事件
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//判断是否有音乐在缓存中
				if(HasMusic)
				{
				IsPlay = !IsPlay;
				if(IsPlay == false)
				{
					mediaplayer.Pause();
					playButton.setIcon(pauseIcon);
					timer.stop();
				}
				else {
					mediaplayer.Play();
					playButton.setIcon(playIcon);
					timer.start();
				}
				}
			}
		});
		//设置播放模式
		styleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				style++;
				style = style%3;
				switch (style) {
				case 0: {
					styleButton.setIcon(circleIcon);
					break;
				}
				case 1:
				{
					styleButton.setIcon(orderIcon);
					break;
				}
				case 2:
				{
					styleButton.setIcon(randomIcon);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + style);
				}
			}
		});
		//下一首事件
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//判断是否有歌曲播放
				if(HasMusic)
				{
					//判断播放模式
					if(style== 0){
						min = 0;
						sec = 0;
						playButton.setIcon(playIcon);
						mediaplayer.Play("../music/"+data[curindex][0]+".mp3");
					}
					else if(style == 1){
						min = 0;
						sec = 0;
						playButton.setIcon(playIcon);
						foreindex = curindex;
						curindex++;
						if(curindex == song_num)
						{
							curindex = 0;
						}
						mediaplayer.Play("../music/"+data[curindex][0]+".mp3");
						totaltimeLabel.setText(data[curindex][3].toString());
						playSlider.setMinimum(0);
						playSlider.setMaximum(Integer.valueOf(data[curindex][4].toString()));
					}
					else  if(style == 2){
						min = 0;
						sec = 0;
						playButton.setIcon(playIcon);
						foreindex = curindex;
						curindex = (int)(Math.random()*song_num);
						mediaplayer.Play("../music/"+data[curindex][0]+".mp3");
						totaltimeLabel.setText(data[curindex][3].toString());
						playSlider.setMinimum(0);
						playSlider.setMaximum(Integer.valueOf(data[curindex][4].toString()));
					}
					try {
						ShowMusicInfo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(showThread!=null)
					{
						showThread.stop();
						showThread =new Highlight();
						showThread.start();					}
					else {
						showThread.start();
					}
				}
				
			}
		});
		//上一首事件
		foreButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(HasMusic)
				{
				if(foreindex!=-1)
				{
				
					curindex = foreindex;
					foreindex = -1;
					playButton.setIcon(playIcon);
					playSlider.setMaximum(Integer.parseInt(data[curindex][4].toString()));
					totaltimeLabel.setText(String.valueOf(data[curindex][3]));
					nowtimeLabel.setText("0:00");
					try {
						ShowMusicInfo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mediaplayer.Play("../music/"+data[curindex][0].toString() +".mp3");
					if(showThread!=null)
					{
						showThread.stop();
						showThread =new Highlight();
						showThread.start();
					}
					else {
						showThread.start();
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"当前无上一首","",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			}
		});
		//返回事件
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tabbedPane.setVisible(true);
				musicexactInfoJPanel.setVisible(false);
				FriendBtn.setVisible(true);
				btn_add_pl.setVisible(true);
			}
		});
		nowtimeLabel.setBounds(300,690,40,20);
		totaltimeLabel.setBounds(800,690,40,20);
		playSlider.setBounds(350,690,450,20);
		playSlider.setValue(0);
		contentPane.add(playSlider);
		contentPane.add(nowtimeLabel);
		contentPane.add(totaltimeLabel);
		contentPane.setBackground(new Color(255,255,255));
		//小音乐窗口
		musicInfoJPanel.setBounds(840,670,240,110);
		musicInfoJPanel.setBackground(new Color(255,255,255));
		musicInfoJPanel.setLayout(null);
		musicInfoJPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		musicnJLabel.setBounds(960,680,70,20);
		picJLabel.setBounds(845,675,65,65);
		singerJLabel.setBounds(960,720,70,20);
		musicInfoJPanel.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				musicexactInfoJPanel.setVisible(true);
				tabbedPane.setVisible(false);
				FriendBtn.setVisible(false);
				btn_add_pl.setVisible(false);
			}
		});

		
		contentPane.add(singerJLabel);
		contentPane.add(musicnJLabel);
		contentPane.add(picJLabel);
		contentPane.add(musicInfoJPanel);
		
		musicexactInfoJPanel.setVisible(false);
		musicexactInfoJPanel.setBounds(0, 0, 1080, 670);
		musicexactInfoJPanel.setBackground(new Color(255,255,255));
		backButton.setBackground(new Color(255,255,255));
		musicexactInfoJPanel.add(backButton);
		musicexactInfoJPanel.setLayout(null);
		bigpicJLabel.setBounds(60,190,300,300);
		lyricJScrollPane.setBounds(470,30,490,610);
		lyricJScrollPane.setBackground(new Color(255,255,255));
		lyricTextPane.setBounds(0,0,460,610);
		lyricJScrollPane.getViewport().add(lyricTextPane);
		musicexactInfoJPanel.add(bigpicJLabel);
		
		musicexactInfoJPanel.add(lyricJScrollPane);
	  }
    }
	public void ShowMusicInfo() throws IOException, BadLocationException
	{
		//设置音乐小信息
		//设置文本标签
		musicnJLabel.setText(data[curindex][0].toString());
		singerJLabel.setText(data[curindex][1].toString());
		//缩放图片
		BufferedImage tempBufferedImage  = ImageIO.read(new File("../post/"+String.valueOf(data[curindex][0])+".jpg"));
	
		//设置大界面信息
		tempBufferedImage = resizeBufferedImage(tempBufferedImage,picJLabel.getWidth(),picJLabel.getHeight(), true);
		ImageIcon imageIcon = new ImageIcon(tempBufferedImage);
		picJLabel.setIcon(imageIcon);
		//设置图片
		tempBufferedImage  = ImageIO.read(new File("../post/"+String.valueOf(data[curindex][0])+".jpg"));
		tempBufferedImage = resizeBufferedImage(tempBufferedImage,bigpicJLabel.getWidth(),bigpicJLabel.getHeight(),true);
		imageIcon = new ImageIcon(tempBufferedImage);
		bigpicJLabel.setIcon(imageIcon);
		//获取歌词
		getLyric("../lyrics/"+data[curindex][0]+".lrc");
		showLyric();
	}
	//获取歌词函数
	public void getLyric(String filename) throws IOException {
		//清空数组
		this.lyric = new Object[100][2];
		BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(new FileInputStream(filename),Charset.defaultCharset().name()));
		String lineString = "";
		int index = 0;
		while((lineString = bufferedReader.readLine())!=null)
		{
	
			String[] timeLyric = lineString.split("]");
			//将lrc文件分为时间信息和歌词信息
			timeLyric[0] = timeLyric[0].replace("[", "");
			timeLyric[0] = timeLyric[0].replaceAll("[?]", "");
			String[] times = timeLyric[0].split(":");
			
			int min = Integer.valueOf(times[0]);
			double sec = Double.valueOf(times[1]);
			int sec_i = (int)sec;
			int totaltime = min*60+sec_i;
			this.lyric[index][0] = totaltime;
			if(timeLyric.length!=1)
			{	
			    this.lyric[index][1] = timeLyric[1];
			}
			else {
				this.lyric[index][1] = "";
			}
			index++;
		}
		bufferedReader.close();
		
	}
	//歌词显示函数
	public void showLyric() throws BadLocationException {
		int index = 0;
		String tempString = "";
		//导入歌词
		while(lyric[index][0]!=null)
		{
			tempString+=lyric[index][1].toString()+"\n\n";
			index++;
		}
		lyricTextPane.setText(tempString);
	}
	//遍历JTable数组
	public void Traverse(JTable table)
	{
		data = new Object[100][5];
		//遍历
		for(int i = 0; i<table.getModel().getRowCount();i++)
		{
			if(table.getValueAt(i, 0)==null)
			{
				song_num = i;
				break;
			}
			data[i][0] = table.getValueAt(i,0);
			data[i][1] = table.getValueAt(i, 1);
			data[i][2] = table.getValueAt(i, 4);
			data[i][3] = table.getValueAt(i, 5);
			data[i][4] = Transform(table.getValueAt(i, 5).toString());
		}
	}
	//将String转为int
	public int Transform(String string)
	{
		String [] totaltime = string.split(":");
		int time = Integer.parseInt(totaltime[0].toString())*60+Integer.parseInt(totaltime[1].toString());
		return time;
	}
    //继承
	public class Highlight extends Thread
	{
		public void run()
		{
			int index = 0;
			int start = 0;
			int end = 0;
			JScrollBar jScrollBar = lyricJScrollPane.getVerticalScrollBar();
			System.out.println("wqe--");
			System.out.println(lyric[index][0]);
			while (lyric[index][0]!=null) {
				if(Integer.parseInt(lyric[index][0].toString())<=(60*min+sec))
				{
					jScrollBar.setValue(index*35);
					Document document = lyricTextPane.getDocument();
					StyleContext sc = StyleContext.getDefaultStyleContext();
					AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);
					String strtemp;
					end = String.valueOf(lyric[index][1]).length();
					end = start+end;
					try {
						strtemp = document.getText(start, end-start);
						document.remove(start,end-start );
						document.insertString(start, strtemp, aset);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					start = end+2;
					index ++;
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void setButton(JButton jButton,String path) {
		BufferedImage tempBufferedImage;
		try {
			tempBufferedImage = ImageIO.read(new File(path));
			tempBufferedImage = resizeBufferedImage(tempBufferedImage, jButton.getWidth(), jButton.getHeight(), true);
			ImageIcon imageIcon = new ImageIcon(tempBufferedImage);
			jButton.setIcon(imageIcon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jButton.setBackground(new Color(255,255,255));
		jButton.setBorderPainted(false);
		jButton.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(jButton);
	}	
	//改变Icon
	public ImageIcon changeIcon(String path,JButton jButton) {
		BufferedImage tempBufferedImage;
		ImageIcon imageIcon = null;
		try {
			tempBufferedImage = ImageIO.read(new File(path));
			tempBufferedImage = resizeBufferedImage(tempBufferedImage, jButton.getWidth(), jButton.getHeight(), true);
			imageIcon = new ImageIcon(tempBufferedImage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageIcon;
	}	
	//缩放函数
	public BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (flag && sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else if(flag && sx <= sy){
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
	public class TimerActioner implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			//添加下一首事件
			if((sec+min*60)!= Integer.parseInt(data[curindex][4].toString()))
			{
				sec++;
			}
			else {
				//清零
				min = 0;
				sec = 0;
				//判断当前的播放模式
				//循环播放事件
				if(style == 0)
				{
					//将时间设置为0
					min = 0;
					sec = 0;
					foreindex  = curindex;
					//更改信息
					try {
						ShowMusicInfo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//重复播放
					mediaplayer.Play("../music/"+String.valueOf(data[curindex][0])+".mp3");
				}
				//顺序播放事件
				else if(style == 1)
				{
				//将时间设置为0
				min = 0;
				sec = 0;
				
				//更新歌曲
				foreindex = curindex;
				//判断是否是最后一首歌曲
				if(curindex!=song_num){
					curindex ++;
				}
				else {
					curindex = 0;
				}
				//更改信息
				try {
					ShowMusicInfo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				playSlider.setMinimum(0);
				playSlider.setMaximum(Integer.valueOf(data[curindex][4].toString()));
				//播放		
				mediaplayer.Play("../music/"+String.valueOf(data[curindex][0])+".mp3");
				playSlider.setMinimum(0);
				playSlider.setMaximum(Integer.valueOf(data[curindex][4].toString()));
				totaltimeLabel.setText(data[curindex][3].toString());
				}
				//随机播放
				else if(style == 2)
				{
					//将时间设置为0
					min = 0;
					sec = 0;
					foreindex = curindex;
					//随机寻找
					curindex = (int)(Math.random()*(index-1));
					//更改信息
					try {
						ShowMusicInfo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//播放
					mediaplayer.Play("../music/"+String.valueOf(data[curindex][0])+".mp3");
					playSlider.setMinimum(0);
					playSlider.setMaximum(Integer.valueOf(data[curindex][4].toString()));
					totaltimeLabel.setText(data[curindex][3].toString());
				}
				//开启歌曲线程
				if(showThread!=null)
				{
					showThread.stop();
					showThread =new Highlight();
					showThread.start();
				}
				else {
					showThread.start();
				}
				}
			
			while(sec>60) {
				min++;
				sec -= 60;
			}
			//progressBar.setValue(min*60 +sec);
			//重开线程
			
			nowtimeLabel.setText(String.valueOf(min)+":"+String.valueOf(sec));
			playSlider.setValue(min*60+sec);
		}
	}
	public class UnicodeReader extends Reader {


		  PushbackInputStream internalIn;
		  InputStreamReader   internalIn2 = null;
		  String              defaultEnc;
		 
		  private static final int BOM_SIZE = 4;
		 
		  
		  UnicodeReader(InputStream in, String defaultEnc) {
		     internalIn = new PushbackInputStream(in, BOM_SIZE);
		     this.defaultEnc = defaultEnc;
		  }
		 
		  public String getDefaultEncoding() {
		     return defaultEnc;
		  }
		 
		  
		  public String getEncoding() {
		     if (internalIn2 == null) return null;
		     return internalIn2.getEncoding();
		  }
		 
		  
		  protected void init() throws IOException {
		     if (internalIn2 != null) return;
		 
		     String encoding;
		     byte bom[] = new byte[BOM_SIZE];
		     int n, unread;
		     n = internalIn.read(bom, 0, bom.length);
		 
		     if ( (bom[0] == (byte)0x00) && (bom[1] == (byte)0x00) &&
		                 (bom[2] == (byte)0xFE) && (bom[3] == (byte)0xFF) ) {
		        encoding = "UTF-32BE";
		        unread = n - 4;
		     } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) &&
		                 (bom[2] == (byte)0x00) && (bom[3] == (byte)0x00) ) {
		        encoding = "UTF-32LE";
		        unread = n - 4;
		     } else if (  (bom[0] == (byte)0xEF) && (bom[1] == (byte)0xBB) &&
		           (bom[2] == (byte)0xBF) ) {
		        encoding = "UTF-8";
		        unread = n - 3;
		     } else if ( (bom[0] == (byte)0xFE) && (bom[1] == (byte)0xFF) ) {
		        encoding = "UTF-16BE";
		        unread = n - 2;
		     } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) ) {
		        encoding = "UTF-16LE";
		        unread = n - 2;
		     } else {
		        // Unicode BOM mark not found, unread all bytes
		        encoding = defaultEnc;
		        unread = n;
		     }    
		     //System.out.println("read=" + n + ", unread=" + unread);
		 
		     if (unread > 0) internalIn.unread(bom, (n - unread), unread);
		 
		     // Use given encoding
		     if (encoding == null) {
		        internalIn2 = new InputStreamReader(internalIn);
		     } else {
		        internalIn2 = new InputStreamReader(internalIn, encoding);
		     }
		  }
		 
		  public void close() throws IOException {
		     init();
		     internalIn2.close();
		  }
		 
		  public int read(char[] cbuf, int off, int len) throws IOException {
		     init();
		     return internalIn2.read(cbuf, off, len);
		  }
		}
}
