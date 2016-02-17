/**
 * 
 */
package it.indieCODE.sweng2013.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author phra
 *
 */
public class Intro extends Composite implements ClickHandler, Scheda {

	/**
	 * 
	 */

	final TextBox nameField = new TextBox();
	final Button amministrativo = new Button("Premi per entrare in modalità amministrativo");
	final Button manutenzione = new Button("Premi per entrare in modalità manutenzione");
	final Label errorLabel = new Label();
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final Label textToServerLabel = new Label();
	final HTML serverResponseLabel = new HTML();

	/**
	 * @param tabpanel
	 */
	public Intro(final TabPanel tabpanel) {
		final VerticalPanel panel = new VerticalPanel();
		amministrativo.addStyleName("sendButton");
		panel.add(amministrativo);
		panel.add(manutenzione);
		
		manutenzione.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("manuntenzione");
				tabpanel.selectTab(4);
			}
		});
		
		amministrativo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("amministrativo");
				tabpanel.selectTab(1);
			}
		});
		//History.fireCurrentHistoryState();


		// All composites must call initWidget() in their constructors.
		initWidget(panel);
		// Give the overall composite a style name.
		//setStyleName("example-Noleggio");
	}

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.Scheda#makeFlowPanel()
	 */
	@Override
	public FlowPanel makeFlowPanel() {
		// A holder panel - just a wrapper to avoid a sizing problem
		final FlowPanel panel = new FlowPanel();

		// The main area for the page is just whatever HTML we pass to it
		//HTML page = new HTML(HTMLstrings.noleggio);

		panel.add(this);
		
		// Set the page size and align the text centrally
		this.setSize("100%", "200px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
		
		// Return the panel to whatever asked for it
		return panel;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Window.alert("Amministrativo");

	}
}
