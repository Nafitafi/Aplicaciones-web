package com.example.ResourceHub.service;

import com.example.ResourceHub.model.Recurso;
import com.example.ResourceHub.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;


    public List<Recurso> obtenerTodos(){
        return recursoRepository.findAll();
    }

    public Recurso crearRecurso(Recurso recurso){
        return recursoRepository.save(recurso);
    }

    public Optional<Recurso> actualizar( Long idRecurso, Recurso recurso){
        return recursoRepository.findById(idRecurso).map(recursoExistente ->{
            recursoExistente.setTitulo(recurso.getTitulo());
            recursoExistente.setCategoria(recurso.getCategoria());
            recursoExistente.setUrl(recurso.getUrl());
            recursoExistente.setTipo(recurso.getTipo());

            return recursoRepository.save(recursoExistente);
        });
    }

    public Optional<Recurso> actualizarParcial( Long idRecurso, Recurso recurso){
        return recursoRepository.findById(idRecurso).map(recursoExistente ->{
            if(recurso.getTitulo() != null) recursoExistente.setTitulo(recurso.getTitulo());
            if(recurso.getCategoria() != null)recursoExistente.setCategoria(recurso.getCategoria());
            if(recurso.getUrl() != null)recursoExistente.setUrl(recurso.getUrl());
            if(recurso.getTipo() != null)recursoExistente.setTipo(recurso.getTipo());

            return recursoRepository.save(recursoExistente);
        });
    }

    public boolean eliminarRecurso(Long id){
        return recursoRepository.deleteById(id);
    }
}
