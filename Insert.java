import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Insert extends JDialog implements ActionListener, ItemListener{
	JLabel label1,label2,label3,label4,label5,label6,label7;
	JPanel p1,p2,p3,p4,p5,p6,p7;
	JTextField txt1,txt2,txt3,txt4;
	JComboBox combo1,combo2,combo3;
	JButton btn_sure,btn_sub;
	String[] s1 = {"黑","白","灰","红","银","蓝","黄","其它"};
	String[] s2 = {"A","B","C","D"};
	String s;
	int j = 0;
	String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	public Insert(Frame fck,String ckm,boolean msck){
		super(fck,ckm,msck);
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		label1 = new JLabel("车辆编号：");
		label2 = new JLabel("车牌号码：");
		label3 = new JLabel("车主姓名：");
		label4 = new JLabel("车辆颜色：");
		label5 = new JLabel("车库编号：");
		label6 = new JLabel("停放位置：");
		label7 = new JLabel();
		txt1 = new JTextField(10);
		txt2 = new JTextField(10);
		txt3 = new JTextField(10);
		txt4 = new JTextField(10);
		combo1 = new JComboBox(s1);
		combo2 = new JComboBox(s2);
		combo3 = new JComboBox();
		combo2.addItemListener(this);
		btn_sure = new JButton("确定");
		btn_sub = new JButton("取消");
		p1.add(label1);p1.add(txt1);
		p2.add(label2);p2.add(txt2);
		p3.add(label3);p3.add(txt3);
		p4.add(label4);p4.add(combo1);
		p5.add(label5);p5.add(combo2);p5.add(label7);
		p6.add(label6);p6.add(txt4);
		p7.add(btn_sure);p7.add(btn_sub);
		this.setLayout(new GridLayout(7,1));
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		this.add(p5);
		this.add(p6);
		this.add(p7);
		btn_sure.addActionListener(this);
		btn_sub.addActionListener(this);
//		this.setTitle("添加车辆信息");
		this.setSize(480,420);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Qcxx qcxx;
		Ckxx ckxx;
		int flag;
		if(e.getActionCommand().equals("确定")){
			flag = 0;
			try{
				con = DriverManager.getConnection(url,"sa","a");
				Sta = con.createStatement();
				rs = Sta.executeQuery("select * from 车库信息  where 车库编号 = '"+combo2.getSelectedItem().toString()+"'");
				while(rs.next()){
					if(txt1.getText().equals(rs.getString(2).trim())){
						flag = 1;
						JOptionPane.showMessageDialog(null, "相同的车辆编号，请重新输入！"); 
						break;
					}
					else if(txt4.getText().equals(rs.getString(4).trim())){
						flag = 1;
						JOptionPane.showMessageDialog(null, "相同的停车位置，请重新输入！"); 
						break;
					}
					if(flag == 1){break;}
				}
				if(flag == 0){
				String sql = "insert into 车辆信息  values (?,?,?,?)";
				con = DriverManager.getConnection(url,"sa","a");
				Sta = con.createStatement();
				PreparedStatement preState = con.prepareStatement(sql);
				preState.setInt(1, Integer.parseInt(txt1.getText()));
				 preState.setString(2,txt2.getText());
				 preState.setString(3,txt3.getText());	
				 preState.setString(4,combo1.getSelectedItem().toString());
				 preState.executeUpdate();
				 
				 sql = "insert into 车库信息  values (?,?,?,?)";
				 preState = con.prepareStatement(sql);
				 preState.setString(1,combo2.getSelectedItem().toString());
				 preState.setInt(2, Integer.parseInt(txt1.getText()));
				 preState.setInt(3, j);	
				 preState.setInt(4, Integer.parseInt(txt4.getText()));
				 preState.executeUpdate();
				 qcxx=new Qcxx();
				 ckxx = new Ckxx();
				 this.dispose();
				 JOptionPane.showMessageDialog(null, "添加信息成功！"); 
				}
			}
			catch(Exception e1){JOptionPane.showMessageDialog(null, "请填写完所有信息！"); }
		}
		else if(e.getActionCommand().equals("取消")){
			this.dispose();
		}
	}
	Connection con ;
	Statement Sta;
	ResultSet rs;
	public void itemStateChanged(ItemEvent arg0) {
		// TODO 自动生成的方法存根
		s = combo2.getSelectedItem().toString();
		String sql = "select *  from 车库信息  where 车库编号  = '"+ s +"'";
		int k = 0;
		try {
			con = DriverManager.getConnection(url,"sa","a");
			Sta = con.createStatement();
			rs = Sta.executeQuery(sql);
			if(s.equals("A")||s.equals("D")){j = 20;}
			else if(s.equals("B")||s.equals("C")){j = 10;}
			while(rs.next()){
				k ++;
			 }
			j = j-k;
			label7.setText("剩余位置："+j);
			con.close();
			 rs.close();
			 Sta.close();
		} catch (Exception e){System.out.println(e);}
	}
}
