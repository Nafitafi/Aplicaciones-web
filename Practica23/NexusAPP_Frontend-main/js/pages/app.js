import AuthService from '../services/AuthService.js';
import PostService from '../services/PostService.js';
import { composerTemplate, postCardTemplate, postModalTemplate } from '../templates.js';

/**
 * ============================================================
 *  app.js — Controlador de index.html (el feed)
 * ============================================================
 */

// Referencias a elementos del DOM
const composer = document.getElementById('composer-user-info');
const postsContainer = document.getElementById('posts-container');
const modalRoot = document.getElementById('modal-root');
const logoutBtn = document.getElementById('logout-btn');

/**
 * Punto de entrada de la página.
 */
document.addEventListener('DOMContentLoaded', () => {
    // TODO: 1. Si NO hay sesión (AuthService.isAuthenticated()), redirigir a 'login.html' y hacer return
    // TODO: 2. Llamar a renderUser()
    // TODO: 3. Llamar a setupEventListeners()
    // TODO: 4. Llamar a loadPosts()

    if (!AuthService.isAuthenticated){
        window.location.href = 'login.html';
        return
    }
    renderUser(); 
    setupEventListeners();
    loadPosts();
});

/**
 * Pinta el avatar y el "¿Qué estás pensando?" con el usuario del login.
 */
function renderUser() {
    // TODO: 1. Obtener el usuario con AuthService.getUser()
    // TODO: 2. Inyectar composerTemplate(user) en composer.innerHTML
    const user = AuthService.getUser();
    if (user === "undefined"){
        window.location.href = 'login.html';
        return
    }
    composer.innerHTML = composerTemplate(user);

}

/**
 * Registra los eventos de la página.
 */
function setupEventListeners() {
    // TODO: 1. Escuchar 'click' en logoutBtn:
    //          - AuthService.logout()
    //          - Redirigir a 'login.html'
    //
    // TODO: 2. Escuchar 'click' en composer (delegación de eventos: el trigger se creó con innerHTML):
    //          - Si e.target.closest('#open-post-modal') existe, llamar a openModal()
    //
    // TODO: 3. Escuchar 'click' en modalRoot (el modal tampoco existe al cargar la página):
    //          - Si se hizo clic en la X (e.target.closest('.close-modal'))
    //            o fuera del modal (e.target.classList.contains('modal-overlay')), llamar a closeModal()
    //
    // TODO: 4. Escuchar 'submit' en modalRoot y pasarle la función createPost
    logoutBtn.addEventListener('click', () =>{
        AuthService.logout();
        window.location.href = 'login.html'
    })
    composer.addEventListener('click', (e) =>{
        openModal();
    })
}

/**
 * Pide los posts a la API y pinta una tarjeta por cada uno.
 */
async function loadPosts() {
    // TODO: 1. Dentro de un try, obtener los posts con await PostService.getAll()
    // TODO: 2. Limpiar postsContainer.innerHTML (quita el "Cargando posts...")
    // TODO: 3. Si no hay posts, poner '<p style="text-align: center;">No hay posts aún</p>' y hacer return
    // TODO: 4. Recorrer los posts. Para cada uno:
    //          - Crear un div con document.createElement('div')
    //          - Asignarle className = 'post-card fade-in'
    //          - Inyectarle postCardTemplate(post) en innerHTML
    //          - Agregarlo a postsContainer con appendChild
    // TODO: 5. En el catch, llamar a handleError(error)
    try{
        const posts = await PostService.getAll();
        postsContainer.innerHTML = '';
        if(posts.length === 0){
            postsContainer.innerHTML = `<p> No hay posts aún</p>`;
            return;
        }

        posts.forEach(post => {
            const card = document.createElement('div');
            card.className = 'post-card fade-in';
            card.innerHTML = postCardTemplate(post);
            postsContainer.appendChild(card);
        });

    } catch (error) {
        handleError(error);
    }
}

/**
 * Abre el modal de crear publicación.
 */
function openModal() {
    // TODO: Inyectar postModalTemplate() en modalRoot.innerHTML
    modalRoot.innerHTML = postModalTemplate();
}

/**
 * Cierra el modal.
 */
function closeModal() {
    // TODO: Vaciar modalRoot.innerHTML
    modalRoot.innerHTML = '';
}

/**
 * Envía el formulario del modal y recarga el feed.
 *
 * @param {SubmitEvent} e - El evento submit
 */
async function createPost(e) {
    // TODO: 1. Si e.target.id no es 'create-post-form', hacer return
    // TODO: 2. Llamar a e.preventDefault()
    // TODO: 3. Leer los valores de #post-title y #post-content (con .trim())
    // TODO: 4. Dentro de un try:
    //          - await PostService.create(title, content)
    //          - closeModal()
    //          - loadPosts() para ver el post nuevo
    // TODO: 5. En el catch, llamar a handleError(error)
}

/**
 * Muestra el error; si la sesión se cerró (401) regresa al login.
 *
 * @param {Error} error
 */
function handleError(error) {
    alert(error.message);
    if (!AuthService.isAuthenticated()) {
        window.location.href = 'login.html';
    }
}
