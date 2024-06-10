import entidades.Tarea;
import entidades.Procesador;
import utils.MyLinkedList.MyLinkedList;

import java.util.*;

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
        MyLinkedList<Procesador> seleccionados = null;
        boolean stop = false; int i = 0;

        //Mientras haya candidatos disponibles
        Iterator<Tarea> it_candidatos = candidatos.iterator();
        while(it_candidatos.hasNext()) {
            candidato = it_candidatos.next();
            //Determina un conjunto de seleccionados. El primer seleccionado de la lista es el mejor candidato
            seleccionados = seleccionar(candidato);
            //Si es factible esa tarea, la asigno al procesador correspondiente, si no, elijo el siguiente mejor procesador
            while(i < seleccionados.size() && !stop) {
                seleccionado = seleccionados.get(i);
                if(seleccionado.aceptaTarea(candidato)) {
                    seleccionado.asignarTarea(candidato);
                    stop = true;
                }
                i++;
            }
            //Si se recorrieron todos los seleccionados y ninguno acepta la tarea, NO HAY SOLUCION
            if(i == this.procesadores.size()) {
                //Si no hay solucion, vacio a los procesadores que hayan quedado con tareas asignadas
                eliminarTareas();
                //Retorno que no hay solucion
                return null;
            }
            //Vacio la lista para no guardar repetidos y reseteo valores auxiliares
            seleccionados.clear();
            stop = false; i = 0;
            //Elimino mi candidato del Conjunto de Candidatos
            it_candidatos.remove(); //Elimino en el iterador porque si no me lanza excepcion
        }

        //Guardo mi tiempo maximo de ejecucion obtenido y retorno conjunto solucion
        this.tiempoMax = this.procesadores.stream().mapToInt(Procesador::getTiempoDeEjecucion).max().orElse(0);
        return this.procesadores;
    }

    //Devuelve una lista con orden ascendente de todos los procesadores segun el tiempo de ejecucion que suman con la tarea candidata
    //El primer procesador es el mejor seleccionado, el siguiente el segundo mejor, el siguiente el tercer mejor, y asi sucesivamente
    private MyLinkedList<Procesador> seleccionar(Tarea t) {
        //Lista personalizada que contiene un insertar ordenado
        MyLinkedList<Procesador> seleccionados = new MyLinkedList<Procesador>(null);
        for(Procesador p : this.procesadores) {
            this.cantCandidatosConsiderados++;
            p.asignarTarea(t);
            //Segun el tiempo que sume el procesador con la tarea, se insertara en un orden
            seleccionados.insertOrder(p);
            p.desasignarTarea(t);
        }

        return seleccionados;
    }

    //Si Greedy no encontro una solucion, los procesadores deben quedar sin tareas asignadas
    private void eliminarTareas() {
        for(Procesador p : this.procesadores) {
            p.borrarTareasAsignadas();
        }
    }
}
