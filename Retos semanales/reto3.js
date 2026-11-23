const organizeShoes = (shoes) => {
// Creamos un objeto para llevar el inventario de botas por talla
  const inventory = {};
  const pairedSizes = [];

  for (const { type, size } of shoes) {
    // Si la talla aún no está en el inventario, se inicializa
    if (!inventory[size]) {
      inventory[size] = { I: 0, R: 0 };
    }

    // Registramos la bota actual ('I' o 'R')
    inventory[size][type]++;

    // Si ya hay al menos una bota izquierda y una derecha de esta talla, se forma el par
    if (inventory[size].I > 0 && inventory[size].R > 0) {
      pairedSizes.push(size);
      // Se descuentan ambas botas del inventario disponible
      inventory[size].I--;
      inventory[size].R--;
    }
  }

  return pairedSizes;
};


const shoes = [
{ type: 'I', size: 38 },
{ type: 'R', size: 38 },
{ type: 'R', size: 42 },
{ type: 'I', size: 41 },
{ type: 'I', size: 42 }
]

console.log(organizeShoes(shoes))
// [38, 42]

const shoes2 = [
  { type: 'I', size: 38 },
  { type: 'R', size: 38 },
  { type: 'I', size: 38 },
  { type: 'I', size: 38 },
  { type: 'R', size: 38 }
]

console.log(organizeShoes(shoes2))
// [38, 38]

const shoes3 = [
  { type: 'I', size: 38 },
  { type: 'R', size: 36 },
  { type: 'R', size: 42 },
  { type: 'I', size: 41 },
  { type: 'I', size: 43 }
]

console.log(organizeShoes(shoes3))
// []