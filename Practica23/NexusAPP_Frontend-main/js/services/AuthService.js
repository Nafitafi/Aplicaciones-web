/**
 * URL base de la API de Nexus (proyecto Spring Boot con JWT)
 */
const BASE_URL = 'http://localhost:8080/api';

/**
 * ============================================================
 *  AuthService — Inicio de sesión y datos de la sesión
 * ============================================================
 *
 * La API responde al login con:
 *   { token: "eyJ...", user: { id, username, email } }
 *
 * Guardamos las dos cosas en localStorage:
 *   - token → se manda en cada petición (Authorization: Bearer ...)
 *   - user  → para mostrar quién inició sesión
 */
class AuthService {

    /**
     * Inicia sesión contra la API.
     *
     * @param {string} username - Nombre de usuario (ej. carlos_dev)
     * @param {string} password - Contraseña (ej. 1234)
     * @returns {Promise<Object>} El usuario que inició sesión { id, username, email }
     */
    static async login(username, password) {
        // TODO: 1. Hacer un fetch POST a `${BASE_URL}/auth/login`
        //          - headers: { 'Content-Type': 'application/json' }
        //          - body: JSON.stringify({ username, password })
        // TODO: 2. Convertir la respuesta a JSON (await res.json())
        // TODO: 3. Si la respuesta NO es ok (res.ok === false), lanzar un Error con el mensaje
        //          que manda la API (data.error) o uno por defecto: 'Error al iniciar sesión'
        // TODO: 4. Guardar en localStorage:
        //          - 'token' → data.token
        //          - 'user'  → JSON.stringify(data.user)   (localStorage solo guarda texto)
        // TODO: 5. Retornar data.user
        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-type': 'application/json'},
            body: JSON.stringify({username, password})
        })
        const data = await response.json() 
        if (!response.ok){
            throw new Error(data.error || "Ocurrió un error al iniciar sesión")
        }
        localStorage.setItem("token", data.token)
        localStorage.setItem("user", JSON.stringify(data.user));
        return data.user;
    }

    /** Borra la sesión del localStorage. */
    static logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    }

    /** ¿Hay un token guardado? */
    static isAuthenticated() {
        return !!localStorage.getItem('token');
    }

    /** @returns {string|null} El token guardado */
    static getToken() {
        return localStorage.getItem('token');
    }

    /** @returns {Object|null} El usuario guardado { id, username, email } */
    static getUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default AuthService;
