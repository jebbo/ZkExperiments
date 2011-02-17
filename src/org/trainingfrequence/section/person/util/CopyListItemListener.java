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
import org.zkoss.zul.Listitem;

/**
 * Copy a ListItem in the clearest Listbox
 * 
 * @author Maurizio Mazzotta
 *
 */
public class CopyListItemListener implements EventListener {
	private Listbox listb;
	private Listitem itemListbox;
	private Listitem newListItem;
	
	/**
	 * 
	 * @param listb
	 * @param itemListbox
	 */
	public CopyListItemListener(Listbox listb, Listitem itemListbox) {
		this.listb = listb;
		this.itemListbox = itemListbox;
	}
	
	//Can't determine type of List and Iterator because they can assume 
	//different object type childs of ZK listbox
	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event event) throws Exception {
		String extractId;
		
		if (!listb.isVisible())
			listb.setVisible(true);
		
		extractId = itemListbox.getId().substring(1);
		if (searchListItem("r" + extractId) == false) { 
			newListItem = new Listitem(itemListbox.getLabel());
			newListItem.setId("r" + extractId);
			newListItem.addEventListener("onClick",new RemoveListItemListener(listb,newListItem)); 
			
			listb.getItems().add(newListItem);
			
			Util.ListboxSort(listb);
		}
	}
	
	//Can't determine type of List and Iterator because they can assume 
	//different object type childs of ZK listbox
	@SuppressWarnings("unchecked")
	/**
	 * Search if listbox contains the Listitem
	 * 
	 * @param itemID
	 * @return true if element exist, false id doesn't exist
	 */
	private boolean searchListItem(String itemID) {
		Listitem item;
		List listItem;
		Iterator i;
		boolean find = Boolean.FALSE;
		
		listItem = listb.getItems();
		
		i = listItem.iterator();
		
		while ((i.hasNext()) && (Boolean.FALSE)) {
			item = (Listitem)i.next();
			if (item.getId().equals(itemID)) {
				find = Boolean.TRUE;
			}
		}
		return find;
	}
}