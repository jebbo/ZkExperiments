package it.ZkExperiments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

public class InsertCategory extends GenericForwardComposer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox name;
	Textbox description;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception 
	{
		super.doAfterCompose(comp);
	}
	
	public void onClick$insert () throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		PreparedStatement yy = conn.prepareStatement("INSERT INTO category(name,description) VALUES(?,?)");
		yy.setString(1,name.getValue());
		yy.setString(2,description.getValue());
		
		yy.execute();
		
		/*String sql = "INSERT category VALUES(" +
		"'0','" + name.getValue() 				+ "','" + description.getValue() 	+ "')";
		y.executeUpdate(sql);
		
		System.out.println(sql);*/
		
		conn.close();
		
		alert("Category saved!");
	}
}
