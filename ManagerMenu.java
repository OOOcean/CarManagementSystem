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
	String[] s1 = {"������Ϣ","������Ϣ"};
	Qcxx qcxx;
	Ckxx ckxx;
	public ManagerMenu(){
		choise = new JPanel();
		value = new JPanel();
		view = new JPanel();
		down = new JPanel();
		label = new JLabel();
		txt = new JTextField(16);
		btn_sure = new JButton("��ѯ");
		btn_insert = new JButton("���");
		btn_update = new JButton("�޸�");
		btn_delete = new JButton("ɾ��");
		combo = new JComboBox(s1);
		label.setText("�������");
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
		this.setTitle("������Ϣ����ϵͳ������������Ա");
		this.setSize(580,520);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void itemStateChanged(ItemEvent e1) {
		// TODO �Զ����ɵķ������
		view.setVisible(true);
		if(combo.getSelectedItem().equals("������Ϣ")){
			label.setText("�������");
			qcxx=new Qcxx();
			table1.setModel(qcxx);
			gd1.setVisible(true);
			gd2.setVisible(false);
		}
		if(combo.getSelectedItem().equals("������Ϣ")){
			label.setText("������");
			ckxx = new Ckxx();
			table2.setModel(ckxx);
			gd1.setVisible(false);
			gd2.setVisible(true);	
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		String buttonName = e.getActionCommand();
		String type = txt.getText();
		if(buttonName.equals("��ѯ")){
			if(label.getText().equals("�������")){
				String xingming=this.txt.getText().trim();
				String sql="select * from ������Ϣ where �������='"+txt.getText()+"'";
				qcxx=new Qcxx(sql);
				table1.setModel(qcxx);
			}
			else if(label.getText().equals("������")){
				String bianhao=this.txt.getText().trim();
				String sql="select * from ������Ϣ where ������='"+bianhao+"'";
				ckxx=new Ckxx(sql);
				table2.setModel(ckxx);
			}
		}
		else if(buttonName.equals("���")){
			Insert insert = new Insert(this,"��ӳ�����Ϣ",true);
			qcxx=new Qcxx();
			table1.setModel(qcxx);
			ckxx=new Ckxx();
			table2.setModel(ckxx);
		}
		else if(buttonName.equals("�޸�")){
			if(label.getText().equals("��������")){
				int i=this.table1.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"��ѡ��Ҫ�޸ĵ���");
					return;
					}
				ModifyCar modifycar = new ModifyCar(this,"�޸ĳ�����Ϣ",true,qcxx,i);
				qcxx=new Qcxx();
				table1.setModel(qcxx);
			}
			if(label.getText().equals("������")){
				int i=this.table2.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"��ѡ��Ҫ�޸ĵ���");
					return;
					}
				ModifyGarage modifygarage = new ModifyGarage(this,"�޸ĳ�����Ϣ",true,ckxx,i);
				ckxx=new Ckxx();
				table2.setModel(ckxx);
			}
		}
		else if(buttonName.equals("ɾ��")){
			if(label.getText().equals("��������")){
				int i=this.table1.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"��ѡ��Ҫɾ������");
					return;
					}
				String st=(String)qcxx.getValueAt(i,0);
				PreparedStatement preState=null;
				String url = "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
				try {
					Connection con = DriverManager.getConnection(url,"sa","a");
					preState=con.prepareStatement("delete from ������Ϣ where �������=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					preState=con.prepareStatement("delete from ������Ϣ where �������=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					JOptionPane.showMessageDialog(this,"ɾ���ɹ���");
					} 
				catch (Exception e2)
				{
					JOptionPane.showMessageDialog(this,"ɾ��ʧ�ܣ�");
					}
				
				qcxx=new Qcxx();
				table1.setModel(qcxx);
				}
			if(label.getText().equals("������")){
				int i=this.table2.getSelectedRow();
				if(i==-1)
				{
					JOptionPane.showMessageDialog(this,"��ѡ��Ҫɾ������");
					return;
					}
				String st=(String)ckxx.getValueAt(i,1);
				PreparedStatement preState=null;
				String url = "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
				try {
					Connection con = DriverManager.getConnection(url,"sa","a");
					preState=con.prepareStatement("delete from ������Ϣ where �������=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					preState=con.prepareStatement("delete from ������Ϣ where �������=?");
					preState.setString(1,st);
					preState.executeUpdate();	
					JOptionPane.showMessageDialog(this,"ɾ���ɹ���");
					qcxx=new Qcxx();
					table1.setModel(qcxx);
					ckxx=new Ckxx();
					table2.setModel(ckxx);
					} 
				catch (Exception e2)
				{
					JOptionPane.showMessageDialog(this,"ɾ��ʧ�ܣ�");
				}				
			}
		}
		
	}
}
