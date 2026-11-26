/**
 * ============================================================
 *  templates.js — El HTML de cada "tarjetita" de la interfaz
 * ============================================================
 *
 * El HTML ya está listo. Lo que falta es INYECTAR LOS DATOS:
 * cambia cada texto en MAYÚSCULAS por el dato real usando ${ }.
 *
 *   Ejemplo:   <h3>AUTOR</h3>   →   <h3>${post.user.username}</h3>
 *
 * Los datos vienen tal cual los regresa la API:
 *   user    → { id, username, email }
 *   post    → { id, title, content, createdAt, user: { id, username, email } }
 *   comment → { id, text, createdAt, user: { id, username, email } }
 */

/** Convierte "2026-11-24T23:09:24.57" en una fecha legible. Úsala para la FECHA del post. */
const formatDate = (isoDate) => new Date(isoDate).toLocaleString('es-MX');

/** Avatar con las iniciales de un nombre (servicio gratuito ui-avatars.com). */
const avatarUrl = (name, background = 'random') =>
    `https://ui-avatars.com/api/?name=${encodeURIComponent(name)}&background=${background}&color=fff`;

/**
 * Barra superior: avatar del usuario y el botón que abre el modal.
 * Se inyecta en #composer-user-info.
 *
 * TODO: Cambia USUARIO por el username del usuario (2 lugares).
 *       Para la imagen usa: avatarUrl(user.username, '4F46E5')
 */
export const composerTemplate = (user) => `
    <img class="avatar" src="https://ui-avatars.com/api/?name=${user.username}&background=4F46E5&color=fff" alt="Perfil">
    <div class="composer-trigger" id="open-post-modal">
        ¿Qué estás pensando, ${user.username}?
    </div>
`;

/**
 * Tarjeta de un post.
 * Se inyecta dentro de un <div class="post-card fade-in"> en #posts-container.
 *
 * TODO: Cambia los textos en MAYÚSCULAS por los datos del post:
 *         AUTOR     → post.user.username   (también en la imagen: avatarUrl(post.user.username))
 *         FECHA     → formatDate(post.createdAt)
 *         TITULO    → post.title
 *         CONTENIDO → post.content
 */
export const postCardTemplate = (post) => `
    <div class="post-header">
        <img class="avatar" src="${avatarUrl(post.user.username)}}" alt="${post.user.username}">
        <div class="post-user-info">
            <h3>${post.user.username}</h3>
            <span>${formatDate(post.createdAt)}</span>
        </div>
    </div>
    <div class="post-content">
        <div class="post-title">${post.tittle}</div>
        <p>${post.content}</p>
    </div>
    <div class="post-actions">
        <button class="action-btn"><i class="far fa-thumbs-up"></i> Me gusta</button>
        <button class="action-btn"><i class="far fa-comment"></i> Comentar</button>
    </div>
`;

/**
 * Modal para crear un post.
 * Se inyecta en #modal-root. No necesita datos: úsalo tal cual.
 */
export const postModalTemplate = () => `
    <div class="modal-overlay" id="post-modal" style="display: flex;">
        <div class="modal-content fade-in">
            <div class="modal-header">
                <h2>Crear publicación</h2>
                <button class="close-modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="create-post-form">
                    <div class="form-group">
                        <input type="text" id="post-title" placeholder="Título de tu post" required>
                    </div>
                    <div class="form-group">
                        <textarea id="post-content" placeholder="¿Qué estás pensando?"
                            style="width: 100%; min-height: 150px; padding: 12px; border: 1px solid var(--border); border-radius: var(--radius); resize: none; font-size: 1.1rem; outline: none;"
                            required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Publicar</button>
                </form>
            </div>
        </div>
    </div>
`;

/* ------------------------------------------------------------
 *  EXTRA (reto opcional): comentarios
 *  La API ya los soporta:
 *    GET  /api/comments/post/{postId}
 *    POST /api/comments  { postId, text }
 * ------------------------------------------------------------ */

/**
 * Sección de comentarios de un post: la lista y el campo para escribir.
 * Se agrega al final de la tarjeta del post.
 *
 * TODO: POST_ID → post.id (2 lugares)   USUARIO → currentUser.username
 */
export const commentsSectionTemplate = (post, currentUser) => `
    <div class="comments-section">
        <div class="comments-list" id="comments-POST_ID"></div>
        <form class="comment-form" data-post-id="POST_ID">
            <img class="avatar" style="width: 32px; height: 32px;" src="https://ui-avatars.com/api/?name=USUARIO&background=4F46E5&color=fff" alt="Avatar">
            <input type="text" class="comment-input" placeholder="Escribe un comentario..." required>
        </form>
    </div>
`;

/**
 * Burbuja de un comentario. Se inyecta en #comments-{postId}.
 *
 * TODO: AUTOR → comment.user.username (2 lugares)   TEXTO → comment.text
 */
export const commentTemplate = (comment) => `
    <div class="comment">
        <img class="avatar" style="width: 32px; height: 32px;" src="https://ui-avatars.com/api/?name=AUTOR&background=random&color=fff" alt="Avatar">
        <div class="comment-bubble">
            <div class="comment-user">AUTOR</div>
            <div class="comment-text">TEXTO</div>
        </div>
    </div>
`;
