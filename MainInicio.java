package muestras._07Notas;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

public class MainInicio {

	public static void main(String[] args) {

		// 1 01/01/2000 25.0 30.5 20
		// String accion = args[0];
		// cd desktop/pruebas/registros

		String accion = args[0]; // Sevira para elegir la accion a realizar

		// Compara la opcion elegida
		if (accion.equalsIgnoreCase("guardar")) { // crea archivo y guarda datos
			int id = Integer.parseInt(args[1]);
			String fecha = args[2];
			double temperatura = Double.parseDouble(args[3]);
			double humedad = Double.parseDouble(args[4]);
			int precipitacion = Integer.parseInt(args[5]);

			EscrituraRegistros escrituraRegistros = new EscrituraRegistros();
			Meteo clima = new Meteo(id, fecha, temperatura, humedad, precipitacion);
			escrituraRegistros.guardarRegistro(clima);
		}
		else if (accion.equalsIgnoreCase("lectura")) { // Leer los datos en el archivo
			LecturaRegistros lecturaRegistros = new LecturaRegistros();
			lecturaRegistros.lecturaRegistro();
		} 
		else if (accion.equalsIgnoreCase("crearXML")) { // crea un archivo XML de la archivo objeto
			
			//CrearXML.crearXML(id, fecha, temperatura, humedad, precipitacion);
			
			
			
			
		} 
		else if (accion.equalsIgnoreCase("leerXML")) { // lee el archivo XML generado del objeto

		}

	}
}

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//Esto se encargara de agregar los datos a un archivo de tipo objeto
class EscrituraRegistros {
	public void guardarRegistro(Meteo escrituraDatos) {
		ObjectOutputStream escritura;
		// Verificara si el archivo existe, devolviendo true o false.
		File archivo = new File("registro.bin");
		boolean resultado = archivo.exists();
		// Imprimra el tipo de acction que se realiza.
		String respuesta = resultado ? "\nSi existe el archivo. Se ha agregado nuevos datos"
				: "\nEl archivo no existe.\n Se ha creado el registro.";
		try {
			if (resultado) { // Verifica si existe el archivo.
				// Si existe, agregara mas lecturas al registro
				escritura = new AppendObjectOutpurStream(new FileOutputStream("registro.bin", true));
			} else { // Si no existe, lo creara.
				escritura = new ObjectOutputStream(new FileOutputStream("registro.bin", true));
			}
			escritura.writeObject(escrituraDatos); // guardara en un archivo objeto los datos pasados.
			escritura.close();
			System.out.println(respuesta); // Indicara lo que se ha hecho en el sistema.
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

class LecturaRegistros {
	public void lecturaRegistro() {
		// Realizara la lectura del objeto binario, Se puede especificar la ubicacion
		// del archivo.
		try (ObjectInputStream lectura = new ObjectInputStream(new FileInputStream("registro.bin"));) {
			// Se crea un nuevo objeto en donde se guardara el primer dato leido del objeto
			// archivo.
			Object listaLectura = lectura.readObject();
			while (listaLectura != null) { // controla que mientras halla objetos se realiza el bucle.
				// Se instancia un objeto, codigo antiguo: 'if (listaLectura instanceof Meteo)'
				if (listaLectura instanceof Object) {
					System.out.println(listaLectura); // imprimira el contenido del objeto.
					// con esto leeremos los dato del objeto. Si no se pone solo leeria el primer
					// conjuntos de datos y se crearia un buble infinito.
					listaLectura = lectura.readObject();
				}
			}
		} catch (EOFException ex) {
			System.out.println("\nFinal del fichero.");
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (ClassNotFoundException ex) {
			System.err.println(ex);
		}

	}
}

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
// esta clase se encarga de ir agregando mas datos al archivo.
class AppendObjectOutpurStream extends ObjectOutputStream {
	public AppendObjectOutpurStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
	}
}

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

class Meteo implements Serializable {
	// se agrega automaticon con la implementacion de 'Serializable'
	private static final long serialVersionUID = 1L;

	private String fecha;
	private double temperatura, humedad;
	private int id, precipitacion;

	// CONSTRUCTOR
	public Meteo(int id, String fecha, double temperatura, double humedad, int precipitacion) {
		this.id = id;
		this.fecha = fecha;
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.precipitacion = precipitacion;
	}

	// GETTERS
	public int getId() {
		return id;
	}

	public String getFecha() {
		return fecha;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public double getHumedad() {
		return humedad;
	}

	public int getPrecipitacion() {
		return precipitacion;
	}

	// SETTERS
	public void setId(int id) {
		this.id = id;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public void setHumedad(double humedad) {
		this.humedad = humedad;
	}

	public void setPrecipitacion(int precipitacion) {
		this.precipitacion = precipitacion;
	}

	// METODOS
	public String toString() {

		return "\nID: " + id + "\nFecha: " + fecha + "\nTemperatura: " + temperatura + "\nHumedad: " + humedad
				+ "\nPrecipitacion: " + precipitacion;
	}

}

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

class CrearXML {

	public static void crearXML(int id, String fech, double temperatur, double humeda, int precipitacio) {

		try {

			DocumentBuilderFactory documento = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentoBuilder = documento.newDocumentBuilder();

			// elemento raiz
			Document etiqueta = documentoBuilder.newDocument();
			Element rootElement = etiqueta.createElement("Lectura_metereologica");
			etiqueta.appendChild(rootElement);

			// Lectura
			Element Lectura = etiqueta.createElement("Lectura");
			rootElement.appendChild(Lectura);

			// atributo del elemento Lentura
			Attr attr = etiqueta.createAttribute("id");
			attr.setValue(Integer.toString(id)); // recibe el id de la lectura y lo castea a texto
			Lectura.setAttributeNode(attr);

			// fecha
			Element fecha = etiqueta.createElement("fecha");
			// fecha.appendChild(doc.createTextNode("01/01/2000"));
			fecha.appendChild(etiqueta.createTextNode(fech));

			Lectura.appendChild(fecha);

			// temperatura
			Element temperatura = etiqueta.createElement("temperatura");
			temperatura.appendChild(etiqueta.createTextNode(String.valueOf(temperatur)));
			Lectura.appendChild(temperatura);

			// humedad
			Element humedad = etiqueta.createElement("humedad");
			humedad.appendChild(etiqueta.createTextNode(String.valueOf(humeda)));
			Lectura.appendChild(humedad);

			// salario
			Element precipitacion = etiqueta.createElement("precipitacion");
			precipitacion.appendChild(etiqueta.createTextNode(Integer.toString(precipitacio)));
			Lectura.appendChild(precipitacion);

			// escribimos el contenido en un archivo .xml
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(etiqueta);
			StreamResult result = new StreamResult(new File("LecturasXML.xml"));

			transformer.transform(source, result);
			System.out.println("Archivo creado y guardado!"); // mensaje final de que se ha creado el archivo

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

}

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
