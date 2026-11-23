
package pe.edu.senati.citas_api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CitasApiApplication {

    private final Environment env;

    public CitasApiApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        TimeZone.setDefault(
            TimeZone.getTimeZone("America/Lima")
        );

        SpringApplication.run(
            CitasApiApplication.class, args
        );
    }

    @GetMapping("/")
    public Map<String, String> inicio() {
        Map<String, String> datos = new LinkedHashMap<>();

        datos.put("proyecto", "citas-api");
        datos.put("perfil", String.join(",", env.getActiveProfiles()));
        datos.put("estado", "OK");

        return datos;
    }
}
