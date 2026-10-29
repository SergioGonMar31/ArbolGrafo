package modelo;

import java.util.Comparator;

//Arbol de busqueda binaria. El dato "menor" va a la izquierda y el mayor (o igual) a la derecha
public class ArbolBusqueda<T> extends ArbolBinario<T> {
    protected Comparator<T> comparador;
    private boolean borrado;                 // para saber si borrar() encontró el dato


    //Constructor
    public ArbolBusqueda(Comparator<T> comparador) {
        super();
        this.comparador = comparador;
    }

    //Insertar datos en la raiz
    public void insertar(T dato) {
        raiz = insertar(raiz, dato);
    }


    //Insertar datos en los nodos,
    private Nodo<T> insertar(Nodo<T> n, T dato) {
        if (n == null) return new Nodo<>(dato);
        //Si el dato dado es "menor" que el dato que hay en el nodo, se añade a su izquierda
        if (comparador.compare(dato, n.getDato()) < 0) {
            n.setIzquierda(insertar(n.getIzquierda(), dato));
        } else {
            //Si es mayor o igual se añade a su derecha
            n.setDerecha(insertar(n.getDerecha(), dato));
        }
        return n;
    }

   //Devuelve el dato buscado
    public T buscar(T dato) {
        //Empezamos en la raiz
        Nodo<T> n = raiz;
        while (n != null) {
            //Le da a r el valor que devuelve compare tras comparar ambos datos
            int r = comparador.compare(dato, n.getDato());
            //Si r es igual a 0 significa que el dato es igual, asi que devuelve el dato de n
            if (r == 0) return n.getDato();
            //Si el dato es menor que cero n pasa a ser el dato de la izquierda, si es mayor el de la derecha
            n = (r < 0) ? n.getIzquierda() : n.getDerecha();
        }
        return null;
    }

   //Borra el dato que se introduce
    public boolean borrar(T dato) {
        borrado = false;
        raiz = borrar(raiz, dato);
        return borrado;
    }


    private Nodo<T> borrar(Nodo<T> n, T dato) {
        if (n == null) return null;

        //Compara el dato dado con el dato del nodo
        int r = comparador.compare(dato, n.getDato());
        //Si es menor vuelve a llamar a borrar pero con los datos de la izquierda
        if (r < 0) {
            n.setIzquierda(borrar(n.getIzquierda(), dato));
            //Si es mayor vuelve a llamar a borrar con los datos de la derecha
        } else if (r > 0) {
            n.setDerecha(borrar(n.getDerecha(), dato));
        } else {
            borrado = true;
            // Si por la izquierda no tiene datos devuelve el dato de la derecha
            if (n.getIzquierda() == null) return n.getDerecha();
            //Si el de la izquierda no tiene nada devuelve el de la izquierda
            if (n.getDerecha() == null) return n.getIzquierda();
            // Creamos un nodo sucesor.
            Nodo<T> sucesor = n.getDerecha();
            //Mientras haya nodo a la izquierda. Cambia al de la izquierda
            while (sucesor.getIzquierda() != null) sucesor = sucesor.getIzquierda();
            //Al nodo se le asigna el dato del sucesor (Izquierda)
            n.setDato(sucesor.getDato());
            //Se borra el dato de la derecha
            n.setDerecha(borrar(n.getDerecha(), sucesor.getDato()));
        }
        return n;
    }
}
