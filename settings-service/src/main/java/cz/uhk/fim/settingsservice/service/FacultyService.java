package cz.uhk.fim.settingsservice.service;

import cz.uhk.fim.settingsservice.mapper.FacultyMapper;
import cz.uhk.fim.settingsservice.model.FacultyCreateRequest;
import cz.uhk.fim.settingsservice.model.FacultyResponse;
import cz.uhk.fim.settingsservice.repository.FacultyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyResponse createFaculty(FacultyCreateRequest facultyCreateRequest) {
        var entity = facultyMapper.toFacultyEntity(facultyCreateRequest);
        entity.setId(null);
        var savedEntity = facultyRepository.save(entity);
        return facultyMapper.toFacultyResponse(savedEntity);
    }

    public void deleteFaculty(UUID id) {
        var entity = facultyRepository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Faculty with id %s not found", id));
        }
        facultyRepository.deleteById(id);
        //There should be maybe some publish event to notify other services about deletion
    }

    public List<FacultyResponse> getAllFaculties() {
        var entities = facultyRepository.findAll();
        return entities.stream()
                .map(facultyMapper::toFacultyResponse)
                .toList();
    }

    public FacultyResponse getFacultyById(UUID id) {
        var entity = facultyRepository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Faculty with id %s not found", id));
        }
        return facultyMapper.toFacultyResponse(entity.get());
    }

    public FacultyResponse updateFaculty(UUID id, FacultyCreateRequest facultyCreateRequest) {
        var entity = facultyRepository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Faculty with id %s not found", id));
        }
        var updatedEntity = facultyMapper.toFacultyEntity(facultyCreateRequest);
        updatedEntity.setId(id);
        var savedEntity = facultyRepository.save(updatedEntity);
        return facultyMapper.toFacultyResponse(savedEntity);
    }
}
