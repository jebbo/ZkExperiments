package it.ZkElements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
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
	Listheader lH;
	Listheader rH;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception 
	{
		Listitem temp;
		
		super.doAfterCompose(comp);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		Statement y = conn.createStatement();
		ResultSet rs  = y.executeQuery("SELECT * FROM category ORDER BY name");
		
		while(rs.next())
		{
			temp = new Listitem(rs.getString(2));
			temp.setId("l"+rs.getString(1));
			l.getItems().add(temp);	
		}
		
		List ll = l.getItems();
		
		Iterator i = ll.iterator();
		
		while(i.hasNext())
		{
			temp = (Listitem)i.next();
			temp.addEventListener("onClick", new ListListener(l,r,temp,"l",lH,rH)); 
		}
		
		y.close();
		conn.close();
	}
	
	public void onClick$insert () throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		long dataBir = 0;
		int personID = 0;
		String categoryID;
		Listitem temp;
		
		if (birthday.getValue()!=null)
			dataBir = birthday.getValue().getTime();
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO person(name,surname,phone,birthday,email,note) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,name.getValue());
		stmt.setString(2,surname.getValue());
		stmt.setString(3,phone.getValue());
		stmt.setLong  (4,dataBir);
		stmt.setString(5,email.getValue());
		stmt.setString(6,note.getValue());
		
		int num = stmt.executeUpdate();
		if (num == 1) {
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs!=null){
				while(rs.next()){
					personID = rs.getInt(1);
				}
			}
			rs.close();
		}

		stmt.close();
		
		List rr = r.getItems();
		
		Iterator i = rr.iterator();
		
		while(i.hasNext())
		{
			temp = (Listitem)i.next();
			categoryID = temp.getId().substring(1);
			stmt = conn.prepareStatement("INSERT INTO person_category(personID,categoryID) VALUES(?,?)");
			stmt.setInt(1,personID);
			stmt.setString(2,categoryID);
			
			stmt.executeUpdate();
			stmt.close();
		}
		
		conn.close();
		
		alert("Person saved!");
		
	}
}
