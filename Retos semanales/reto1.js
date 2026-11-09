const prepareSongs=(songs) => {
  // Crear un conjunto para eliminar duplicados
  const cancionesUnicas = new Set(songs) 
  //Las ordena de manera ascendente. Usando el método sort() y la función de comparación (a, b) => a - b
  return Array.from(cancionesUnicas).sort((a, b) => a - b)
}



//Ejecución de la función prepareSongs con diferentes listas de canciones
const songs1 = [3, 1, 2, 3, 4, 2, 5]
const playlist1 = prepareSongs(songs1)
console.log(playlist1) // [1, 2, 3, 4, 5]

const songs2 = [6, 5, 5, 5, 5]
const playlist2 = prepareSongs(songs2)
console.log(playlist2) // [5, 6]

const songs3 = []
const playlist3 = prepareSongs(songs3)
console.log(playlist3) // []
// Nadie anotó nada, la lista queda vacía


