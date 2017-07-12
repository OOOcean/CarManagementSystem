import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Login extends JFrame implements ActionListener{
	JPanel up,center,down,main,none,dx;
	JLabel name,pass;
	JTextField txt_name;
	JPasswordField txt_pass;
	JButton btn_log,btn_reg;
	ButtonGroup group;
	JRadioButton radioButton1,radioButton2;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Login login = new Login();
	}
	public Login(){		
		radioButton1 = new JRadioButton("管理员");
		radioButton2 = new JRadioButton("用户");
		group = new ButtonGroup();
		group.add(radioButton1);group.add(radioButton2);
		name = new JLabel("账号：");
		pass = new JLabel("密码：");
		txt_name = new JTextField(10);
		txt_pass = new JPasswordField(10);
		btn_log = new JButton("确认");
		btn_reg = new JButton("注册");
		up = new JPanel();
		center = new JPanel();
		down = new JPanel();
		none = new JPanel();
		main = new JPanel();
		dx = new JPanel();
		main.setLayout(new GridLayout(5,1));
		up.add(name); up.add(txt_name);
		center.add(pass); center.add(txt_pass);
		dx.add(radioButton1);dx.add(radioButton2);
		down.add(btn_log); down.add(btn_reg);
		main.add(none); main.add(up); main.add(center);main.add(dx);main.add(down);
		btn_log.addActionListener(this);
		btn_reg.addActionListener(this);
		
		this.add(main);
		this.setTitle("汽车信息管理系统");
		this.setSize(380,320);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	Connection con ;
	Statement Sta;
	ResultSet rs;
	PreparedStatement preState;
	String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		String s = null;
		String name = null,pass;
		String sql;
		char c;
		String psw="";
		int flag = 0,ff=0;
		if(e.getActionCommand().equals("确认")){
			try{
				if(radioButton1.isSelected()){
					s = radioButton1.getText().toString();
					ff=1;
				}
				else if(radioButton2.isSelected()){
					s = radioButton2.getText().toString();
					ff=1;
				}			
				if(ff==1){				
					name = txt_name.getText();
					pass = String.copyValueOf(txt_pass.getPassword());
					for(int i = 0;i<pass.length();i++){
						if(pass.charAt(i)>122){
							c = (char) (pass.charAt(i)-122);
						}
						else{
							c = (char) (pass.charAt(i)+5); 
						}
						psw += c;
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "请选择登录身份！");
				}
				if(s.equals("管理员")){
				
					try{
						sql = "select * from 管理员";
						con = DriverManager.getConnection(url,"sa","a");
						Sta = con.createStatement();
						rs = Sta.executeQuery(sql);
						flag = 0;
						while(rs.next()){
							if(name.equals(rs.getString(1).trim())){
								if(rs.getString(2).trim().equals(psw)){
									flag=1;
									break;
								}
							}
						}
						if(flag==1){
							this.dispose();
							ManagerMenu managermenu = new ManagerMenu();
						}
						else{
							JOptionPane.showMessageDialog(null, "请检查密码！");
						}
					}
					catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请检查登录名！");
					}				
				}
				else if(s.equals("用户")){				
					try{
						sql = "select * from 用户";
						con = DriverManager.getConnection(url,"sa","a");
						Sta = con.createStatement();
						rs = Sta.executeQuery(sql);
						flag = 0;
						while(rs.next()){
							if(name.equals(rs.getString(1).trim())){
								if(rs.getString(2).trim().equals(psw)){
									flag=1;
									break;
								}
							}
						}
						if(flag==1){
							UserMenu.name = name;
							UserMenu usermenu = new UserMenu();
							this.dispose();
						}
						else{
							JOptionPane.showMessageDialog(null, "请检查密码！");
						}
					}
					catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请检查登录名！");
					}				
				}
			}
			catch(Exception e1){
				
			}
		}
		else if(e.getActionCommand().equals("注册")){
			Regist regist = new Regist(this,"注册用户",true);
		}
	}
}
