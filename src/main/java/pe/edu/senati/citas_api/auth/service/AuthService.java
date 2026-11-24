
package pe.edu.senati.citas_api.auth.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pe.edu.senati.citas_api.auth.dto.LoginDTO;
import pe.edu.senati.citas_api.auth.dto.SesionDTO;
import pe.edu.senati.citas_api.auth.entity.Usuario;
import pe.edu.senati.citas_api.auth.repository.RolRepository;
import pe.edu.senati.citas_api.auth.repository.UsuarioRepository;

@Service
public class AuthService {

    private static final List<String> ROLES_PERMITIDOS =
            List.of("GERENTE", "ADMIN");

    private final UsuarioRepository usuarios;
    private final RolRepository roles;

    public AuthService(UsuarioRepository usuarios, RolRepository roles) {
        this.usuarios = usuarios;
        this.roles = roles;
    }

    public SesionDTO login(LoginDTO dto) {

        // 1. Validar correo y contraseña
        Usuario u = usuarios.findByEmail(dto.email())
                .filter(x -> x.getPassword().equals(dto.password()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Correo o contraseña incorrectos"));

        // 2. Verificar que el usuario esté activo
        if (!Boolean.TRUE.equals(u.getActivo())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "El usuario está inactivo");
        }

        // 3. Verificar rol permitido
        String rol = roles.findById(u.getRolId())
                .map(r -> r.getNombre())
                .orElse("");

        if (!ROLES_PERMITIDOS.contains(rol)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Tu rol (" + rol + ") no puede usar esta API");
        }

        return new SesionDTO(u.getId(), u.getEmail(), rol);
    }
}
