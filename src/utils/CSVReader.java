package utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
<<<<<<< HEAD

import entidades.Procesador;
=======
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
import entidades.Tarea;

public class CSVReader {

	public CSVReader() {
	}
	
	public ArrayList<Tarea> readTasks(String taskPath) {
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(taskPath);
		ArrayList<Tarea> tareasParseadas = new ArrayList<Tarea>();

		for (String[] line: lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String nombre = line[1].trim();
			Integer tiempo = Integer.parseInt(line[2].trim());
			Boolean critica = Boolean.parseBoolean(line[3].trim());
			Integer prioridad = Integer.parseInt(line[4].trim());
			tareasParseadas.add(new Tarea(id, nombre, tiempo, critica, prioridad));
		}

		return tareasParseadas;
	}
	
<<<<<<< HEAD
public ArrayList<Procesador> readProcessors(String processorPath) {
=======
public ArrayList<String[]> readProcessors(String processorPath) {
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(processorPath);
<<<<<<< HEAD
		ArrayList<Procesador> procesadoresParseados = new ArrayList<Procesador>();

=======
		
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
		for (String[] line: lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String codigo = line[1].trim();
			Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
			Integer anio = Integer.parseInt(line[3].trim());
<<<<<<< HEAD
			Integer tiempoMax = Integer.parseInt(line[4].trim());
			procesadoresParseados.add(new Procesador(id, codigo, refrigerado, anio, tiempoMax));
		}

		return procesadoresParseados;
=======
			// Aca instanciar lo que necesiten en base a los datos leidos
		}

		return lines;
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
	}

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		File file = new File(path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		
		return lines;
	}
	
}
