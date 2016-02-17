package it.indieCODE.sweng2013.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IndieCODE_sweng2013 implements EntryPoint, ValueChangeHandler<String>, HTMLstrings {



	public void goToSignUpPage() {
		String url = GWT.getHostPageBaseURL() + "prova.html";

		if(!GWT.isProdMode()) {
			Window.alert("We are in development mode!");
			url += "?gwt.codesvr=127.0.0.1:9997";
		}
		Window.Location.replace(url);
	}

	/*FlowPanel makePage(String html) {
		// A holder panel - just a wrapper to avoid a sizing problem
		FlowPanel panel = new FlowPanel();

		// The main area for the page is just whatever HTML we pass to it
		HTML page = new HTML(html);
		panel.add(page);

		// Set the page size and align the text centrally
		page.setSize("300px", "200px");
		page.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);

		// Add a border and some padding to give it that 'WOW' factor
		DOM.setStyleAttribute(page.getElement(), "border", "10px solid blue");
		DOM.setStyleAttribute(page.getElement(), "padding", "20px");

		// Return the panel to whatever asked for it
		return panel;
	}*/

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		
		final TabPanel tabPanel = new TabPanel();
		//final FlowPanel intro = makePage(HTMLstrings.intro);
		//final FlowPanel amministrativo = makePage(HTMLstrings.amministrativo);
		//final FlowPanel manutenzione = makePage(HTMLstrings.manutenzione);
		//final FlowPanel noleggio = makePage(HTMLstrings.noleggio);
		//final FlowPanel registrazione = makePage(HTMLstrings.registrazione);
		
		History.addValueChangeHandler(this);
		tabPanel.setStyleName("panel");
		//intro.getElement().getStyle().setPosition(Position.RELATIVE);
		//intro.getElement().setId("INTRO");
		tabPanel.add(new Intro(tabPanel).makeFlowPanel()," INTRO ");
		tabPanel.add(new Amministrativo(tabPanel).makeFlowPanel()," AMMINISTRATIVO ");
		tabPanel.add(new Registrazione("registrazione",tabPanel).makeFlowPanel()," REGISTRAZIONE ");
		tabPanel.add(new Noleggio("noleggio",tabPanel).makeFlowPanel()," NOLEGGIO ");
		tabPanel.add(new Manutenzione(tabPanel).makeFlowPanel()," MANUTENZIONE ");
		tabPanel.add(new Ricerca("","", 0, 0, 0).makeFlowPanel()," RICERCA ");
		tabPanel.add(new Pagamento("",tabPanel).makeFlowPanel()," PAGAMENTO ");
		tabPanel.add(new GestioneFlotta("",tabPanel).makeFlowPanel()," GESTIONE FLOTTA ");
		
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				History.newItem("page" + event.getSelectedItem());
			}
		});

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();

				// Parse the history token
				try {
					if (historyToken.substring(0, 4).equals("page")) {
						String tabIndexToken = historyToken.substring(4, 5);
						int tabIndex = Integer.parseInt(tabIndexToken);
						// Select the specified tab panel
						tabPanel.selectTab(tabIndex);
					} else {
						tabPanel.selectTab(0);
					}

				} catch (IndexOutOfBoundsException e) {
					tabPanel.selectTab(0);
				}
			}
		});

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		tabPanel.selectTab(0);
		//tabPanel.setSize("100px", "100px");

//		RootPanel.get("ROOT").setStyleName("panel");
		RootPanel.get("ROOT").add(tabPanel);
		//RootPanel introPanel = RootPanel.get("INTRO"); //.get("nameFieldContainer");
		//intro.getElement().getStyle().setPosition(Position.RELATIVE);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if(History.getToken().equals("")) { }
	}
}
