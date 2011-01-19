package it.ZkExperiments.util;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
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
	private Listheader listbSourceHead;
	private Listheader listbDestHead;
	
	public MoveListItemListener(Listbox listbSource, Listbox listbDest, Listitem itemListbox, 
						String listChange, Listheader listbSourceHead, Listheader listbDestHead) {
		this.listbSource = listbSource;
		this.listbDest = listbDest;
		this.itemListbox = itemListbox;
		this.listChange = listChange;
		this.listbSourceHead = listbSourceHead;
		this.listbDestHead = listbDestHead;
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
		String extractId;
		
		if (!listbDest.isVisible())
			listbDest.setVisible(true);
		
		if (listChange == "r")
			listChange = "l";
		else
			listChange = "r";
		
		extractId = itemListbox.getId().substring(1);
		newListItem = new Listitem(itemListbox.getLabel());
		newListItem.setId(listChange + extractId);
		newListItem.addEventListener("onClick", new MoveListItemListener(listbDest,listbSource,newListItem,listChange,listbSourceHead,listbDestHead)); 
		listbDest.getItems().add(newListItem);
		listbSourceHead.sort(true,true);
		listbDestHead.sort(true,true);		
		
		listbSource.getItems().remove(itemListbox);
		if(listbSource.getItems().size() == 0) {
			listbSource.setVisible(false);
		}
	}
}