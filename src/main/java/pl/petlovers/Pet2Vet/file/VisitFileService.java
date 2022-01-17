package pl.petlovers.Pet2Vet.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.petlovers.Pet2Vet.visit.Visit;
import pl.petlovers.Pet2Vet.visit.VisitRepository;

import java.io.IOException;
import java.util.List;

@Service
public class VisitFileService {

  private final FileRepository fileRepository;
  private final VisitRepository visitRepository;

  @Autowired
  public VisitFileService(FileRepository fileRepository, VisitRepository visitRepository) {
    this.fileRepository = fileRepository;
    this.visitRepository = visitRepository;
  }

  public List<File> getAll(long visitId){
    return visitRepository.getById(visitId).getFiles();
  }

  public File get(long fileId) {
    return fileRepository.getById(fileId);
  }

  public File create(long visitId, MultipartFile multipartFile) throws IOException {
    Visit visit = visitRepository.getById(visitId);
    File file = File.of(multipartFile);
    visit.addFile(file);
    return fileRepository.save(file);
  }

  public File update(long visitId, long fileId, File file) { //fixme unused visitId
    File fileFromDb = get(fileId);
    fileFromDb.modify(file);
    return fileRepository.save(fileFromDb);
  }

  public void delete(long fileId) {
    fileRepository.delete(get(fileId));
  }


}
