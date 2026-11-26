import AuthService from './AuthService.js';

/**
 * URL base de la API de Nexus (proyecto Spring Boot con JWT)
 */
const BASE_URL = 'http://localhost:8080/api';

/**
 * ============================================================
 *  PostService — Publicaciones (todas las rutas piden token)
 * ============================================================
 *
 *   GET  /posts  → todos los posts
 *   POST /posts  → { title, content }
 */
class PostService {

    /**
     * Cabeceras para las peticiones protegidas.
     *
     * @returns {Object} headers con Content-Type y Authorization
     */
    static getHeaders() {
        // TODO: Retornar un objeto con:
        //       - 'Content-Type': 'application/json'
        //       - 'Authorization': `Bearer ${...}`  (el token lo da AuthService.getToken())
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${AuthService.getToken()}`
        }
    }

    /**
     * Revisa la respuesta de la API: si hubo error lanza un Error con su mensaje.
     * Si es 401 el token ya no sirve, así que cierra la sesión.
     *
     * @param {Response} res - La respuesta del fetch
     * @param {string} defaultMessage - Mensaje si la API no manda uno
     * @returns {Promise<any>} Los datos en JSON
     */
    static async handleResponse(res, defaultMessage) {
        const data = await res.json();

        if (!res.ok) {
            if (res.status === 401) {
                AuthService.logout();
                throw new Error('Tu sesión expiró, vuelve a iniciar sesión');
            }
            throw new Error(data.error || defaultMessage);
        }

        return data;
    }

    /**
     * Obtiene todos los posts.
     *
     * @returns {Promise<Array>} Lista de posts, del más reciente al más antiguo
     */
    static async getAll() {
        // TODO: 1. Hacer un fetch GET a `${BASE_URL}/posts` mandando { headers: this.getHeaders() }
        // TODO: 2. Retornar this.handleResponse(res, 'Error al obtener los posts')
        const response = await fetch(`${BASE_URL}/posts`, {headers: this.getHeaders()})
        return this.handleResponse(response, "error al obtener los posts")
    }

    /**
     * Crea un post a nombre del usuario que inició sesión.
     *
     * @param {string} title - Título del post
     * @param {string} content - Contenido del post
     * @returns {Promise<Object>} El post creado
     */
    static async create(title, content) {
        // TODO: 1. Hacer un fetch POST a `${BASE_URL}/posts`
        //          - headers: this.getHeaders()
        //          - body: JSON.stringify({ title, content })
        //          OJO: NO se manda userId. La API sabe quién eres por el token.
        // TODO: 2. Retornar this.handleResponse(res, 'Error al crear la publicación')
        const response = await fetch(`${BASE_URL}/posts`, {
            method: 'POST',
            headers: this.getHeaders(),
            body: JSON.stringify({tittle, content})
        })
        return this.handleResponse(response, "Error al crear post")
    }
}

export default PostService;
