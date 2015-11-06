package PaqueteCliente;

import org.json.JSONException;

public class p {

	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		Cliente c = new Cliente("lolo");
		System.out.println(c.Object);
		c.mensajeUnoAUno("juanito", "holi");
	}

}
