import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
public class Ckxx extends AbstractTableModel{
	static String url= "jdbc:sqlserver://localhost:1433;DatabaseName = CarManagement";
	Vector ziduan,jilu;
	static Connection con ;
	static Statement Sta;
	public int getColumnCount() {
		return this.ziduan.size();
	}
	public int getRowCount() {
		return this.jilu.size();
	}
	public Object getValueAt(int hang, int lie) {
		return ((Vector)this.jilu.get(hang)).get(lie);
	}
	public String getColumnName(int e)
	{
		return (String)this.ziduan.get(e);
	}
	public Ckxx(){
		query("select * from  车库信息");
	}
	public Ckxx(String s){
		query(s);
	}
	public void query(String sql){
		ziduan=new Vector();
		ziduan.add("车库编号");
		ziduan.add("车辆编号");
		ziduan.add("车库容量");
		ziduan.add("停放位置");		
        jilu=new Vector();
        ResultSet rs;
		try {
			con = DriverManager.getConnection(url,"sa","a");
			Sta = con.createStatement();
			rs = Sta.executeQuery(sql);
			while(rs.next()){
				 Vector line=new Vector();
				 line.add(rs.getString(1));
				 line.add(rs.getString(2));
				 line.add(rs.getString(3));
				 line.add(rs.getString(4));
				 jilu.add(line);
			 }
			con.close();
			 rs.close();
			 Sta.close();
		} catch (Exception e){}	    
	}
}
