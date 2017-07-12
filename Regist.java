import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Regist extends JDialog implements ActionListener{
	JPanel p1,p2,p3,p4;
	JLabel label1,label2,label3;
	JTextField txt1;
	JPasswordField txt2,txt3;
	JButton btn_sure,btn_sub;
	public Regist(Login login, String string, boolean b){
		super(login,string,b);
		label1 = new JLabel("用户名：");
		label2 = new JLabel("新密码：");
		label3 = new JLabel("确认密码：");
		txt1 = new JTextField(10);
		txt2 = new JPasswordField(10);
		txt3 = new JPasswordField(10);
		btn_sure = new JButton("确定");
		btn_sub = new JButton("取消");
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();	
		p4 = new JPanel();
		p1.add(label1);	p1.add(txt1); 
		p2.add(label2);	p2.add(txt2);	
		p3.add(label3);	p3.add(txt3);
		p4.add(btn_sure);	p4.add(btn_sub);
		this.setLayout(new GridLayout(4,1));
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		btn_sure.addActionListener(this);
		btn_sub.addActionListener(this);
		this.setSize(380,320);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	Connection con ;
	Statement Sta;
	ResultSet rs;
	PreparedStatement preState;
	String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		String name,pass1,pass2;
		String sql;
		if(e.getActionCommand().equals("确定")){
			int count=0;
			char c;
			String psw="";
			name = txt1.getText();
			pass1 = String.copyValueOf(txt2.getPassword());
			pass2 = String.copyValueOf(txt3.getPassword());
			sql = "select * from 用户  where name = '"+name+"'";
			if(pass1.equals(pass2)){
				try{
					con = DriverManager.getConnection(url,"sa","a");
					Sta = con.createStatement();
					rs = Sta.executeQuery(sql);
					while(rs.next()){
						if(rs.getString(1)!=null){
							count ++;
						}
					}
					System.out.println(count);
					if(count==0){
						for(int i = 0;i<pass1.length();i++){
							if(pass1.charAt(i)>122){
								c = (char) (pass1.charAt(i)-122);
							}
							else{
								c = (char) (pass1.charAt(i)+5); 
							}
							psw += c;
						}
						sql = "insert into 用户  values (?,?)";
						preState = con.prepareStatement(sql);
						preState.setString(1,name);
						preState.setString(2,psw);
						preState.executeUpdate();
//						sql = "insert into 车辆信息  values (null,null,?,null)";
//						preState = con.prepareStatement(sql);
//						preState.setString(1,name);
//						preState.executeUpdate();
						JOptionPane.showMessageDialog(null, "注册成功！"); 
						this.dispose();
					}
					else{
						JOptionPane.showMessageDialog(null, "相同的用户名，请重新输入！"); 
					}
				}
				catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请填写完所有信息！");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "请确认两次密码输入相同！"); 
			}
		}
		else if(e.getActionCommand().equals("取消")){
			this.dispose();
		}
	}
}
