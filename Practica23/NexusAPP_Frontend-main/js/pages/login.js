import AuthService from '../services/AuthService.js';

/**
 * ============================================================
 *  login.js — Controlador de login.html
 * ============================================================
 */
document.addEventListener('DOMContentLoaded', () => {

    // Si ya hay sesión no tiene caso mostrar el login
    if (AuthService.isAuthenticated()) {
        window.location.href = 'index.html';
        return;
    }

    const loginForm = document.getElementById('login-form');

    // TODO: 1. Escuchar el evento 'submit' de loginForm (la función debe ser async)

    // TODO: 2. Llamar a e.preventDefault() para que el formulario no recargue la página
    // TODO: 3. Leer los valores de los inputs #username (con .trim()) y #password
    // TODO: 4. Dentro de un try:
    //          - await AuthService.login(username, password)
    //          - Redirigir a 'index.html' (window.location.href)
    // TODO: 5. En el catch: mostrar el error con alert(error.message)
    loginForm.addEventListener('submit', async (e)=>{
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try{
            await AuthService.login(username, password);
            window.location.href = 'index.html'
        } catch (error){
            alert(error.message)
        }
    })
});
