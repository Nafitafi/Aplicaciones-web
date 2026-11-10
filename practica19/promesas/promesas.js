function obtenerDatosDelServidor(){
    return new Promise((resolve, reject) => {
    setTimeout(() =>{
        let usuario = {
            nombre: "Alejandra",
            edad: 20
        }

        if(usuario){
            resolve(usuario)
        } else {
            reject("Ocurrio un error al obtener los datos")
        }
    }, 2000)
}
)
}

obtenerDatosDelServidor()
    .then((resultado) =>{
        return resultado.nombre.toUpperCase();
    })
    .catch(nombreMayuscula=>{
        console.log(nombreMayuscula)
    })
    .finally(() =>{
        console.log("Fin del programa")
    })

fetch("https://jsonplaceholder.typicode.com/posts")
    .then(response =>{
        return response.json();
    })
    .then(json =>{
        console.log("Lista de posts: ", json)
    })
    .catch(err =>{
        console.log(err)
    })
    .finally(() =>{
        console.log("termino la peticion al servidor")
    })
