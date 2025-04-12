package com.douc.teatromoro;
import java.util.ArrayList;
import java.util.Scanner;

public class TeatroMoro {

    static int totalEntradasVendidas = 0;
    static double totalIngresos = 0;
    static int contadorEntradas = 1;
    static ArrayList<Entrada> entradasVendidas = new ArrayList<>();

    static final String nombreTeatro = "Teatro Moro";
    static final int capacidadSala = 100;
    static int entradasDisponibles = capacidadSala;
    static final double precioBase = 10000.0;

    static class Entrada {
        int numero;
        String ubicacion;
        String tipoCliente;
        double precioFinal;

        Entrada(int numero, String ubicacion, String tipoCliente, double precioFinal) {
            this.numero = numero;
            this.ubicacion = ubicacion;
            this.tipoCliente = tipoCliente;
            this.precioFinal = precioFinal;
        }

        @Override
        public String toString() {
            return "Número: " + numero + ", Ubicación: " + ubicacion +
                   ", Tipo Cliente: " + tipoCliente + ", Precio Final: $" + precioFinal;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> venderEntrada(scanner);
                case 2 -> mostrarPromociones();
                case 3 -> buscarEntrada(scanner);
                case 4 -> eliminarEntrada(scanner);
                case 5 -> mostrarTodasLasEntradas();
                case 6 -> salirDelSistema();
                default -> System.out.println("Opción inválida. Intenta nuevamente.");
            }

        } while (opcion != 6);
    }

    static void mostrarMenu() {
        System.out.println("\n=== Menú de Opciones - " + nombreTeatro + " ===");
        System.out.println("1. Venta de entradas");
        System.out.println("2. Ver promociones");
        System.out.println("3. Buscar entrada");
        System.out.println("4. Eliminar entrada");
        System.out.println("5. Mostrar entradas");
        System.out.println("6. Salir");
    }

    static void venderEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        System.out.print("Ubicación (VIP/Platea/General): ");
        String ubicacion = scanner.nextLine();
        System.out.print("Tipo de cliente (Normal/Estudiante/TerceraEdad): ");
        String tipoCliente = scanner.nextLine();

        double descuento = switch (tipoCliente.toLowerCase()) {
            case "estudiante" -> 0.10;
            case "terceraedad" -> 0.15;
            default -> 0;
        };

        double precioFinal = precioBase - (precioBase * descuento);
        Entrada entrada = new Entrada(contadorEntradas++, ubicacion, tipoCliente, precioFinal);
        entradasVendidas.add(entrada);
        totalEntradasVendidas++;
        totalIngresos += precioFinal;
        entradasDisponibles--;

        System.out.println("Entrada vendida: " + entrada);
    }

    static void mostrarPromociones() {
        System.out.println("\n--- Promociones Disponibles ---");
        System.out.println("- 10% de descuento para estudiantes");
        System.out.println("- 15% de descuento para personas de la tercera edad");
        System.out.println("- Compra 5 entradas o más y obtén una entrada gratis (próximamente)");
    }

    static void buscarEntrada(Scanner scanner) {
        System.out.print("Buscar por (numero/ubicacion/tipo): ");
        String criterio = scanner.nextLine();
        System.out.print("Valor de búsqueda: ");
        String valor = scanner.nextLine();

        boolean encontrada = false;
        for (Entrada e : entradasVendidas) {
            if ((criterio.equalsIgnoreCase("numero") && Integer.toString(e.numero).equals(valor)) ||
                (criterio.equalsIgnoreCase("ubicacion") && e.ubicacion.equalsIgnoreCase(valor)) ||
                (criterio.equalsIgnoreCase("tipo") && e.tipoCliente.equalsIgnoreCase(valor))) {
                System.out.println("Entrada encontrada: " + e);
                encontrada = true;
            }
        }
        if (!encontrada) {
            System.out.println("No se encontró ninguna entrada con ese criterio.");
        }
    }

    static void eliminarEntrada(Scanner scanner) {
        System.out.print("Número de entrada a eliminar: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        for (int i = 0; i < entradasVendidas.size(); i++) {
            if (entradasVendidas.get(i).numero == numero) {
                totalIngresos -= entradasVendidas.get(i).precioFinal;
                entradasVendidas.remove(i);
                totalEntradasVendidas--;
                entradasDisponibles++;
                System.out.println("Entrada eliminada correctamente.");
                return;
            }
        }
        System.out.println("No se encontró la entrada con ese número.");
    }
    
    static void mostrarTodasLasEntradas() {
    System.out.println("\n--- Listado de todas las entradas vendidas ---");
    if (entradasVendidas.isEmpty()) {
        System.out.println("No se han vendido entradas aún.");
    } else {
        for (Entrada e : entradasVendidas) {
            System.out.println(e);
        }
    }
}

    static void salirDelSistema() {
        System.out.println("Saliendo del sistema...");
        System.out.println("Entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Ingresos totales: $" + totalIngresos);
    }
}