import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class UserMenu extends JFrame implements ActionListener{
	static String name;
	static String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	static Connection con ;
	static Statement Sta;
	static ResultSet rs;
	static String sql;
	Vector content1,field1,content2,field2,content3;
	JPanel p1,p2,p3,p4,p5,p6,p7;
	JComboBox combo;
	JLabel label1,label2,label3,label4,label5,label6;
	static JTextField txt2;
	static JTextField txt3;
	static JTextField txt4;
	static JTextField txt5;
	static JTextField txt6;
	static int i;
	JButton btn_query,btn_del;
	public UserMenu(){
		label1 = new JLabel("��ӭ��:"+name);
		label2 = new JLabel("�������:");
		label3 = new JLabel("���ƺ���:");
		label4 = new JLabel("������ɫ:");
		label5 = new JLabel("������:");
		label6 = new JLabel("ͣ��λ��:");
		txt2 = new JTextField();
		txt3 = new JTextField();
		txt4 = new JTextField();
		txt5 = new JTextField();
		txt6 = new JTextField();
		try{
			con = DriverManager.getConnection(url,"sa","a");
			Sta = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql = "select * from ������Ϣ  where ��������  = '"+name+"'";
			rs = Sta.executeQuery(sql);
			while(rs.next()){
				txt2.setText((rs.getString(1)));	txt2.setEditable(false);
				txt3.setText((rs.getString(2)));	txt3.setEditable(false);
				txt4.setText((rs.getString(4)));	txt4.setEditable(false);
			
			}
			sql = "select * from ������Ϣ  where �������  = '"+txt2.getText()+"'";
			rs = Sta.executeQuery(sql);
			while(rs.next()){
				txt5.setText((rs.getString(1)));	txt5.setEditable(false);
				txt6.setText((rs.getString(4)));	txt6.setEditable(false);
			}
		}
		catch(Exception e){System.out.println(e);	}
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p1.add(label2);p1.add(txt2);
		p2.add(label3);p2.add(txt3);
		p3.add(label4);p3.add(txt4);
		p4.add(label5);p4.add(txt5);
		p5.add(label6);p5.add(txt6);
		p6.setLayout(new GridLayout(5,1));
		p6.add(p1);	p6.add(p2);	p6.add(p3);	p6.add(p4);	p6.add(p5);
		btn_query = new JButton("�޸�");
		btn_del = new JButton("ɾ��");
		p7.add(btn_query);	p7.add(btn_del);
		btn_query.addActionListener(this);
		btn_del.addActionListener(this);
		this.setLayout(new BorderLayout());
		
		this.add(label1,BorderLayout.NORTH);
		this.add(p6);
		this.add(p7,BorderLayout.SOUTH);
		this.setTitle("������Ϣ����ϵͳ���������ͻ���");
		this.setSize(580,520);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public static void shuaxin(){
		try{
			con = DriverManager.getConnection(url,"sa","a");
			Sta = con.createStatement();
			sql = "select * from ������Ϣ  where ��������  = '"+name+"'";
			rs = Sta.executeQuery(sql);
			while(rs.next()){
				txt2.setText((rs.getString(1)));	txt2.setEditable(false);
				txt3.setText((rs.getString(2)));	txt3.setEditable(false);
				txt4.setText((rs.getString(4)));	txt4.setEditable(false);				
			}
			sql = "select * from ������Ϣ  where �������  = '"+txt2.getText()+"'";
			rs = Sta.executeQuery(sql);
			while(rs.next()){
				txt5.setText((rs.getString(1)));	txt5.setEditable(false);
				txt6.setText((rs.getString(4)));	txt6.setEditable(false);
			}
		}
		catch(Exception e1){System.out.println(e1);	}
	}
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getActionCommand().equals("�޸�")){
			UserModify.a1 = txt2.getText();
			UserModify.a2 = txt3.getText();
			UserModify.a3 = txt6.getText();
			UserModify.name = name;
			new UserModify(this,"�޸���Ϣ",true);
			if(i==1){this.dispose();}
			try{
				con = DriverManager.getConnection(url,"sa","a");
				Sta = con.createStatement();
				sql = "select * from ������Ϣ  where ��������  = '"+name+"'";
				rs = Sta.executeQuery(sql);
				while(rs.next()){rs.getString(1);
					txt2.setText((rs.getString(1)));	txt2.setEditable(false);
					txt3.setText((rs.getString(2)));	txt3.setEditable(false);
					txt4.setText((rs.getString(4)));	txt4.setEditable(false);				
				}
				sql = "select * from ������Ϣ  where �������  = '"+txt2.getText()+"'";
				rs = Sta.executeQuery(sql);
				while(rs.next()){
					txt5.setText((rs.getString(1)));	txt5.setEditable(false);
					txt6.setText((rs.getString(4)));	txt6.setEditable(false);
				}
			}
			catch(Exception e1){System.out.println(e1);	}
		}
		else if(e.getActionCommand().equals("ɾ��")){
			String sql = "delete from ������Ϣ where ��������=?";
			int n = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);   
			if (n == JOptionPane.YES_OPTION) {   
				try{
					PreparedStatement preState = con.prepareStatement(sql);
					preState.setString(1,name);
					preState.executeUpdate();
					sql = "delete from ������Ϣ where �������=?";
					preState.setString(1,txt2.getText());
					preState.executeUpdate();
				    JOptionPane.showMessageDialog(new JFrame(),"��ɾ��");  
				    txt2.setText("");
				    txt3.setText("");
				    txt4.setText("");
				    txt5.setText("");
				    txt6.setText("");
				    
				    shuaxin();
				}
				catch(Exception e1){
					
				}
			} else if (n == JOptionPane.NO_OPTION) {   
			      
			}  
		}
		
		
	}
}
