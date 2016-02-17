/**
 * 
 */
package it.indieCODE.sweng2013.client;

import java.sql.Date;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import it.indieCODE.sweng2013.shared.Auto;
import it.indieCODE.sweng2013.shared.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

/**
 * @author phra
 *
 */
public final class Noleggio extends Composite implements ClickHandler, Scheda {

	private TextBox textBoxIdAuto = new TextBox();
	private static TextBox textBoxIdCliente = new TextBox();
	private TextBox textBoxDataFine = new TextBox();
	private TextBox textBoxGuidatore = new TextBox();
	private TextBox textBoxSeggiolini = new TextBox();
	private TextBox textBoxAgenziaConsegna = new TextBox();
	private TextBox textBoxCosto = new TextBox();
	private CheckBox checkNavigatore = new CheckBox("navigatore");
	public static String IDcliente = "";
	final Button send = new Button("Noleggia");
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	public static LinkedList<Auto> auto ;
	CellTable<Auto> table = new CellTable<Auto>();

	VerticalPanel panel = new VerticalPanel();
	@Override
	public void onClick(ClickEvent event) {
		Window.alert("Inserisci i dati");
	}

	//private static boolean flag = true;

	/**
	 * @param str
	 */

	@SuppressWarnings("deprecation")
	public Noleggio(String str, final TabPanel tabpanel) {

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		greetingService.allAuto(
				new AsyncCallback<LinkedList<Auto>>() {

					@Override
					public void onFailure(Throwable caught) {	
						System.out.println("error load all local auto");
					}


					@Override
					public void onSuccess(LinkedList<Auto> result) {
						// TODO Auto-generated method stub
						auto = result;
						tabella();
					}
				});





		checkNavigatore.setStyleName("text");
		panel.add(new HTML("<text>Inserisci ID Auto:</text>"));
		panel.add(textBoxIdAuto);
		panel.add(new HTML("<text>Inserisci ID Cliente:</text>"));
		panel.add(textBoxIdCliente);
		panel.add(new HTML("<text>Inserisci Data Fine Noleggio:</text>"));
		panel.add(textBoxDataFine);
		panel.add(new HTML("<text>Inserisci ID agenzia Di Consegna:</text>"));
		panel.add(textBoxAgenziaConsegna);
		panel.add(new HTML("<text>Inserisci Eventuale Patente di Guidatore Aggiuntivo:</text>"));
		panel.add(textBoxGuidatore);
		panel.add(new HTML("<text>Inserisci Numero Di Seggiolini:</text>"));
		panel.add(textBoxSeggiolini);
		textBoxSeggiolini.setText("0");
//		panel.add(new HTML("<text>Inserisci Costo Totale Noleggio:</text>"));
//		panel.add(textBoxCosto);
		panel.add(checkNavigatore);
		panel.add(send);
		checkNavigatore.setChecked(false);

		send.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if ((textBoxIdAuto.getText().length() == 0) &&
						(textBoxDataFine.getText().length() == 0) &&
						(textBoxIdCliente.getText().length() == 0) &&
						(textBoxCosto.getText().length() == 0)) return;
				String fine,guidatore;
				int seggiolini, agenzia, auto, cliente;
				fine = textBoxDataFine.getText();
				if (Utils.isStringNumeric(textBoxIdAuto.getText())) auto = new Integer(textBoxIdAuto.getText()); else return;
				if (Utils.isStringNumeric(textBoxIdCliente.getText())) cliente = new Integer(textBoxIdCliente.getText()); else return;
				if (textBoxAgenziaConsegna.getText().length() == 0 || !Utils.isStringNumeric(textBoxAgenziaConsegna.getText())) agenzia = -1; else agenzia = new Integer(textBoxAgenziaConsegna.getText());
				if (textBoxSeggiolini.getText().length() == 0 || !Utils.isStringNumeric(textBoxSeggiolini.getText())) seggiolini = 0; else seggiolini = new Integer(textBoxSeggiolini.getText());
				if (textBoxGuidatore.getText().length() == 0) guidatore = null; else guidatore = textBoxGuidatore.getText();
				greetingService.noleggio(auto, cliente, agenzia, new Date(new java.util.Date(fine).getTime()), guidatore, seggiolini, checkNavigatore.isChecked(), new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert(caught.getMessage());

					}

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						Window.alert("Noleggio Avvenuto Con Successo");
						tabpanel.selectTab(0);
						Window.Location.reload();
					}

				});
			}
		});



		// All composites must call initWidget() in their constructors.
		initWidget(panel);
		// Give the overall composite a style name.
		//setStyleName("example-Noleggio");
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public static void reload(){
		textBoxIdCliente.setText(IDcliente);
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.Scheda#makeFlowPanel()
	 */

	public void tabella(){

		TextColumn<Auto> nameColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto object) {
				return object.getMarca();
			}
		};

		nameColumn.setSortable(true); 

		TextColumn<Auto> idColumn = new TextColumn<Auto>() {
			@Override
			public String getValue(Auto contact) {
				return contact.getID()+"";
			}
		};

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

		columnSortHandler.setComparator(nameColumn,
				new Comparator<Auto>() {
			@Override
			public int compare(Auto o1, Auto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getMarca().compareTo(o2.getMarca()) : 1;
				}
				return -1;
			}
		});
		table.addColumnSortHandler(columnSortHandler);

		table.getColumnSortList().push(nameColumn);

		// Add it to the root panel.
		//			    panel.add(table);
	}
	@Override
	public FlowPanel makeFlowPanel() {
		FlowPanel panel = new FlowPanel();
		table.setStyleName("tables");
		panel.add(table);
		panel.add(this);
		this.setSize("100%", "650px");
		DOM.setStyleAttribute(this.getElement(), "border", "10px solid DarkSlateGray ");
		return panel;
	}
}
