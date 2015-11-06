package PaqueteCliente;
import org.json.JSONException;

import PaqueteJSON.*;

/**
 * 
 * @author Kevin
 *
 */

public class Cliente extends JSON{
	
	/**
	 * Constructor de la clase Cliente
	 * @param usuario
	 * @throws JSONException
	 */
	public Cliente(String usuario) throws JSONException {
		super();
		this.usuario = usuario;
		this.makeJSONObject("usuario", usuario);
	}
	
	/**
	 * Atributos de la clase Cliente
	 */
	public String ubicacion;
	public String usuario;
	public String mensaje;
	
	/**
	 * Recive un String, permite redactar el mensaje que va a ser recibido
	 * @param msj
	 * @throws JSONException
	 */
	public void redactarMensaje(String msj) throws JSONException{
		this.setMensaje(msj);
		this.makeJSONObject(this.usuario,this.mensaje);
		System.out.println(this.Object);
	}
	
	/**
	 * Recive String con el usuario y el mensaje, permite realizar el envio de msj uno a uno
	 * @param Usuario
	 * @param mensaje
	 * @throws JSONException
	 */
	public void mensajeUnoAUno(String Usuario, String mensaje) throws JSONException{
		this.redactarMensaje(mensaje);
		this.AddtoJSONObject(Object, "receptor", Usuario);
		System.out.println(this.Object.toString());	
	}
	
	/**
	 *Recive String con el mensaje, permite realizar el envio de difusion
	 * @param mensaje
	 * @throws JSONException
	 */
	public void mensajeDifusion(String mensaje) throws JSONException{
		this.redactarMensaje(mensaje);
	}
	
	/**
	 * Inicio de la seccion de getters
	 */
	public String getUsername(){
		return usuario;
	}
	
	public String getMensaje(){
		return mensaje;
	}

	public String getUbicacion() {
		return ubicacion;
	}
	/**
	 * Fin de la seccion de getters
	 */
	
	/**
	 * Inicio de la seccion de setters
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * Fin de la seccion de setters
	 */
}
