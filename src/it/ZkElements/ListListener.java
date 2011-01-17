package it.ZkElements;

import it.ZkExperiments.util.MyListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.SimpleListModel;

class ListListener implements EventListener
{
	private Listbox l;
	private Listbox r;
	private Listitem temp;
	private Listitem newListItem;
	private String listaCambio;
	
	public ListListener (Listbox l, 
			Listbox r, Listitem temp, String listaCambio)
	{
		this.l = l;
		this.r = r;
		this.temp = temp;
		this.listaCambio = listaCambio;
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
		newListItem.addEventListener("onClick", new ListListener(r,l,newListItem,listaCambio)); 
		
		List rr = r.getItems();
		
		Iterator i = rr.iterator();
		List<Listitem> data = new ArrayList<Listitem>();
		Listitem itemTemp;
		while(i.hasNext())
		{
			itemTemp = (Listitem) i.next();
			data.add(itemTemp);
		}
		data.add(newListItem);
		
		//Collections.sort(data);
		
		ListModel strset = new SimpleListModel(data);
		
		//r.getItems().add(newListItem);
		r.setModel(strset);
		
		l.getItems().remove(temp);
		if(l.getItems().size() == 0)
			l.setVisible(false);
	}
}