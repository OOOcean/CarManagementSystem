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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ModifyCar extends JDialog implements ActionListener{
	JLabel label1,label2,label3,label4;
	JTextField txt1,txt2,txt3,txt4;
	JComboBox combo;
	JButton btn_sure,btn_sub;
	JPanel p1,p2,p3,p4,p5,ppp;
	String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	String[] s1 = {"��","��","��","��","��","��","��","����"};
	public ModifyCar(ManagerMenu functionMenu, String string, boolean b,Qcxx qcxx,int line) {
		// TODO �Զ����ɵĹ��캯�����
		super(functionMenu,string,b);
		label1 = new JLabel("�������");
		label2 = new JLabel("���ƺ���");
		label3 = new JLabel("��������");
		label4 = new JLabel("������ɫ");
		txt1 = new JTextField(10);
		txt1.setText((String)qcxx.getValueAt(line,0));
		txt1.setEditable(false);
		txt2 = new JTextField(10);
		txt2.setText((String)qcxx.getValueAt(line,1));
		txt3 = new JTextField(10);
		txt3.setText((String)qcxx.getValueAt(line,2));
		combo = new JComboBox(s1);
		btn_sure = new JButton("�޸�");
		btn_sure.addActionListener(this);
		btn_sub = new JButton("ȡ��");
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
		p4.add(label4);p4.add(combo);
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
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�޸�")){
			try{
				con = DriverManager.getConnection(url,"sa","a");
				preState = con.prepareStatement("update ������Ϣ  set ���ƺ���=?,��������=?,������ɫ=? where �������=?");
				preState.setString(1,txt2.getText());
				preState.setString(2,txt3.getText());
				preState.setString(3,combo.getSelectedItem().toString());
				preState.setString(4,txt1.getText());
				preState.executeUpdate();
				JOptionPane.showMessageDialog(this,"�޸ĳɹ���");
				this.dispose();
			}catch(Exception e1){}
		}
		else if(e.getActionCommand().equals("ȡ��")){
			this.dispose();
		}
	}

}
