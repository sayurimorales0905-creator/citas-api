
package pe.edu.senati.citas_api.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.senati.citas_api.auth.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por correo electrónico
    Optional<Usuario> findByEmail(String email);
}
