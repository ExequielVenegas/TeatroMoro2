package com.douc.teatromoro;

import java.util.ArrayList;
import java.util.Scanner;

public class TeatroMoro {

    static int totalEntradasVendidas = 0;
    static double totalIngresos = 0;
    static int contadorEntradas = 1;
    static final int capacidadSala = 100;
    static int entradasDisponibles = capacidadSala;
    static final String nombreTeatro = "Teatro Moro";
    static final double precioBase = 10000.0;

    static ArrayList<Entrada> entradasVendidas = new ArrayList<>();
    static ArrayList<Entrada> reservasPendientes = new ArrayList<>();

    static class Entrada {
        int numero;
        String ubicacion;
        String tipoCliente;
        double precioFinal;
        boolean esReserva;

        Entrada(int numero, String ubicacion, String tipoCliente, double precioFinal, boolean esReserva) {
            this.numero = numero;
            this.ubicacion = ubicacion;
            this.tipoCliente = tipoCliente;
            this.precioFinal = precioFinal;
            this.esReserva = esReserva;
        }

        @Override
        public String toString() {
            return "Número: " + numero + ", Ubicación: " + ubicacion +
                   ", Tipo Cliente: " + tipoCliente + ", Precio Final: $" + precioFinal +
                   (esReserva ? " [RESERVA]" : " [VENDIDA]");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> reservarEntrada(scanner);
                case 2 -> venderEntrada(scanner);
                case 3 -> modificarVenta(scanner);
                case 4 -> imprimirBoleta(scanner);
                case 5 -> mostrarTodasLasEntradas();
                case 6 -> salirDelSistema();
                default -> System.out.println("Opción inválida. Intenta nuevamente.");
            }

        } while (opcion != 6);
    }

    static void mostrarMenu() {
        System.out.println("\n=== Menú de Opciones - " + nombreTeatro + " ===");
        System.out.println("1. Reservar entradas");
        System.out.println("2. Comprar entradas");
        System.out.println("3. Modificar venta");
        System.out.println("4. Imprimir boleta");
        System.out.println("5. Mostrar entradas");
        System.out.println("6. Salir");
    }

    static void reservarEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay más entradas disponibles para reservar.");
            return;
        }

        System.out.println("Iniciando reserva de entrada...");
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
        Entrada entrada = new Entrada(contadorEntradas++, ubicacion, tipoCliente, precioFinal, true);
        reservasPendientes.add(entrada);
        entradasDisponibles--;

        System.out.println("Reserva realizada: " + entrada);
    }

    static void venderEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        System.out.println("Iniciando venta directa...");
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
        Entrada entrada = new Entrada(contadorEntradas++, ubicacion, tipoCliente, precioFinal, false);
        entradasVendidas.add(entrada);
        totalEntradasVendidas++;
        totalIngresos += precioFinal;
        entradasDisponibles--;

        System.out.println("Entrada vendida: " + entrada);
    }

    static void modificarVenta(Scanner scanner) {
        System.out.print("Número de entrada a modificar: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        for (Entrada e : entradasVendidas) {
            if (e.numero == numero) {
                System.out.println("Modificando entrada: " + e);
                System.out.print("Nueva ubicación: ");
                e.ubicacion = scanner.nextLine();
                System.out.print("Nuevo tipo de cliente: ");
                e.tipoCliente = scanner.nextLine();

                double descuento = switch (e.tipoCliente.toLowerCase()) {
                    case "estudiante" -> 0.10;
                    case "terceraedad" -> 0.15;
                    default -> 0;
                };

                totalIngresos -= e.precioFinal;
                e.precioFinal = precioBase - (precioBase * descuento);
                totalIngresos += e.precioFinal;

                System.out.println("Entrada modificada: " + e);
                return;
            }
        }
        System.out.println("No se encontró la entrada.");
    }

    static void imprimirBoleta(Scanner scanner) {
        System.out.print("Número de entrada para imprimir boleta: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        for (Entrada e : entradasVendidas) {
            if (e.numero == numero) {
                System.out.println("\n--- BOLETA ---");
                System.out.println("Teatro: " + nombreTeatro);
                System.out.println("Entrada N°: " + e.numero);
                System.out.println("Ubicación: " + e.ubicacion);
                System.out.println("Tipo Cliente: " + e.tipoCliente);
                System.out.println("Precio: $" + e.precioFinal);
                System.out.println("Gracias por su compra.\n");
                return;
            }
        }
        System.out.println("No se encontró la entrada.");
    }

    static void mostrarTodasLasEntradas() {
        System.out.println("\n--- Entradas Vendidas ---");
        if (entradasVendidas.isEmpty()) {
            System.out.println("No hay entradas vendidas.");
        } else {
            for (Entrada e : entradasVendidas) {
                System.out.println(e);
            }
        }

        System.out.println("\n--- Reservas Pendientes ---");
        if (reservasPendientes.isEmpty()) {
            System.out.println("No hay reservas pendientes.");
        } else {
            for (Entrada e : reservasPendientes) {
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
