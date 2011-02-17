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

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

/**
 * Move a ListItem in the clearest Listbox
 * 
 * @author Maurizio Mazzotta
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
		
		Util.ListboxSort(listbDest);
		
		if (listbSource.getItems().size() == 0) {
			listbSource.setVisible(false);
		}
	}
}