import * as state from './state.js'
import * as ui from './ui.js'

document.addEventListener('DOMContentLoaded', async () => {
    const input = document.getElementById("taskInput");
    const addBtn = document.querySelector(".btn-add");
    const lista = document.getElementById("task-list");
    const tasks = await state.fetchTask();

    ui.render(tasks);
     addBtn.addEventListener("click", async () => {
        if (input.value.trim() == "") return;
        await state.addTask(input.value.trim())
        input.value = '';
        ui.render(state.tasks);
     });

    input.addEventListener("keydown", (e) => {
        if (e.key === "Enter") addBtn.click();
    })
    lista.addEventListener('click', async (e) => {
        const id = parseInt(e.target.closest('li').dataset.id)

        if(e.target.classList.contains('btn-delete')){
            if (confirm('¿Estás seguro de eliminar el elemento?')){
                await state.deleteTask(id)
            }
        } else if(e.target.classList.contains('btn-edit')){
            const task = state.tasks.find(t => t.id === id);
            const newText = prompt('Editar tarea', task.text);
            if(newText) await state.editTask(id, newText);
        } else if (e.target.tagName !== 'button'){
            await state.toggleTask(id);
        }
        ui.render(state.tasks);
    })
})

