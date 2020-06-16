package yinyin;
//�����赥�Ҽ������˵�

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

/**�Ҽ�����赥�����˵���*/
public class Playlist_Menu{
	private String db="audio_playlist";
	private String number;
	private String playlist_name;
	private JTabbedPane panel;
	private JPopupMenu jp=new JPopupMenu();
	private JMenuItem delItem=new JMenuItem("ɾ���赥");
	private JMenuItem RenameItem=new JMenuItem("�������赥");
	

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
	
	/**�˵�չʾ����*/
	public void showMenu(Component invoker, int x, int y) {
		jp.add(delItem);
		jp.add(RenameItem);
		jp.setVisible(true);
		
		class ItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JMenuItem menuItem = (JMenuItem) e.getSource();//��ȡ������Ĳ˵��� 
				System.out.println("���������ǲ˵��" + menuItem.getText());
				if(menuItem.getText().equals("ɾ���赥"))
				{
					//ɾ���ݿ�
					String order="DELETE FROM p"+number+ " WHERE playlist_name='"+playlist_name+"'";
					Database dbmenu=new Database();
					dbmenu.DelOrder(order, db);
					//ɾ����
					for(int i=0;i<panel.getTabCount();i++)
					{
						if(playlist_name.equals(panel.getTitleAt(i)))
						{
							panel.removeTabAt(i);
						}
					}
				}
				else {
					//������
					//�������޸ģ���������ȥ�ģ����������ݿ�
					String newName=JOptionPane.showInputDialog("������������");
					String oldName=null;
					//�ҵ������ֵ�����
					for(int i=0;i<panel.getTabCount();i++)
					{
						if(playlist_name.equals(panel.getTitleAt(i)))
						{
							oldName=panel.getTitleAt(i);
							panel.setTitleAt(i, newName);
						}
					}
					//�������ݿ�
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
