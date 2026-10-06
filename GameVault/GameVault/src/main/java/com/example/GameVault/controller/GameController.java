package com.example.GameVault.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GameController {
    private static List<Juego> juegosdb = new ArrayList<>();
    private static Long idCounter = 1L;
    private static final String UPLOAD_DIR="src/main/resources/static/uploads/";

    @GetMapping("/fragments-demo")
    public String fragments(){
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(){return "juegos";}

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(){
        return "formulario";
    }
}

