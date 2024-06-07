<<<<<<< HEAD
import entidades.Procesador;
=======
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
import entidades.Tarea;
import utils.CSVReader;
import utils.Tree.Tree;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "entidades.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	private Map<String, Tarea> tareas;
	private LinkedList<Tarea> tareasCriticas;
	private LinkedList<Tarea> tareasNoCriticas;
	private Tree tareasOrdenadas;
<<<<<<< HEAD
	private AsignacionTareas asignador;
=======
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
	private CSVReader reader;

	/*
     * El costo computacional de este metodo es 0(n) porque recorre N lineas en el archivo csv para obtener las tareas parseadas
     */
	public Servicios(String pathProcesadores, String pathTareas) {
		this.tareas = new HashMap<String, Tarea>();
		this.tareasCriticas = new LinkedList<Tarea>();
		this.tareasNoCriticas = new LinkedList<Tarea>();
		this.tareasOrdenadas = new Tree();
		this.reader = new CSVReader();
<<<<<<< HEAD
		ArrayList<Tarea> tareas_obtenidas = reader.readTasks(pathTareas);
		this.cargarTareas(tareas_obtenidas);
		this.asignador = new AsignacionTareas(tareas_obtenidas, reader.readProcessors(pathProcesadores));
=======
		cargarTareas(reader.readTasks(pathTareas));
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
	}
	
	/*
     * El costo computacional de éste metodo es 0(1) porque en tiempo de ejecucion es constante, es decir, siempre
     * es una operacion secuencial
     */
	public Tarea servicio1(String ID) {
		return tareas.get(ID);
	}
    
    /*
     * El costo computacional de éste metodo es 0(1) porque simplemente evaluo el valor booleano que me pasan como parametro
     * Si es true devuelvo mi lista de todas las tareas criticas, si no devuelvo mi lista de todas las tareas no criticas
     */
	public List<Tarea> servicio2(boolean esCritica) {
		if(esCritica)
			return this.tareasCriticas;

		return this.tareasNoCriticas;
	}

    /*
     * El costo computacional de este metodo en el peor de los casos es 0(n) si el arbol tuviese una forma de enredadera. Si el arbol fuese balanceado
     * el costo se reduciria a 0(log n) porque voy descartando ramas del arbol a medida que comparo los niveles de prioridad (el costo se incrementa en partes)
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		return this.tareasOrdenadas.searchNodesByRange(prioridadInferior, prioridadSuperior);
	}

<<<<<<< HEAD
	public List<Procesador> servicio4() {
		return this.asignador.solucionBacktracking();
	}

	public List<Procesador> servicio5() {
		return this.asignador.solucionGreedy();
	}
	public int getTiempoMaxDeEjecucionDeAsignacion() {
		return this.asignador.getTiempoMax();
	}

	public int getCantEstadosGeneradosServicio4() {
		return this.asignador.getCantEstadosGenerados();
	}

=======
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e

	private void cargarTareas(ArrayList<Tarea> tareasParseadas) {
		for (Tarea tarea : tareasParseadas) {
			this.tareas.put(tarea.getId(), tarea); //O(1)
			verificarEstadoCritico(tarea); //O(1)
			//El criterio de ordenamiento en el arbol es el nivel de prioridad de las tareas O(H) donde H es la altura del arbol
			this.tareasOrdenadas.add(tarea.getPrioridad(), tarea);
		}
	}

	private void verificarEstadoCritico(Tarea tarea) {
		if (tarea.isCritica())
			this.tareasCriticas.add(tarea);
		else
			this.tareasNoCriticas.add(tarea);
	}

}
