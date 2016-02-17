/**
 * 
 */
package it.indieCODE.sweng2013.client;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import it.indieCODE.sweng2013.shared.Affitto;
import it.indieCODE.sweng2013.shared.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

/**
 * @author phra
 *
 */
public class Manutenzione extends Composite implements ClickHandler, Scheda {


	final TextBox id = new TextBox();
	final Button sendButton = new Button("Invio");
	final CheckBox checkmulta = new CheckBox("Multa");
	final Label errorLabel = new Label();
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final Label textToServerLabel = new Label();
	final HTML serverResponseLabel = new HTML();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	public  LinkedList<Affitto> affitti;
	CellTable<Affitto> table = new CellTable<Affitto>();

	/**	
	 * 
	 */
	public Manutenzione(final TabPanel tabpanel) {
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);

		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		// Place the check above the text box using a vertical panel.
		VerticalPanel panel = new VerticalPanel();
		//panel.getElement().getStyle().setPosition(Position.RELATIVE);
		//checkBox.
		panel.add(new HTML("<text>ID Auto:</text>"));
		id.setTitle("inserisci ID della macchina");
		//		id.setText("inserisci ID della macchina");
		panel.add(id);
		panel.add(checkmulta);
		// Set the check box's caption, and check it by default.
		sendButton.addStyleName("sendButton");
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		//closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		panel.add(sendButton);
		panel.add(errorLabel);
		greetingService.getAffittiLocal(new AsyncCallback<LinkedList<Affitto>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.getMessage());

			}

			@Override
			public void onSuccess(LinkedList<Affitto> result) {
				affitti = result;
				tabella();
			}

		});

		//rootPanel.add(btnCiao);
		//id.selectAll();

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		/**
		 * @author phra
		 *
		 */
		class MyHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			@Override
			@SuppressWarnings("deprecation")
			public void onClick(ClickEvent event) {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = id.getText();
				if (!Utils.isStringNumeric(id.getText())) {
					errorLabel.setText("Please enter a number");
					return;
				}
				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.consegnaAuto(new Integer(id.getText()), checkmulta.isChecked(),
						new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					@Override
					public void onSuccess(String result) {
						Pagamento.prezzoTotale = result;
						Pagamento.aggiornaMulta();
						tabpanel.selectTab(6);
						serverResponseLabel.removeStyleName("Ammonto Spesa");
						serverResponseLabel.setHTML("totale da pagare: "+result);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
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
	private static final String SERVER_ERROR = "ERROR";

	public void tabella(){

		TextColumn<Affitto> idColumn = new TextColumn<Affitto>() {
			@Override
			public String getValue(Affitto object) {
				return object.getIDauto()+"";
			}
		};

		idColumn.setSortable(true); 

		TextColumn<Affitto> CostodColumn = new TextColumn<Affitto>() {
			@Override
			public String getValue(Affitto contact) {
				return contact.getCosto()+"";
			}
		};



		TextColumn<Affitto> FineColumn = new TextColumn<Affitto>() {
			@Override
			public String getValue(Affitto contact) {
				return contact.getDatafine()+"";
			}
		};

		TextColumn<Affitto> AgenziaColumn = new TextColumn<Affitto>() {
			@Override
			public String getValue(Affitto contact) {
				if(contact.getIDagenzia() == -1)return "locale";
				else return contact.getIDagenzia()+"";
			}
		};

		TextColumn<Affitto> ClienteColumn = new TextColumn<Affitto>() {
			@Override
			public String getValue(Affitto contact) {
				return contact.getIDcliente()+"";
			}
		};
		

		TextColumn<Affitto> SeggioliniColumn = new TextColumn<Affitto>() {
			@Override
			public String getValue(Affitto contact) {
				return contact.getSeggiolini()+"";
			}
		};

		table.addColumn(AgenziaColumn, "Agenzia");
		table.addColumn(idColumn, "ID Auto");
		table.addColumn(ClienteColumn, "ID Cliente");
		table.addColumn(CostodColumn, "Costo");
		table.addColumn(FineColumn, "Data Fine");
		table.addColumn(SeggioliniColumn,"Seggiolini");
		ListDataProvider<Affitto> dataProvider = new ListDataProvider<Affitto>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(table);

		// Add the data to the data provider, which automatically pushes it to the
		// widget.
		List<Affitto> list = dataProvider.getList();
		for (Affitto contact : affitti) {
			list.add(contact);
		}

		// Add a ColumnSortEvent.ListHandler to connect sorting to the
		// java.util.List.
		ListHandler<Affitto> columnSortHandler = new ListHandler<Affitto>(
				list);

		columnSortHandler.setComparator(idColumn,
				new Comparator<Affitto>() {
			@Override
			public int compare(Affitto o1, Affitto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null && ( o1.getIDauto() > o2.getIDauto()) ) ?  0 : 1;
				}
				return -1;
			}
		});
		table.addColumnSortHandler(columnSortHandler);

		table.getColumnSortList().push(idColumn);

		// Add it to the root panel.
		//			    panel.add(table);
	}
	@Override
	public FlowPanel makeFlowPanel() {
		// A holder panel - just a wrapper to avoid a sizing problem
		final FlowPanel panel = new FlowPanel();

		// The main area for the page is just whatever HTML we pass to it
		//HTML page = new HTML(HTMLstrings.noleggio);
		table.setStyleName("tablese");
		panel.add(table);
		panel.add(this);

		// Set the page size and align the text centrally
		this.setSize("100%", "200px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
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
