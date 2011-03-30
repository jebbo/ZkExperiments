package org.trainingfrequence.section.person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.trainingfrequence.section.person.util.CopyListItemListener;
import org.trainingfrequence.section.person.util.Util;
import org.zkoss.idom.Attribute;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.mysql.jdbc.Statement;
import com.sun.org.apache.xerces.internal.dom.ParentNode;

public class InsertCategory extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox name;
	private Textbox description;
	private Listbox listbLeft;
	private Listbox listbRigth;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
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
	
	public void onClick$insert() throws WrongValueException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		categoryInsert(name.getValue(),description.getValue());
		
		alert("Category saved!");
	}
	
	/*public void onClose$win() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		System.out.println("LANCIO RENDER");
		listbLeft.getItems().clear();
		Util.renderizeListbox(listbLeft, listbRigth);
		System.out.println("OK");
		
	}*/
	
	private void setListbLeft(Listbox list) {
		this.listbLeft = list;
	}
	
	private void setListbRigth(Listbox list) {
		this.listbRigth = list;
		
		
		
	}

	private void categoryInsert(String name,String desc) throws InstantiationException,
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
					System.out.println("ID generato:" + categoryID);
				}
			}
			rs.close();
		}
		stmt.close();
		conn.close();
		
		if (categoryID != 0) {
			Listitem itemListbox = new Listitem(name);
			itemListbox.setId("l"+categoryID);
			listbLeft.getItems().add(itemListbox);
			
			itemListbox.addEventListener("onClick", 
					new CopyListItemListener(listbRigth,itemListbox));
		}
	}
	
	/**
	 * Get the params map that are overhanded at creation time. <br>
	 * Reading the params that are binded to the createEvent.<br>
	 * 
	 * @param event
	 * @return params map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCreationArgsMap(Event event) {
		CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
		return ce.getArg();
		/*System.out.println(((ForwardEvent) event).getOrigin());
		return null;*/
	}
}
