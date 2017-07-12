import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class UserModify extends JDialog implements ActionListener{
	int flag;
	static String a1,a2,a3,name;
	JLabel label1,label2,label3,label4,label5;
	JTextField txt1,txt2,txt3,txt4,txt5;
	JComboBox combo1,combo2;
	JButton btn_sure,btn_sub;
	JPanel p1,p2,p3,p4,p5,p6,ppp;
	String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	String[] s1 = {"��","��","��","��","��","��","��","����"};
	String[] s2 = {"A","B","C","D"};
	public UserModify(UserMenu userMenu, String string, boolean b) {
		// TODO �Զ����ɵĹ��캯�����
		super(userMenu,string,b);
		label1 = new JLabel("�������");
		label2 = new JLabel("���ƺ���");
		label3 = new JLabel("������ɫ");
		label4 = new JLabel("������");
		label5 = new JLabel("ͣ��λ��:");
		txt1 = new JTextField(10);txt1.setText(a1);
		txt2 = new JTextField(10);txt2.setText(a2);
		txt3 = new JTextField(10);txt3.setText(a3);
		combo1 = new JComboBox(s1);
		combo2 = new JComboBox(s2);
		btn_sure = new JButton("�޸�");
		btn_sure.addActionListener(this);
		btn_sub = new JButton("ȡ��");
		btn_sub.addActionListener(this);
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		ppp = new JPanel();
		p1.add(label1);p1.add(txt1);
		p2.add(label2);p2.add(txt2);
		p3.add(label3);p3.add(combo1);
		p4.add(label4);p4.add(combo2);
		p5.add(label5);p5.add(txt3);
		p6.add(btn_sure);p6.add(btn_sub);
		ppp.setLayout(new GridLayout(5,1));
		ppp.add(p1);ppp.add(p2);ppp.add(p3);ppp.add(p4);ppp.add(p5);
		this.setLayout(new BorderLayout());
		this.add(ppp);this.add(p6,BorderLayout.SOUTH);
		this.setSize(380,320);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�޸�")){

			Connection con ;
			Statement Sta;
			ResultSet rs;
//			PreparedStatement preState;
			try{
				con = DriverManager.getConnection(url,"sa","a");
				Sta = con.createStatement();
				rs = Sta.executeQuery("select * from ������Ϣ  where ������ = '"+txt3.getText()+"' and ������� = '"+txt1.getText()+"'");
				flag=0;
				int b = 0;
				while(rs.next()){
					if(txt1.getText().equals(rs.getString(2).trim())){
						JOptionPane.showMessageDialog(null, "��ͬ�ĳ�����ţ����������룡"); 
						flag=1;
						break;
					}
					else if(txt3.getText().equals(rs.getString(4).trim())){
						JOptionPane.showMessageDialog(null, "��ͬ��ͣ��λ�ã����������룡"); 
						flag=1;
						break;
					}
				}
				if(flag == 0){
					
					String sql;
					rs = Sta.executeQuery("select * from ������Ϣ  where ��������='"+name+"'");
					while(rs.next()){
						flag++;
					}
					
						
					b=0;
					rs = Sta.executeQuery("select * from ������Ϣ,������Ϣ  where ������Ϣ.�������='"+txt1.getText()+"'and ��������='"+name+"'");
					while(rs.next()){
						b++;
					}
					if(b>0){
						try{
							sql = "update ������Ϣ  set �������=?,���ƺ���=?,������ɫ=? where ��������=?";
							PreparedStatement preState = con.prepareStatement(sql);
							preState.setString(1,txt1.getText());
							preState.setString(2,txt2.getText());
							preState.setString(3,combo1.getSelectedItem().toString());	
							preState.setString(4,name);
							preState.executeUpdate();
							
							sql = "update  ������Ϣ  set ������=?,ͣ��λ��=? where �������=?";
							preState = con.prepareStatement(sql);
							preState.setString(1,combo2.getSelectedItem().toString());
							preState.setInt(2, Integer.parseInt(txt3.getText()));
							preState.setInt(3, Integer.parseInt(txt1.getText()));	
							preState.executeUpdate();
							this.dispose();
							JOptionPane.showMessageDialog(null, "�޸���Ϣ�ɹ���");
						}catch(Exception e1){System.out.println(e1);}
					}
					else{
						sql = "insert into ������Ϣ  values (?,?,?,?)";
						PreparedStatement preState = con.prepareStatement(sql);
						preState.setInt(1, Integer.parseInt(txt1.getText()));
						 preState.setString(2,txt2.getText());
						 preState.setString(3,name);	
						 preState.setString(4,combo1.getSelectedItem().toString());
						 preState.executeUpdate();
						 
						 int j=0;
							if(combo2.getSelectedItem().toString().equals("A")||combo2.getSelectedItem().toString().equals("D")){
								j=20;
							}
							else {j = 10;}
							sql = "insert into ������Ϣ  values (?,?,?,?)";
							PreparedStatement pre;
							pre = con.prepareStatement(sql);
							pre.setString(1,combo2.getSelectedItem().toString());
							pre.setInt(2, Integer.parseInt(txt1.getText()));
							pre.setInt(3, j);	
							pre.setInt(4, Integer.parseInt(txt3.getText()));
							pre.executeUpdate();
							this.dispose();
							JOptionPane.showMessageDialog(null, "�����Ϣ�ɹ��������µ�¼�鿴��");
							UserMenu.i = 1;
							Login log = new Login();
					}
				}
				con.close();
				Sta.close();
				rs.close();
			}
			catch(Exception e1){
				System.out.println(e1);
//				JOptionPane.showMessageDialog(null, "����д��������Ϣ��"); 
				}
		}
		else if(e.getActionCommand().equals("ȡ��")){
			this.dispose();
		}
	}

}
