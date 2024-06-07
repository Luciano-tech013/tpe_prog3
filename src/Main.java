import entidades.Procesador;
import entidades.Tarea;
import java.util.List;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {
        Servicios servicios = new Servicios("./src/datasets/Procesadores.csv", "./src/datasets/Tareas.csv");

        //Servicio 1
        System.out.println("Servicio 1: ");

        Tarea tarea = servicios.servicio1("T2");
        System.out.println(tarea);

        //Servicio 2

        List<Tarea> resultado1 = servicios.servicio2(false);
        imprimirTareas(resultado1);

        //Servicio 3
        List<Tarea> resultado2 = servicios.servicio3(50, 100);
        imprimirTareas(resultado2);

        //Servicio 4
        /*List<Procesador> resultado3 = servicios.servicio4();
        imprimirProcesadores(resultado3);
        System.out.println("Tiempo Maximo de Ejecucion: " + servicios.getTiempoMaxDeEjecucionDeAsignacion());
        System.out.println("Metrica para analizar costo de solucion: " + servicios.getCantEstadosGeneradosServicio4());*/

        //Servicio 5
        List<Procesador> resultado4 = servicios.servicio5();
        imprimirProcesadores(resultado4);
        System.out.println("Tiempo Maximo de Ejecucion: " + servicios.getTiempoMaxDeEjecucionDeAsignacion());
        System.out.println("Metrica para analizar costo de solucion: ");
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


