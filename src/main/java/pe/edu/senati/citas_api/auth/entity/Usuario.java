
package pe.edu.senati.citas_api.auth.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(name = "rol_id", nullable = false)
    private Long rolId;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Long getRolId() { return rolId; }
    public Boolean getActivo() { return activo; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
}
