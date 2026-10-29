package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ejercicio 2: listín telefónico sobre un árbol binario de búsqueda ordenado por
 * (apellidos, nombre). Un HashMap por teléfono permite buscarlo rápido y garantiza que sea único.
 */
public class ListinTelefonico extends ArbolBusqueda<Contacto> {

    // Orden: apellidos, nombre y, para desempatar homónimos, el teléfono (que es único)
    private static final Comparator<Contacto> ORDEN = (a, b) -> {
        int r = a.getApellidos().compareToIgnoreCase(b.getApellidos());
        if (r != 0) return r;
        r = a.getNombre().compareToIgnoreCase(b.getNombre());
        if (r != 0) return r;
        return a.getTelefono().compareTo(b.getTelefono());
    };

    private final Map<String, Contacto> porTelefono = new HashMap<>();

    public ListinTelefonico() {
        super(ORDEN);
    }

    //Funcion para el alta
    public boolean alta(Contacto c) {
        //Si el telefono ya esta registrado no te deja añadirlo
        if (porTelefono.containsKey(c.getTelefono())) return false;
        //Inserta al contacto
        insertar(c);
        //Añade al mapa el telefono
        porTelefono.put(c.getTelefono(), c);
        return true;
    }

    //Funcion para dar de baja
    public boolean baja(String telefono) {
        //Crear el contacto y remueve el telefono
        Contacto c = porTelefono.remove(telefono);
        //Si el contacto no existe devuelve false
        if (c == null) return false;
        //Borra el contacto
        borrar(c);
        return true;
    }

    //Funcio que modifica una entrada
    public boolean modificar(String telefonoActual, String nombre, String apellidos, String nuevoTelefono, String email) {
        //Si el telefono no esta en el mapa devuelve false
        if (!porTelefono.containsKey(telefonoActual)) return false;
        //Si el nuevo telefono no es igual al anterior y el nuevo ya esta en el mapa entonces devuelve false
        if (!nuevoTelefono.equals(telefonoActual) && porTelefono.containsKey(nuevoTelefono)) return false;
        //Quitamos el telefono actual y damos de alta al nuevo
        baja(telefonoActual);
        alta(new Contacto(nombre, apellidos, nuevoTelefono, email));
        return true;
    }

    //Funcion que busca por telefono. La más facil al ser el telefono unico
    public Contacto buscarTelefono(String telefono) {
        return porTelefono.get(telefono);
    }

    // Buscqueda por apellido y nombre
    public List<Contacto> buscarNombre(String apellidos, String nombre) {
        List<Contacto> res = new ArrayList<>();
        buscarNombre(raiz, apellidos, nombre, res);
        return res;
    }

    //Busca por nombre
    private void buscarNombre(Nodo<Contacto> n, String ap, String no, List<Contacto> res) {
        //Si esta vacio se sale
        if (n == null) return;
        //Se trae el contacto de la raiz
        Contacto c = n.getDato();
        //Comprueba el apellido y compara con el compare
        int r = c.getApellidos().compareToIgnoreCase(ap);
        //Si es 0 entonces la raiz es el nombre buscado o se llaman igual
        if (r == 0) r = c.getNombre().compareToIgnoreCase(no);

        //Si es mayor que 0 entonces hay que buscar por la izquierda
        if (r > 0) {
            buscarNombre(n.getIzquierda(), ap, no, res);
            //Si es menor entonces buscamos por la derecha
        } else if (r < 0) {
            buscarNombre(n.getDerecha(), ap, no, res);
        } else {
            //Si son iguales buscamos por la izquierda y luego por la derecha. Vamos añadiendo al arrayList los nombres
            buscarNombre(n.getIzquierda(), ap, no, res);
            res.add(c);
            buscarNombre(n.getDerecha(), ap, no, res);
        }
    }

    // Buscar por prefijo
    public List<Contacto> buscarPrefijo(String prefijo) {
        List<Contacto> res = new ArrayList<>();
        prefijo(raiz, prefijo, res);
        return res;
    }

    //Busqueda por prefijo
    private void prefijo(Nodo<Contacto> n, String p, List<Contacto> res) {
        if (n == null) return;
        //Cogemos el apellido
        String a = n.getDato().getApellidos();

        //Con el regionMatches vemos si coindcide el prefijo.
        boolean empieza = a.regionMatches(true, 0, p, 0, p.length());

        //Comprueba por la izquierda y la derecha
        if (a.compareToIgnoreCase(p) >= 0) prefijo(n.getIzquierda(), p, res);
        if (empieza) res.add(n.getDato());
        if (a.compareToIgnoreCase(p) < 0 || empieza) prefijo(n.getDerecha(), p, res);
    }

    //Buscar por rango
    public List<Contacto> buscarRango(String desde, String hasta) {
        List<Contacto> res = new ArrayList<>();
        rango(raiz, desde, hasta, res);
        return res;
    }

    //Busqueda por rango
    private void rango(Nodo<Contacto> n, String d, String h, List<Contacto> res) {
        if (n == null) return;
        //coge el apellido
        String a = n.getDato().getApellidos();
        //Comprueba que coincide
        boolean prefijoDeH = a.regionMatches(true, 0, h, 0, h.length());
        boolean menorOIgualH = a.compareToIgnoreCase(h) <= 0 || prefijoDeH;
        boolean mayorOIgualD = a.compareToIgnoreCase(d) >= 0;

        if (mayorOIgualD) rango(n.getIzquierda(), d, h, res);
        if (mayorOIgualD && menorOIgualH) res.add(n.getDato());
        if (menorOIgualH) rango(n.getDerecha(), d, h, res);
    }

    // Todos los contactos ordenados (recorrido inorden).
    public List<Contacto> listar() {
        return inorden();
    }
}