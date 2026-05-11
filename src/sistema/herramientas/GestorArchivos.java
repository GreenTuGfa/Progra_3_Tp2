package sistema.herramientas;

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

import sistema.modelos.Localidad;

public class GestorArchivos {
    private Gson gson;

    public GestorArchivos() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardar(List<Localidad> lista, String ruta) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(lista, escritor);
        } catch (IOException excepcion) {
            // Un print simple para saber qué falló durante la ejecución
            System.out.println("Error al guardar: " + excepcion.getMessage());
        }
    }

    public List<Localidad> cargar(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader lector = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Localidad>>(){}.getType();
            List<Localidad> resultado = gson.fromJson(lector, tipoLista);
            
            return (resultado != null) ? resultado : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
