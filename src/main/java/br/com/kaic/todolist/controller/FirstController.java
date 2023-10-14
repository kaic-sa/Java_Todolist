package br.com.kaic.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rota")
public class FirstController {
    @GetMapping("/")
    public String mensagem(){
        return "working";
    }
}