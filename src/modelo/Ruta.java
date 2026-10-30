package modelo;

import java.util.List;

//Clase Ruta
public class Ruta {
    private final List<Paso> pasos;
    private final int minutosTotales;
    private final int transbordos;


    public Ruta(List<Paso> pasos) {
        this.pasos = pasos;

        //Calcula las cosas con los pasos que le hemos pasado
        int total = 0;
        int subidas = 0;
        for (Paso p : pasos) {
            total += p.minutos();
            if (p.subida()) subidas++;
        }
        this.minutosTotales = total;
        //Ve los transbordos (Le quita 1 porque el primero no cuenta como transbordo)
        this.transbordos = Math.max(0, subidas - 1);
    }

    public List<Paso> getPasos() { return pasos; }
    public int getMinutosTotales() { return minutosTotales; }
    public int getTransbordos() { return transbordos; }
    public boolean estaVacia() { return pasos.isEmpty(); }

    //Imprime los pasos dados
    public void imprimir() {
        for (Paso p : pasos) {
            Arista a = p.tramo();
            String linea = a.modo().esTransporte() ? " (" + a.linea() + ")" : "";
            System.out.printf("  %s -> %s  %s%s  [%d min%s]%n",
                    p.desde(), a.destino(), a.modo().getTexto(), linea, p.minutos(),
                    p.subida() ? ", incl. espera" : "");
        }
        System.out.println("  TIEMPO TOTAL: " + minutosTotales + " min | TRANSBORDOS: " + transbordos);
    }
}
