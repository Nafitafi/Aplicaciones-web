import * as obj from "./saludo.mjs"
console.log(obj.saludar("Sal"));
console.log("Este es el valor de PI: ", obj.pi)
console.log(obj.config.url_api)

import Estudiante from "./estudiante.js";
import Persona from "./Persona.js";
let estudiante = new Estudiante();
estudiante.nombre = "Sal";
estudiante.carrera = "Art";

console.log(estudiante.saludar())
console.log(estudiante.estudiar())
console.log(estudiante.verPromedio())

let persona = new Persona("Sal", 20)
console.log(persona.saludar())