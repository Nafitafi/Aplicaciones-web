function obtenerDatosDelServidor() {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            let usuario = {
                nombre: "Alejandra",
                edad: 20
            }

            if (usuario) {
                resolve(usuario)
            } else {
                reject("Ocurrio un error al obtener los datos")
            }
        }, 2000)
    }
    )
}

obtenerDatosDelServidor()
    .then((resultado) => {
        return resultado.nombre.toUpperCase();
    })
    .catch(nombreMayuscula => {
        console.log(nombreMayuscula)
    })
    .finally(() => {
        console.log("Fin del programa")
    })

async function obtenerposts() {
    try {
        let response = await fetch("https://jsonplaceholder.typicode.com/posts");
        console.log(response);
    } catch (err) {
        console.log(err);
    } finally {
        
    }
}

obtenerposts();
