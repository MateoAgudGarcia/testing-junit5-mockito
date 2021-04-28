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
        Speciality speciality = new Speciality();

        service.delete(speciality);

        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void findById(){
        Speciality speciality = new Speciality();
        when(specialtyRepository.findById(2L)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality = service.findById(2L);

        assertNotNull(foundSpeciality);

        verify(specialtyRepository).findById(2L);
//        verify(specialtyRepository).findById(anyLong());
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
        service.deleteById(12L);
        service.deleteById(12L);

        verify(specialtyRepository,times(2)).deleteById(12L); // Verify is for the Mock Object
    }

    @Test
    void deleteByIdAtLeastOnce() {
        service.deleteById(12L);
        service.deleteById(12L);

        verify(specialtyRepository,atLeastOnce()).deleteById(12L); // Verify is for the Mock Object
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(12L);
        service.deleteById(12L);

        verify(specialtyRepository,atMost(5)).deleteById(12L); // Verify is for the Mock Object
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(12L);
        service.deleteById(12L);

        verify(specialtyRepository,never()).deleteById(15L); // Verify is for the Mock Object
    }

    @Test
    void delete() {
        service.delete(new Speciality());
    }
}