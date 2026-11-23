const list = document.querySelector("ul");

export const render = (tasks) => {
    list.innerHTML = "";
    tasks.forEach(task => {
        const li = document.createElement("li");
        li.className = `task-item ${task.completed ? `completed` : ``}`;
        li.dataset.id = task.id;
        li.innerHTML = ` 
        <span>${task.text}</span>
        <div class="actions">
            <button class = "btn-edit">e</button>
            <button class = "btn-delete">x</button>
        </div>`
        list.appendChild(li);
    })
}