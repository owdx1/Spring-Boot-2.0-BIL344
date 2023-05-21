package com.example.demo.model;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;




@CrossOrigin // for connecting frontend with the backend. ables us to fetch data throughout different ports.
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView mainPage(){
        ModelAndView mav = new ModelAndView("main-page");

        return mav;
    }
    @GetMapping("/addNewUser")
    public ModelAndView addNewUser(){
        ModelAndView mav = new ModelAndView("add-new-user");
        return mav;
    }

    @GetMapping("/allUsers")
    public ModelAndView getUsers(@RequestParam(value = "filterType", defaultValue = "none") String filterType) {
        ModelAndView mav = new ModelAndView("all-users");
        List<BmiUser> filteredUsers;

        if ("name".equalsIgnoreCase(filterType)) {
            filteredUsers = userService.getUsersByName();
        } else if ("id".equalsIgnoreCase(filterType)) {
            filteredUsers = userService.getUsersByAscedingId();
        } else {
            filteredUsers = userService.getUsers();
        }

        mav.addObject("filteredUsers", filteredUsers);
        return mav;
    }


    @GetMapping("/normals")
    public List<BmiUser> getDataFromNView(){
        return userService.getDataFromNView();
    }



    @GetMapping("/{id}/edit")
    public ModelAndView editUser(@PathVariable("id") Integer id){
        ModelAndView mav = new ModelAndView("edit-user");
        BmiUser currentUser = userService.getUserById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        mav.addObject("user" , currentUser);


        return mav;
    }

    @GetMapping(path = "/{id}")
    public ModelAndView getUserById(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView("single-user");
        BmiUser user = userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("{id}/update")
    public void updateUser(@ModelAttribute BmiUser updatedUser, HttpServletResponse response) throws IOException {
        Integer id = updatedUser.getId();

        userService.updateUser(updatedUser, id);
        response.sendRedirect("/users/" + id);
    }

    @PostMapping("/addNewUser")
    public void registerNewBmiUser(@RequestParam(name = "firstname") String firstname,
                                   @RequestParam(name="lastname")String lastname,
                                   @RequestParam(name="weight")float weight,
                                   @RequestParam(name="height")float height,
                                   HttpServletResponse response) throws IOException {

        BmiUser user = new BmiUser();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setHeight(height);
        user.setWeight(weight);

        try {
            BmiUser savedUser = userService.addNewBmiUser(user);
            response.sendRedirect("/users/" + savedUser.getId());
        } catch (Exception e) {

            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        userService.deleteBmiUser(id);
        response.sendRedirect("/users");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/testConnection")
    public String testConnection(){
        try{
            String dbName = jdbcTemplate.queryForObject("SELECT current_database()", String.class);
            return "Connected to: " + dbName ;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }


}
