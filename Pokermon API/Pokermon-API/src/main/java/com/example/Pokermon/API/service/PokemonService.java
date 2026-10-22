package com.example.Pokermon.API.service;

import com.example.Pokermon.API.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Pokermon.API.repository.PokemonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    public List<Pokemon> listarTodos(){
        return pokemonRepository.findAll();
    }

    public Pokemon guardar(Pokemon pokemon){
        return pokemonRepository.save(pokemon);
    }

    public Optional<Pokemon> listarPorId(Long id){
        return pokemonRepository.findById(id);
    }
}
