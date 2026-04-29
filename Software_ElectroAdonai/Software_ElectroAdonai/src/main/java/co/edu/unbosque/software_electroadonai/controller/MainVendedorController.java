package co.edu.unbosque.software_electroadonai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mainVendedor")
public class MainVendedorController {

    @GetMapping("/mainVendedor")
    public String main() {
        return "mainVendedor";
    }

}
