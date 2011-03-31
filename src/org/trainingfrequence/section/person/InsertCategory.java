
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
import java.util.Map;

import org.trainingfrequence.section.person.util.CopyListItemListener;
import org.trainingfrequence.section.person.util.Util;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.mysql.jdbc.Statement;

/**
 * 
 * @author Maurizio Mazzotta
 *
 */
public class InsertCategory extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	private Textbox name;
	private Textbox description;
	private Listbox listbLeft;
	private Listbox listbRigth;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	/**
	 * This method retrieves the objects passed in paramMap
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$InsertCategory(Event event) throws Exception {
		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);
		
		/*
		 * BINDING STUFF:
		 * 
         * Get the overhanded MainController.
		 * Get the selectedItem from the Mainmodule.
		 */
		if (args.containsKey("listbLeft")) {
			setListbLeft((Listbox) args.get("listbLeft"));
		} else {
			setListbLeft(null);
		}
		
		if (args.containsKey("listbRigth")) {
			setListbRigth((Listbox) args.get("listbRigth"));
		} else {
			setListbRigth(null);
		}
	}
	/**
	 * setter to prelevate the first Listbox from parent
	 * 
	 * @param list
	 */
	private void setListbLeft(Listbox list) {
		this.listbLeft = list;
	}
	
	/**
	 * setter to prelevate the second Listbox from parent
	 * 
	 * @param list
	 */
	private void setListbRigth(Listbox list) {
		this.listbRigth = list;	
	}
	
	/**
	 * Capture the event onclik in the button for insert a new category
	 * 
	 * @throws WrongValueException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	//Can't determine type of List and Iterator because they can assume 
	//different object type childs of ZK listbox
	@SuppressWarnings("unchecked")
	public void onClick$insert() throws WrongValueException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Integer categoryID = 0;
		
		categoryID = categoryInsert(name.getValue(),description.getValue());
		//if the DB return the ID
		if (categoryID != 0) {
			//create its new Listitem and add it at the Listbox
			Listitem itemListbox = new Listitem(name.getValue());
			itemListbox.setId("l"+categoryID);
			listbLeft.getItems().add(itemListbox);
			
			//add the EventListener that allow moving in the next Listbox
			itemListbox.addEventListener("onClick", 
					new CopyListItemListener(listbRigth,itemListbox));
			
			//force the ascendent sort in the first Listbox
			Util.ListboxSort(listbLeft);
			
			//initialize the text fields to prepare the application a new insert
			name.setValue("");
			description.setValue("");
			
			alert("Category saved! \n" +
					"You can Insert a new category or close this window to return in " +
					"the page for Insert a new Person");
		}
		else {
			alert("Category NOT saved! Try again!");
		}	
	}
	
	/**
	 * Get the params map that are overhanded at creation time. <br>
	 * Reading the params that are binded to the createEvent.<br>
	 * 
	 * @param event
	 * @return params map
	 */
	//getArg return Map with generic type
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCreationArgsMap(Event event) {
		CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
		return ce.getArg();
	}
	
	//TODO Questa classe verra' sostituita dall'oggetto di Luca (deve tornare l'ID di categoria)
	private Integer categoryInsert(String name,String desc) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Integer categoryID = 0;
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=19god85-");
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO category(name,description) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,name);
		stmt.setString(2,desc);
		int num = stmt.executeUpdate();
		if (num == 1) {
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs!=null) {
				while (rs.next()) {
					categoryID = rs.getInt(1);
				}
			}
			rs.close();
		}
		stmt.close();
		conn.close();

		return categoryID;
	}
}
