package PaqueteJSON;
import org.json.*;

public class JSON {
	
	JSONObject o;
	JSONArray a;
	
	
	public void makeJSONObject(String name,Object value ) throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put(name, value);
		this.setJSONObject(obj);
	}
	
	public void AddtoJSONObject(JSONObject object,String name,Object value) throws JSONException{
		object.put(name, value);
	}
	
	public void makeJSONArray(JSONObject object){
		JSONArray array = new JSONArray();
		array.put(object);
		this.setJSONArray(array);
	}
	
	public void AddtoJSONArray(JSONArray array, JSONObject object){
		array.put(object);
	}
	
	public void setJSONObject(JSONObject object){
		o=object;
	}
	
	public void setJSONArray(JSONArray array){
		a=array;
	}
	
	public JSONObject getJSONObject(){
		return o;
	}
	
	public JSONArray getJSONArray(){
		return a;
	}

}