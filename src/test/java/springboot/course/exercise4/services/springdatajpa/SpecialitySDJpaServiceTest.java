package springboot.course.exercise4.services.springdatajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.course.exercise4.model.Speciality;
import springboot.course.exercise4.repositories.SpecialtyRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
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
        then(specialtyRepository).should().delete(any(Speciality.class));
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
        then(specialtyRepository).should().findById(anyLong());
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
        then(specialtyRepository).should(times(1)).findById(anyLong());
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
        then(specialtyRepository).should(atLeastOnce()).deleteById(12L); // Verify is for the Mock Object
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
}