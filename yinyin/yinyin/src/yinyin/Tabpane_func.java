package yinyin;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;

/**tabѡ��࣬���������˵��������赥��*/
public class Tabpane_func implements Runnable {
	private JTabbedPane tabbedPane;
	private String number;
	
	public void setPane(JTabbedPane tabbedPane) {
		this.tabbedPane=tabbedPane;
	}
	public JTabbedPane getPane()
	{
		return this.tabbedPane;
	}
	
	public void setNumber(String number) {
		this.number=number;
	}
	public String getNumber()
	{
		return this.number;
	}
	
	public void run() {
		tabbedPane.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				//��ȡ����ĸ赥����
				String name=null;
				for(int i=0;i<tabbedPane.getTabCount();i++)
				{
					Rectangle rect=tabbedPane.getBoundsAt(i);
					if(rect.contains(e.getX(),e.getY()))
					{
						name=tabbedPane.getTitleAt(i);
					}
				}
				// ���������Ҽ�������ʾ�����˵�
                if (e.isMetaDown()) {
                	//���뵱ǰ�û������͸赥����
                	Playlist_Menu jp_menu=new Playlist_Menu(tabbedPane);
                	jp_menu.setName(name);
                	jp_menu.setNumber(number);
                	jp_menu.showMenu(e.getComponent(), e.getX(), e.getY());
                }
				
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
				
			}
		});
	}
}
