package com.udacity.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.udacity.graphql.entity.Dog;
import com.udacity.graphql.exception.BreedNotFoundException;
import com.udacity.graphql.exception.DogNotFoundException;
import com.udacity.graphql.repository.DogRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Mutation implements GraphQLMutationResolver {

    private DogRepository dogRepository;

    public Mutation(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Boolean deleteDogBreed(String breed) {
        Collection<Dog> allDogs = (Collection<Dog>) dogRepository.findAll();
        Optional<Dog> optionalDog = allDogs.stream().filter(d -> d.getBreed().equals(breed)).findFirst();
        if(optionalDog.isPresent()) {
            dogRepository.delete(optionalDog.get());
            return true;
        }
        throw new BreedNotFoundException("Dog not found", breed);
    }

    public Dog updateDogName(String newName, Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            dog.setName(newName);
            dogRepository.save(dog);
            return dog;
        }
        throw new DogNotFoundException("Dog not found", id);
    }
}
