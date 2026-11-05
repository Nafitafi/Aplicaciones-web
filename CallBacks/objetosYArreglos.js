let numeros = [1, 2, 3, 4, 5];
let dobles = numeros.map(num => `<p>Numero: ${num}</p>`);
console.log(numeros);
console.log(dobles);

const html = dobles.join("");
console.log(html);

const edad = 18;



function puerta(edad = 0) {
    if (edad >= 18 && edad <= 45) {
        console.log("Puedes pasar")
        return;
    } else if (edad >= 18 && edad < 45) {
        console.log("Vete a dormir paro")
        return
    }
    console.log("NO Puedes pasar")
}

const dia = 3;
switch(dia){
    case 1: 
        console.log("Lunes")
        break;
    case 2: 
        console.log("Martes")
        break;
    case 3: 
        console.log("Miercoles")
        break;
    default:
        console.log("Es otro día")
}

//Exiate for IN (Recorre llaves)y for OF(Recorre valores) 
const alumno = {
    nombre: "Sal",
    edad: 20,
    hobbies: ["guitarra", "dibujo", "investigaer"],
    direccion: {
        calle: "Nkocfell",
        numero: 42
    },
    saludar: function(){
        return `Hola soy ${this.nombre} y tengo ${edad} años!`
    }
}

console.log(alumno);
alumno.edad= 25;
console.log(alumno);
//Exiate for IN (Recorre llaves)
for(const llave in alumno){
    console.log(`${llave} = ${alumno[llave]}`)
} 


//y for OF(Recorre valores) 
for(const hobbie of alumno.hobbies){
    console.log(hobbie)
}

console.log(alumno.saludar());

for(let i = 0; i < alumno.hobbies.length; i++){
    
}