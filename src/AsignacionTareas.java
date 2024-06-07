import entidades.Tarea;
import entidades.Procesador;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AsignacionTareas {
    private int tiempoMax;
    private int cantEstadosGenerados;
    private List<Tarea> tareas;
    private List<Procesador> procesadores;

    public AsignacionTareas(List<Tarea> tareas, List<Procesador> procesadores) {
        this.tiempoMax = 0;
        this.cantEstadosGenerados = 0;
        this.tareas = tareas;
        this.procesadores = procesadores;
    }

    public int getTiempoMax() {
        return tiempoMax;
    }

    public int getCantEstadosGenerados() {
        return cantEstadosGenerados;
    }

    private LinkedList<Tarea> generarCopiaTareas() {
        LinkedList<Tarea> tareasCopia = new LinkedList<Tarea>();
        for(Tarea t : this.tareas) {
            tareasCopia.add(t.getCopia());
        }

        return tareasCopia;
    }

    public List<Procesador> solucionBacktracking() {
        //Verifico posibles excepciones
        if(!this.tareas.isEmpty() && !this.procesadores.isEmpty())
            this.solucionBacktracking(0);

        return this.procesadores;
    }

    private void solucionBacktracking(int indice) {
        //Caso base
        if(indice == this.tareas.size()) {
            //Encontrar el procesador con el max tiempo de ejecuccion
            int tiempoActual = this.procesadores.stream().mapToInt(Procesador::getTiempoDeEjecucion).max().orElse(0);
            //Definicion de solucion: encontrar el minimo tiempo de ejecucion
            if(tiempoActual < this.tiempoMax || this.tiempoMax == 0)
                this.tiempoMax = tiempoActual;
        } else {
            //Recorro hijos
            for(Procesador p : this.procesadores) {
                String l = "Soy" + p.getId();
                //El indice me permite obtener las tareas en la lista
                Tarea t = this.tareas.get(indice);
                //Restricciones
                if(p.aceptaTarea(t)) {
                    //Agrego tarea a posible conjunto solucion
                    p.asignarTarea(t);
                    //Estrategia de Poda
                    if(p.getTiempoDeEjecucion() < this.tiempoMax || this.tiempoMax == 0) {
                        this.cantEstadosGenerados++;
                        solucionBacktracking(indice + 1);
                    }
                    //Elimino tarea de mi posible conjunto de solucion para no perder otras posibles soluciones
                    p.desasignarTarea(t);
                }
            }
        }
    }

    public List<Procesador> solucionGreedy() {
        //valido posibles excepciones
        if(this.tareas.isEmpty() && this.procesadores.isEmpty())
            return null;

        //Defino Conjunto de Candidatos
        LinkedList<Tarea> candidatos = this.generarCopiaTareas();

        Procesador p = null;
        Tarea candidato = null;
        Iterator<Tarea> it_candidatos = candidatos.iterator();
        //Mientras haya candidatos disponibles
        while(it_candidatos.hasNext()) {
            candidato = it_candidatos.next();
            //Determina el mejor candidato del conjunto de candidatos en base al criterio greedy
            p = seleccionar(candidato);
            //Elimino mi candidato del Conjunto de Candidatos
            it_candidatos.remove(); //Elimino en el iterador porque si no me lanza excepcion
            //Si es factible esa tarea
            if(p.aceptaTarea(candidato))
                //La asigno al procesador
                p.asignarTarea(candidato);
        }

        //Guardo mi tiempo maximo de ejecucion obtenido y retorno conjunto solucion
        this.tiempoMax = this.procesadores.stream().mapToInt(Procesador::getTiempoDeEjecucion).max().orElse(0);
        return this.procesadores;
    }

    private Procesador seleccionar(Tarea t) {
        Procesador seleccionado = null;
        int tiempoEjecucion = 0, tiempoMaxProcesador = 0;
        for(Procesador p : this.procesadores) {
            tiempoEjecucion = p.getTiempoDeEjecucion() + t.getTiempoDeEjecucion();
            //Criterio Greedy
            if(tiempoEjecucion < tiempoMaxProcesador || tiempoMaxProcesador == 0) {
                seleccionado = p;
                tiempoMaxProcesador = tiempoEjecucion;
            }
        }

        return seleccionado;
    }
}
