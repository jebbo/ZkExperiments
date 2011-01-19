package it.ZkExperiments;

import it.ZkExperiments.util.moveListItemListener;

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

/**
 * 
 * @author jebbo
 *
 */
public class insertPerson extends GenericForwardComposer {
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
	Listbox listbLeft;
	Listbox listbRigth;
	Listheader listbLeftHead;
	Listheader listbRigthtHead;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception 
	{
		Listitem itemListbox;
		List listItem;
		Iterator i;
		
		super.doAfterCompose(comp);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		Statement stmt = conn.createStatement();
		ResultSet rs  = stmt.executeQuery("SELECT * FROM category ORDER BY name");
		
		while (rs.next()) {
			itemListbox = new Listitem(rs.getString(2));
			itemListbox.setId("l"+rs.getString(1));
			listbLeft.getItems().add(itemListbox);	
		}
		
		listItem = listbLeft.getItems();
		
		i = listItem.iterator();
		
		while (i.hasNext()) {
			itemListbox = (Listitem)i.next();
			itemListbox.addEventListener("onClick", new moveListItemListener(listbLeft,listbRigth,itemListbox,"l",listbLeftHead,listbRigthtHead)); 
		}
		
		stmt.close();
		conn.close();
	}
	
	/**
	 * Insert in the DB the new person and his relationship with categories
	 *  
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void onClick$insert() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		long dataBir = 0;
		int personID = 0;
		String categoryID;
		Listitem itemListbox;
		List listItem;
		Iterator i;
		
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
			if (rs!=null) {
				while (rs.next()) {
					personID = rs.getInt(1);
				}
			}
			rs.close();
		}
		
		stmt.close();
		
		listItem = listbRigth.getItems();
		
		i = listItem.iterator();
		
		while (i.hasNext()) {
			itemListbox = (Listitem)i.next();
			categoryID = itemListbox.getId().substring(1);
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
