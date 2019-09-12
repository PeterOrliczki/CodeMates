package com.codecool.controller;

import com.codecool.dto.CartIdDto;
import com.codecool.dto.CartItemDto;
import com.codecool.dto.JobsDto;
import com.codecool.entity.CartItems;
import com.codecool.entity.Carts;
import com.codecool.entity.Orders;
import com.codecool.entity.Users;
import com.codecool.repository.CartRepository;
import com.codecool.repository.OrdersRepository;
import com.codecool.repository.UserRepository;
import com.codecool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CourierController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping(path = "/courier/getMyCurrentJobs")
    public List<Orders> getMyCurrentJobs() {
        Users user = userRepository.findByName(userService.currentUser());
        return ordersRepository.findAllByCourierId(user.getId());
    }

    @GetMapping(path = "/courier/getAllJobs")
    public List<JobsDto> getAllJobs(){
        List<Carts> allByCheckedOutAndPickedup = cartRepository.findAllByCheckedOutAndPickedup(true, false);
        List<JobsDto> jobsDtos = new ArrayList<>();
        int quantity = 0;
        for (Carts c : allByCheckedOutAndPickedup){
            List<CartItems> cartItems = c.getCartItems();
            for (CartItems ci : cartItems){
                quantity += ci.getQuantity();
            }
        }
        for (Carts c : allByCheckedOutAndPickedup){
            jobsDtos.add(new JobsDto(c.getId(), c.getUser().getName(), c.getUser().getLocation(), c.getUser().getAddress(), quantity, c.getCheckout_date()));
        }
        return jobsDtos;
    }

    @PostMapping(path = "/courier/pickUpJob")
    @ResponseBody
    void pickUpJob(@RequestBody CartIdDto cartId){
        Carts byId = cartRepository.findById(cartId.getCartId());
        byId.setPickedup(true);
        cartRepository.save(byId);
    }

}
