package com.example.OperazioniCRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/cars")
public class Controller {

    @Autowired
    private CarRepository carRepository;

    @PostMapping("/newCar") //crea nuova Car
    public Car newCar (@RequestBody Car car ){
        carRepository.save(car);
        return car;
    }

    @GetMapping("/all")        // restituisce la lista di tutte le Car
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping("/car/{id}")
    // restituisce una singola Car - se id non è presente in db (usa existsById()), restituisce Car vuota
    public Car getCarById(@PathVariable Long id) {
        Boolean exist = carRepository.existsById(id);
        if (exist) {
            return carRepository.findById(id).orElse(null);
        }
        return new Car();
    }

    @PutMapping("/put/{id}")
    public Car updateCar(@PathVariable Long id, @RequestParam String type) {
        Boolean exist = carRepository.existsById(id);
        if (exist) {
            Car car = carRepository.findById(id).orElse(null);       //   aggiorna type della Car specifica, identificata da id e passando query param -
            //      se id non è presente in db (usa existsById()), restituisce Car vuota
            car.setType(type);
            carRepository.save(car);
            return car;
        }
        return new Car();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOneCar(@PathVariable Long id) {         // cancella la Car specifica - se non presente, la risposta deve avere Conflict HTTP status
        Boolean exist = carRepository.existsById(id);
        if (exist) {
            carRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK); //ok equivale a 200
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/delete")             // cancella tutte le Cars in db
    public void deleteCar() {
        carRepository.deleteAll();
    }
}


    /*esegue le operazioni CRUD:
        crea nuova Car
        restituisce la lista di tutte le Car
        restituisce una singola Car - se id non è presente in db (usa existsById()),
        restituisce Car vuota
        aggiorna type della Car specifica, identificata da id e passando query param - se id non è presente in db (usa existsById()), restituisce Car vuota
        cancella la Car specifica - se non presente, la risposta deve avere Conflict HTTP status
        cancella tutte le Cars in db*/