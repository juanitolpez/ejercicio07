package es.cic.taller.ejercicio07;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.datefield.LocalDateFieldState;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private PersonaForm personaForm = new PersonaForm(this);
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        //Campo del nombre
        final TextField tfNombre = new TextField();
        tfNombre.setCaption("Nombre");
        
        //Campo del apellido
        final TextField tfApellido = new TextField();
        tfApellido.setCaption("Apellido");
        
        //Campo de la fecha
        DateTimeField tfFecha = new DateTimeField();
        tfFecha.setCaption("Fecha");
        
        //Lista de los paises
        Collection<Pais> listaPaises = getListaPaises();
 
        ComboBox<Pais>  cPais= new ComboBox<>("Select your country", listaPaises);
 
        cPais.setPlaceholder("No country selected");
        cPais.setItemCaptionGenerator(Pais::getNombreCompleto);
        cPais.setEmptySelectionAllowed(false);
        
        //Boton final
        Button button = new Button("Aceptar");
        button.addClickListener( e -> {
        	Persona p = new Persona();
        	
        	p.setNombre(tfNombre.getValue());
        	p.setApellido(tfApellido.getValue());
        	p.setFecha(tfFecha.getValue());
        	p.setPais(cPais.getValue());
        	
            Notification.show(toString(p), Type.TRAY_NOTIFICATION);
        });
        
        
        Button button2 = new Button("Cambiar");
        button2.addClickListener(e ->{
        	button2.setVisible(!button2.isVisible());
        });
        
        
        Button button3 = new Button("Cambiar persona");
        button3.addClickListener(e ->{
        	Persona persona = leerDesdeBBDD();
        	
        	tfNombre.setValue(persona.getNombre());
        	tfApellido.setValue(persona.getApellido());
        	tfFecha.setValue(persona.getFecha());
        	cPais.setValue(persona.getPais());
        	
        	personaForm.setPersona(persona);
        });
        
        cPais.setPlaceholder("No has seleccionado pais");
       // cPais.setItemCaptionGenerator(itemCaptionGenerator);
        
        
        
        layout.addComponents(tfNombre, tfApellido, tfFecha, cPais, button, button2);
        
        setContent(layout);
    }

	private Persona leerDesdeBBDD() {
		Persona persona = new Persona();
		
		persona.setNombre("Alberto");
		persona.setApellido("Pico");
		persona.setFecha(LocalDateTime.now());
		Pais pais = new Pais("España");
		persona.setPais(pais);
		
		return persona;
	}

	private String toString(Persona p) throws NullPointerException {
		LocalDateTime fecha = p.getFecha();
		if(fecha==null) {
			Notification.show("Falta fecha", Type.ERROR_MESSAGE);
			throw new NullPointerException();
		}
		
		String mensaje = String.format("Hola %s %s, estamos a %s en %s", p.getNombre(), p.getApellido(), 
				p.getFecha(), p.getPais().getNombreCompleto());
		
		return mensaje;
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

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
