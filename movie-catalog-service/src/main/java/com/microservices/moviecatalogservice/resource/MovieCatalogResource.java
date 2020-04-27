package com.microservices.moviecatalogservice.resource;

import com.microservices.moviecatalogservice.model.CatalogItem;
import com.microservices.moviecatalogservice.model.UserRating;
import com.microservices.moviecatalogservice.service.MovieInfo;
import com.microservices.moviecatalogservice.service.UserRatingInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final MovieInfo movieInfo;
    private final UserRatingInfo userRatingInfo;

    public MovieCatalogResource(MovieInfo movieInfo, UserRatingInfo userRatingInfo) {
        this.movieInfo = movieInfo;
        this.userRatingInfo = userRatingInfo;
    }

    @GetMapping("/{userId}")
    // @HystrixCommand(fallbackMethod = "getFallbackCatalog") -- this will call the fallback if any of the service calls fails
    public List<CatalogItem> getCatalog(@PathVariable final String userId) {

//        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user" + userId, UserRating.class);
        UserRating userRating = userRatingInfo.getUserRating(userId);
        return userRating.getRatings().stream()
                .map(movieInfo::getCatalogItem)
                .collect(Collectors.toList());
    }

//    public List<CatalogItem> getFallbackCatalog(@PathVariable final String userId) {
//        return Arrays.asList(new CatalogItem("No movie", "", 0));
//    }

}


/*
    Movie movie = webClientBuilder.build()
        .get()
        .uri("http://localhost:8082/movies/" + rating.getMovieId())
        .retrieve()
        .bodyToMono(Movie.class)
        .block();
* */