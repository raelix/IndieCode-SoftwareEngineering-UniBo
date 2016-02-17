package it.indieCODE.sweng2013.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Pagamento extends Composite implements ClickHandler, Scheda {

    
    public static String prezzoTotale ="";
    private CheckBox CheckBoxContanti = new CheckBox("Contanti");
    private CheckBox CheckBoxBancomat = new CheckBox("Bancomat");
    private static TextBox textBoxCosto = new TextBox();
    private Button finepagamento = new Button("Esegui il pagamento");
	
    //private static boolean flag = true;

    @Override
    public void onClick(ClickEvent event) {
        Window.alert("Pagamento avvenuto");
    }

    
    public Pagamento(String pagamento, final TabPanel tabpanel) {
     // Place the check above the text box using a vertical panel.
      VerticalPanel panel = new VerticalPanel();
      CheckBoxContanti.setStyleName("text");
      CheckBoxBancomat.setStyleName("text");
      panel.add(new HTML("<text>Inserisci Prezzo Totale:</text>"));
      panel.add(textBoxCosto);
      panel.add(CheckBoxContanti);
      panel.add(CheckBoxBancomat);
      panel.add(finepagamento);
      textBoxCosto.setTitle("Inserisci la cifra");
      CheckBoxContanti.addClickHandler(new ClickHandler(){
    	  @SuppressWarnings("deprecation")
		@Override
		public void onClick(ClickEvent event){
    		  Window.alert("Pagamento in contanti");
    		  CheckBoxBancomat.setChecked(false);
    	  }
      });
      
      CheckBoxBancomat.addClickHandler(new ClickHandler(){
    	  @SuppressWarnings("deprecation")
		@Override
		public void onClick(ClickEvent event){
    		  Window.alert("Pagamento con il Bancomat");
    		  CheckBoxContanti.setChecked(false);
    	  }
      });
      finepagamento.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Pagamento avvenuto");
				tabpanel.selectTab(0);
				Window.Location.reload();
			}
		});
      initWidget(panel);
    }
    
    public static void aggiornaMulta(){
    	textBoxCosto.setText(prezzoTotale);
    	
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