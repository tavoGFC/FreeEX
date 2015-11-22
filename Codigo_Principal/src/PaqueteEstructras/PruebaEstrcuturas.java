package PaqueteEstructras;

import PaqueteCliente.Usuario;

public class PruebaEstrcuturas {
	public static void main (String args[]) throws Exception {
	       final GrafMatPeso t = new GrafMatPeso (4);
	     	 t.print();
	     	 System.out.println("------------------------------------");
	       	 Usuario a = new Usuario("a@gmail.com","a1",t);
	       	 Usuario b = new Usuario("b@hotmail.com","b1",t);
	       	 Usuario c = new Usuario("c@yahoo.com","c1",t);
	         Usuario d = new Usuario("d@gmail.com","d1",t);
	       	 t.print();
	       	 System.out.println("------------------------------------");
	       	 System.out.println(a.getJSONObject());
	       	 System.out.println(b.getJSONObject());
	       	 System.out.println(c.getJSONObject());
	       	 System.out.println(d.getJSONObject());

//	       t.setKey (0, "v0");
//	       t.setKey (1, "v1");
//	       t.setKey (2, "v2");
//	       t.setKey (3, "v3");
//	       t.setKey (4, "v4");
//	       t.setKey (5, "v5");
//	       t.nuevoArco (0,1,2);
//	       t.nuevoArco (0,5,9);
//	       t.nuevoArco (1,2,8);
//	       t.nuevoArco (1,3,15);
//	       t.nuevoArco (1,5,6);
//	       t.nuevoArco (2,3,1);
//	       t.nuevoArco (4,3,3);
//	       t.nuevoArco (4,2,7);
//	       t.nuevoArco (5,4,3);
//	       t.print();
	 
	       final int [] pred = Dijkstra.dijkstra (t, 0);
	       for (int n=0; n<t.getMaxV(); n++) {   
	    	   Dijkstra.printPath (t, pred, 1, n);
	       }
	}

}
