package Ejercicios;

import java.util.List;
import java.util.Scanner;
import modelo.*;
import utiles.Depurar;

public class Ejercicio3 {

    private final Scanner sc = new Scanner(System.in);

    public void comenzar() {
        Grafo ciudad = CiudadDemo.crear();
        Planificador planificador = new Planificador(ciudad);
        List<String> lugares = ciudad.getNodos();

        System.out.println("=== PLANIFICADOR DE RUTAS ===");
        do {
            for (int i = 0; i < lugares.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + lugares.get(i));
            }
            String origen = elegirLugar(lugares, "Origen (número): ");
            String destino = elegirLugar(lugares, "Destino (número): ");

            Opciones opciones = new Opciones(
                    preguntarSiNo("¿Evitar bicicleta?"),
                    preguntarSiNo("¿Minimizar transbordos?"),
                    preguntarSiNo("¿Hora punta?"));

            Ruta ruta = planificador.calcular(origen, destino, opciones);

            System.out.println("\nRuta " + origen + " -> " + destino + ":");
            if (ruta == null) {
                System.out.println("  No existe ruta con esas restricciones.");
            } else if (ruta.estaVacia()) {
                System.out.println("  Ya estás en el destino.");
            } else {
                ruta.imprimir();
            }
            System.out.println();
        } while (preguntarSiNo("¿Otra consulta?"));
        System.out.println("Fin.");
    }

    private String elegirLugar(List<String> lugares, String mensaje) {
        while (true) {
            int indice = Depurar.entero(mensaje,new Scanner(System.in));
                if (indice >= 1 && indice <= lugares.size()) return lugares.get(indice - 1);
            System.out.println("  Número no válido (1-" + lugares.size() + ").");
        }
    }

    private boolean preguntarSiNo(String mensaje) {
        while (true) {
            String r = Depurar.palabra(mensaje + " (s/n): ",new Scanner (System.in));
            if (r.equalsIgnoreCase("s")) return true;
            if (r.equalsIgnoreCase("n")) return false;
            System.out.println("  Responde s o n.");
        }
    }

    public static void main(String[] args) {
        new Ejercicio3().comenzar();
    }
}