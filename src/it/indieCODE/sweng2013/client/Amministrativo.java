/**
 * 
 */
package it.indieCODE.sweng2013.client;

import it.indieCODE.sweng2013.shared.Utils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Amministrativo extends Composite implements ClickHandler, Scheda {

	final static TextBox nameField = new TextBox();
//	final Button sendButton = new Button("Noleggia");
	final Label errorLabel = new Label();
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final Label textToServerLabel = new Label();
	final HTML serverResponseLabel = new HTML();
	private Button RegistraUtente = new Button("Prosegui");
	public static String ID = "";

	public Amministrativo(final TabPanel tabpanel) {
		final VerticalPanel panel = new VerticalPanel();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		closeButton.getElement().setId("closeButton");
		nameField.setText(ID);
		panel.add(nameField);
//		sendButton.addStyleName("sendButton");
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		panel.add(new HTML("<text>Inserisci ID Cliente:</text>"));
		panel.add(nameField);
		panel.add(RegistraUtente);
//		panel.add(sendButton);
		panel.add(errorLabel);
		nameField.setFocus(true);
		nameField.selectAll();
		initWidget(panel);
		
		RegistraUtente.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(nameField.getText().length() != 0 && Utils.isStringNumeric(nameField.getText())){
					if (!Noleggio.IDcliente.equalsIgnoreCase(""))ID=Noleggio.IDcliente;
					else{
						ID = nameField.getText();
						Noleggio.IDcliente = ID;
					}
					Noleggio.reload();
					tabpanel.selectTab(3);	
				}
				else if(nameField.getText().length() == 0 )
					tabpanel.selectTab(2);
				else return;
			}
		});
	}


	public static void reload(){
		nameField.setText(ID);
	}



	@Override
	public FlowPanel makeFlowPanel() {
		final FlowPanel panel = new FlowPanel();
		panel.add(this);
		this.setSize("100%", "200px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
	return panel;
	}

	@Override
	public void onClick(ClickEvent event) {
		Window.alert("Amministrativo");

	}

}
