package entidades;

public class Tarea {
    private String id;
    private String nombre;
    private int tiempoDeEjecucion;
    private int tiempo;
    private boolean critica;
    private int prioridad;

    public Tarea(String id, String nombre, int tiempo, boolean critica, int prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempoDeEjecucion = tiempo;
        this.tiempo = tiempo;
        this.critica = critica;
        this.prioridad = prioridad;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public boolean isCritica() {
        return critica;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoDeEjecucion() {
        return tiempoDeEjecucion;
    }

    public Tarea getCopia() {
        Tarea otraCopia = new Tarea(this.getId(), this.getNombre(), this.getTiempoDeEjecucion(), this.isCritica(), this.getPrioridad());
        return otraCopia;
    }

    @Override
    public String toString() {
        return this.getId() + " - " + this.getNombre() + " - " + this.getPrioridad() + " - " + this.isCritica() + " - " + this.getTiempoDeEjecucion();
    }

    @Override
    public boolean equals(Object o) {
        Tarea otraTarea = (Tarea) o;
        return this.id.equals(otraTarea.getId());
    }
}
