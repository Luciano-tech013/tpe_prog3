package entidades;
import java.util.Iterator;
import java.util.LinkedList;

public class Procesador {
    private final int TAREAS_CRITICAS_ACEPTADAS;
    private final int TIEMPO_MAXIMO_DE_EJECUCION;
    private String id;
    private String codigo;
    private boolean refrigerado;
    private int anio;
    private int tiempoDeEjecucion;
    private int cantTareasCriticas;
    private LinkedList<Tarea> tareasAsignadas;

    public Procesador(String id, String codigo, boolean refrigerado, int anio, int tiempoMax) {
        this.id = id;
        this.codigo = codigo;
        this.anio = anio;
        this.tiempoDeEjecucion = 0;
        this.cantTareasCriticas = 0;
        this.TAREAS_CRITICAS_ACEPTADAS = 2;
        this.TIEMPO_MAXIMO_DE_EJECUCION = tiempoMax;
        this.refrigerado = refrigerado;
        this.tareasAsignadas = new LinkedList<Tarea>();
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isRefrigerado() {
        return refrigerado;
    }

    public int getAnio() {
        return anio;
    }

    public int getTiempoDeEjecucion() {
        return tiempoDeEjecucion;
    }

    public int getCantTareasCriticas() {
        return cantTareasCriticas;
    }

    public Iterator<Tarea> getTareas() {
        return this.tareasAsignadas.iterator();
    }
    public void asignarTarea(Tarea tarea) {
        if(tarea.isCritica())
            this.cantTareasCriticas++;

        this.tiempoDeEjecucion += tarea.getTiempoDeEjecucion();
        this.tareasAsignadas.add(tarea);
    }

    public void desasignarTarea(Tarea tarea) {
        if(tarea.isCritica())
            this.cantTareasCriticas--;

        this.tiempoDeEjecucion -= tarea.getTiempoDeEjecucion();
        this.tareasAsignadas.remove(tarea);
    }

    public boolean aceptaTarea(Tarea tarea) {
        String s = "Soy " + this.getId();
        if(tarea.isCritica() && (this.cantTareasCriticas + 1 > this.TAREAS_CRITICAS_ACEPTADAS))
            return false;

        if(!this.isRefrigerado() && (this.getTiempoDeEjecucion() + tarea.getTiempoDeEjecucion()) > TIEMPO_MAXIMO_DE_EJECUCION)
            return false;

        return true;
    }
}
