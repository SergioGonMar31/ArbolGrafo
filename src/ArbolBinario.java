import java.util.LinkedList;
import java.util.Queue;

    public class ArbolBinario<T> {
        protected Nodo<T> raiz;

        public ArbolBinario() {
            this.raiz = null;
        }

        public boolean estaVacio() {
            return raiz == null;
        }

        public Nodo<T> getRaiz() {
            return raiz;
        }

        //Funcion que muestra el arbol por niveles
        public void mostrarPorNiveles() {
            //Si la raiz esta vacio entonces el arbol esta vacio
            if (raiz == null) {
                System.out.println("(árbol vacío)");
                return;
            }

            //La cola representa cada nivel del arbol
            Queue<Nodo<T>> cola = new LinkedList<>();
            cola.add(raiz);
            int nivel = 0;
            //Mientra haya arbol seguira mostrandolo
            while (!cola.isEmpty()) {
                int tamanyo = cola.size();
                StringBuilder sb = new StringBuilder("Nivel " + nivel + ": ");
                //Por cada nivel va añadiendo lo que haya
                for (int i = 0; i < tamanyo; i++) {
                    //Devuelve y elimina lo ultimo que habia en la cola
                    Nodo<T> n = cola.poll();
                    sb.append(n.getDato()).append(' ');
                    //Si por la izquierda hay algo, añade el nodo a la cola. Lo mismo con la derecha
                    if (n.getIzquierda() != null) cola.add(n.getIzquierda());
                    if (n.getDerecha() != null) cola.add(n.getDerecha());
                }
                //Muestra ese nivel y aumenta el nivel
                System.out.println(sb);
                nivel++;
            }
        }
    }