import Ejercicios.Ejercicio1;
import Ejercicios.Ejercicio2;
import Ejercicios.Ejercicio3;
import utiles.Depurar;

void main() {
    boolean salir = false;
    while (!salir) {
        String opcion = Depurar.texto(menu(), new Scanner(System.in));
        switch (opcion) {
            case "1":
                Ejercicio1 ej1 = new Ejercicio1();
                ej1.comenzar();
                break;
            case "2":
                Ejercicio2 ej2 = new Ejercicio2();
                ej2.comenzar();
                break;
            case "3":
                Ejercicio3 ej3 = new Ejercicio3();
                ej3.comenzar();
                break;
            case "4":
                salir=true;
                break;
            default:
                System.err.println("Elige una opción valida");
        }
    }
    System.err.println("Has salido del programa.");
}


public static String menu() {
    return "===MENU EJERCICIOS=== \n1. Ejercicio 1 \n2. Ejercicio 2 \n3. Ejercicio 3 \n4. Salir \nElige una opción: ";
}