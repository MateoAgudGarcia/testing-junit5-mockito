package springboot.course.exercise4.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.course.exercise4.model.Pet;
import springboot.course.exercise4.model.Visit;
import springboot.course.exercise4.services.VisitService;
import springboot.course.exercise4.services.map.PetMapService;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    VisitService visitService;

    @Spy
    PetMapService petMapService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        //GIVEN
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(420L);
        Pet pet2 = new Pet(609L);

        petMapService.save(pet);
        petMapService.save(pet2);

        given(petMapService.findById(anyLong())).willCallRealMethod();//willReturn(pet);

        //WHEN
        Visit visit = visitController.loadPetWithVisit(420L,model);

        //THEN
        assertNotNull(visit);
        assertNotNull(visit.getPet());
        assertThat(visit.getPet().getId()).isEqualTo(420L);
        verify(petMapService,times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        //GIVEN
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(420L);
        Pet pet2 = new Pet(609L);

        petMapService.save(pet);
        petMapService.save(pet2);

        given(petMapService.findById(anyLong())).willReturn(pet);

        //WHEN
        Visit visit = visitController.loadPetWithVisit(420L,model);

        //THEN
        assertNotNull(visit);
        assertNotNull(visit.getPet());
        assertThat(visit.getPet().getId()).isEqualTo(420L);
        verify(petMapService,times(1)).findById(anyLong());
    }
}