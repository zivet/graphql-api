package com.udacity.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.udacity.graphql.entity.Dog;
import com.udacity.graphql.exception.DogNotFoundException;
import com.udacity.graphql.repository.DogRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {

    private DogRepository dogRepository;

    public Query(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Iterable<String> findDogBreeds() {
        Iterable<Dog> allDogs = dogRepository.findAll();
        List<String> allBreeds = new ArrayList<>();
        allDogs.forEach(d -> allBreeds.add(d.getBreed()));
        return allBreeds;
    }

    public Dog findDogBreedById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()) {
            return optionalDog.get();
        }
        else {
            throw new DogNotFoundException("Dog not found", id);
        }
    }

    public Iterable<String> findAllDogNames() {
        Iterable<Dog> allDogs = dogRepository.findAll();
        List<String> allBreeds = new ArrayList<>();
        allDogs.forEach(d -> allBreeds.add(d.getName()));
        return allBreeds;
    }

    public Iterable<Dog> findAllDogs() {
        return dogRepository.findAll();
    }
}
