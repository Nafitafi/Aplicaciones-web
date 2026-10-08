package com.example.GameVault.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//SOLO MANEJO DE PETICIONES NO NEGOCIO
@Controller
public class GameController {
    private static List<Juego> juegosDb = new ArrayList<>();
    private static Long idCounter = 1L;

    @GetMapping("/fragments-demo")
    public String fragments(){
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(Model model){
        model.addAttribute("juegos", juegosDb);
        return "juegos";}

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(){
        return "formulario";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("portada") MultipartFile portada) {

        // Redirigimos a la lista de juegos (Patrón PRG - Post/Redirect/Get)
        return "redirect:/juegos";
    }

}

