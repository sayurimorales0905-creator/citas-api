
package pe.edu.senati.citas_api.auth.dto;

public record SesionDTO(
    Long usuarioId,
    String email,
    String rol
) {}
