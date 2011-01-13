package it.ZkElements;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

class ListListener implements EventListener
{
	private Listbox l;
	private Listbox r;
	private Listitem temp;
	
	public ListListener (Listbox l, 
			Listbox r, Listitem temp)
	{
		this.l = l;
		this.r = r;
		this.temp = temp;
	}
	
	@Override
	public void onEvent(Event event) throws Exception 
	{
		if (!r.isVisible())
			r.setVisible(true);
		r.getItems().add(temp);
		l.getItems().remove(temp);
	}
}