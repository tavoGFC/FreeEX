package PaqueteEstructras;

/**
 * 
 * @author Kevin
 *
 */
public class Dijkstra {
	
	/**
	 * Este metodo recibe un grafo con pesos y un entero el cual representa el nodo de origen, marca como vistados los nodos y retorna el anterior 
	 * para mantener referencia.
	 * @param grafo
	 * @param s
	 * @return
	 */
    public static int [] dijkstra (GrafMatPeso grafo, int s) {
    	
       /**
        * la distacia minima conocidad desde "s"	
        */
       final int [] dist = new int [grafo.largo()];  
       final int [] ant = new int [grafo.largo()];
       /**
        * Todos se encuentran el falso inicialmente
        */
       final boolean [] visitado = new boolean [grafo.largo()];
 
       for (int i=0; i<dist.length; i++) {
          dist[i] = Integer.MAX_VALUE;
       }
       dist[s] = 0;
 
       for (int i=0; i<dist.length; i++) {
          final int sig = minVertex (dist, visitado);
          visitado[sig] = true;
 
 
          final int [] n = grafo.adyacencia(sig);
          for (int j=0; j<n.length; j++) {
             final int v = n[j];
             final int d = dist[sig] + grafo.getPeso(sig,v);
             if (dist[v] > d) {
                dist[v] = d;
                ant[v] = sig;
             }
          }
       }
       return ant;  // (ignore pred[s]==0!)
    }
    /**
     * Recibe un array con las distancia y uno con el estado de visitado, luego calcula el minimo entre los vertices
     * @param dist
     * @param v
     * @return
     */
    private static int minVertex (int [] dist, boolean [] v) {
       int x = Integer.MAX_VALUE;
       int y = -1;   // graph not connected, or no unvisited vertices
       for (int i=0; i<dist.length; i++) {
          if (!v[i] && dist[i]<x) {y=i; x=dist[i];}
       }
       return y;
    }
    /**
     * Este metodo recibe un grafo con pesos un arreglo de enteros que contiene los predecesores, el entero desde donde
     * se desea conocer el camino mas corto y un entero que guarda el nodo anterior e imprime el camino mas corto desde ese nodo
     * @param G
     * @param pred
     * @param s
     * @param e
     */
    public static void printPath (GrafMatPeso G, int [] ant, int s, int e) {
       final java.util.ArrayList path = new java.util.ArrayList();
       int x = e;
       while (x!=s) {
          path.add (0, G.getKey(x));
          x = ant[x];
       }
       path.add (0, G.getKey(s));
       System.out.println (path);
    } 
 }