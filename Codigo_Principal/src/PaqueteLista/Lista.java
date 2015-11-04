package PaqueteLista;
/**
 * 
 * @author Kevin
 *
 */
public class Lista {
	public Nodo head;
	public Nodo tail;
	
	/**
	 * Constructor de la clase Lista
	 */
	public Lista(){
		head=tail=null;
	}
	
	public boolean estaVacia(){
		return head==null;
	}
	/**
	 * Agrega un nuevo objeto al final de la lista
	 * @param ele Es el objeto que se va a agregar en la lista
	 */
	public void nuevoObj(Object ele){
		Nodo nuevo= new Nodo(ele);
		if (!estaVacia()){
			this.tail.sig=nuevo;
			nuevo.ant=tail;
			nuevo.indice = tail.indice+1;
			tail=nuevo;
		}
		else{
			this.head = this.tail = nuevo;
			nuevo.indice=0;
		}
	}
	/**
	 * Retorna el tipo de clase que se ingresa en "ele" y retorna ese tipo cuando el elemento es encontrado
	 * @param ele Un elemento de cualquier tipo.
	 * @return retorna ese tipo de elemento cuando el elemento es encontrado
	 */
	public <Tipo>Tipo buscar(Tipo ele){
		Nodo temp;
		temp=this.head;
		while (temp!=null){
			if (!temp.ele.equals(ele)){
				temp=temp.sig;
			}
			else{
				break;
			}
		}
		if (temp==null){
			System.out.println("No se encontro el elemento");
			return null;
		}
		System.out.println(temp.ele);
		return (Tipo) temp.ele;
	}
	/**
	 * Retorna la cantidad de elementos del tipo del objeto que fue buscado
	 * @param ele Es el objeto que se va a utilizar para coprobar y realizar el contador cuando el elemento del nodo claza con el buscado
	 * @return la cantidad de elementos del tipo del objeto que fue buscado
	 */
	public int CantObj(Object ele){
		Nodo temp;
		temp=head;
		int i=0;
		while (temp!=null){
			if (!temp.ele.getClass().equals(ele.getClass())){
				temp=temp.sig;
			}
			else{
				i=i+1;
				temp=temp.sig;
			}
		}
		System.out.println("se encuentran " + i + " del elemento buscado");
		return i;
	}
	/**
	 * Busca un elemento de la lista y lo elimina de la misma
	 * @param ele Es el objeto que se va a borrar de la lista
	 */
	public void EliminarObj(Object ele){
		Nodo temp;
		temp=head;
		if (head!=null){
			if(ele.equals(head.ele)){
				this.head=head.sig;
			}
			else if(ele.equals(tail.ele)){
				this.tail=tail.ant;
			}
			else{
				boolean borrado=false;
				while (temp!=null){
					if (!temp.ele.equals(ele)){
						if ( borrado){
							temp.indice--;
							temp=temp.sig;
						}
						else{
							temp=temp.sig;
						}
					}
					else{
						temp.ant.sig=temp.sig;
						temp.sig.ant=temp.ant;
						borrado=true;
						temp=temp.sig;
					}
				}
			}
		}
	}
	/**
	 * Brinda el elemento ubicado en el indice del nodo que fue ingresado
	 * @param n numero del indice del la lista
	 * @return elemento ubicado en el indice del nodo que fue ingresado
	 */
	public Object Sub(int n){
		if (n<0){
			System.out.println("Error");
			return null;
		}
		if (n>this.tail.indice){
			System.out.println("Error,indice fuera del rango de lista ");
			return null;
		}
		else{
			Nodo temp= this.head;
			int i=0;
			while (i<n){
				temp=temp.sig;
				i++;
			}
			return temp.ele;
		}
	}
	/**
	 * Imprime en pantalla los elementos contenidos en la lista
	 */
	public void Imprimir(){
		Nodo temp;
		temp=this.head;
		if (head==null){
			System.out.println("Lista vacia");
			return;
		}
		while(temp!=this.tail){
			System.out.println(temp.ele.toString());
			temp=temp.sig;
		}
		System.out.println(this.tail.ele.toString());
	}
}