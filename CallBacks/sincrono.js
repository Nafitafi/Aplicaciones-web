function obetnerPost(){
    fetch("https://jsonplaceholder.typicode.com/posts").then(r =>{
        console.log("Culminé el get") 
        callback()});
}

const ejecutarAlTerminar = () => {console.log("Finaliza el programa");}

console.log("Inicia el programa");
obetnerPost(ejecutarAlTerminar);
