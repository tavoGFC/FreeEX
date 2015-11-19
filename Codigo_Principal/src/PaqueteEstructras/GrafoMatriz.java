package PaqueteEstructras;

/**
 * 
 * @author Kevin
 *
 */
public class GrafoMatriz {
	
	/**
	 * Atributos de la Clase GrafoMatriz
	 */
	int numVerts; 
	static int MaxVerts = 20;
	Vertice [] verts;
	int [][] matAd;
	
	/**
	 * Constructores de la Clase
	 */
	public GrafoMatriz() { 
		this(MaxVerts);
	} 
	
	public GrafoMatriz(int mx){ 
	matAd = new int [mx][mx]; 
	verts = new Vertice[mx];
	for (int i = 0; i < mx; i++){
		for (int j = 0; i < mx; i++)
			matAd[i][j] = 1; 
		}
	numVerts = 0; 
	}
	
	/**
	 * Recibe un string con el indificador y crea un nuevo vertice
	 * @param nom
	 */
	public void nuevoVertice(String nom){
		boolean esta = numVertice(nom)>=0;
		if (!esta){
			Vertice v = new Vertice(nom);
			v.asigVert(numVerts);
			verts[numVerts++]=v;
		}
	}
	
	/**
	 * Recibe un string con el indentificador y retorna el numero del vertice en el garfo
	 * @param vs
	 * @return numero del vertice en el grafo
	 */
	public int numVertice(String vs){
		Vertice v = new Vertice(vs);
		boolean encontrado = false;
		int i = 0;
		for (; (i < numVerts) && !encontrado;){
			encontrado = verts[i].equals(v);
			if (!encontrado) i++ ;
			}
		return (i < numVerts) ? i : -1 ; 
	} 
	
//	/**
//	 * Recive strings con los indetificadores de los vertices y crea el arco entre ellos
//	 */
//	public void nuevoArco(String a, int b)throws Exception { 
//		int va;
//		va = numVertice(a);
//		if (va < 0 || b < 0) throw new Exception("Vértice no existe");
//		matAd[va][b] = 1; 
//	}
//	
	/**
	 * Recive dos strings con los indetificadores de los vertices y dice si hay una relacion entre ellos
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public boolean adyacente(String a, String b)throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0) throw new Exception ("Vértice no existe");
		return matAd[va][vb] == 1; 
	}

}


	