package it.ZkElements;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

class ListListener implements EventListener
{
	private Listbox l;
	private Listbox r;
	private Listitem temp;
	private Listitem newListItem;
	private String listaCambio;
	private Listheader lH;
	private Listheader rH;
	
	public ListListener (Listbox l, 
			Listbox r, Listitem temp, String listaCambio, Listheader lH, Listheader rH)
	{
		this.l = l;
		this.r = r;
		this.temp = temp;
		this.listaCambio = listaCambio;
		this.lH = lH;
		this.rH = rH;
	}
	
	@Override
	public void onEvent(Event event) throws Exception 
	{
		String tmp;
		if (!r.isVisible())
			r.setVisible(true);
		System.out.println();
		
		if (listaCambio == "r")
			listaCambio = "l";
		else
			listaCambio = "r";
		
		tmp = temp.getId().substring(1);
		newListItem = new Listitem(temp.getLabel());
		newListItem.setId(listaCambio+tmp);
		newListItem.addEventListener("onClick", new ListListener(r,l,newListItem,listaCambio,lH,rH)); 
		
		r.getItems().add(newListItem);
		lH.sort(true,true);
		rH.sort(true,true);		
		
		l.getItems().remove(temp);
		if(l.getItems().size() == 0)
			l.setVisible(false);
	}
}