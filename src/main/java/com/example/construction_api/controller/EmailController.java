package com.example.construction_api.controller;

import com.example.construction_api.service.email.EmailSenderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {


    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }


    @GetMapping("/send")
    public void sendEmail(@RequestParam(value = "to") String to,
                                    @RequestParam(value = "email") String email) {


         emailSenderService.send(to, email);


    }


}
