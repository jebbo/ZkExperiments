package it.ZkElements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class insertPerson extends GenericForwardComposer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Combobox category;
	Textbox name;
	Textbox surname;
	Textbox phone;
	Decimalbox height;
	Datebox birthday;
	Textbox email;
	Textbox note;
	Listbox r;
	Listbox l;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception 
	{
		HashMap catItem = new HashMap();
		
		super.doAfterCompose(comp);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		Statement y = conn.createStatement();
		ResultSet rs  = y.executeQuery("SELECT * FROM category ORDER BY name");
		
		while(rs.next())
		{
			l.getItems().add(new Listitem(rs.getString(2)));	
		}
		
		List ll = l.getItems();
		
		Iterator i = ll.iterator();
		
		while(i.hasNext())
		{
			Listitem temp = (Listitem)i.next();
			
			temp.addEventListener("onClick", new ListListener(l,r,temp)); 
		}
		
		conn.close();
	}
	
	public void onClick$insert () throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		Statement y = conn.createStatement();
		
		String sql = "INSERT person VALUES(" +
		"'0','" + name.getValue() 				+ "','" + surname.getValue() 	+ "'," +
	 		"'" + phone.getValue() 				+ "','" + height.getValue() 	+ "'," +
	 		"'" + birthday.getValue().getTime() + "','"+ email.getValue() 		+ "'," +
	 		"'" + note.getValue() 				+ "')";
		y.executeUpdate(sql);
		
		System.out.println(sql);
		
	}
}
