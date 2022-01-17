package pl.petlovers.Pet2Vet.file;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.visit.Visit;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;



@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "FILES")
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  private byte[] data;

  private String name;
  private String url;
  private String contentType;
  private Long size;
  private LocalDateTime created;
  private LocalDateTime modified;

  public static File of(MultipartFile multipartFile) throws IOException {
    return File.builder()
      .name(StringUtils.cleanPath(multipartFile.getOriginalFilename()))
      .contentType(multipartFile.getContentType())
      .data(multipartFile.getBytes())
      .size(multipartFile.getSize())
      .build();
  }

  protected void modify(File file) {
    if (isNoneBlank(file.getName())) {
      this.setName(file.getName());
      setModified(LocalDateTime.now());
    }
    if (isNoneBlank(file.getUrl())) {
      this.setUrl(file.getUrl());
      setModified(LocalDateTime.now());
    }
  }
}
