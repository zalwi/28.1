package pl.zalwi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.service.TaskService;

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
}
