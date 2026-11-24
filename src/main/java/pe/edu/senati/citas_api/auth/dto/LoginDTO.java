
package pe.edu.senati.citas_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo no válido")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    String password

) {}
