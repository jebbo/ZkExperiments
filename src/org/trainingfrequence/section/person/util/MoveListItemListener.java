package org.trainingfrequence.section.person.util;

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

import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

/**
 * Move a ListItem in the clearest Listbox
 * 
 * @author jebbo
 *
 */
public class MoveListItemListener implements EventListener {
	private Listbox listbSource;
	private Listbox listbDest;
	private Listitem itemListbox;
	private Listitem newListItem;
	private String listChange;
	
	/**
	 * MoveListItemListener constructor
	 * 
	 * @param listbSource 	Source List of Item
	 * @param listbDest		Destination List of Item		
	 * @param itemListbox	Item moved
	 * @param listChange	Flag to indicate what is the list destination, "l" or "r" 
	 */
	public MoveListItemListener(Listbox listbSource, Listbox listbDest, 
								Listitem itemListbox, String listChange) {
		this.listbSource = listbSource;
		this.listbDest = listbDest;
		this.itemListbox = itemListbox;
		this.listChange = listChange;
	}
	
	//Can't determine type of List and Iterator because they can assume 
	//different object type childs of ZK listbox
	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event event) throws Exception {
		String extractId;					//to extract the payload of id item
		Iterator i,i2;	
		List childListDest,listTmp;						
		Object objTmp,objTmp2;				//Temp list and Object to find the Listheader for sort
		Listheader listHeaderFind = null;		
		Boolean find = Boolean.TRUE;
		
		if (!listbDest.isVisible())
			listbDest.setVisible(true);
		
		if (listChange == "r")
			listChange = "l";
		else
			listChange = "r";
		
		extractId = itemListbox.getId().substring(1);
		//create a clone item with appropriate new id and listener
		newListItem = new Listitem(itemListbox.getLabel());
		newListItem.setId(listChange + extractId);
		newListItem.addEventListener("onClick", 
							new MoveListItemListener(listbDest,listbSource,
												newListItem,listChange)); 
		//add the new item from destination listbox
		listbDest.getItems().add(newListItem);
		//remove the old item from source listbox
		listbSource.getItems().remove(itemListbox);
		
		childListDest = listbDest.getChildren();
		i = childListDest.iterator();
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
		
		if (listbSource.getItems().size() == 0) {
			listbSource.setVisible(false);
		}
	}
}