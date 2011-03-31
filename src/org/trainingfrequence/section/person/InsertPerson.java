
/*
	TRAINING FREQUENCE
	Copyright (C) 2011  Maurizio Mazzotta jebbo85@gmail.com
	
	This file is a part of Training Frequence
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.trainingfrequence.section.person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import org.trainingfrequence.section.person.util.*;

/**
 * 
 * @author Maurizio Mazzotta
 *
 */
public class InsertPerson extends GenericForwardComposer {
	
	//static final Logger logger = Logger.getLogger(InsertPerson.class);
	
	private static final long serialVersionUID = 1L;
	private Textbox name;
	private Textbox surname;
	private Textbox phone;
	private Datebox birthday;
	private Textbox email;
	private Textbox note;
	private Listbox listbLeft;
	private Listbox listbRigth;
	private Button  action;
	private Integer id;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception 
	{
		//PropertyConfigurator.configure("log4j.properties");

		setId(null);
		
		super.doAfterCompose(comp);
		
		try {
			setId((Integer) Executions.getCurrent().getDesktop().getAttribute("id"));
		}
		catch (ClassCastException cce){
			//TODO log frontend
			//logger.error("Id value isn't Integer");
		}
		
		if (Executions.getCurrent().getDesktop().hasAttribute("id")) {
			//TODO cambiare il valore del setAttribute in COSTANTE
			action.setAttribute("actionType", "u");
			action.setLabel("Modify Person");
			//logger.info("Page Modify");
		}
		else {
			action.setLabel("Insert Person");
			//logger.info("Page Insert");
		}
		Executions.getCurrent().getDesktop().removeAttribute("id");
		
		Util.renderizeListbox(listbLeft, listbRigth);
	}
	
	/**
	 * Insert in the DB the new person and his relationship with categories
	 *  
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void onClick$action() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		long dataBir = 0;
		
		
		/*if (action.getAttribute("actionType").equals("u")) {
			
		}*/
		
		if (birthday.getValue()!=null)
			dataBir = birthday.getValue().getTime();
		
		if(personInsert(name.getValue(),surname.getValue(),phone.getValue(),dataBir,
				email.getValue(),note.getValue())) {
			alert("Person saved!");
		}
		else {
			alert("Person NOT saved!");
		}
		
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	//TODO Questa classe verra' sostituita dall'oggetto di Luca (deve tornare booleano)
	@SuppressWarnings("unchecked")
	private Boolean personInsert(String name, String surname, String phone, 
			long dataBir, String email, String note) throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException { 
		int personID = 0;
		String categoryID;
		Listitem itemListbox;
		List listItem;
		Iterator i;
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=19god85-");
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO person(name,surname,phone,birthday,email,note) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,name);
		stmt.setString(2,surname);
		stmt.setString(3,phone);
		stmt.setLong  (4,dataBir);
		stmt.setString(5,email);
		stmt.setString(6,note);
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
		
		return Boolean.TRUE;
	}
}
