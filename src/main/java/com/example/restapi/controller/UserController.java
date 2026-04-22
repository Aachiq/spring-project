package com.example.restapi.controller;

import com.example.restapi.dto.UserDto;
import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // get Users from db
    @GetMapping("users-list")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    // practice from express familiar coding
    // app.get("/categories", (req, res) => res.json(["A","B","C"]) });
    @GetMapping("data-array")
    public List<String> getDataList(){
        List<String> dataList = Arrays.asList("A", "B", "C");
        return dataList;
        /*
            List<String> dataList;
            dataList = Arrays.asList("A", "B", "C");
            return dataList;
        */
    }
    // the example above works fine -> but it's fixed size array -< Now let's do dynamic Array

    @GetMapping("data-dynamic")
    public List<String> getModifiableListData(){
        List<String> modifiableList = new ArrayList<>();
        modifiableList.add("A");
        modifiableList.add("B");
        return modifiableList;
    }

    /* KEEP Notes
    List<String> categories = Arrays.asList("A", "B"); // old version fixed size
    List<String> categories = List.of("A", "B"); // // new version fixed size
    // both need wrapper of "ArrayList<>" to be modifiable
     List<String> categories = new ArrayList<>(Arrays.asList("A","B"));
     List<String> categories = new ArrayList<>(List.of("A","B"));
    */


}
