package Ejercicios;

import utiles.Depurar;
import modelo.*;
import java.util.List;
import java.util.Scanner;

public class Ejercicio2 {
    static Scanner sc = new Scanner(System.in);

    static void mostrar(List<Contacto> lista) {
        if (lista.isEmpty()) System.out.println("  (sin resultados)");
        for (Contacto c : lista) System.out.println("  " + c);
    }

    public void comenzar() {
        ListinTelefonico listin = new ListinTelefonico();
        listin.alta(new Contacto("Ana", "García", "600111222", "ana@mail.com"));
        listin.alta(new Contacto("Luis", "Garrido", "600333444", ""));
        listin.alta(new Contacto("Marta", "Benítez", "600555666", "marta@mail.com"));
        listin.alta(new Contacto("Pedro", "Cabrera", "600777888", ""));
        listin.alta(new Contacto("Sofía", "Zamora", "600999000", "sofia@mail.com"));

        while (true) {
            System.out.println("\n=== LISTÍN TELEFÓNICO ===");
            System.out.println("1. Alta   2. Baja   3. Modificar   4. Buscar por teléfono");
            System.out.println("5. Buscar por apellidos+nombre   6. Buscar por prefijo de apellidos");
            System.out.println("7. Buscar por rango alfabético   8. Listar todo   0. Salir");
            String op = Depurar.texto("Opción: ", new Scanner(System.in));

            switch (op) {
                case "1" -> {
                    String nombre = Depurar.texto("Nombre: ",new Scanner(System.in));
                    String apellidos = Depurar.texto("Apellidos: ", new Scanner(System.in));
                    String tel = Depurar.texto("Telefono: ", new Scanner(System.in));
                    String email = Depurar.textoVacio("Email: ", new Scanner(System.in));
                    System.out.println(listin.alta(new Contacto(nombre, apellidos, tel, email)) ? "Alta realizada." : "Error: ese teléfono ya existe.");
                }
                case "2" -> System.out.println(listin.baja(Depurar.texto("Teléfono a borrar: ",new Scanner(System.in))) ? "modelo.Contacto eliminado." : "No existe ese teléfono.");
                case "3" -> {
                    String tel = Depurar.texto("Teléfono del contacto a modificar: ",new Scanner(System.in));
                    Contacto c = listin.buscarTelefono(tel);
                    if (c == null) {
                        System.out.println("No existe ese teléfono.");
                        break;
                    }
                    System.out.println("Actual: " + c + "  (Enter = mantener)");
                    String nombre = Depurar.textoVacio("Nuevo nombre: ",new Scanner(System.in));
                    if (nombre.isEmpty()) nombre = c.getNombre();
                    String apellidos = Depurar.textoVacio("Nuevos apellidos: ",new Scanner(System.in));
                    if (apellidos.isEmpty()) apellidos = c.getApellidos();
                    String nuevoTel = Depurar.textoVacio("Nuevo teléfono: ",new Scanner(System.in));
                    if (nuevoTel.isEmpty()) nuevoTel = c.getTelefono();
                    String email = Depurar.textoVacio("Nuevo email: ",new Scanner(System.in));
                    if (email.isEmpty()) email = c.getEmail();
                    System.out.println(listin.modificar(tel, nombre, apellidos, nuevoTel, email)
                            ? "Modificado." : "Error: el nuevo teléfono ya existe.");
                }
                case "4" -> {
                    Contacto c = listin.buscarTelefono(Depurar.texto("Teléfono: ",new Scanner(System.in)));
                    System.out.println(c == null ? "  No encontrado." : "  " + c);
                }
                case "5" -> mostrar(listin.buscarNombre(Depurar.texto("Apellidos: ",new Scanner(System.in)), Depurar.texto("Nombre: ",new Scanner(System.in))));
                case "6" -> mostrar(listin.buscarPrefijo(Depurar.texto("Prefijo de apellidos: ",new Scanner(System.in))));
                case "7" -> mostrar(listin.buscarRango(Depurar.texto("Desde: ",new Scanner(System.in)), Depurar.texto("Hasta: ",new Scanner(System.in))));
                case "8" -> mostrar(listin.listar());
                case "0" -> {
                    System.out.println("Adiós.");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}