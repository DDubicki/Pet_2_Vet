package pl.petlovers.Pet2Vet.specie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.AppUserNotFoundException;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetSpecieNotFoundException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.specie.controller.PetSpecieDTO;

import java.util.*;

@Slf4j
@Service
public class PetSpecieService {

  private final PetSpecieRepository petSpecieRepository;

  public PetSpecieService(PetSpecieRepository petSpecieRepository) {
    this.petSpecieRepository = petSpecieRepository;
  }

  public List<PetSpecie> getAll() {
    log.info("Fetching all pets species");

    return petSpecieRepository.findAll();
  }

  public PetSpecie create(PetSpecie petSpecie) {
    log.info("Creating " + petSpecie.toString());
    petSpecieRepository.save(petSpecie);

    return petSpecie;
  }

  public PetSpecie update(long specieId, PetSpecie petSpecieNewData) {
    log.info("Fetching pet specie with id = " + specieId);
    PetSpecie petSpecieFromDB = petSpecieRepository.getById(specieId);
    log.info("Updating of " + petSpecieFromDB + " to " + petSpecieNewData.toString());
    petSpecieFromDB.modify(PetSpecieDTO.of(petSpecieNewData));

    return petSpecieRepository.save(petSpecieFromDB);
  }

  public void delete(long specieId) {
    petSpecieRepository.delete(get(specieId));
    log.info("Deleting pet's specie");
  }

  public PetSpecie get(long kindId) {
    log.info("Fetching pet's specie with id = " + kindId);

    return petSpecieRepository.findById(kindId).orElseThrow(() -> new PetSpecieNotFoundException(kindId));
  }

}
