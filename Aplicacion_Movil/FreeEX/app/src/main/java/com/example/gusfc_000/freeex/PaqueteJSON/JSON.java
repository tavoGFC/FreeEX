package com.example.gusfc_000.freeex.PaqueteJSON;
import org.json.*;

/**
 * 
 * @author Kevin
 *
 */
public class JSON {
	
	/**
	 * Atributos de la clase JSON
	 */
	protected JSONObject Object;
	protected JSONArray Array;
	
	/**
	 * Recibe un String con el nomnbre del key y un objeto para su asignar su valor
	 * @param name
	 * @param value
	 * @throws JSONException
	 */
	public void makeJSONObject(String name,Object value ) throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put(name, value);
		this.setJSONObject(obj);
	}
	/**
	 * Recibe un objeto JSON,un String con el nomnbre del key y un objeto para su asignar su valor
	 * y a�ade el nuevo key al objeto ya creado
	 * @param object
	 * @param name
	 * @param value
	 * @throws JSONException
	 */
	public void AddtoJSONObject(JSONObject object,String name,Object value) throws JSONException{
		object.put(name, value);
	}
	/**
	 * Recibe un objeto JSON y lo agrega al Arreglo JSON
	 * @param object
	 */
	public void makeJSONArray(JSONObject object){
		JSONArray array = new JSONArray();
		array.put(object);
		this.setJSONArray(array);
	}
	
	/**
	 * Recibe un arreglo JSON, un objeto JSON y lo agrega al Arreglo JSON
	 * @param array
	 * @param object
	 */
	public void AddtoJSONArray(JSONArray array, JSONObject object){
		array.put(object);
	}
	
	/**
	 * Inicio de la seccion de setters
	 */
	public void setJSONObject(JSONObject object){
		this.Object=object;
	}
	
	public void setJSONArray(JSONArray array){
		this.Array=array;
	}
	/**
	 * Fin de la seccion de setters
	 */
	
	/**
	 * Inicio de la seccion de getters
	 */
	public JSONObject getJSONObject(){
		return Object;
	}
	
	public JSONArray getJSONArray(){
		return Array;
	}
	
	/**
	 * Fin de la seccion de getters
	 */

}