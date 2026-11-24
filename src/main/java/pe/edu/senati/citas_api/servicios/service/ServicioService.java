
package pe.edu.senati.citas_api.servicios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pe.edu.senati.citas_api.servicios.dto.ServicioDTO;
import pe.edu.senati.citas_api.servicios.entity.Servicio;
import pe.edu.senati.citas_api.servicios.repository.ServicioRepository;

@Service
public class ServicioService {

    private final ServicioRepository repository;

    public ServicioService(ServicioRepository repository) {
        this.repository = repository;
    }

    // Listar todos los servicios
    public List<ServicioDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::aDTO)
                .toList();
    }

    // Buscar un servicio por ID
    public Optional<ServicioDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::aDTO);
    }

    // Buscar servicios por título
    public List<ServicioDTO> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::aDTO)
                .toList();
    }

    // Listar solamente servicios activos
    public List<ServicioDTO> listarActivos() {
        return repository.findByActivoTrue()
                .stream()
                .map(this::aDTO)
                .toList();
    }

    // Crear un nuevo servicio
    public ServicioDTO crear(ServicioDTO dto) {

        if (repository.existsByTituloIgnoreCase(dto.titulo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un servicio con ese título");
        }

        Servicio nuevo = new Servicio();
        copiar(dto, nuevo);

        return aDTO(repository.save(nuevo));
    }

    // Actualizar un servicio
    public Optional<ServicioDTO> actualizar(Long id, ServicioDTO dto) {

        if (repository.existsByTituloIgnoreCaseAndIdNot(dto.titulo(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe otro servicio con ese título");
        }

        return repository.findById(id).map(existente -> {
            copiar(dto, existente);
            return aDTO(repository.save(existente));
        });
    }

    // Eliminar un servicio
    public boolean eliminar(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        try {
            repository.deleteById(id);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar: el servicio ya se usa en citas o terapistas. Desactívalo con activo=false");
        }

        return true;
    }

    // Convertir entidad a DTO
    private ServicioDTO aDTO(Servicio e) {
        return new ServicioDTO(
                e.getId(),
                e.getTitulo(),
                e.getDescripcion(),
                e.getCosto(),
                e.getDuracion(),
                e.getFoto(),
                e.getActivo()
        );
    }

    // Copiar datos del DTO a la entidad
    private void copiar(ServicioDTO dto, Servicio e) {
        e.setTitulo(dto.titulo());
        e.setDescripcion(dto.descripcion());
        e.setCosto(dto.costo());
        e.setDuracion(dto.duracion());
        e.setFoto(dto.foto());
        e.setActivo(dto.activo() == null ? true : dto.activo());
    }
}
