package modelo;

import utiles.Modo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Grafo
public class Grafo {
    //Mapa con las aristas
    private final Map<String, List<Arista>> adyacencia = new LinkedHashMap<>();

    //Si existe un valor con esa llave no la añade
    public void agregarNodo(String nombre) {
        adyacencia.putIfAbsent(nombre, new ArrayList<>());
    }

    //Agrega el nodo con sus aristas
    public void agregarDirigida(String desde, String hasta, int minutos, Modo modo, String linea) {
        agregarNodo(desde);
        agregarNodo(hasta);
        adyacencia.get(desde).add(new Arista(hasta, minutos, modo, linea));
    }

    //Agrega un tramo con dos sentidos
    public void agregarDoble(String a, String b, int minutos, Modo modo, String linea) {
        agregarDirigida(a, b, minutos, modo, linea);
        agregarDirigida(b, a, minutos, modo, linea);
    }

    //Comprueba que existe el nodo pasado
    public boolean existe(String nodo) {
        return adyacencia.containsKey(nodo);
    }

    public List<String> getNodos() {
        return new ArrayList<>(adyacencia.keySet());
    }

    public List<Arista> getAristas(String nodo) {
        return adyacencia.getOrDefault(nodo, List.of());
    }
}
