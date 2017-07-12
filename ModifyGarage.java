import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class ModifyGarage extends JDialog implements ActionListener{
	JLabel label1,label2,label3,label4;
	JTextField txt1,txt2,txt3,txt4;
	JComboBox combo;
	JButton btn_sure,btn_sub;
	JPanel p1,p2,p3,p4,p5,ppp;
	String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	public ModifyGarage(ManagerMenu functionMenu, String string, boolean b,Ckxx ckxx,int line) {
		// TODO 自动生成的构造函数存根
		super(functionMenu,string,b);
		label1 = new JLabel("车库编号");
		label2 = new JLabel("车辆编号");
		label3 = new JLabel("车库容量");
		label4 = new JLabel("停放位置");
		txt1 = new JTextField(10);
		txt1.setText((String)ckxx.getValueAt(line,0));
		txt1.setEditable(false);
		txt2 = new JTextField(10);
		txt2.setText((String)ckxx.getValueAt(line,1));
		txt2.setEditable(false);
		txt3 = new JTextField(10);
		txt3.setText((String)ckxx.getValueAt(line,2));
		txt3.setEditable(false);
		txt4 = new JTextField(10);
		txt4.setText((String)ckxx.getValueAt(line,3));
		btn_sure = new JButton("修改");
		btn_sure.addActionListener(this);
		btn_sub = new JButton("取消");
		btn_sub.addActionListener(this);
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		ppp = new JPanel();
		p1.add(label1);p1.add(txt1);
		p2.add(label2);p2.add(txt2);
		p3.add(label3);p3.add(txt3);
		p4.add(label4);p4.add(txt4);
		p5.add(btn_sure);p5.add(btn_sub);
		ppp.setLayout(new GridLayout(4,1));
		ppp.add(p1);ppp.add(p2);ppp.add(p3);ppp.add(p4);
		this.setLayout(new BorderLayout());
		this.add(ppp);this.add(p5,BorderLayout.SOUTH);
		this.setSize(480,420);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	Connection con ;
	Statement Sta;
	ResultSet rs;
	PreparedStatement preState;
	int flag;
	public void actionPerformed(ActionEvent e) {
		flag = 0;
		if(e.getActionCommand().equals("修改")){
			try{
				con = DriverManager.getConnection(url,"sa","a");
				Sta = con.createStatement();
				rs = Sta.executeQuery("select * from 车库信息  where 车库编号 = '"+txt1.getText()+"' and 车辆编号 = '"+txt2.getText()+"'");
				while(rs.next()){
					if(txt4.getText().equals(rs.getString(4))){
						JOptionPane.showMessageDialog(null, "相同的停车位置，请重新输入！"); 
						flag = 1;
						break;
					}
				}
				if(flag==0){
				preState = con.prepareStatement("update 车库信息  set 停放位置=? where 车库编号=? and 车辆编号=?");
				preState.setString(1,txt4.getText());
				preState.setString(2,txt1.getText());
				preState.setString(3,txt2.getText());
				preState.executeUpdate();
				JOptionPane.showMessageDialog(this,"修改成功！");
				this.dispose();
				}
			}catch(Exception e1){}
		}
		else if(e.getActionCommand().equals("取消")){
			this.dispose();
		}
	}

}
