package es.cic.taller.ejercicio07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PersonaForm extends FormLayout{
	 private final VerticalLayout layout = new VerticalLayout();
     
     //Campo del nombre
	 private final TextField nombre = new TextField();
     
     //Campo del apellido
	 private final TextField apellido = new TextField();
     
     //Campo de la fecha
	 private DateTimeField fecha = new DateTimeField();
     
	 private Collection listaPaises = getListaPaises();
	 private ComboBox<Pais> pais = new ComboBox<>("Select your country", listaPaises);
     
	 private Binder<Persona> binder = new Binder<>(Persona.class); 
	 
     private MyUI myUI;
     
     public PersonaForm(MyUI miUI) {
    	 super();
    	 myUI = miUI;
    	 addComponents(nombre, apellido, fecha, pais);
    	 binder.bindInstanceFields(this);
     }
     
     public void setPersona(Persona persona) {
    	 nombre = " ";
     }
     
     private Collection<Pais> getListaPaises(){
 		List<Pais> listaPaises = new ArrayList<Pais>();
 		
 		Pais pais = new Pais("España");
 		listaPaises.add(pais);
 		
 		Pais pais2 = new Pais("Francia");
 		listaPaises.add(pais2);
 		
 		Pais pais3 = new Pais("Noruega");
 		listaPaises.add(pais3);
 		
 		Pais pais4 = new Pais("Finlandia");
 		listaPaises.add(pais4);
 		
 		return listaPaises;
 	}
}
