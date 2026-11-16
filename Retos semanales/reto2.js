const createPowerTower = (altura, simbolo) => {
  // Verifica si la altura es menor o igual a 0 o si el simbolo es una cadena vacía
  if (altura <= 0 || simbolo.length === 0) {
    return "";
  }

  let torre = [];
  const anchoMaximo = 2 * altura - 1;

  // Crea un bucle que itera desde 1 hasta la altura especificada
  for (let i = 1; i <= altura; i++) {
    const cantidadSimbolos = 2 * i - 1;
    const guiones = (anchoMaximo - cantidadSimbolos) / 2;
    const nivel = "_".repeat(guiones) + simbolo.repeat(cantidadSimbolos) + "_".repeat(guiones);
    
    torre.push(nivel);
  }

  // Agrega las dos líneas de la base centradas con #
  const guionesBase = (anchoMaximo - 1) / 2;
  const base = "_".repeat(guionesBase) + "#" + "_".repeat(guionesBase);
  torre.push(base, base);

  // Une todos los niveles con saltos de línea (sin incluir uno al final)
  return torre.join("\n");
};

const tower = createPowerTower(7, '!')
console.log(tower)