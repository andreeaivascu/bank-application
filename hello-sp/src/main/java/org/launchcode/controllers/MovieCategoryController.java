package org.launchcode.controllers;

import org.launchcode.data.MovieCategoryRepository;
import org.launchcode.models.MovieCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class MovieCategoryController {

    @Autowired
    private MovieCategoryRepository movieCategoryRepository;

    @GetMapping("movieCategory")
    public String displayAllCategories(Model model){
        model.addAttribute("categories", movieCategoryRepository.findAll());
        model.addAttribute("title", "All categories");
        return "movieCategory/index";
    }

    @RequestMapping("movieCategory/create")
    public String displayCreateCategoryForm(Model model){
        model.addAttribute("title", "Create Category");
        model.addAttribute(new MovieCategory());
        return "movieCategory/create";
    }

    @PostMapping("movieCategory/create")
    public String processCreateCategoryForm(@ModelAttribute @Valid MovieCategory movieCategory, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Category"); //Create Movie
            return "movieCategory/create";
        }
        movieCategoryRepository.save(movieCategory);
        return "redirect:";
    }
}
