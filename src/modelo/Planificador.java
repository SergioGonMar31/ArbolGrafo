package modelo;

import utiles.Modo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Calcula la ruta óptima con Dijkstra.
 *
 * Truco: Dijkstra no se hace sobre "lugares" sino sobre ESTADOS = (lugar, línea que estoy usando).
 * Llegar a Centro en el metro M1 no es lo mismo que llegar andando, porque desde ahí
 * seguir en M1 no cuesta transbordo y cambiar a otra línea sí. Así se pueden contar y penalizar.
 */
public class Planificador {

    private static final int PENALIZACION_TRANSBORDO = 20;   // minutos "virtuales" si se pide minimizar

    private record Estado(String nodo, String linea) { }
    private record Elemento(Estado estado, int coste) { }

    private final Grafo grafo;

    public Planificador(Grafo grafo) {
        this.grafo = grafo;
    }

    //Devuelve la ruta optima o null
    public Ruta calcular(String origen, String destino, Opciones opciones) {
        Map<Estado, Integer> distancia = new HashMap<>();
        Map<Estado, Estado> anterior = new HashMap<>();
        Map<Estado, Paso> pasoAnterior = new HashMap<>();
        PriorityQueue<Elemento> cola = new PriorityQueue<>(Comparator.comparingInt(Elemento::coste));

        Estado inicio = new Estado(origen, "");
        distancia.put(inicio, 0);
        cola.add(new Elemento(inicio, 0));

        //Mientras la cola este llena sigue
        while (!cola.isEmpty()) {
            Elemento actual = cola.poll();
            Estado estado = actual.estado();

            //Si el coste es mayor al valor maximo de la distancia se salta esta vuelta
            if (actual.coste() > distancia.getOrDefault(estado, Integer.MAX_VALUE)) continue;

            //Si el nodo esta ya en el destino se devuelve la ruta usada
            if (estado.nodo().equals(destino)) {
                return new Ruta(reconstruir(estado, anterior, pasoAnterior));
            }

            //Para cada arista
            for (Arista tramo : grafo.getAristas(estado.nodo())) {
                if (opciones.evitarBici() && tramo.modo() == Modo.BICI) continue; //Si se evita la bici y la arista es de bici entonces se salta todo

                //Se sube si es transporte
                boolean subida = tramo.modo().esTransporte() && !tramo.linea().equals(estado.linea());

                //Calcula los minutos y el coste de la arista
                int minutos = minutosTramo(tramo, opciones) + (subida ? espera(tramo.modo(), opciones) : 0);
                int coste = minutos + (subida && opciones.minimizarTransbordos() ? PENALIZACION_TRANSBORDO : 0);

                Estado siguiente = new Estado(tramo.destino(), tramo.linea());
                int nuevaDistancia = actual.coste() + coste;

                //Si la distancia nueva es menor a la distancia actual, cambia la distancia por la nueva
                if (nuevaDistancia < distancia.getOrDefault(siguiente, Integer.MAX_VALUE)) {
                    distancia.put(siguiente, nuevaDistancia);
                    anterior.put(siguiente, estado);
                    pasoAnterior.put(siguiente, new Paso(estado.nodo(), tramo, minutos, subida));
                    cola.add(new Elemento(siguiente, nuevaDistancia));
                }
            }
        }
        return null;   // no hay ruta
    }

    //Sigue los pasos desde el inicio hasta el final y los devuelve
    private java.util.List<Paso> reconstruir(Estado fin, Map<Estado, Estado> anterior, Map<Estado, Paso> pasoAnterior) {
        LinkedList<Paso> pasos = new LinkedList<>();
        for (Estado e = fin; anterior.containsKey(e); e = anterior.get(e)) {
            pasos.addFirst(pasoAnterior.get(e));
        }
        return pasos;
    }

    //Tarda más en hora punta que cuando no lo es
    private int minutosTramo(Arista tramo, Opciones opciones) {
        if (!opciones.horaPunta()) return tramo.minutos();
        return switch (tramo.modo()) {
            case BUS -> (int) Math.ceil(tramo.minutos() * 1.5);
            case METRO -> (int) Math.ceil(tramo.minutos() * 1.25);
            default -> tramo.minutos();
        };
    }

   //En la espera del transporte, si es hora punta tarda más si no lo es tarda menos
    private int espera(Modo modo, Opciones opciones) {
        return switch (modo) {
            case BUS -> opciones.horaPunta() ? 7 : 4;
            case METRO -> opciones.horaPunta() ? 5 : 3;
            default -> 0;
        };
    }
}
