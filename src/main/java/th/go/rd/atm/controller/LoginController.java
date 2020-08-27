package th.go.rd.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.go.rd.atm.model.Customer;
import th.go.rd.atm.service.CustomerService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private CustomerService customerService;

    public LoginController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute Customer customer, Model model) {
        // 1. ตรวจสอบว่า id, pin ตรงกับข้อมูลลูกค้าใดหรือไม่
        Customer matchingCustomer = customerService.checkPin(customer);

        // 2. ถ้าตรง ให้ไปหน้า home และสวัสดีลูกค้า
        if (matchingCustomer != null) {
            model.addAttribute("greeting", "Welcome, " +
                                           matchingCustomer.getName());
        } else {
            // 3. ถ้าไม่ตรง ไปหน้า home แจ้งว่าไม่มีลูกค้า
            model.addAttribute("greeting", "Can't find customer");
        }
        return "home";
    }
}
