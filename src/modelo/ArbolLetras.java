package modelo;

import java.util.List;

public class ArbolLetras extends ArbolBinario<Character> {

    //Construye el árbol colocando las letras por niveles, de izquierda a derecha.
    public ArbolLetras(String letras) {
        super();
        Nodo[] nodos = new Nodo[letras.length()];
        for (int i = 0; i < nodos.length; i++) {
            nodos[i] = new Nodo(letras.charAt(i));
        }
        for (int i = 0; i < nodos.length; i++) {
            int l = 2 * i + 1, r = 2 * i + 2;      // posición de los hijos en un árbol completo
            if (l < nodos.length) nodos[i].setIzquierda(nodos[l]);
            if (r < nodos.length) nodos[i].setDerecha(nodos[r]);
        }
        raiz = nodos[0];
    }

    public boolean buscarRuta(char objetivo, List<Character> letras, List<Character> dirs) {
        return dfs(raiz, objetivo, letras, dirs);
    }

    //Funcion que busca la letra objetivo
    private boolean dfs(Nodo<Character> n, char objetivo, List<Character> letras, List<Character> dirs) {
        //Si no han introducido letra devuelve false
        if (n == null) return false;

        //Añade la letra a la lista y comprueba
        letras.add(n.getDato());
        //Si es la letra objetivo devuelve true y termina
        if (n.getDato() == objetivo) return true;

        //Si continua, empieza por la izquierda. Añade L (left) a la lista de direcciones y empieza a buscar
        dirs.add('L');
        //Vuelve a llamar a la funcion de busqueda dandole como raiz el nodo en el que estamos actualmente. Si se encuentra por este lado devuelve True.
        if (dfs(n.getIzquierda(), objetivo, letras, dirs)) return true;
        //Si no estaba por aqui quita la ultima direccion guardad (L) y continua por la derecha
        dirs.remove(dirs.size() - 1);

        //Lo mismo que antes pero ahora por la derecha
        dirs.add('R');
        if (dfs(n.getDerecha(), objetivo, letras, dirs)) return true;
        dirs.remove(dirs.size() - 1);

        //Si tampoco se encuentra por la derecha quita la direccion y devuelve false (No esta en el arbol)
        letras.remove(letras.size() - 1);
        return false;
    }
}