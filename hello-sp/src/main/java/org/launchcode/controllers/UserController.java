package org.launchcode.controllers;


import org.launchcode.data.UserRepository;
import org.launchcode.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {




    @Autowired
    private UserRepository userRepository;



    @GetMapping
    public String displayAllUsers(Model model){

            model.addAttribute("title", "All users!");
            model.addAttribute("users", userRepository.findAll());

        return "users/index";

    }

    @GetMapping("detail")
    public String displayUserDetails(@RequestParam Integer userId, Model model){

        Optional<User> result = userRepository.findById(userId);

        if(result.isEmpty()){
            model.addAttribute("title" + "Invalid user Id : " + userId);
        }else{
            User user = result.get();
            model.addAttribute("title", user.getName() + " details ");

            model.addAttribute("user", user);

        }
        return "users/detail";
    }


    @GetMapping("create")
    public String displayCreateUserForm(Model model){
        model.addAttribute("title", "Create User");
       model.addAttribute(new User());
        return "users/create";
    }


    @PostMapping("create")
    public String processCreateUserForm(@ModelAttribute @Valid User user, Errors errors, Model model)  {

        if(errors.hasErrors()){
            model.addAttribute("title", "Create user");
            model.addAttribute(user);
            return "users/create";
        }

        /* String password=user.getPassword();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
                System.out.println(hexString.toString());
                password=hexString.toString();

            }



        } catch (Exception ex) {
            System.out.println("error");
        }
       // System.out.println(hash.length);

        user.setPassword(password);*/
        //System.out.println(user.getPassword());
        userRepository.save(user);

        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteUserForm(Model model){
        model.addAttribute("title", "Delete user");
        model.addAttribute("users",userRepository.findAll());

        return "users/delete";
    }

    @PostMapping("delete")
    public String processDeleteUserForm(@RequestParam(required = false) int[] userIds){

        if(userIds != null) {
            for (int id : userIds) {
                userRepository.deleteById(id);

            }
        }
        return "redirect:";
    }


     @GetMapping("update")
    public String displayUpdateUserForm(Model model){
        model.addAttribute("title", "Update user");

        return "users/update";
    }

    @PostMapping("update")
    public String processUpdateUserForm(@RequestParam(required = false) int userId,@RequestParam(required = false) String email,@RequestParam(required = false) String telephone,@RequestParam(required = false) String address,@RequestParam(required = false) String password){
        ArrayList<User> users=(ArrayList<User>)userRepository.findAll();

        if(users != null)
            {

                for ( int i=0;i<users.size();i++){
                    System.out.println(users.get(i)+" "+users.get(i).getName()+" "+users.get(i).getId());

                    if(users.get(i).getId()==userId) {
                        System.out.println(users.get(i));
                        if(email!=""){
                            users.get(i).setEmail(email);}
                        if(telephone!=""){
                            users.get(i).setTelephone(telephone);}
                        if(address!=""){
                            users.get(i).setAddress(address);}
                        if(password!=""){
                            users.get(i).setPassword(password);}
                        userRepository.save(users.get(i));
                    }
                }
            }

        return "redirect:";
    }


}
