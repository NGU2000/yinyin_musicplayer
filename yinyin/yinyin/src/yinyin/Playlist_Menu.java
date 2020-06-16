package yinyin;
//创建歌单右键弹出菜单

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**右键点击歌单弹出菜单类*/
public class Playlist_Menu{
	private String db="audio_playlist";
	private String number;
	private String playlist_name;
	private JTabbedPane panel;
	private JPopupMenu jp=new JPopupMenu();
	private JMenuItem delItem=new JMenuItem("删除歌单");
	private JMenuItem RenameItem=new JMenuItem("重命名歌单");
	

	public Playlist_Menu(JTabbedPane panel)
	{
		this.panel=panel;
	}
	
	public void setNumber(String number) {
		this.number=number;
	}
	
	public String getNumber()
	{
		return this.number;
	}
	
	public void setName(String name) {
		this.playlist_name=name;
	}
	
	public String getName()
	{
		return this.playlist_name;
	}
	
	/**菜单展示函数*/
	public void showMenu(Component invoker, int x, int y) {
		jp.add(delItem);
		jp.add(RenameItem);
		jp.setVisible(true);
		
		class ItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JMenuItem menuItem = (JMenuItem) e.getSource();//获取所点击的菜单项 
				System.out.println("您单击的是菜单项：" + menuItem.getText());
				if(menuItem.getText().equals("删除歌单"))
				{
					//删数据库
					String order="DELETE FROM p"+number+ " WHERE playlist_name='"+playlist_name+"'";
					Database dbmenu=new Database();
					dbmenu.DelOrder(order, db);
					//删索引
					for(int i=0;i<panel.getTabCount();i++)
					{
						if(playlist_name.equals(panel.getTitleAt(i)))
						{
							panel.removeTabAt(i);
						}
					}
				}
				else {
					//重命名
					//弹出框修改，到索引中去改，最后更新数据库
					String newName=JOptionPane.showInputDialog("请输入新名字");
					String oldName=null;
					//找到旧名字的索引
					for(int i=0;i<panel.getTabCount();i++)
					{
						if(playlist_name.equals(panel.getTitleAt(i)))
						{
							oldName=panel.getTitleAt(i);
							panel.setTitleAt(i, newName);
						}
					}
					//更新数据库
					String order="UPDATE p"+number+" SET playlist_name='"+newName+
							"' WHERE playlist_name='"+oldName+"'";
					Database dbmenu=new Database();
					dbmenu.UpdateOrder(order, db);
				}
			}
		}
		
		delItem.addActionListener(new ItemListener ());
		RenameItem.addActionListener(new ItemListener());
		jp.show(invoker,x,y);
		
	}
}
