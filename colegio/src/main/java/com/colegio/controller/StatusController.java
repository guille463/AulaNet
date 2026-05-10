package com.colegio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para verificar el estado del servidor.
 *
 * @author Guillermo Rafael Jimenez Munoz
 * @version 2.0
 */
@RestController
@RequestMapping("/api/v1")
public class StatusController {

    @GetMapping("/status")
    public String status() {
        return "OK";
    }
}
