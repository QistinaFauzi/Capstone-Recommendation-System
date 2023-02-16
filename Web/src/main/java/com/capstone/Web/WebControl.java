package com.capstone.Web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class WebControl {

    @Autowired
    private RestTemplate restTemplate;

    //USER CONTROL//
    //Automatically direct user to login page
    @GetMapping("/")
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "index";
    }
    //Redirect to error page
    @GetMapping("/oops")
    public String error(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "oops";
    }

    //Redirect User to the homepage
    @GetMapping("/home")
    public String home(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entity = new HttpEntity<Movie>(headers);
        List<Movie> dataList = restTemplate.exchange("http://localhost:8083/allmovies", HttpMethod.GET, entity, List.class).getBody();
        model.addAttribute("dataList", dataList);
        return "homepage";
    }
    //Direct User to registration page after clicking the register link
    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    //Allow User to make a registration and return to login page if success
    @PostMapping("/register")
    public String registeraction(@ModelAttribute("user") User user, Model model){
        //check username exist
        String username = user.getUsername();
        HttpEntity<User> checkusername = new HttpEntity<User>(user);
        User find = restTemplate.exchange("http://localhost:8081/findbyname/"+username, HttpMethod.GET, checkusername, User.class).getBody();
        Optional<User> userdata = Optional.ofNullable(find);
        if(userdata.isPresent()){
            model.addAttribute("message", "Username already exist");
            return "register";
        }else {
            //check verify password
            String password = user.getPassword();
            HttpEntity<User> checkpassword = new HttpEntity<User>(user);
            User find2 = restTemplate.exchange("http://localhost:8081/findbyname/" + username, HttpMethod.GET, checkpassword, User.class).getBody();
            Optional<User> userdata2 = Optional.ofNullable(find2);
                if (!user.getPassword().equals(user.getVerifypassword())) {
                    model.addAttribute("message", "Please make sure the passwords are same");
                    return "register";
                }else {
                    //hashing password and save in the User Object
                    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
                    var enteredpassword = user.getPassword();
                    var encodedPassword = encoder.encode(enteredpassword);
                    user.setPassword(encodedPassword);
                    HttpEntity<User> entity = new HttpEntity<User>(user);
                    restTemplate.exchange("http://localhost:8081/register", HttpMethod.POST, entity, User.class).getBody();
                    return "index";
                }
        }
    }
    //Allow registered user to login as Admin/User
    //Use redirect view because homepage show movies set at /home
    @PostMapping("/login")
    public RedirectView login(@ModelAttribute("user") User user, Model model, HttpSession session) {
        String username = user.getUsername();
        String type = user.getType();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        //find user by name
        User find = restTemplate.exchange("http://localhost:8081/findbyname/" + username, HttpMethod.GET, entity, User.class).getBody();
        Optional<User> userdata = Optional.ofNullable(find);
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

        if (userdata.isPresent()) {

            if (encoder.matches(user.getPassword(), userdata.get().getPassword())) {
                //find user by type to determine their directed page for admin/user
                if (userdata.get().getType().equals("Admin")) {
                    model.addAttribute("message", "Welcome " + user.getUsername());
                    return new RedirectView("/backadmin");
                }
                else{
                    //create a session for name and id
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("userid", userdata.get().getUserid());
                    return new RedirectView("/home");
                }
            } else{
                model.addAttribute("invalid", "Make sure password is correct");
                return new RedirectView("/");
            }
        } else{
            model.addAttribute("invalid", "User does not exist!");
            return new RedirectView("/");
        }
    }
    //redirect user to index page after logout
    @GetMapping("/logout")
    public String logout(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "index";
    }

    //ADMIN CONTROL
    //Back link to return to admin dashboard
    @GetMapping("/backadmin")
    public String backadmin(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "adminpage";
    }

    //Allow admin to go to add new movie
    @GetMapping("/addmoviepage")
    public String addnewmovie(){
        return "addmovie";
    }

    //Admin add new movie
    @PostMapping("/addmovie")
    public String addnewMovie(@ModelAttribute("movie") Movie movie){
        HttpEntity<Movie> entity = new HttpEntity<Movie>(movie);
        restTemplate.exchange("http://localhost:8083/addmovie", HttpMethod.POST, entity, Movie.class).getBody();
        return "adminpage";
    }
    //List out all movies in database for admin
    @GetMapping("/viewallmovies")
    public String goviewmovies(Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entity = new HttpEntity<Movie>(headers);
        List<Movie> dataList = restTemplate.exchange("http://localhost:8083/allmovies", HttpMethod.GET, entity, List.class).getBody();
        model.addAttribute("dataList", dataList);
        return "viewallmovies";
    }

    //Direct Admin to add new User page
    @GetMapping("/adduser")
    public String adduserpage(){
        return "adduser";
    }

    //Allow admin to add new User
    @PostMapping("/adduseradmin")
    public String adminadduser(@ModelAttribute("user") User user, Model model){
        //check username exist
        String username = user.getUsername();
        HttpEntity<User> checkusername = new HttpEntity<User>(user);
        User find = restTemplate.exchange("http://localhost:8081/findbyname/"+username, HttpMethod.GET, checkusername, User.class).getBody();
        Optional<User> userdata = Optional.ofNullable(find);
        if(userdata.isPresent()){
            model.addAttribute("message", "Username already exist");
            return "adduser";
        }
        else {
            //check verify password
            String password = user.getPassword();
            HttpEntity<User> checkpassword = new HttpEntity<User>(user);
            User find2 = restTemplate.exchange("http://localhost:8081/findbyname/" + username, HttpMethod.GET, checkpassword, User.class).getBody();
            Optional<User> userdata2 = Optional.ofNullable(find2);
            if (!user.getPassword().equals(user.getVerifypassword())) {
                model.addAttribute("message", "Please make sure the passwords are same");
                return "adduser";
            } else {
                //hashing password
                Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
                var enteredpassword = user.getPassword();
                var encodedPassword = encoder.encode(enteredpassword);
                user.setPassword(encodedPassword);
                HttpEntity<User> entity = new HttpEntity<User>(user);
                restTemplate.exchange("http://localhost:8081/register", HttpMethod.POST, entity, User.class).getBody();

                return "adminpage";
            }
        }
    }

    //RECOMMEND CONTROL
    //return a list of recommended movies after click on a movie
    @GetMapping("/getrecommend")
    public String movieinfo(@RequestParam("movieid") int movieid, Model model){

        //get the movie by movieid c licked and set in a dataList
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Movie> entity = new HttpEntity<Movie>(headers);
        Movie dataList = restTemplate.exchange("http://localhost:8083/findbyid/"+movieid, HttpMethod.GET, entity, Movie.class).getBody();
        model.addAttribute("dataList", dataList);

        //retrieve movieid that satisfied the condition in the recommend microservice
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Recommend> entity2 = new HttpEntity<Recommend>(headers2);
        //don't use List.class. not compatible data type
        List<Recommend> dataid = restTemplate.exchange("http://localhost:8084/recommend/" + movieid, HttpMethod.GET, entity2, new ParameterizedTypeReference<List<Recommend>>() {}).getBody();
        System.out.println("The data id is working fine");
        System.out.println(dataid);

        //retrieve the movies of selected movieid above
        HttpHeaders headers3 = new HttpHeaders();
        headers3.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entity3 = new HttpEntity<Movie>(headers3);
        List<Movie> dataListmovies = new ArrayList<>();
        for (Recommend recommend : dataid) {
            int movieId = recommend.getMovieid();
            List<Movie> recommendedMovies = restTemplate.exchange("http://localhost:8083/findallmoviesbyid/" + movieId, HttpMethod.GET, entity3, new ParameterizedTypeReference<List<Movie>>() {}).getBody();
            dataListmovies.addAll(recommendedMovies);
        }
        model.addAttribute("dataListmovies", dataListmovies);
        return "recommendpage";

    }

    //RATING CONTROL
    //save rating
    @PostMapping("/addmovierating")
    public RedirectView addrate(@ModelAttribute("rating") Rating rating, Model model){
        int movieId = rating.getMovieid();//retrieve movie id
        int userId = rating.getUserid();//retrieve user Id
        int ratingId = rating.getRatingid();
        double userrating = rating.getRating();//get rating
        //enter all the detail into rating object
        rating = new Rating(ratingId, movieId, userrating, userId);

        HttpEntity<Rating> entity = new HttpEntity<Rating>(rating);
        restTemplate.exchange("http://localhost:8085/addrating", HttpMethod.POST, entity, Rating.class).getBody();

        //to retrieve the movies and set into a list using resttemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entitymovie = new HttpEntity<Movie>(headers);
        List<Movie> dataList = restTemplate.exchange("http://localhost:8083/allmovies", HttpMethod.GET, entitymovie, new ParameterizedTypeReference<List<Movie>>() {}).getBody();
        model.addAttribute("datalist", dataList);
        return new RedirectView("/home");
    }

    //FAVOURITE CONTROL
    //save favourite using userid, movieid
    @PostMapping("/addfavourite")
    public String addfav(@RequestParam("userid") int userid, @RequestParam("movieid") int movieid, @ModelAttribute("favourite") Favourite favourite, Model model) {

        //get the fav id
        int favid = favourite.getFavid();
        //insert all data into favourite
        favourite = new Favourite(favid, movieid, userid);
        //assign to an entity
        HttpEntity<Favourite> entity = new HttpEntity<Favourite>(favourite);
        //save favourite
        restTemplate.exchange("http://localhost:8086/savefavourite", HttpMethod.POST, entity, Favourite.class).getBody();

        //get favourite using userid
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); //set as array
        HttpEntity<Favourite> entityfav = new HttpEntity<Favourite>(headers);
        //get using API
        List<Favourite> favlist = restTemplate.exchange("http://localhost:8086/getfavsbyuser/" + userid, HttpMethod.GET, entityfav, new ParameterizedTypeReference<List<Favourite>>() {
        }).getBody();
        //System.out.println(favlist); //this works fine

        //add movie details from favourited into fav movie list to display
        HttpHeaders headersmovie = new HttpHeaders();
        headersmovie.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entitymovie = new HttpEntity<Movie>(headersmovie);
        List<Movie> dataListmovie = new ArrayList<>();
        //loop each movie in list to get by movie id
        for (Favourite favouritemovie : favlist) {
            int movieId = favouritemovie.getMovieid();
            List<Movie> favMovies = restTemplate.exchange("http://localhost:8083/findallmoviesbyid/" + movieId, HttpMethod.GET, entitymovie, new ParameterizedTypeReference<List<Movie>>() {
            }).getBody();
            dataListmovie.addAll(favMovies);
        }
        model.addAttribute("datalistmovie", dataListmovie);

        return "favourited";// redirect to favourite page
    }

    //view all favourited movies
    @GetMapping("/viewfavourites")
    public String viewallfavs(Model model, HttpSession session){
        //get favourite data for current user
        Integer userid = (Integer) session.getAttribute("userid");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Favourite> entity = new HttpEntity<Favourite>(headers);
        List<Favourite> favlist = restTemplate.exchange("http://localhost:8086/getfavsbyuser/" + userid, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Favourite>>() {
        }).getBody();

        //add movie details from favourited into fav movie list to display
        HttpHeaders headersmovie = new HttpHeaders();
        headersmovie.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entitymovie = new HttpEntity<Movie>(headersmovie);
        List<Movie> dataListmovie = new ArrayList<>();
        //loop each movie in list to get by movie id
        for (Favourite favouritemovie : favlist) {
            int movieId = favouritemovie.getMovieid();
            List<Movie> favMovies = restTemplate.exchange("http://localhost:8083/findallmoviesbyid/" + movieId, HttpMethod.GET, entitymovie, new ParameterizedTypeReference<List<Movie>>() {
            }).getBody();
            dataListmovie.addAll(favMovies);
        }
        model.addAttribute("datalistmovie", dataListmovie);

        return "favourited";// redirect to favourite page
    }
}

