package entidades;
import java.util.Iterator;
import java.util.LinkedList;

public class Procesador {
    private final int TAREAS_CRITICAS_ACEPTADAS;
    private String id;
    private String codigo;
    private boolean refrigerado;
    private int anio;
    private int tiempoDeEjecucion;
    private int tiempoMaxDeEjecucion; //para procesadores no refrigerados
    private int cantTareasCriticas;
    private LinkedList<Tarea> tareasAsignadas;

    public Procesador(String id, String codigo, boolean refrigerado, int anio) {
        this.TAREAS_CRITICAS_ACEPTADAS = 2;
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
        this.tiempoDeEjecucion = 0;
        this.tiempoMaxDeEjecucion = 0;
        this.cantTareasCriticas = 0;
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

    public void setTiempoMaxDeEjecucion(int tiempoMax) {
        this.tiempoMaxDeEjecucion = tiempoMax;
    }

    public int getCantTareasCriticas() {
        return cantTareasCriticas;
    }

    public Iterator<Tarea> getTareas() {
        return this.tareasAsignadas.iterator();
    }

    public Procesador getCopia() {
        Procesador copia = new Procesador(this.id, this.codigo, this.refrigerado, this.anio);
        for(Tarea t : this.tareasAsignadas) {
            copia.asignarTarea(t);
        }

        return copia;
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
        if(tarea.isCritica() && (this.cantTareasCriticas + 1 > this.TAREAS_CRITICAS_ACEPTADAS))
            return false;

        if(!this.isRefrigerado() && (this.getTiempoDeEjecucion() + tarea.getTiempoDeEjecucion()) > this.tiempoMaxDeEjecucion)
            return false;

        return true;
    }

    public void borrarTareasAsignadas() {
        this.tareasAsignadas.clear();
    }
}
