package pl.petlovers.Pet2Vet.specie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.specie.PetSpecieService;

import java.util.List;

@RestController
@RequestMapping("/species")
public class SpecieController {

  private final PetSpecieService petSpecieService;

  @Autowired
  public SpecieController(PetSpecieService petSpecieService) {
    this.petSpecieService = petSpecieService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<PetSpecieDTO> get() {
    return petSpecieService.getAll()
        .stream()
        .map(PetSpecieDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{specieId}")
  public PetSpecieDTO get(@PathVariable long specieId) {
    return PetSpecieDTO.of(petSpecieService.get(specieId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PetSpecieDTO create(@RequestBody PetSpecieDTO petSpecieDTO) {
    return PetSpecieDTO.of(petSpecieService.create(petSpecieDTO.toPetSpecie()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/{specieId}")
  public PetSpecieDTO update(@PathVariable long specieId, @RequestBody PetSpecieDTO petSpecieNewDataDTO) {
    return PetSpecieDTO.of(petSpecieService.update(specieId, petSpecieNewDataDTO.toPetSpecie()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{specieId}")
  public void cancel(@PathVariable long specieId) {
    petSpecieService.delete(specieId);
  }

}
