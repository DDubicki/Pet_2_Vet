package pl.petlovers.Pet2Vet.pet.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.meal.Meal;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.vaccine.Vaccine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

  private String name;
  private String sex;
  private LocalDate birthday;
//  private PetSpecie specieObject;
//  private List <Meal> meals;
//  private List <Vaccine> vaccines;
//  private List<Note> notes;

  private String specie;
  private List <Long> mealsIDs;
  private List <Long> vaccinesIDs;
  private List <Long> notesIDs;

  public static PetDTO of(Pet pet) {
    return PetDTO.builder()
        .name(pet.getName())
        .sex(pet.getSex())
        .birthday(pet.getBirthday())
        .specie(pet.getSpecie().getName())
        .vaccinesIDs(pet.getVaccines().stream().map(Vaccine::getId).toList())
        .mealsIDs(pet.getMeals().stream().map(Meal::getId).toList())
        .notesIDs(pet.getNotes().stream().map(Note::getId).toList())
        .build();
  }

  public Pet toPet() {
    return Pet.builder()
        .name(name)
        .sex(sex)
        .birthday(birthday)
//        todo jak zmienić te IDiki na obiekty
//        .specie(specieObject)
//        .vaccines(vaccines)
//        .meals(meals)
//        .notes(notes)
        .build();
  }

}