package it.ZkElements;

import it.ZkExperiments.util.MyListItem;

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
			System.out.println(temp.getId());
			temp.addEventListener("onClick", new ListListener(l,r,temp,"l",lH,rH)); 
		}
		
		y.close();
		conn.close();
	}
	
	public void onClick$insert () throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		long dataBir = 0;
		String personID="";
		
		if (birthday.getValue()!=null)
			dataBir = birthday.getValue().getTime();
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		PreparedStatement yy = conn.prepareStatement("INSERT INTO person(name,surname,phone,birthday,email,note) VALUES(?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		yy.setString(1,name.getValue());
		yy.setString(2,surname.getValue());
		yy.setString(3,phone.getValue());
		yy.setLong	(4,dataBir);
		yy.setString(5,email.getValue());
		yy.setString(6,note.getValue());
		
		ResultSet res = yy.getGeneratedKeys();
		personID = res.getString(1);
		
		System.out.println("PERSONID="+personID);
		
		/*yy = conn.prepareStatement("INSERT INTO person_category VALUES(?,?)");
		yy.setString(1,name.getValue());
		yy.setString(2,surname.getValue());*/
		
		yy.execute();
		
//		Metodo.inseriscipersona(string nome, string cognome)
		
		yy.close();
		conn.close();
		
		alert("Person saved!");
		
	}
}
