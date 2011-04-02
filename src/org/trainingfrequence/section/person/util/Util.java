
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

package org.trainingfrequence.section.person.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  @author Maurizio Mazzotta
 */
public class Util {
	
	/**
	 * This method make a sort ascendent in the Listbox passed
	 * 
	 * @param listb
	 */
	//Can't determine type of List and Iterator because they can assume 
	//different object type childs of ZK listbox
	@SuppressWarnings("unchecked")
	public static void ListboxSort(Listbox listb) {
		Iterator i,i2;	
		List childList,listTmp;						
		Object objTmp,objTmp2;				//Temp list and Object to find the Listheader for sort
		Listheader listHeaderFind = null;		
		Boolean find = Boolean.TRUE;
		
		childList = listb.getChildren();
		i = childList.iterator();
		//cycle to search the ListHeader id
		while(i.hasNext() && find) {
			objTmp = i.next();
			if (objTmp instanceof Listhead) {
				Listhead tmpListhead =  (Listhead) (objTmp);
				listTmp = tmpListhead.getChildren();
				i2 = listTmp.iterator();
				while(i2.hasNext()) {
					objTmp2 = i2.next();
					if (objTmp2 instanceof Listheader) {
						listHeaderFind = ((Listheader) objTmp2);
						find = Boolean.FALSE;
					}//end if
				}//end while
			}//end if
		}//end while
		listHeaderFind.sort(Boolean.TRUE, Boolean.TRUE);
	}
	
	/**
	 * This method read categories in DB and create list items for the listbox, 
	 * then add for every item a EventListener that allows the Listitem to move in the next Listbox 
	 * when the user click on the Listitem
	 * 
	 * @param listbLeft
	 * @param listbRigth
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	//Can't determine type of List and Iterator because they can assume 
	//different object type childs of ZK listbox
	@SuppressWarnings("unchecked")
	public static void renderizeListbox (Listbox listbLeft, Listbox listbRigth) 
			throws InstantiationException, IllegalAccessException, 
				ClassNotFoundException, SQLException {
		Listitem itemListbox = null;
		List listItem;
		Iterator i;
		ArrayList<Listitem> listItems = new ArrayList<Listitem>(); 
		
		listItems = getItemLisbox();
		for(Listitem l: listItems) {
			listbLeft.getItems().add(l);	
		}
		
		//Cycle on the Listitem and add the EventListener to allow the moving in the next Listbox
		listItem = listbLeft.getItems();
		i = listItem.iterator();
		while (i.hasNext()) {
			itemListbox = (Listitem)i.next();
			itemListbox.addEventListener("onClick", 
					new CopyListItemListener(listbRigth,itemListbox)); 
			//itemListbox.addEventListener("onClick", 
			//		new MoveListItemListener(listbLeft,listbRigth,
			//				itemListbox,"l")); 
		}
	}
	
	//TODO Questo metodo verr� sostituito dalle classe di Luca
	public static ArrayList<Listitem> getItemLisbox () 
			throws InstantiationException, IllegalAccessException,
				ClassNotFoundException, SQLException{
		Listitem itemListbox = null;
		ArrayList<Listitem> list = new ArrayList<Listitem>();
		
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=");
		
		Statement stmt = conn.createStatement();
		ResultSet rs  = stmt.executeQuery("SELECT * FROM category ORDER BY name");
		
		//create a ListItem for every records and immediately add the object at the Listbox
		while (rs.next()) {
			itemListbox = new Listitem(rs.getString(2));
			itemListbox.setId("l"+rs.getString(1));
			list.add(itemListbox);
		}
		rs.close();
		stmt.close();
		conn.close();
		
		return list;
	}
	
}