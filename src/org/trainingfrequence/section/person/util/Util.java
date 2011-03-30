package org.trainingfrequence.section.person.util;

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

/**
 *  @author Maurizio Mazzotta
 */
public class Util {
	
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
	
	public static void renderizeListbox (Listbox listbLeft, Listbox listbRigth) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Listitem itemListbox;
		List listItem;
		Iterator i;
		
		System.out.println("CHIAMATA RENDERIZZA LISTBOX");
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/test?" +
                                   "user=root&password=19god85-");
		
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
			itemListbox.addEventListener("onClick", 
					new CopyListItemListener(listbRigth,itemListbox)); 
			//itemListbox.addEventListener("onClick", 
			//		new MoveListItemListener(listbLeft,listbRigth,
			//				itemListbox,"l")); 
		}
		
		stmt.close();
		conn.close();
	}
	
}