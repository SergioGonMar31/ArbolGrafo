import utiles.Depurar;

import java.util.*;

public class Ejercicio1 {

   //Funcion para comenzar el bucle del ejercicio
    public void comenzar() {
        ArbolLetras arbol = new ArbolLetras("ABCDEFGHIJKLMNO");


        System.out.println("=== ÁRBOL DE LETRAS ===");
        arbol.mostrarPorNiveles();

        boolean salir = false;
        do {
            String entrada = Depurar.palabra("\nIntroduce una letra (o 'salir'): ", new Scanner(System.in));
            if (entrada.equalsIgnoreCase("salir")) {
                salir = true;
            } else if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.err.println("Error: introduce UNA sola letra.");
            } else {
                char objetivo = Character.toUpperCase(entrada.charAt(0));
                List<Character> letras = new ArrayList<>();
                List<Character> dirs = new ArrayList<>();

                if (arbol.buscarRuta(objetivo, letras, dirs)) {
                    System.out.println("Letras visitadas: " + letras);
                    System.out.println("Direcciones:      " + (dirs.isEmpty() ? "(es la raíz)" : dirs));
                } else {
                    System.out.println("La letra '" + objetivo + "' NO existe en el árbol.");
                }
            }
        } while (!salir);
        System.out.println("Fin.");
    }

}
