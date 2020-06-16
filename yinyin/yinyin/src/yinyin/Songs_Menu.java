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

/**�Ҽ�����������ֵĲ˵���*/
//������ ɾ��/�ƶ�
public class Songs_Menu {
	private String db="audio_songs";
	private String number;
	private String playlist_name;
	private String song_name;
	private JTable table;
	private JPopupMenu jp=new JPopupMenu();
	private JMenuItem delItem=new JMenuItem("ɾ������");
	private JMenuItem moveItem=new JMenuItem("�ƶ�����");
	private JMenuItem addItem=new JMenuItem("�������赥");
	
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
	
	/**�˵�չʾ����*/
	public void showMenu(Component invoker, int x, int y) {
		jp.add(delItem);
		jp.add(moveItem);
		jp.add(addItem);
		jp.setVisible(true);
		
		class ItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JMenuItem menuItem = (JMenuItem) e.getSource();//��ȡ������Ĳ˵��� 
				System.out.println("���������ǲ˵��" + menuItem.getText());
				
				if(menuItem.getText().equals("ɾ������"))
				{
					//ɾ���ݿ�
					Songs sdb=new Songs();
					sdb.DelSong(number, playlist_name, song_name);
					//ɾ���ڱ���е���һ��
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
				else if (menuItem.getText().equals("�ƶ�����")) {
					//�ƶ�
					//�������޸ģ���������ȥ�ģ����������ݿ�
					int rows=table.getRowCount();
					//���Ȳ�ѯ���û����еĸ赥�����ų���ǰ�赥
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
							int index=JOptionPane.showOptionDialog ( null, " ѡ������ĸ赥","�����ƶ�",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
									 null, options, options[0] ) ;
							
							//�������ݿ�
							Songs sdb=new Songs();
							sdb.MoveSong(number, playlist_name, (String)options[index], song_name, (String)table.getValueAt(i, 1),
									(String)table.getValueAt(i, 2) ,(String)table.getValueAt(i, 3),(String)table.getValueAt(i, 4),(String)table.getValueAt(i, 5));
							
							//�ٶ�Ӧ����2�����
							//��ɾ��������еĶ�Ӧ��
							DefaultTableModel model= (DefaultTableModel) table.getModel();
							model.removeRow(i);
							model.setRowCount(table.getRowCount()-1 );
							table.revalidate();
							
							//�������µ�table��������
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
					//�����������赥��ͬʱ���赥Ҳ�����ø���
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
							int index=JOptionPane.showOptionDialog ( null, " ѡ�����ĸ赥","�������",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
									 null, options, options[0] ) ;
							//�������ݿ�
							Songs sdb=new Songs();
							sdb.AddSong(number, (String)options[index], song_name, (String)table.getValueAt(i, 1),(String)table.getValueAt(i, 4),(String)table.getValueAt(i, 5));
							//ͬʱ����table
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
							//��ȡҪ�����ı��
							JTable selectedTable=tables.get(a);
							Vector<String> rowData=new Vector<String>();
							for(int j=0;j<table.getColumnCount();j++)
							{
								rowData.add((String)table.getValueAt(table.getSelectedRow(), j));
							}
							//�Ȱ����е����ݶ���ȡ
							DefaultTableModel model= (DefaultTableModel) selectedTable.getModel();
							Vector<Vector> content=model.getDataVector();
							content.add(rowData);
							//���°��������ݶ�����,��ˢ�±��
							String[] head= {"����","����","����","�ղ�","ר����","ʱ��"};
							Vector<String> headVector=new Vector<String>(Arrays.asList(head));
							DefaultTableModel dtm1=new DefaultTableModel();
							dtm1.setDataVector(content, headVector);
							selectedTable.setModel(dtm1);
							selectedTable.validate();
							selectedTable.updateUI();
							//���¼���
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
