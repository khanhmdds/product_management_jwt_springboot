package com.example.ajax_boot_c08.service;

import com.example.ajax_boot_c08.model.Customer;
import com.example.ajax_boot_c08.model.CustomerPrinciple;
//import com.example.ajax_boot_c08.model.User;
//import com.example.ajax_boot_c08.model.UserPrinciple;
import com.example.ajax_boot_c08.repository.ICustomerRepository;
//import com.example.ajax_boot_c08.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService{
    @Autowired
    private ICustomerRepository iCustomerRepository;

    public List<Customer> findAll() {
        return iCustomerRepository.findAll();
    }

    public Page<Customer> findAllPage(Pageable pageable) {
        return iCustomerRepository.findAll(pageable);
    }

    public Customer save(Customer customer) {
        return iCustomerRepository.save(customer);
    }

    public void delete(Long id) {
        iCustomerRepository.deleteById(id);
    }

    public Customer findById(Long id) {
        return iCustomerRepository.findById(id).get();
    }

    public List<Customer> findAllByName(String name) {
        return iCustomerRepository.findAllByNameContaining(name);
    }

    public Customer findAllByUsernameAndEmail(String username, String email) {
        return iCustomerRepository.findAllByUsernameAndEmail(username, email);
    }

    public Customer findAllByUsername(String username) {
        return iCustomerRepository.findAllByUsername(username);
    }

    public Customer findAllByEmail(String email) {
        return iCustomerRepository.findAllByEmail(email);
    }

    @Transactional
    public void changePassword(Long id, String password) {
        Customer userAcc = iCustomerRepository.findById(id).get();
        if (userAcc != null) {
            userAcc.setPassword(password);
            iCustomerRepository.save(userAcc);
        } else {
            System.out.println("Fail");
        }
    }


    public Optional<Customer> findByUsername(String username) {
        return iCustomerRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> userOptional = iCustomerRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return CustomerPrinciple.build(userOptional.get());
    }

}
