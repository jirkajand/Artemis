package cz.uhk.fim.settingsservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.uhk.fim.settingsservice.entity.FacultyEntity;
import cz.uhk.fim.settingsservice.model.FacultyCreateRequest;
import cz.uhk.fim.settingsservice.repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FacultyManagementIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        facultyRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void createFaculty_returnsCreatedFaculty() throws Exception {
        FacultyCreateRequest body = new FacultyCreateRequest();
        body.setFacultyNameInternational("Faculty of informatics and management");
        body.setFacultyNameLocal("Fakulta informatiky a managementu");
        body.setColor("#FF5733");
        body.setShortName("FIM");

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/faculty")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.facultyNameInternational").value("Faculty of informatics and management"));
    }

    @Test
    @WithMockUser
    void getAllFaculties_returnsList() throws Exception {
        facultyRepository.save(FacultyEntity.builder()
                .facultyNameInternational("Test International")
                .facultyNameLocal("Test Local")
                .color("#123456")
                .shortName("TEST")
                .build());
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/faculty")
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].facultyNameInternational").value("Test International"));
    }

    @Test
    @WithMockUser
    void getFacultyById_returnsFaculty() throws Exception {
        var entity = facultyRepository.save(FacultyEntity.builder()
                .facultyNameInternational("GetById International")
                .facultyNameLocal("GetById Local")
                .color("#654321")
                .shortName("GBID")
                .build());
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/faculty/" + entity.getId())
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.facultyNameInternational").value("GetById International"));
    }

    @Test
    @WithMockUser
    void updateFaculty_returnsUpdatedFaculty() throws Exception {
        var entity = facultyRepository.save(FacultyEntity.builder()
                .facultyNameInternational("Old International")
                .facultyNameLocal("Old Local")
                .color("#000000")
                .shortName("OLD")
                .build());
        FacultyCreateRequest body = new FacultyCreateRequest();
        body.setFacultyNameInternational("Updated International");
        body.setFacultyNameLocal("Updated Local");
        body.setColor("#111111");
        body.setShortName("UPD");

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/faculty/" + entity.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.facultyNameInternational").value("Updated International"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.shortName").value("UPD"));
    }

    @Test
    @WithMockUser
    void deleteFaculty_deletesFaculty() throws Exception {
        var entity = facultyRepository.save(FacultyEntity.builder()
                .facultyNameInternational("Delete International")
                .facultyNameLocal("Delete Local")
                .color("#abcdef")
                .shortName("DEL")
                .build());
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/faculty/" + entity.getId())
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isNoContent());
    }
}
