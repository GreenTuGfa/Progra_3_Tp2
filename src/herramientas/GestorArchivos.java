package herramientas;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import modelos.Localidad;

public class GestorArchivos {
	private Gson gson;

    public GestorArchivos() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardar(List<Localidad> lista, String ruta) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(lista, escritor);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    public List<Localidad> cargar(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) 
            return new ArrayList<>();

        try (FileReader lector = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Localidad>>(){}.getType();
            List<Localidad> resultado = gson.fromJson(lector, tipoLista);
            return (resultado != null) ? resultado : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
