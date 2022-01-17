package pl.petlovers.Pet2Vet.file.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.petlovers.Pet2Vet.file.File;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

  private Long id;
  private String name;
  private String url;
  private Long size;
  private String contentType;
  private LocalDateTime created;
  private LocalDateTime modified;

  public static FileDTO of(File file) {
    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/download/")
      .path(String.valueOf((file.getId())))
      .toUriString();

    return FileDTO.builder()
      .id(file.getId())
      .name(file.getName())
      .url(downloadURL)
      .created(file.getCreated())
      .modified(file.getModified())
      .build();
  }

  public File toFile() {
    return File.builder()
      .id(id)
      .name(name)
      .url(url)
      .created(created)
      .modified(modified)
      .build();
  }
}
