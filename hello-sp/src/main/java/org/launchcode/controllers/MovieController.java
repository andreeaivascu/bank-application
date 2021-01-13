package org.launchcode.controllers;
import org.launchcode.data.MovieCategoryRepository;
import org.launchcode.data.MovieRepository;
import org.launchcode.data.TagRepository;
import org.launchcode.models.*;
import org.launchcode.models.dto.MovieTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieCategoryRepository movieCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllMovies(@RequestParam(required = false) Integer categoryId, Model model){
        if(categoryId == null) {
            model.addAttribute("title", "All movies!");
            model.addAttribute("movies", movieRepository.findAll());
        } else{
            Optional<MovieCategory> result = movieCategoryRepository.findById(categoryId);
            if(result.isEmpty()){
                model.addAttribute("title", "Invalid category: " + categoryId);
            }else{
                MovieCategory category = result.get();
                model.addAttribute("title", "Movies in category: " + category.getName());
                model.addAttribute("movies", category.getMovies());
            }
        }
        return "movies/index";

    }

    @GetMapping("create")
    public String renderCreateMovieForm(Model model){
        model.addAttribute("title", "Create Movie");
        model.addAttribute(new Movie());
        model.addAttribute("categories", movieCategoryRepository.findAll());
        return "movies/create";
    }

    @PostMapping("create")
    public String createMovie(@ModelAttribute @Valid Movie newMovie, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Movie");
            return "movies/create";
        }
        movieRepository.save(newMovie);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteMovieForm(Model model){
        model.addAttribute("title", "Delete Movies");
        model.addAttribute("movies",movieRepository.findAll());
        return "movies/delete";
    }

    @PostMapping("delete")
    public String processDeleteMovieForm(@RequestParam(required = false) int[] movieIds){

        if(movieIds != null) {
            for (int id : movieIds) {
                movieRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("detail")
    public String displayMovieDetails(@RequestParam Integer movieId, Model model){

        Optional<Movie> result = movieRepository.findById(movieId);

        if(result.isEmpty()){
            model.addAttribute("title" + "Invalid movie Id : " + movieId);
        }else{
            Movie movie = result.get();
            model.addAttribute("title", movie.getName() + " details ");
            model.addAttribute("movie", movie);
            model.addAttribute("tags", movie.getTags());
        }
        return "movies/detail";
    }
    
    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer movieId, Model model){
        Optional<Movie> result = movieRepository.findById(movieId);
        Movie movie = result.get();

        model.addAttribute("title", "Add tag to: " + movie.getName());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("movie", movie);
        MovieTagDTO movieTag = new MovieTagDTO();
        movieTag.setMovie(movie);
        model.addAttribute("movieTag", movieTag);

        return "movies/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid MovieTagDTO movieTag, Errors errors, Model model){
        if(!errors.hasErrors()){
            Movie movie = movieTag.getMovie();
            Tag tag = movieTag.getTag();

            if( !movie.getTags().contains(tag)){
                movie.addTag(tag);
                movieRepository.save(movie);
            }

            return "redirect:detail?movieId=" + movie.getId();
        }

        return "movies/add-tag";
    }


    @GetMapping("update")
    public String displayUpdateMovieForm(Model model){
        model.addAttribute("title", "Update movie");

        return "movies/update";
    }

    @PostMapping("update")
    public String processUpdateMovieForm(@RequestParam(required = false) int movieId,@RequestParam(required = false) String name,@RequestParam(required = false) String category,@RequestParam(required = false) String description,@RequestParam(required = false) String email){
        ArrayList<Movie> movies=(ArrayList<Movie>)movieRepository.findAll();

        if(movies != null)
        {

            for ( int i=0;i<movies.size();i++){
                System.out.println(movies.get(i)+" "+movies.get(i).getName()+" "+movies.get(i).getId());

                if(movies.get(i).getId()==movieId) {
                    System.out.println(movies.get(i));

                    MovieCategory movieCategory=new MovieCategory();
                    movieCategory.setName(category);
                    

                    MovieDetails movieDetails=new MovieDetails();
                    movieDetails.setContactEmail(email);
                    movieDetails.setDescription(description);

                    if(name!=""){
                        movies.get(i).setName(name);}
                   /* if(category!=""){
                        movies.get(i).setCategory(movieCategory);}*/
                    if(email!="" || description!=""){
                        if(email=="")
                        {
                            movieDetails.setContactEmail(movies.get(i).getMovieDetails().getContactEmail());
                        }
                        if(description=="")
                        {
                            movieDetails.setDescription(movies.get(i).getMovieDetails().getDescription());
                        }
                        movies.get(i).setMovieDetails(movieDetails);}
                    movieRepository.save(movies.get(i));
                }
            }
        }

        return "redirect:";
    }


}
