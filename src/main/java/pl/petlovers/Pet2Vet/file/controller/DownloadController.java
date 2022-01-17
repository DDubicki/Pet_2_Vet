package pl.petlovers.Pet2Vet.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.petlovers.Pet2Vet.file.File;
import pl.petlovers.Pet2Vet.file.FileRepository;

@RestController
public class DownloadController {

  private final FileRepository repository;

  @Autowired
  public DownloadController(FileRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/download/{fileId}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
    File file = repository.findById(fileId).orElseThrow();

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
      .contentType(MediaType.valueOf(file.getContentType()))
      .body(file.getData());
  }
}

