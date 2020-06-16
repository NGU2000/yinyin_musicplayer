package yinyin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**右键点击歌曲出现的菜单类*/
//主操作 删除/移动
public class Songs_Menu {
	private String db="audio_songs";
	private String number;
	private String playlist_name;
	private String song_name;
	private JTable table;
	private JPopupMenu jp=new JPopupMenu();
	private JMenuItem delItem=new JMenuItem("删除歌曲");
	private JMenuItem moveItem=new JMenuItem("移动歌曲");
	private JMenuItem addItem=new JMenuItem("增加至歌单");
	
	public void setNumber(String number) {
		this.number=number;
	}
	
	public void setPlaylist_name(String playlist_name) {
		this.playlist_name=playlist_name;
	}
	
	public void setSong_name(String song_name) {
		this.song_name=song_name;
	}
	
	public String getNumber() {
		return this.number;
	}
	
	public String getPlaylist_name() {
		return this.playlist_name;
	}
	
	public String getSong_name() {
		return this.song_name;
	}
	
	public Songs_Menu(JTable table)
	{
		this.table=table;
	}
	
	/**菜单展示函数*/
	public void showMenu(Component invoker, int x, int y) {
		jp.add(delItem);
		jp.add(moveItem);
		jp.add(addItem);
		jp.setVisible(true);
		
		class ItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JMenuItem menuItem = (JMenuItem) e.getSource();//获取所点击的菜单项 
				System.out.println("您单击的是菜单项：" + menuItem.getText());
				
				if(menuItem.getText().equals("删除歌曲"))
				{
					//删数据库
					Songs sdb=new Songs();
					sdb.DelSong(number, playlist_name, song_name);
					//删除在表格中的那一行
					int rows=table.getRowCount();
					for(int i=0;i<rows-1;i++)
					{
						if(table.getValueAt(i, 0).equals(song_name))
						{
							DefaultTableModel model= (DefaultTableModel) table.getModel();
							model.removeRow(i);
							model.setRowCount(table.getRowCount()-1 );
							table.revalidate();
						}
					}
				}
				else if (menuItem.getText().equals("移动歌曲")) {
					//移动
					//弹出框修改，到索引中去改，最后更新数据库
					int rows=table.getRowCount();
					//首先查询该用户所有的歌单，并排除当前歌单
					PlaylistFunc pfFunc=new PlaylistFunc();
					Vector<String> v=pfFunc.getAlllist(number);
					v.remove(playlist_name);
					Object[] options=new Object[v.size()];
					for(int i=0;i<v.size();i++)
					{
						options[i]=v.get(i);
					}
					for(int i=0;i<rows-1;i++)
					{
						if(table.getValueAt(i, 0).equals(song_name))
						{
							int index=JOptionPane.showOptionDialog ( null, " 选择移入的歌单","歌曲移动",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
									 null, options, options[0] ) ;
							
							//更新数据库
							Songs sdb=new Songs();
							sdb.MoveSong(number, playlist_name, (String)options[index], song_name, (String)table.getValueAt(i, 1),
									(String)table.getValueAt(i, 2) ,(String)table.getValueAt(i, 3),(String)table.getValueAt(i, 4),(String)table.getValueAt(i, 5));
							
							//再对应更新2个表格
							//先删除本表格中的对应行
							DefaultTableModel model= (DefaultTableModel) table.getModel();
							model.removeRow(i);
							model.setRowCount(table.getRowCount()-1 );
							table.revalidate();
							
							//在锁定新的table进行增加
							MainWindow main=new MainWindow(number);
							JTabbedPane tabbedPane=main.getTabpane();
							int len=tabbedPane.getTabCount();
							int a=0;
							for(a=0;a<len;a++)
							{
								if(tabbedPane.getTitleAt(a).equals((String)options[index]))
								{break;}
							}
							JTable selectedTable=main.tables.get(a);
							System.out.println(selectedTable.getName());
							DefaultTableModel model1= (DefaultTableModel) selectedTable.getModel();
							Vector<String> rowData=new Vector<String>();
							for(int j=0;j<table.getColumnCount();j++)
							{
								rowData.add(table.getValueAt(i, j).toString());
								System.out.println(table.getValueAt(i, j).toString());
							}
							
							model1.addRow(rowData);
							selectedTable.revalidate();
							main.tables.set(a, selectedTable);
						}
					}

				}
				else {
					//加入至其他歌单，同时本歌单也保留该歌曲
					int rows=table.getRowCount();
					PlaylistFunc pfFunc=new PlaylistFunc();
					Vector<String> v=pfFunc.getAlllist(number);
					v.remove(playlist_name);
					Object[] options=new Object[v.size()];
					for(int i=0;i<v.size();i++)
					{
						options[i]=v.get(i);
					}
					for(int i=0;i<rows-1;i++)
					{
						if(table.getValueAt(i, 0).equals(song_name))
						{
							int index=JOptionPane.showOptionDialog ( null, " 选择加入的歌单","歌曲添加",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
									 null, options, options[0] ) ;
							//更新数据库
							Songs sdb=new Songs();
							sdb.AddSong(number, (String)options[index], song_name, (String)table.getValueAt(i, 1),(String)table.getValueAt(i, 4),(String)table.getValueAt(i, 5));
							//同时更新table
							MainWindow main=new MainWindow(number);
							JTabbedPane tabbedPane=main.getTabpane();
							int len=tabbedPane.getTabCount();
							int a=0;
							for(a=0;a<len;a++)
							{
								if(tabbedPane.getTitleAt(a).equals((String)options[index]))
								{break;}
							}
							
							Vector<JTable> tables=main.getTables();
							//获取要操作的表格
							JTable selectedTable=tables.get(a);
							Vector<String> rowData=new Vector<String>();
							for(int j=0;j<table.getColumnCount();j++)
							{
								rowData.add((String)table.getValueAt(table.getSelectedRow(), j));
							}
							//先把所有的数据都提取
							DefaultTableModel model= (DefaultTableModel) selectedTable.getModel();
							Vector<Vector> content=model.getDataVector();
							content.add(rowData);
							//重新把所有数据都加入,并刷新表格
							String[] head= {"歌曲","歌手","下载","收藏","专辑名","时长"};
							Vector<String> headVector=new Vector<String>(Arrays.asList(head));
							DefaultTableModel dtm1=new DefaultTableModel();
							dtm1.setDataVector(content, headVector);
							selectedTable.setModel(dtm1);
							selectedTable.validate();
							selectedTable.updateUI();
							//重新加入
							main.tables.set(a, selectedTable);
							main.getScrollpanes().get(a).validate();
							main.getScrollpanes().get(a).updateUI();
							break;
						}
					}
				}
			}
		}
		
		delItem.addActionListener(new ItemListener ());
		moveItem.addActionListener(new ItemListener());
		addItem.addActionListener(new ItemListener());
		jp.show(invoker,x,y);
		
	}
	
}
