package th.go.rd.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class CustomerController {

    @RequestMapping("/customer")
    public String getCustomerPage(Model model) {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1,"Peter","1234"));
        customers.add(new Customer(2,"Nancy","2345"));
        customers.add(new Customer(3,"Rick","3456"));

        model.addAttribute("allCustomers", customers);
        return "customer";  // customer.html
    }
}
