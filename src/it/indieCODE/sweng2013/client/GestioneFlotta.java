package it.indieCODE.sweng2013.client;
import it.indieCODE.sweng2013.shared.Utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GestioneFlotta extends Composite implements ClickHandler, Scheda {

	private TextBox textBoxNomeAuto = new TextBox();
	private TextBox textBoxModello = new TextBox();
	private Button cerca = new Button("trasferisci");
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	@Override
	public void onClick(ClickEvent event) {
		Window.alert("GestioneFlotta");
	}

	public GestioneFlotta(String str, final TabPanel tabpanel) {

		VerticalPanel panel = new VerticalPanel();
		panel.add(new HTML("<text>ID Auto Da Richiedere:</text>"));
		panel.add(textBoxNomeAuto);
		panel.add(new HTML("<text>Inserisci ID Agenzia:</text>"));
		panel.add(textBoxModello);
		panel.add(cerca);
		textBoxNomeAuto.setTitle("Inserisci ID Auto ");
//		textBoxNomeAuto.setText("ID Auto");
//		textBoxModello.setText("Inserisci Nome Agenzia");
		textBoxModello.setTitle("Nome Agenzia");
		initWidget(panel);
		cerca.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(Utils.isStringNumeric(textBoxNomeAuto.getText()) && Utils.isStringNumeric(textBoxModello.getText())){
					greetingService.richiestaAuto(new Integer(textBoxNomeAuto.getText()), (new Integer(textBoxModello.getText())),
							new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							//serverResponseLabel.addStyleName("serverResponseLabelError");
							//serverResponseLabel.setHTML(SERVER_ERROR);
							Window.alert("Richiesta fallita");
						}

						@Override
						public void onSuccess(String result) {
							Window.alert(result);
							tabpanel.selectTab(3);
							Window.Location.reload();
						}
					});
				}

			}
		});
	}

	@Override
	public FlowPanel makeFlowPanel() {
		FlowPanel panel = new FlowPanel();
		panel.add(this);
		this.setSize("100%", "300px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
		return panel;
	}

}
