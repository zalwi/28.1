package pl.zalwi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.data.Task;
import pl.zalwi.data.TaskForm;
import pl.zalwi.service.TaskService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String main() {
        return "home";
    }

    @GetMapping("/list")
    public String listTransactions(@RequestParam(name = "finished") Optional<Boolean> isFinished, Model model) {
        isFinished.ifPresentOrElse(
                (sortByStatusIsFinished) -> {
                    model.addAttribute("tasks", taskService.findAllByStatus(sortByStatusIsFinished));
                    if (sortByStatusIsFinished) {
                        model.addAttribute("listDescription", "Zakończone zadania");
                    } else {
                        model.addAttribute("listDescription", "Zadania bieżące");
                    }
                },
                () -> {
                    model.addAttribute("tasks", taskService.findAll());
                    model.addAttribute("listDescription", "Wszystkie zadania");
                }
        );
        return "list";
    }

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("actionDescription", "Dodawanie nowego zadania");
        model.addAttribute("action", "Dodaj");
        model.addAttribute("actionLink", "add");
        model.addAttribute("isBlocked", false);
        model.addAttribute("isNew", true);
        return "form";
    }

    @GetMapping("/update")
    public String updateTask(@RequestParam(name = "id") Long id, Model model) {
        Optional<Task> optionalTask = taskService.findOneById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
        } else {
            return "err";
        }
        model.addAttribute("actionDescription", "Modyfikowanie zaddania");
        model.addAttribute("action", "Modyfikuj");
        model.addAttribute("actionLink", "modify");
        model.addAttribute("isBlocked", false);
        model.addAttribute("isBlockedEndDate", false);
        return "form";
    }

    @GetMapping("/remove")
    public String removeTask(@RequestParam(name = "id") Long id, Model model) {
        Optional<Task> optionalTask = taskService.findOneById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
        } else {
            return "err";
        }
        model.addAttribute("actionDescription", "Usuwanie zadania");
        model.addAttribute("action", "Usuń");
        model.addAttribute("actionLink", "delete");
        model.addAttribute("isBlocked", true);
        model.addAttribute("isBlockedEndDate", true);
        return "form";
    }

    @GetMapping("/endNow")
    public String endNowTask(@RequestParam(name = "id") Long id, Model model) {
        Optional<Task> optionalTask = taskService.findOneById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String correctSqlFormatDateTime = now.format(formatter);
            task.setEndDate(LocalDateTime.parse(correctSqlFormatDateTime, formatter));
            task.setFinished(true);
            System.out.println(task);
            model.addAttribute("task", task);
        } else {
            return "err";
        }
        model.addAttribute("actionDescription", "Kończenie zadania");
        model.addAttribute("action", "Zakończ");
        model.addAttribute("actionLink", "modifyEndTimeToNow");
        model.addAttribute("isBlocked", true);
        model.addAttribute("isBlockedEndDate", true);

        return "form";
    }

    @PostMapping("/add")
    public String addTask(TaskForm taskData){
        taskService.save(taskData.convertToTask());
        return "redirect:/";
    }

    @PostMapping("/modify")
    public String modifyTask(TaskForm taskData) {
        taskService.update(taskData.convertToTask());
        return "redirect:/list";
    }

    @PostMapping("/modifyEndTimeToNow")
    public String modifyEndTimeToNowForTask(TaskForm taskData) {
        taskService.update(taskData.convertToTask());
        return "redirect:/list?finished=true";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id) {
        taskService.delete(id);
        return "redirect:/list";
    }
}
