package PaqueteCliente;
import org.json.JSONException;

import PaqueteJSON.*;


public class Cliente extends JSON{
	
	public String ubicacion;
	public String usuario;
	public String mensaje;
	
	public void setUsername(String nombre){
		usuario=nombre;
	}
	public String getUsername(){
		return usuario;
	}
	public String getMensaje(){
		return mensaje;
	}
	public void redactarMensaje(String msj) throws JSONException{
		mensaje=msj;
		this.makeJSONObject(this.usuario,this.mensaje);
		System.out.println(this.Object);
	}

}
