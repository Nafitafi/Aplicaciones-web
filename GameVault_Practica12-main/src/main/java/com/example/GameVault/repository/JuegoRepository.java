package com.example.GameVault.repository;

import com.example.GameVault.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    // Métodos derivados: JPA arma el SQL a partir del nombre del método.

    // Busca por título sin importar mayúsculas.
    List<Juego> findByTituloContainingIgnoreCase(String titulo);

    // Ejemplo: juegos más baratos que un precio dado.
    // List<Juego> findByPrecioLessThan(Double precio);

    // Tip de seguridad: busca por ID y dueño a la vez para que la BD filtre lo ajeno.
    // Optional<Juego> findByIdAndUsuarioId(Long id, Long usuarioId);

    // @Query usa JPQL: se consulta la clase Juego, no la tabla.

    // Busca una palabra dentro de la descripción.
    @Query("SELECT j FROM Juego j WHERE LOWER(j.descripcion) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<Juego> buscarPorDescripcion(@Param("palabra") String palabra);

    // Juegos más recientes primero.
    @Query("SELECT j FROM Juego j ORDER BY j.id DESC")
    List<Juego> obtenerJuegosRecientes();

    // Juegos de un usuario; el usuarioId debe venir de la sesión.
    List<Juego> findByUsuarioId(Long usuarioId);

    // :palabra es un parámetro seguro; nunca concatenes texto del usuario en un query.
    @Query("SELECT j FROM Juego j WHERE LOWER(j.titulo) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(j.categoria) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<Juego> buscarPorTituloOCategoria(@Param("palabra") String palabra);
}
