package PaqueteJSON;
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
	public JSONObject Object;
	public JSONArray Array;
	
	/**
	 * Recive un String con el nomnbre del key y un objeto para su asignar su valor
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
	 * Recive un objeto JSON,un String con el nomnbre del key y un objeto para su asignar su valor
	 * y añade el nuevo key al objeto ya creado
	 * @param object
	 * @param name
	 * @param value
	 * @throws JSONException
	 */
	public void AddtoJSONObject(JSONObject object,String name,Object value) throws JSONException{
		object.put(name, value);
	}
	/**
	 * Recive un objeto JSON y lo agrega al Arreglo JSON
	 * @param object
	 */
	public void makeJSONArray(JSONObject object){
		JSONArray array = new JSONArray();
		array.put(object);
		this.setJSONArray(array);
	}
	
	/**
	 * Recive un arreglo JSON, un objeto JSON y lo agrega al Arreglo JSON
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
	 * Fin de la seccion de getters
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