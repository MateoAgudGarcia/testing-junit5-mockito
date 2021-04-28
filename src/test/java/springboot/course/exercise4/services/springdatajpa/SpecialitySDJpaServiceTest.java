package springboot.course.exercise4.services.springdatajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.course.exercise4.model.Speciality;
import springboot.course.exercise4.repositories.SpecialtyRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteByObject(){
        //NO GIVEN

        //WHEN
        Speciality speciality = new Speciality();
        service.delete(speciality);

        //THEN
        then(specialtyRepository).should(timeout(100)).delete(any(Speciality.class));
    }

    @Test
    void findById(){
        //GIVEN
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(2L)).willReturn(Optional.of(speciality));

        //WHEN
        Speciality foundSpeciality = service.findById(2L);

        //THEN
        assertNotNull(foundSpeciality);
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
    }

    @Test
    void findByIdBDD(){
        //GIVEN
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(2L)).willReturn(Optional.of(speciality));

        //WHEN
        Speciality foundSpeciality = service.findById(2L);

        //THEN
        assertNotNull(foundSpeciality);
        then(specialtyRepository).should(timeout(200).times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //NO GIVEN

        //WHEN
        service.deleteById(12L);
        service.deleteById(12L);

        //THEN
        then(specialtyRepository).should(times(2)).deleteById(anyLong()); // Verify is for the Mock Object
    }

    @Test
    void deleteByIdAtLeastOnce() {
        //NO GIVEN

        //WHEN
        service.deleteById(12L);
        service.deleteById(12L);

        //THEN
        then(specialtyRepository).should(timeout(100).atLeastOnce()).deleteById(12L); // Verify is for the Mock Object
    }

    @Test
    void deleteByIdAtMost() {
        //NO GIVEN

        //WHEN
        service.deleteById(12L);
        service.deleteById(12L);

        then(specialtyRepository).should(atMost(5)).deleteById(anyLong()); // Verify is for the Mock Object
    }

    @Test
    void deleteByIdNever() {
        //NO GIVEN

        //WHEN
        service.deleteById(12L);
        service.deleteById(12L);

        //THEN
        then(specialtyRepository).should(never()).deleteById(13L); // Verify is for the Mock Object
    }

    @Test
    void delete() {
        //NO GIVEN

        //THEN
        service.delete(new Speciality());

        //THEN
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteThrowException(){
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        then(specialtyRepository).should().delete(any());
    }

    @Test
    void deleteThrowExceptionBDD(){
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }

    @Test
    void findByIDThrowException(){
        given(specialtyRepository.findById(2L)).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.findById(2L));

        then(specialtyRepository).should().findById(2L);
    }

    @Test
    void saveLambda(){
        //GIVEN
        final String MATCH_ME =  "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(2L);
        // It's necessary to mock in order to make a match
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);

        //WHEN
        Speciality returnedSpeciality = service.save(speciality);

        //THEN
        assertThat(returnedSpeciality.getId()).isEqualTo(2L);
    }

    @Test
    void saveLambdaNull(){
        //GIVEN
        final String MATCH_ME =  "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("NOT A MATCH");

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(2L);
        // It's necessary to mock in order to make a match
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);

        //WHEN
        Speciality returnedSpeciality = service.save(speciality);

        //THEN
        assertNull(returnedSpeciality);
    }
}