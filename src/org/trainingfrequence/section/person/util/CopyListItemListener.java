package org.trainingfrequence.section.person.util;

import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

/**
 * Copy a ListItem in the clearest Listbox
 * 
 * @author jebbo
 *
 */
public class CopyListItemListener implements EventListener {
	private Listbox listb;
	private Listitem itemListbox;
	private Listitem newListItem;
	private Listheader listbHead;
	
	public CopyListItemListener(Listbox listb, Listitem itemListbox, Listheader listbHead) {
		this.listb = listb;
		this.itemListbox = itemListbox;
		this.listbHead = listbHead;
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
		String extractId;
		
		if (!listb.isVisible())
			listb.setVisible(true);
		
		extractId = itemListbox.getId().substring(1);
		if (searchListItem("r" + extractId) == false) { 
			newListItem = new Listitem(itemListbox.getLabel());
			newListItem.setId("r" + extractId);
			newListItem.addEventListener("onClick",new RemoveListItemListener(listb,newListItem,listbHead)); 
			
			listb.getItems().add(newListItem);
			listbHead.sort(true,true);	
		}
	}
	
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
		boolean block = false;
		
		listItem = listb.getItems();
		
		i = listItem.iterator();
		
		while ((i.hasNext()) && (block==false)) {
			item = (Listitem)i.next();
			if (item.getId().equals(itemID)) {
				block = true;
			}
		}
		return block;
	}
}