package co.develhope.crud.controllers;

import co.develhope.crud.entities.Car;
import co.develhope.crud.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    // Create a car
    @PostMapping("/addCar")
    public Car create(@RequestBody Car car){
        return carRepository.saveAndFlush(car);
    }

    // Read one - List all cars
    @GetMapping("/getAllCars")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    // Read all - Single car
    @GetMapping("/getCarById/{id}")
    public Optional<Car> getCarById(@PathVariable Long id){
        if(carRepository.existsById(id)){
        return carRepository.findById(id);
        }else{
            return null;
        }
    }

    // Update
    @PutMapping("/putCar/{id}")
    public Car updateCar(@PathVariable Long id, @RequestParam Car car){
        if(carRepository.existsById(id)){
            car.setId(id);
            carRepository.save(car);
            return car;
        }else{
            return null;
        }
    }

    // delete a specific car
    @DeleteMapping("/deleteCar/{id}")
    public String deleteById(@PathVariable Long id, HttpServletResponse response){
        if(carRepository.existsById(id)){
            carRepository.deleteById(id);
            return "The car " + id + " was removed";
        }else{
            try {
                response.sendError(409, "Conflict");
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    // delete all
    @DeleteMapping("/deleteAll")
    public String deleteAllCars(){
        carRepository.deleteAll();
        return "All cars have been removed";
    }

}
