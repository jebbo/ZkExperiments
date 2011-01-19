package it.ZkExperiments.util;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
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
	private Listheader listbHead;
	
	public RemoveListItemListener(Listbox listb, Listitem itemListbox, Listheader listbHead) {
		this.listb = listb;
		this.itemListbox = itemListbox;
		this.listbHead = listbHead;
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
	
		listb.getItems().remove(itemListbox);
		
		if(listb.getItems().size() == 0) {
			listb.setVisible(false);
		}
	}
}