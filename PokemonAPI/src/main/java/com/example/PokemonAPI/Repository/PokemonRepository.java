package com.example.PokemonAPI.Repository;

import com.example.PokemonAPI.Model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

}
