package org.trainingfrequence.section;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

public class Index extends GenericForwardComposer {
	private Include inner;
	private Label titleHead;
	private Panel navigation;
	private Window language;

	private static final long serialVersionUID = 1L;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		titleHead.setValue(Labels.getLabel("titleHeader"));
		navigation.setTitle(Labels.getLabel("navigation"));
		language.setTitle(Labels.getLabel("language"));
	}

	public void onClick$insPerson() {
		inner.setSrc("insertPerson.zul");
	}

}