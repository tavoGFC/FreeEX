package PaqueteEstructras;

import PaqueteCliente.Usuario;

/**
 * 
 * @author Kevin
 *
 */
public class GrafMatPeso { 
	/**
	 * Atributos de la clase
	 */
	protected int maxV;
    private int [][]  arco;  //matriz de adyacencia
    private Object [] key;
 
    /**
     * Constructor de la clase recive la maxima cantidad de vertices que recibira la matriz
     * @param n
     */
    public GrafMatPeso (int n) {
       this.setMaxV(n);
       arco  = new int [n][n];
       key = new Object[n];
    }
    
    /**
     * Recibe un grafo y cuenta la cantidad de nulls en la matriz
     * @param p
     * @return
     */
    public int cuentaNull(GrafMatPeso p){
    	int cont=0;
    	for(int i=0;i!=p.largo();i++){
    		if (p.getKey(i)==null){
    			cont++;
    		}
    		else{
    			;
    		}
    	}
		return cont;
    }
    /**
     * Recibe un grafo y cuenta la cantidad de vertices en la matriz
     * @param p
     * @return
     */
    public int cuentaVert(GrafMatPeso p){
    	int cont=0;
    	for(int i=0;i!=p.largo();i++){
    		if (p.getKey(i)!=null){
    			cont++;
    		}
    		else{
    			;
    		}
    	}
		return cont;
    }
    
    /**
     * retorna el largo
     * @return
     */
    public int largo(){
    	return key.length;
    } 
    /**
     * Asigna el valor maximo para la matriz
     * @param n
     */
    public void  setMaxV(int n){
    	this.maxV=n;
    }
    /**
     * recibe un numero de vertice y un objeto para asignarlo al valor del key y crea el vertice con dichos valores
     * @param vertice
     * @param value
     */
    public void  setKey (int vertice, Object value){
    	key[vertice]=value;
    }

    /**
     * Obtiene la cantidad maxima de vertices
     * @return
     */
    public int getMaxV(){
    	return this.maxV;
    }
    
   /**
    * recibe un int que representa un vertice y retorna el valor contenido en dicho vertice
    * @param vertice
    * @return
    */
    public Object getKey(int vertice){
    	return key[vertice];
    }
    /**
     * Recibe un origen y un destino, crea un arco entre los vertices con el peso asignado
     * @param origen
     * @param destino
     * @param w
     */
    public void nuevoArco(int origen, int destino, int w){
    	arco[origen][destino] = w;
    }
    /**
     *Recibe un origen y un destino, crea un arco entre los vertices con el peso asignado
     * @param origen
     * @param destino
     * @return
     */
    public boolean esArco(int origen, int destino){
    	return arco[origen][destino]>0;
    }
    /**
     *Recibe un origen y un destino, elimina un arco entre dos vertice
     * @param origen
     * @param destino
     */
    public void borrarArco(int origen, int destino){
    	arco[origen][destino] = 0;
    }
    /**
     *Recibe un origen y un destino y obtiene el peso del arco entre los vertices
     * @param origen
     * @param destino
     * @return
     */
    public int getPeso (int origen, int destino){
    	return arco[origen][destino]; 
    }
   /**
    *Este recibe un vertice y determina todos los ayacentes a este.
    * @param vertice
    * @return
    */
    public int [] adyacencia (int vertice){
       int cont = 0;
       for (int i=0; i<arco[vertice].length; i++) {
          if (arco[vertice][i]>0) cont++;
       }
       final int[]resolucion= new int[cont];
       cont = 0;
       for (int i=0; i<arco[vertice].length; i++) {
          if (arco[vertice][i]>0) resolucion[cont++]=i;
       }
       return resolucion;
    }
    /**
     * Imprime los vertices y sus arcos
     */
    public void print () {
       for (int j=0; j<arco.length; j++) {
          System.out.print (key[j]+": ");
          for (int i=0; i<arco[j].length; i++) {
             if (arco[j][i]>0) System.out.print (key[i]+":"+arco[j][i]+" ");
          }
          System.out.println ();
       }
    }
	 
    public static void main (String args[]) throws Exception {
       final GrafMatPeso t = new GrafMatPeso (6);
//     	 t.print();
//       	 Usuario a = new Usuario("a","a1",t);
//       	 Usuario b = new Usuario("b","b1",t);
//       	 Usuario c = new Usuario("c","c1",t);
//
//       	 System.out.println(t.cuentaVert(t));
//       	 t.print();
       t.setKey (0, "v0");
       t.setKey (1, "v1");
       t.setKey (2, "v2");
       t.setKey (3, "v3");
       t.setKey (4, "v4");
       t.setKey (5, "v5");
       t.nuevoArco (0,1,2);
       t.nuevoArco (0,5,9);
       t.nuevoArco (1,2,8);
       t.nuevoArco (1,3,15);
       t.nuevoArco (1,5,6);
       t.nuevoArco (2,3,1);
       t.nuevoArco (4,3,3);
       t.nuevoArco (4,2,7);
       t.nuevoArco (5,4,3);
       t.print();
 
       final int [] pred = Dijkstra.dijkstra (t, 0);
       for (int n=0; n<6; n++) {          Dijkstra.printPath (t, pred, 0, n);
       }
	 } 
}