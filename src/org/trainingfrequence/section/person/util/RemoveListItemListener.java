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
 * Remove a ListItem in a Listbox
 * 
 * @author jebbo
 *
 */
public class RemoveListItemListener implements EventListener {
	private Listbox listb;
	private Listitem itemListbox;
	
	/**
	 * 
	 * @param listb
	 * @param itemListbox
	 */
	public RemoveListItemListener(Listbox listb, Listitem itemListbox) {
		this.listb = listb;
		this.itemListbox = itemListbox;
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
	
		listb.getItems().remove(itemListbox);
		
		if(listb.getItems().size() == 0) {
			listb.setVisible(Boolean.FALSE);
		}
	}
}