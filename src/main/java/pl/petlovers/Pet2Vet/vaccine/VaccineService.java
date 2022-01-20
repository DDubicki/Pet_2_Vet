package pl.petlovers.Pet2Vet.vaccine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.VaccineNotFoundException;

import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetRepository;
import pl.petlovers.Pet2Vet.pet.controller.PetDTO;
import pl.petlovers.Pet2Vet.vaccine.controller.VaccineDTO;

import java.util.List;

@Slf4j
@Service
public class VaccineService {
  private final VaccineRepository vaccineRepository;
  private final PetRepository petRepository;

  static final String FETCHING_VACCINE = "Fetching vaccine with id = ";

  @Autowired
  public VaccineService(VaccineRepository vaccineRepository, PetRepository petRepository) {
    this.vaccineRepository = vaccineRepository;
    this.petRepository = petRepository;
  }

  public List<Vaccine> getAll() {
    log.info("Fetching all vaccines");

    return vaccineRepository.findAll();
  }

  public Vaccine get(long vaccineId) {
    log.info(FETCHING_VACCINE + vaccineId);

    return vaccineRepository.findById(vaccineId).orElseThrow(() -> new VaccineNotFoundException(vaccineId));
  }

  public Vaccine create(Vaccine vaccine) {
    log.info("Creating " + vaccine.toString());
    vaccineRepository.save(vaccine);

    return vaccine;
  }

  public Vaccine update(long vaccineId, VaccineDTO vaccineNewData) {
    log.info(FETCHING_VACCINE + vaccineId);
    Vaccine vaccineFromDB = vaccineRepository.getById(vaccineId);
    log.info("Updating of " + vaccineFromDB + " to " + vaccineNewData.toString());
    vaccineFromDB.modify(vaccineNewData);

    return vaccineRepository.save(vaccineFromDB);
  }

  public void delete(long vaccineId) {
    Vaccine vaccine = get(vaccineId);
    log.info("Deleting vaccine");

    vaccineRepository.delete(vaccine);
  }

  public List<Vaccine> getPetVaccines(long petId) {
    Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));

    return pet.getVaccines();
  }

  public Vaccine getPetVaccine(long petId, long vaccineId) {
    Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    List<Vaccine> vaccines = pet.getVaccines();

    return findVaccineInList(vaccineId, vaccines);
  }

  public Vaccine createVaccineInPet(long petId, Vaccine vaccine) {
    Pet petFromRepository = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    List<Vaccine> petVaccines = petFromRepository.getVaccines();
    petVaccines.add(vaccine);
    petFromRepository.setVaccines(petVaccines);
    petFromRepository.modify(petFromRepository);
    petRepository.save(petFromRepository);

    return vaccine;
  }

  @Transactional
  public Vaccine updatePetVaccine(long petId, long vaccineId, Vaccine vaccineData) {
    Pet petFromRepository = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    final List<Vaccine> vaccines = petFromRepository.getVaccines();
    updateVaccineIfExistOnList(vaccineId, vaccineData, vaccines);
    petFromRepository.setVaccines(vaccines);

    petRepository.save(petFromRepository);
    update(vaccineId, VaccineDTO.of(vaccineData));

    return vaccineData;
  }

  @Transactional
  public void deletePetVaccine(long petId, long vaccineId) {
    Pet petFromRepository = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    final List<Vaccine> newVaccines = petFromRepository.getVaccines();

    deleteVaccineIfExistOnList(vaccineId, newVaccines);
    petFromRepository.setVaccines(newVaccines);
    petFromRepository.modify(petFromRepository);
    delete(vaccineId);
  }

  private void deleteVaccineIfExistOnList(long vaccineId, List<Vaccine> newVaccines) {
    for (int i = 0; i < newVaccines.size(); i++) {
      if (newVaccines.get(i).getId() == vaccineId) {
        newVaccines.remove(i);

        return;
      }
    }

    throw new VaccineNotFoundException(vaccineId);
  }

  private Vaccine findVaccineInList(long vaccineId, List<Vaccine> vaccines) {
    for (Vaccine vaccine : vaccines) {
      if (vaccine.getId() == vaccineId) {

        return vaccine;
      }
    }

    throw new VaccineNotFoundException(vaccineId);
  }

  private void updateVaccineIfExistOnList(long vaccineId, Vaccine vaccine, List<Vaccine> newVaccines) {
    for (int i = 0; i < newVaccines.size(); i++) {
      if (newVaccines.get(i).getId() == vaccineId) {
        vaccine.setId(newVaccines.get(i).getId());
        newVaccines.set(i, vaccine);

        return;
      }
    }

    throw new VaccineNotFoundException(vaccineId);
  }
}
