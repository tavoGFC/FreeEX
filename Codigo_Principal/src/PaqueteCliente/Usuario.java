package PaqueteCliente;

import org.json.JSONException;
import PaqueteEstructras.*;
import PaqueteJSON.*;

/**
 * 
 * @author Kevin
 *
 */

public class Usuario extends JSON{
	/**
	 * Atributos de la clase Cliente
	 */
	protected String correo;
	protected int ubicacion;
	protected String nombre;
	protected String mensaje;
	protected String mensajeEntrada;
	protected GrafoMatriz grafo;
	protected GrafMatPeso gp;
	
	
	/**
	 * Constructor de la clase Cliente
	 * @param usuario
	 * @throws Exception 
	 */
	
	public Usuario(String correo,String nUsuario,GrafoMatriz grafo) throws Exception {
 		super();
 		this.correo = correo;
 		this.nombre = nUsuario;
 		this.setGrafo(grafo);
 		this.makeJSONObject("Usuario", nUsuario);
 		this.getGrafo().nuevoVertice(getCorreoElectronico());
		
 	}
 	
	public Usuario(String correo,String nUsuario,GrafMatPeso grafo) throws Exception {
		super();
		this.setGrafP(grafo);
		if (this.getGrafP().cuentaVert(this.getGrafP())<this.getGrafP().getMaxV()){
			this.correo = correo;
			this.nombre = nUsuario;
			this.setUbicacion(ubicacion);
			this.makeJSONObject("usuario", nUsuario);
			this.AddtoJSONObject(this.getJSONObject(), "Correo", this.getCorreoElectronico());
			if (this.getGrafP().cuentaNull(grafo)==this.getGrafP().largo()){
				this.getGrafP().setKey(0, this.getCorreoElectronico());
				this.getGrafP().nuevoArco(0, 0, 0);
				for (int i=1;i<this.getGrafP().getMaxV();i++){
					this.getGrafP().nuevoArco(0, i, 12);
				}
			}
			else{
				this.getGrafP().setKey(this.getGrafP().cuentaVert(this.getGrafP()), this.getCorreoElectronico());
				for (int i=0;i<this.getGrafP().getMaxV();i++){
					if(i==this.getGrafP().cuentaVert(this.getGrafP())-1){
						this.getGrafP().nuevoArco(this.getGrafP().cuentaVert(this.getGrafP())-1, i, 0);
					}
					else{
						this.getGrafP().nuevoArco(this.getGrafP().cuentaVert(this.getGrafP())-1, i, 12);
					}
				}
			}
		}
		else{
			System.out.println("No se puede agregar mas usuarios a la conversacion");
		}
	}
	
	/**
	 * Recive un String, permite redactar el mensaje que va a ser recibido
	 * @param msj
	 * @throws JSONException
	 */
	public void redactarMensaje(String msj) throws JSONException{
		this.setMensaje(msj);
		this.makeJSONObject(this.nombre,this.mensaje);
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
	public String getNombreUsuario(){
		return nombre;
	}
	
	public String getCorreoElectronico(){
		return correo;
	}
	
	public String getMensaje(){
		return mensaje;
	}

	public int getUbicacion() {
		return ubicacion;
	}
	public GrafoMatriz getGrafo() {
		return this.grafo;
	}
	public GrafMatPeso getGrafP() {
		return this.gp;
	}
	/**
	 * Fin de la seccion de getters
	 */
	
	/**
	 * Inicio de la seccion de setters
	 */
	public void setUbicacion(int ubicacion) {
		this.ubicacion = ubicacion;
	}
	public void setGrafo(GrafoMatriz grafo) {
		this.grafo= grafo;
	}
	public void setGrafP(GrafMatPeso gp) {
		this.gp= gp;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * Fin de la seccion de setters
	 */
}
