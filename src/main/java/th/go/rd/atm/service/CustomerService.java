package th.go.rd.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.go.rd.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private ArrayList<Customer> customerList;

    @PostConstruct
    public void postContruct() {
        customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer) {
        // .... hash pin ....
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        customerList.add(customer);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(this.customerList);
    }

    public Customer findCustomer(int id) {
        for (Customer c : customerList) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public Customer checkPin(Customer inputCustomer) {
        // 1. หา customer ที่มี id ตรงกับ inputCustomer
        Customer storedCustomer = findCustomer(inputCustomer.getId());
        System.out.println(storedCustomer);

        // 2. ถ้าเจอ ตรวจสอบ pin และถ้า pin ตรง คืนค่า customer นี้
        if (storedCustomer != null) {
            String storedPin = storedCustomer.getPin();
            System.out.println(storedPin);
            if (BCrypt.checkpw(inputCustomer.getPin(), storedPin)) {
                System.out.println("matched");
                return storedCustomer;
            } else System.out.println("not matched");
        }
        // 3. ถ้า id หรือ pin ไม่ตรง คืนค่าเป็น null
        return null;
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }
}
