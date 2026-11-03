let x = 10
//Truthy y falsey
if (x){
    console.log("Hola mundo")
    console.log("Esto es un numero: ", x, "tipo: ", typeof x)
    x = "Esto es un texto"
    console.log( "Ahora es una cadena:", x, "tipo: ", typeof x)
} else {
    console.log("no entró en el if")
}

/**
 * const x2 = 10;
x2 = "texto";
 */


//Funciones declarativas
function suma(a, b){
    return a+b;
}
console.log("El resultado de la suma es: ", suma(10,5))

//FUnciones anonimas
const resta = function(a, b){return a - b}

console.log("El resultado de la resta es: ", resta(10,5))

//Función flecha
const multiplicar = (a, b) => {return a * b};
console.log("El resultado de la multiplicacion es: ", multiplicar(10,5))

//Funciones IIFE 
;(async function(){
    console.log("hola desd ela funcion autoinvitada")
})();

let arreglo = [1, 2, 3, 4, 5]; //Puede ser de lo que quieras
arreglo.push(6)//Agrtegar elemento al final de la lista
console.log("Arreglo despues del push ", arreglo)

arreglo.pop()//Elimina elemento al final de la lista
console.log("Arreglo despues del pop ", arreglo)

arreglo.unshift(0)//Agrega al inicio de la fila
console.log("Arreglo despues del unshift ", arreglo)

arreglo.shift()//Quita al inicio de la fila
console.log("Arreglo despues del shift ", arreglo)

console.log("longitud", arreglo.length)
console.log(arreglo.indexOf(3))
console.log("¿El arreglo tiene el 3? ", arreglo.includes(3));
console.log("elementos unidos por un guion", arreglo.join("-"))

const copia = arreglo.map((e)=> "Hola" + e)
console.log(copia)