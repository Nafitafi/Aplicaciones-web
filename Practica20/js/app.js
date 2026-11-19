import * as state from './state.js'
import * as ui from './ui.js'

document.addEventListener('DOMContentLoaded', async () => {
    const input = document.getElementById("taskInput");
    const addBtn = document.querySelector(".btn-add");

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

})

