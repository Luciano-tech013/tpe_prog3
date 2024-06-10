import entidades.Procesador;
import entidades.Tarea;
import java.util.List;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {
        Servicios servicios = new Servicios("./src/datasets/Procesadores.csv", "./src/datasets/Tareas.csv");

        //Servicio 1
        /*System.out.println("Servicio 1: ");
        Tarea tarea = servicios.servicio1("T2");
        System.out.println(tarea);

        System.out.println("\n---------------------------------");

        //Servicio 2
        System.out.println("Servicio 2: ");
        List<Tarea> resultado1 = servicios.servicio2(false);
        imprimirTareas(resultado1);

        System.out.println("\n---------------------------------");

        //Servicio 3
        System.out.println("Servicio 3: ");
        List<Tarea> resultado2 = servicios.servicio3(80, 100);
        imprimirTareas(resultado2);

        System.out.println("\n---------------------------------");

        //Servicio 4
        System.out.println("Servicio 4: ");
        imprimirProcesadores(servicios.servicio4());
        System.out.println("Tiempo Maximo de Ejecucion: " + servicios.getTiempoMaxDeEjecucionDeAsignacion());
        System.out.println("Metrica para analizar costo de solucion: " + servicios.getCantEstadosGeneradosServicio4());

        System.out.println("\n---------------------------------");*/

        //Servicio 5
        System.out.println("Servicio 5: ");
        List<Procesador> resultado4 = servicios.servicio5();
        if(resultado4 == null) {
            System.out.println("No hay solucion posible");
        } else {
            imprimirProcesadores(resultado4);
            System.out.println("Tiempo Maximo de Ejecucion: " + servicios.getTiempoMaxDeEjecucionDeAsignacion());
            System.out.println("Metrica para analizar costo de solucion: " + servicios.getCantCandidatosConsideradosServicio5());
        }
    }

    public static void imprimirTareas(List<Tarea> lista) {
        for(Tarea tarea : lista) {
            System.out.println(tarea);
        }
    }

    public static void imprimirProcesadores(List<Procesador> lista) {
       Iterator<Tarea> tareas;
       for (Procesador p : lista) {
           System.out.println("Procesador " + p.getId());
           tareas = p.getTareas();
           while (tareas.hasNext()) {
               System.out.println(tareas.next());
           }
           System.out.println("\n---------------------------------");
       }
    }
}


