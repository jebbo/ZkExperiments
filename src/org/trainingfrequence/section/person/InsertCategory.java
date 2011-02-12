package org.trainingfrequence.section.person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

public class InsertCategory extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox name;
	Textbox description;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	public void onClick$insert() throws WrongValueException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		categoryInsert(name.getValue(),description.getValue());
		
		alert("Category saved!");
	}
	
	private static void categoryInsert(String name,String desc) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO category(name,description) VALUES(?,?)");
		stmt.setString(1,name);
		stmt.setString(2,desc);
		
		stmt.execute();
		conn.close();
	}
}
