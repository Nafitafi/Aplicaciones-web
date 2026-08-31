    package com.example.MiPrimerServer;

    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api")
    public class HelloController {
        @GetMapping("/hello")
        public String hello(){
            return "hola desde mi servidor de aplicación";
        }

        @GetMapping("/saludo")
        public String saludar(@RequestParam String nombre){
            return "Hola "+nombre+" desde mi servidor de apps";
        }

        @PostMapping("/mensaje")
        public String recibirMensaje(@RequestBody String mensaje){
            return "Recibí el mensaje";
        }
    }
