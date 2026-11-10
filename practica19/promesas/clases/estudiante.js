import Persona from "./Persona";

export default class Estudiante extends Persona{
    constructor(){
        super();
        this.carrera = "";
    }

    esrudiar(){
        return `${this.nombre} esta estudiando ${this.carrera}`
    }

    #calcularPromedio(){
        return 100;
    }

    verPromedio(){
        return `Promedio ${this.#calcularPromedio()}`
    }
}