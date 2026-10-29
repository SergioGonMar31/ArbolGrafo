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
                break;
            case "3":
                break;
            case "4":
                break;
            default:
                System.err.println("Elige una opción valida");
        }
    }
}


public static String menu() {
    return "===MENU EJERCICIOS=== \n1. Ejercicio 1 \n2. Ejercicio 2 \n3. Ejercicio 3 \n4. Salir \nElige una opción: ";
}