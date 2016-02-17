package it.indieCODE.sweng2013.client;

import it.indieCODE.sweng2013.shared.Auto;
import it.indieCODE.sweng2013.shared.Utils;

import java.sql.Date;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class Ricerca extends Composite implements Scheda {

	private TextBox textBoxID = new TextBox();
	private TextBox textBoxMarca = new TextBox();
	private TextBox textBoxCilindrata = new TextBox();
	private TextBox textBoxModello = new TextBox();
	private TextBox textBoxAnno = new TextBox();
	private Button send = new Button("Cerca");
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private DialogBox dialogBox = new DialogBox();
	private Button closeButton = new Button("CLOSE");
	private Label textToServerLabel = new Label();
	private HTML serverResponseLabel = new HTML();
	public static LinkedList<Auto> auto ;
	CellTable<Auto> table = null; 
	FlowPanel panel = new FlowPanel();


	public Ricerca(String marca, String modello, int anno, int cilindrata, int id) {
		final VerticalPanel panel = new VerticalPanel();
		panel.add(new HTML("<text>Inserisci ID Auto:</text>"));
		panel.add(textBoxID);
		panel.add(new HTML("<text>Inserisci Marca:</text>"));
		panel.add(textBoxMarca);
		panel.add(new HTML("<text>Inserisci Cilindrata:</text>"));
		panel.add(textBoxCilindrata);
		panel.add(new HTML("<text>Inserisci Modello:</text>"));
		panel.add(textBoxModello);
		panel.add(new HTML("<text>Inserisci Anno:</text>"));
		panel.add(textBoxAnno);
		panel.add(send);

		//		textBoxID.setText("ID");
		textBoxMarca.setTitle("inserisci marca");
		//		textBoxMarca.setText("marca");
		textBoxModello.setTitle("inserisci modello");
		//		textBoxModello.setText("modello");
		textBoxCilindrata.setTitle("inserisci cilindrata");
		//		textBoxCilindrata.setText("cilindrata");
		textBoxAnno.setTitle("inserisci anno");
		//		textBoxAnno.setText("anno");
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		closeButton.getElement().setId("closeButton");
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
		initWidget(panel);

		//		 tabella();
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
				String marca,modello;
				int id,cilindrata;
				Date anno;
				if (textBoxID.getText().length() == 0 || !Utils.isStringNumeric(textBoxID.getText())) id = -1; else id = new Integer(textBoxID.getText());
				if (textBoxMarca.getText().length() == 0) marca = null; else marca = textBoxMarca.getText();
				if (textBoxCilindrata.getText().length() == 0 || !Utils.isStringNumeric(textBoxCilindrata.getText())) cilindrata = -1; else cilindrata = new Integer(textBoxCilindrata.getText());
				if (textBoxModello.getText().length() == 0) modello = null; else modello = textBoxModello.getText();
				if (textBoxAnno.getText().length() == 0) anno = null; else anno = new Date(new java.util.Date(textBoxAnno.getText()).getTime());
				greetingService.ricerca(marca,modello,id,anno,cilindrata,
						new AsyncCallback<LinkedList<Auto>>() {

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
					public void onSuccess(LinkedList<Auto> result) {
						System.out.println("trovata lista Auto");
						auto = result;
						if(table != null)
							table.setVisible(false);
						if(result != null)tabella();

					}
				});
			}
		});
	}

	@Override
	public FlowPanel makeFlowPanel() {
		panel.add(this);
		this.setSize("100%", "450px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
		//		DOM.setStyleAttribute(this.getElement(), "padding", "20px");
		return panel;
	}

	public void tabella(){
		table = new CellTable<Auto>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.setStyleName("tabricerca");
		panel.add(table);
		TextColumn<Auto> nameColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto object) {
				return object.getMarca();
			}
		};

		

		TextColumn<Auto> idColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				return contact.getID()+"";
			}
		};
		idColumn.setSortable(true); 

		TextColumn<Auto> addressColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				return contact.getModello();
			}
		};

		TextColumn<Auto> horseColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				return contact.getCilindrata()+"";
			}
		};

		TextColumn<Auto> yearColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				return contact.getAnno().toString();
			}
		};
		
		TextColumn<Auto> AgenziaColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				if(contact.getIDagenzia() == -1)return "locale";
				else return contact.getIDagenzia()+"";
			}
		};


		TextColumn<Auto> categoryColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				switch (contact.getCategoria()){
				case 1:return "Prestige";
				case 2:return "Family";
				case 3:return "Sport";
				case 4:return "Mini";
				default: return "Errore";
				}
			}
		};
		table.addColumn(AgenziaColumn,"Agenzia");
		table.addColumn(idColumn, "Id");
		table.addColumn(categoryColumn, "Categoria");
		table.addColumn(nameColumn, "Marca");
		table.addColumn(addressColumn, "Modello");
		table.addColumn(horseColumn, "Cilindrata");
		table.addColumn(yearColumn, "Anno");

		ListDataProvider<Auto> dataProvider = new ListDataProvider<Auto>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(table);

		// Add the data to the data provider, which automatically pushes it to the
		// widget.
		List<Auto> list = dataProvider.getList();
		for (Auto contact : auto) {
			list.add(contact);
		}

		// Add a ColumnSortEvent.ListHandler to connect sorting to the
		// java.util.List.
		ListHandler<Auto> columnSortHandler = new ListHandler<Auto>(
				list);

		columnSortHandler.setComparator(idColumn,
				new Comparator<Auto>() {
			@Override
			public int compare(Auto o1, Auto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null && ( o1.getID() > o2.getID()) ) ?  0 : 1;
				}
				return -1;
			}
		});
		table.addColumnSortHandler(columnSortHandler);

		table.getColumnSortList().push(nameColumn);

		// Add it to the root panel.
		//			    panel.add(table);
	}

}