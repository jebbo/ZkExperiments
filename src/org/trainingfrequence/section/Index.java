package org.trainingfrequence.section;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;


public class Index extends GenericForwardComposer {
	Include inner;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	public void onClick$insPerson() {	
		inner.setSrc("insertPerson.zul");
	}
	
}