package yinyin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class FriendDisplay 
{
	// 以下面板属于公用
	// 底层panel
	JPanel friendPage1;
	
	// 以下面板属于好友列表
	// 底层pane
	ScrollPane scrollPane;
	// 第二层panel
	JPanel friendPage2;
	
	// 以下面板属于私信
	// 右侧的好友panel
	MessageComponent messageComponent;
	// 传入的参数为主界面的panel 好友界面将会嵌在里面
	FriendDisplay(JPanel mPanel)
	{
		// 创建对象
		friendPage1 = new JPanel();
		scrollPane = new ScrollPane();
		friendPage2 = new JPanel();
		messageComponent = new MessageComponent(friendPage1);
		
		// 逐层添加
		friendPage1.setBounds(0, 0, 1080, 640);
		friendPage1.setLayout(null);
		mPanel.add(friendPage1);
		
		// 逐层添加
		scrollPane.setForeground(Color.WHITE);
		scrollPane.setBounds(10,10,212,630);
		friendPage1.add(scrollPane);
				
		// 逐层添加
		BoxLayout bl = new BoxLayout(friendPage2, BoxLayout.Y_AXIS);
		friendPage2.setLayout(bl);
		friendPage2.setBounds(0,0,212,630);
		scrollPane.add(friendPage2);
		scrollPane.setBackground(Color.WHITE);
		friendPage1.setBackground(Color.WHITE);
		friendPage2.setBackground(Color.WHITE);
		// 至此，基本好友界面搭建完毕
		// 接下来处理好友列表的加载
	}
			
		/** 往好友列表里添加一名好友 提供头像和用户名 测试函数先用头像的路径*/
		public void AddAFriend(String imgPath, String name)
		{		
			FriendComponent fc = new FriendComponent(friendPage2,messageComponent);
			fc.SetImgButton(imgPath);
			fc.SetNameLabel(name);
		}
			
}

/** 好友列表面板 （一行）（在左侧） */
class FriendComponent
{
	// 外层label
	JLabel label;
	// 外层panel
	JPanel panel;
	// 头像
	JButton imgButton;
	// 用户名
	JLabel nameLabel;
	
	// 私信面板
	MessageComponent messageComponent;
	// 开关私信面板
	int isOpen;
	
	FriendComponent(JPanel mPanel, MessageComponent mc)
	{
		// 初始化
		label = new JLabel();
		panel = new JPanel();
		imgButton = new JButton();
		nameLabel = new JLabel();
		isOpen = 0;

		// label设置
		label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		// panel设置
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.addMouseListener(new MouseAdapter() 
		{
			public void mouseEntered(MouseEvent e) 
			{
				EnterChangeColor();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ExitChangeColor();
			}
			
			public void mouseClicked(MouseEvent e) 
			{
				ClickOpenMessagePage();
			}
		});
		
		// 设置位置
		label.setMaximumSize(new Dimension(185,60));
		panel.setBounds(0,0,185,60);
		
		imgButton.setLocation(0, 10);
		imgButton.setSize(50, 40);
		
		nameLabel.setLocation(60, 0);
		nameLabel.setSize(115,50);
		
		// 添加控件
		label.add(panel);
		panel.add(imgButton);
		panel.add(nameLabel);
		mPanel.add(label);
		
		// 私信面板引用获取
		messageComponent = mc;
	}
	
	
	public void SetImgButton(String imgPath) 
	{
		PicToIcon.ManageImageIcon(imgPath,this.imgButton);
	}
	
	public void SetNameLabel(String name)
	{
		this.nameLabel.setText(name);
	}
	
	private void EnterChangeColor()
	{
		panel.setBackground(new Color(222,222,222));
	}
	
	private void ExitChangeColor() 
	{
		panel.setBackground(null);
	}
	
	private void ClickOpenMessagePage()
	{
		if(isOpen % 2 == 0)
		{
			messageComponent.OpenMessageDisplay(nameLabel.getText());
			isOpen++;
		}
		else
		{
			messageComponent.CloseMessageDisplay();
			isOpen++;
		}	
		
	}
}

/** 私信列表面板（在右侧）*/
class MessageComponent
{
	// 底层panel
	JPanel panel;
	
	// 私信对象名称
	JLabel label;
	
	// 消息面板
	JPanel messagePanel;
	// 消息面板滚动条
	JScrollPane scrollPane;
	// 消息面板内容
	JTextPane textPane;
	
	// 输入框
	JTextField inputField;
	// 输入按钮
	JButton sendButton;
	
	MessageComponent(JPanel mPanel)
	{
		panel = new JPanel(null);
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label = new JLabel();
		messagePanel = new JPanel(null);
		textPane = new JTextPane();
		scrollPane = new JScrollPane(textPane);
		inputField = new JTextField();
		sendButton = new JButton();
		
		panel.setBounds(232,10,830,630);
		
		label.setBounds(0,0,830,20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(new Color(1, 89, 78));
		
		messagePanel.setBounds(0,20,830,560);
		messagePanel.setBackground(SystemColor.gray);
		
		inputField.setBounds(0,600,600,20);
		
		sendButton.setText("发送");
		sendButton.setBounds(700,600,80,20);
		sendButton.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// 获取输入框文本
				String inputStr = inputField.getText();
				if (inputStr == null) 
					return;
				else 
				{
					// 修改文本格式
					inputStr = "我：" + inputStr;
					// 获取当前时间
					String timeStr = GetTime();
					// 保存在本地的格式
					String saveStr = timeStr + "|" + inputStr;
					Document document = textPane.getDocument();
					try 
					{
						document.insertString(document.getLength(), "   " +timeStr + "\n", TimeStyle());
						document.insertString(document.getLength(), "   " +inputStr + "\n", TextStyle());
						UpdateLocalRecord(saveStr);
					}
					catch (BadLocationException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		
		InitMessagePanel();
	
		panel.add(label);
		panel.add(messagePanel);
		panel.add(inputField);
		panel.add(sendButton);
		
		panel.setVisible(false);
		mPanel.add(panel);
	}
	
	// 消息面板的初始化
	public void InitMessagePanel()
	{
		messagePanel.add(scrollPane);
		scrollPane.setSize(messagePanel.getWidth(), messagePanel.getHeight());
		textPane.setSize(messagePanel.getWidth(), messagePanel.getHeight());
		textPane.setEditable(false);
	}
	
	
	/** textpane的样式绑定-时间格式 */
	public SimpleAttributeSet TimeStyle()
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.blue);
		StyleConstants.setFontSize(set, 10);
		return set;
	}
	
	/** textpane的样式绑定-文本格式 */
	public SimpleAttributeSet TextStyle()
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.black);
		StyleConstants.setFontSize(set, 16);
		return set;
	}
	// 打开私信面板
	public void OpenMessageDisplay(String name)
	{
		panel.setVisible(true);
		label.setText(name);
		label.repaint();
		LoadLocalRecord();
	}
	
	// 关闭私信面板
	public void CloseMessageDisplay()
	{
		panel.setVisible(false);
		try
		{
			Document document = textPane.getDocument();
			document.remove(0, document.getLength());
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	// 本地聊天记录读取
	public void LoadLocalRecord()
	{
		try 
		{
			// 打开文件流
			FileReader fr = new FileReader("../mll.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String rowString = null;
			while (( rowString = br.readLine()) != null) 
			{
				String [] str = rowString.split("\\|");
				Document document = textPane.getDocument();
				document.insertString(document.getLength(), "   " + str[0] + "\n", TimeStyle());
				document.insertString(document.getLength(), "   " + str[1] + "\n", TextStyle());
			}
			br.close();
			fr.close();
			System.out.print("数据读取成功\n");
		}
		catch (Exception e) 
		{
			System.out.print(e.toString());
		}
	}
	
	// 本地聊天记录存储
	public void UpdateLocalRecord(String saveStr)
	{
		// 打开文件
		File file = new File("../mll.txt");
		try 
		{
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\r\n" + saveStr);
			bw.flush();
			bw.close();
			fw.close();
					
		} 
		catch (Exception e) 
		{
			System.out.print(e.toString());
		}
	}
	
	// 读取当前时间
	public String GetTime()
	{
		Calendar calendar = Calendar.getInstance();
		String str = Integer.toString(calendar.get(Calendar.YEAR)) + "." +
				Integer.toString(calendar.get(Calendar.MONTH)) + "."
				+ Integer.toString(calendar.get(Calendar.MONTH)) + " " +
				Integer.toString(calendar.get(Calendar.HOUR)) + ":" +
				Integer.toString(calendar.get(Calendar.SECOND));
		
		return str;
	}
}


