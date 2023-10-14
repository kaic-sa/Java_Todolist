package br.com.kaicsantos.todolist.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rota1")
public class NovaController {
    @GetMapping("/")
    public String Mensagem (){
    return "working!";
    }
}
