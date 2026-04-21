package com.example.restapi.controller;

import com.example.restapi.dto.UserDto;
import com.example.restapi.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    // why this public
    public UserController(UserService userServ){
        this.userService = userServ;
    }

    @GetMapping("/hello")
    public String greeting(){
        return userService.sayHi("Kamal");
    }

    // keep DI 2 @Authowired
    // @Autowired
    // private UserService userService;

    @GetMapping("/users/{id}")
    public String userById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteById(id);
    }

    // example 2: get value from url params "/hello?name=x"
    /*
    @GetMapping("/users")
    public String greeting(@RequestParam String name){
        return userService.sayHi(name);
    }
    */

    // PART 2: it's time to BODY DATA "POST"
    @PostMapping("/users")
    public String createUser(@RequestBody String name) {
        return "User created: " + name;
    }

    /* ### ERROR HERE
    @PostMapping("/users2")
    public String createUser(@RequestBody String name, int phone) {
        return "User created: " + name + phone;
    }
    */

    @PostMapping("/users2")
    public String createUser(@RequestBody UserDto userData) {
        return "User created: " + userData.getName() + userData.getPhone();
    }
}
