import entidades.Tarea;
import entidades.Procesador;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AsignacionTareas {
    private int tiempoMax;
    private int cantEstadosGenerados;
    private int cantCandidatosConsiderados;
    private List<Tarea> tareas;
    private List<Procesador> procesadores;
    private List<Procesador> mejoresProcesadores;

    public AsignacionTareas(List<Tarea> tareas, List<Procesador> procesadores) {
        this.tiempoMax = 0;
        this.cantEstadosGenerados = 0;
        this.cantCandidatosConsiderados = 0;
        this.tareas = tareas;
        this.procesadores = procesadores;
        this.mejoresProcesadores = new LinkedList<Procesador>();
    }

    public int getTiempoMax() {
        return tiempoMax;
    }

    public int getCantEstadosGenerados() {
        return cantEstadosGenerados;
    }

    public int getCantCandidatosConsiderados() {
        return cantCandidatosConsiderados;
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

        return this.mejoresProcesadores;
    }

    private void solucionBacktracking(int indice) {
        //Caso base
        if(indice == this.tareas.size()) {
            //Encontrar el procesador con el max tiempo de ejecucion
            int tiempoActual = this.procesadores.stream().mapToInt(Procesador::getTiempoDeEjecucion).max().orElse(0);
            //Definicion de solucion: encontrar el minimo tiempo de ejecucion
            if(tiempoActual < this.tiempoMax || this.tiempoMax == 0) {
                this.tiempoMax = tiempoActual;
                //Clono los procesadores para guardar la mejor solucion encontrada
                this.mejoresProcesadores.clear();
                clonarProcesadores();
            }
        } else {
            //El indice me permite obtener la tarea a evaluar
            Tarea t = this.tareas.get(indice);
            //Recorro hijos
            for(Procesador p : this.procesadores) {
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

    private void clonarProcesadores() {
        Procesador nuevoP;
        for(Procesador p : this.procesadores) {
            nuevoP = p.getCopia();
            this.mejoresProcesadores.add(nuevoP);
        }
    }

    public List<Procesador> solucionGreedy() {
        //Reseteo el tiempoMax
        this.tiempoMax = 0;
        //valido posibles excepciones
        if(this.tareas.isEmpty() && this.procesadores.isEmpty())
            return null;

        //Defino Conjunto de Candidatos
        LinkedList<Tarea> candidatos = this.generarCopiaTareas();

        //Declaro auxiliares
        Tarea candidato = null;
        Procesador seleccionado = null;
        LinkedList<Procesador> noConsiderados = new LinkedList<Procesador>();
        boolean stop = false; int i = 0;
        Iterator<Tarea> it_candidatos = candidatos.iterator();

        //Mientras haya candidatos disponibles
        while(it_candidatos.hasNext()) {
            candidato = it_candidatos.next();
            //Determina el mejor candidato del conjunto de candidatos en base al criterio greedy y
            //Si es factible esa tarea la asigno al procesador correspondiente, si no elijo otro procesador
            while(i < this.procesadores.size() && !stop) {
                seleccionado = seleccionar(candidato, noConsiderados);
                if(seleccionado.aceptaTarea(candidato)) {
                    seleccionado.asignarTarea(candidato);
                    stop = true;
                } else {
                    noConsiderados.add(seleccionado);
                }
                i++;
            }

            if(i == this.procesadores.size()-1) {
                eliminarTareas();
                return null;
            }

            noConsiderados.clear();
            stop = false; i = 0;

            //Elimino mi candidato del Conjunto de Candidatos
            it_candidatos.remove(); //Elimino en el iterador porque si no me lanza excepcion
        }

        //Guardo mi tiempo maximo de ejecucion obtenido y retorno conjunto solucion
        this.tiempoMax = this.procesadores.stream().mapToInt(Procesador::getTiempoDeEjecucion).max().orElse(0);
        return this.procesadores;
    }

    private Procesador seleccionar(Tarea t, LinkedList<Procesador> noConsiderados) {
        Procesador seleccionado = null;
        int mejorTiempoDeEjecucion = 0, tiempoMaxProcesador = 0;
        for(Procesador p : this.procesadores) {
            this.cantCandidatosConsiderados++;
            mejorTiempoDeEjecucion = p.getTiempoDeEjecucion() + t.getTiempoDeEjecucion();
            //Criterio Greedy
            if((mejorTiempoDeEjecucion  < tiempoMaxProcesador || tiempoMaxProcesador == 0) && esDiferente(p, noConsiderados)) {
                seleccionado = p;
                tiempoMaxProcesador =  mejorTiempoDeEjecucion;
            }
        }

        return seleccionado;
    }

    //Funcion que retorna si el procesador que se esta evaluando sea diferente a uno que ya se considero (y no acepto X tarea)
    private boolean esDiferente(Procesador p, LinkedList<Procesador> procesadores) {
        if(procesadores.isEmpty())
            return true;

        int i = 0;
        while (i < procesadores.size() && !procesadores.get(i).getId().equals(p.getId())) {
            i++;
        }

        return (i == procesadores.size());
    }

    //Si Greedy no encontro una solucion, los procesadores deben quedar sin tareas asignadas
    private void eliminarTareas() {
        for(Procesador p : this.procesadores) {
            p.borrarTareasAsignadas();
        }
    }
}
