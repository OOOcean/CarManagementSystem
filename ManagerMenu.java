import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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


public class ManagerMenu extends JFrame implements ActionListener, ItemListener{
	Vector content1,field1,content2,field2,content3;
	JPanel choise,value,view,down;
	JComboBox combo;
	JLabel label;
	JTextField txt;
	JButton btn_sure,btn_insert,btn_update,btn_delete;
	JTable table1,table2,table3;
	JScrollPane gd1,gd2,gd3;
	String[] s1 = {"车辆信息","车库信息"};
	Qcxx qcxx;
	Ckxx ckxx;
	public ManagerMenu(){
		choise = new JPanel();
		value = new JPanel();
		view = new JPanel();
		down = new JPanel();
		label = new JLabel();
		txt = new JTextField(16);
		btn_sure = new JButton("查询");
		btn_insert = new JButton("添加");
		btn_update = new JButton("修改");
		btn_delete = new JButton("删除");
		combo = new JComboBox(s1);
		label.setText("车辆编号");
		choise.add(combo);choise.add(label);choise.add(txt);choise.add(btn_sure);
		down.add(btn_insert); down.add(btn_update);	down.add(btn_delete);
		this.setLayout(new BorderLayout());
		
		qcxx=new Qcxx();
		table1 = new JTable(qcxx);
		gd1 = new JScrollPane(table1);
		
		ckxx = new Ckxx();
		table2 = new JTable(ckxx);
		gd2 = new JScrollPane(table2);
		
		this.add(choise,BorderLayout.NORTH);
		view.add(gd1);
		view.add(gd2);
		
		this.add(view);
		this.add(down,BorderLayout.SOUTH);
		btn_sure.addActionListener(this);
		btn_insert.addActionListener(this);
		btn_update.addActionListener(this);
		btn_delete.addActionListener(this);
		combo.addItemListener(this);
		this.setTitle("车辆信息管理系统————管理员");
		this.setSize(580,520);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void itemStateChanged(ItemEvent e1) {
		// TODO 自动生成的方法存根
		view.setVisible(true);
		if(combo.getSelectedItem().equals("车辆信息")){
			label.setText("车辆编号");
			qcxx=new Qcxx();
			table1.setModel(qcxx);
			gd1.setVisible(true);
			gd2.setVisible(false);
		}
		if(combo.getSelectedItem().equals("车库信息")){
			label.setText("车库编号");
			ckxx = new Ckxx();
			table2.setModel(ckxx);
			gd1.setVisible(false);
			gd2.setVisible(true);	
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		String buttonName = e.getActionCommand();
		String type = txt.getText();
		if(buttonName.equals("查询")){
			if(label.getText().equals("车辆编号")){
				String xingming=this.txt.getText().trim();
				String sql="select * from 车辆信息 where 车辆编号='"+txt.getText()+"'";
				qcxx=new Qcxx(sql);
				table1.setModel(qcxx);
			}
			else if(label.getText().equals("车库编号")){
				String bianhao=this.txt.getText().trim();
				String sql="select * from 车库信息 where 车库编号='"+bianhao+"'";
				ckxx=new Ckxx(sql);
				table2.setModel(ckxx);
			}
		}
		else if(buttonName.equals("添加")){
			Insert insert = new Insert(this,"添加车辆信息",true);
			qcxx=new Qcxx();
			table1.setModel(qcxx);
			ckxx=new Ckxx();
			table2.setModel(ckxx);
		}
		else if(buttonName.equals("修改")){
			if(label.getText().equals("车主姓名")){
				int i=this.table1.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"请选中要修改的行");
					return;
					}
				ModifyCar modifycar = new ModifyCar(this,"修改车辆信息",true,qcxx,i);
				qcxx=new Qcxx();
				table1.setModel(qcxx);
			}
			if(label.getText().equals("车库编号")){
				int i=this.table2.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"请选中要修改的行");
					return;
					}
				ModifyGarage modifygarage = new ModifyGarage(this,"修改车库信息",true,ckxx,i);
				ckxx=new Ckxx();
				table2.setModel(ckxx);
			}
		}
		else if(buttonName.equals("删除")){
			if(label.getText().equals("车主姓名")){
				int i=this.table1.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"请选中要删除的行");
					return;
					}
				String st=(String)qcxx.getValueAt(i,0);
				PreparedStatement preState=null;
				String url = "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
				try {
					Connection con = DriverManager.getConnection(url,"sa","a");
					preState=con.prepareStatement("delete from 车辆信息 where 车辆编号=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					preState=con.prepareStatement("delete from 车库信息 where 车辆编号=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					JOptionPane.showMessageDialog(this,"删除成功！");
					} 
				catch (Exception e2)
				{
					JOptionPane.showMessageDialog(this,"删除失败！");
					}
				
				qcxx=new Qcxx();
				table1.setModel(qcxx);
				}
			if(label.getText().equals("车库编号")){
				int i=this.table2.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"请选中要删除的行");
					return;
					}
				String st=(String)ckxx.getValueAt(i,1);
				PreparedStatement preState=null;
				String url = "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
				try {
					Connection con = DriverManager.getConnection(url,"sa","a");
					preState=con.prepareStatement("delete from 车辆信息 where 车辆编号=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					preState=con.prepareStatement("delete from 车库信息 where 车辆编号=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					JOptionPane.showMessageDialog(this,"删除成功！");
					qcxx=new Qcxx();
					table1.setModel(qcxx);
					ckxx=new Ckxx();
					table2.setModel(ckxx);
					} 
				catch (Exception e2)
				{
					JOptionPane.showMessageDialog(this,"删除失败！");
				}				
			}
		}
		
	}
}
