package com.example.GameVault.repository;

import com.example.GameVault.controller.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    //Select * from juegos where titulo LIKE "%titulo%"
    List<Juego> findByTituloContainingIgnoreCase(String titulo);

    // Ejemplo 2: Si tuvieramos un campo 'genero', buscar todos los de un género específico.
    // List<Juego> findByGenero(String genero);

    // Ejemplo 3: Si tuviéramos 'precio' y quisiéramos juegos más baratos que X.
    // List<Juego> findByPrecioLessThan(Double precio);

    // Ejemplo 4: Si tuviéramos 'activo' (boolean).
    // List<Juego> findByActivoTrue();

    //JPQL

}
