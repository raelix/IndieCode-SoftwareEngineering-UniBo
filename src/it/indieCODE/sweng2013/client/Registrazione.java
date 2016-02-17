package it.indieCODE.sweng2013.client;

import java.sql.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class Registrazione extends Composite implements ClickHandler, Scheda {

	private TextBox textBoxNome = new TextBox();
	private TextBox textBoxCognome = new TextBox();
	private TextBox textBoxAnnoDiNascita = new TextBox();
	private TextBox textBoxCodiceFiscale = new TextBox();
	private TextBox textBoxPatente = new TextBox();
	private DialogBox dialogBox = new DialogBox();
	private Button closeButton = new Button("CLOSE");
	final Label textToServerLabel = new Label();
	final HTML serverResponseLabel = new HTML();
	final Button send = new Button("Registrazione");
	final VerticalPanel panel = new VerticalPanel();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	TabPanel tabPanel;
	@Override
	public void onClick(ClickEvent event) {
		//Window.alert("noleggio");
	}

	public Registrazione(String str, final TabPanel tabPanel) {
		VerticalPanel panel = new VerticalPanel();
		this.tabPanel = tabPanel;
		panel.add(new HTML("<text>Inserisci Nome:</text>"));
		panel.add(textBoxNome);
		panel.add(new HTML("<text>Inserisci Cognome:</text>"));
		panel.add(textBoxCognome);
		panel.add(new HTML("<text>Inserisci Codice Fiscale:</text>"));
		panel.add(textBoxCodiceFiscale);
		panel.add(new HTML("<text>Inserisci Anno Di Nascita:</text>"));
		panel.add(textBoxAnnoDiNascita);
		panel.add(new HTML("<text>Inserisci Numero Patente:</text>"));
		panel.add(textBoxPatente);
		panel.add(send);
		textBoxNome.setTitle("inserisci il nome");
//		textBoxNome.setText("Nome");
//		textBoxCognome.setText("Cognome");
		textBoxCognome.setTitle("iInserisci il cognome");
//		textBoxCodiceFiscale.setText("Codice fiscale");
		textBoxCodiceFiscale.setTitle("inserisci il codice fiscale");
//		textBoxAnnoDiNascita.setText("Data di nascita");
		textBoxAnnoDiNascita.setTitle("inserisci data di nascita");
//		textBoxPatente.setText("Patente");
		textBoxPatente.setTitle("inserisci patente");
		initWidget(panel);
		// Give the overall composite a style name.
		//setStyleName("example-Noleggio");
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		// Place the check above the text box using a vertical panel.
		//panel.getElement().getStyle().setPosition(Position.RELATIVE);
		//checkBox.
		// Set the check box's caption, and check it by default.
		//send.addStyleName("sendButton");
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
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

		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				send.setEnabled(true);
				send.setFocus(true);
			}
		});

		send.addClickHandler(new ClickHandler() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(ClickEvent event) {
				//greetingService.ricerca(textBoxMarca.getText(),textBoxModello.getText(),new Integer(textBoxID.getText()),new Integer(textBoxAnno.getText()),new Integer(textBoxCilindrata.getText()),
				if ((textBoxNome.getText().length() == 0) &&
						(textBoxCognome.getText().length() == 0) &&
						(textBoxAnnoDiNascita.getText().length() == 0) &&
						(textBoxCodiceFiscale.getText().length() == 0) &&
						(textBoxPatente.getText().length() == 0)) return;
				greetingService.registrazione(textBoxNome.getText(),textBoxCognome.getText(),new Date(new java.util.Date(textBoxAnnoDiNascita.getText()).getTime()),textBoxCodiceFiscale.getText(),textBoxPatente.getText(),
						new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						//serverResponseLabel.addStyleName("serverResponseLabelError");
						//serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					@Override
					public void onSuccess(String result) {
//						dialogBox.setText("Remote Procedure Call");
						//serverResponseLabel.removeStyleName("serverResponseLabelError");
						//HTML html = new HTML();
						//FIXME html.setText(text);
//						serverResponseLabel.setHTML("ID registrazione "+result);
						
						Amministrativo.ID = result;
						Amministrativo.reload();
//						dialogBox.center();
//						closeButton.setFocus(true);
						tabPanel.selectTab(1);
					}
				});
			}
		});
	}

	@Override
	public FlowPanel makeFlowPanel() {
		FlowPanel panel = new FlowPanel();
		panel.add(this);
		this.setSize("100%", "500px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
		return panel;
	}

}
