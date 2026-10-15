package com.example.GameVault.controller;

import com.example.GameVault.model.Juego;
import com.example.GameVault.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//SOLO MANEJO DE PETICIONES, NO NEGOCIO
@Controller
public class GameController {
    private static Long idCounter = 1L;

    @Autowired
    private JuegoService juegoService;

    @GetMapping("/fragments-demo")
    public String fragments(){
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(Model model){
        model.addAttribute("juegos", this.juegoService.listarTodos());
        return "juegos";}

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("juego", new Juego());
        return "formulario";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("categoria") String categoria,
                               @RequestParam("precio") Double precio,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("portada") MultipartFile portada) {
        Juego juego = new Juego();
        juego.setTitulo(titulo);
        juego.setDescripcion(descripcion);
        juego.setPrecio(precio);
        juego.setCategoria(categoria);
        this.juegoService.guardarJuego(juego, portada);

        // Redirigimos a la lista de juegos (Patrón PRG - Post/Redirect/Get)
        return "redirect:/juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id){
        this.juegoService.eliminarJuego(id);
        return "redirect:/juegos";
    }

    @GetMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id, Model model){
        Juego juego = this.juegoService.buscarPorId(id);
        model.addAttribute("juego", juego);
        return "formulario";
    }

    @PostMapping("/juegos/editar/{id}")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam Long id,
                               @RequestParam("categoria") String categoria,
                               @RequestParam("precio") Double precio,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam( value = "portada", required = false) MultipartFile portada) {
        Juego juego = this.juegoService.buscarPorId(id);
        juego.setTitulo(titulo);
        juego.setDescripcion(descripcion);
        juego.setPrecio(precio);
        juego.setCategoria(categoria);
        this.juegoService.guardarJuego(juego, portada);

        // Redirigimos a la lista de juegos (Patrón PRG - Post/Redirect/Get)
        return "redirect:/juegos";
    }
}

