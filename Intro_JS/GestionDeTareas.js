// =====================================
// Sistema de Gestión de Tareas
// =====================================

/*
INSTRUCCIONES GENERALES:

1. Lee cuidadosamente cada sección.
2. Completa únicamente donde se indica con TODO.
3. No borres la estructura base.
4. Agrega comentarios explicando tu lógica.
5. Prueba cada función antes de continuar.
6. NO USAR INTELIGENCIA ARTIFICIAL.
*/


// =====================================
// 1. Arreglo inicial de tareas
// =====================================

/*
Cada tarea tiene:
- nombre: string
- prioridad: number (1 = alta, 2 = media, 3 = baja)
- completada: boolean
*/

const tareas = [
  { nombre: "Ir a la playa", prioridad: 1, completada: false },
  { nombre: "Estudiar JS", prioridad: 2, completada: true },
  { nombre: "Ver películas", prioridad: 3, completada: false }
];


// =====================================
// 2. Funciones
// =====================================

/*
TODO 1:
Crear una función que recorra el arreglo de tareas
y muestre en consola:
- nombre de la tarea
- estado: "Completada" o "Pendiente"
*/
function mostrarTareas(lista) {
  // Se recorre la lista que entra como parámetro usando forEach
  lista.forEach(tarea => {
    // Se usa un operador ternario para definir el string del estado basado en el booleano 
    const estado = tarea.completada ? 'Completada' : 'Pendiente';
    console.log(`Tarea: ${tarea.nombre}, Estado: ${estado}`);
  })
}


/*
TODO 2:
Crear una función flecha que retorne
solo las tareas completadas.
Usar filter.
*/
const obtenerCompletadas = (lista) => {
  // Se filtra la lista recibida por parámetro manteniendo solo los objetos donde tarea.completada sea true
  return lista.filter(tarea => tarea.completada);
};


/*
TODO 3:
Crear una función flecha que retorne
solo las tareas pendientes.
Usar filter.
*/
const obtenerPendientes = (lista) => {
 // Se filtra la lista recibida por parámetro manteniendo solo los objetos donde tarea.completada sea false
  return lista.filter(tarea => !tarea.completada);
};


/*
TODO 4:
Crear una función flecha que retorne
solo los nombres de las tareas.
Usar map.
*/
const obtenerNombres = (lista) => {
  //Se usa map para solo extraer los nombres
  return lista.map(tarea => tarea.nombre);
};


/*
TODO 5:
Crear una función que retorne
el total de tareas.
*/
function contarTareas(lista) {
  return lista.length;
}


// =====================================
// 3. Objeto sistema
// =====================================

/*
TODO 6:
Completar los métodos usando this.tareas

- mostrarTareas: debe llamar a la función mostrarTareas
- mostrarCompletadas: debe usar obtenerCompletadas
- mostrarPendientes: debe usar obtenerPendientes
*/

const sistema = {
  tareas: tareas,

  mostrarTareas: function() {
    //Se usa el this. para referirse a la propiedad
    mostrarTareas(this.tareas);
  },

  mostrarCompletadas: function() {
     // Se obtiene el arreglo filtrado invocando la función
    const completadas = obtenerCompletadas(this.tareas);
    // Se usa la función mostrarTareas para imprimir en consola de forma limpia el resultado filtrado
    mostrarTareas(completadas);
  },

  mostrarPendientes: function() {
     const pendientes = obtenerPendientes(this.tareas);
    // Se muestran en la consola las pendientes
    mostrarTareas(pendientes);
  }
};


// =====================================
// 4. Condicionales
// =====================================

/*
TODO 7:

- Si el arreglo tareas está vacío:
  mostrar "No hay tareas"

- Si todas las tareas están completadas:
  mostrar "Todas las tareas completadas"
*/
// Se comprueban si hay tareas
if (tareas.length === 0) {
  console.log("No hay tareas");
} 
// el .every se asegura que todo cumpla con la condición
else if (tareas.every(tarea => tarea.completada)) {
  console.log("Todas las tareas completadas");
}

// =====================================
// 5. Switch
// =====================================

/*
TODO 8:

Usar la variable opcion para ejecutar:

1 -> mostrar todas las tareas
2 -> mostrar tareas completadas
3 -> mostrar tareas pendientes
default -> mostrar "Opción inválida"
*/

const opcion = 1;

switch (opcion) {
  case 1:
    console.log("Todas las tareas: ")
    sistema.mostrarTareas();
    break;

  case 2:
    console.log("Tareas completadas: ")
    sistema.mostrarCompletadas();
    break;

  case 3:
    console.log("Tareas pendientes: ")
    sistema.mostrarPendientes();
    break;

  default:
    console.log("Opción inválida");
}


// =====================================
// 6. Pruebas
// =====================================

/*
TODO 9:

Llamar funciones para comprobar que todo funciona correctamente.
Usar console.log donde sea necesario.
*/

// Prueba de conteo de tareas
console.log(`Total de tareas registradas: ${contarTareas(tareas)}`);

// Prueba de obtención de nombres de tareas
console.log("Nombres de todas las tareas:", obtenerNombres(tareas));

// Prueba de simulación de condicionales con un arreglo vacío
const listaVacia = [];
if (listaVacia.length === 0) {
  console.log("No hay tareas");
}