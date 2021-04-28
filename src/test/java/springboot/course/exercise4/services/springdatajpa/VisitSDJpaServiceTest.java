package springboot.course.exercise4.services.springdatajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.course.exercise4.model.Visit;
import springboot.course.exercise4.repositories.VisitRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository repository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        Visit visit = new Visit(1L);
        Set<Visit> visitSet = new HashSet<>();
        visitSet.add(visit);
        //GIVEN
        given(repository.findAll()).willReturn(visitSet);

        //WHEN
        Set<Visit> foundVisitSet = service.findAll();

        //THEN
        assertNotNull(foundVisitSet);
        then(repository).should().findAll();
        assertThat(foundVisitSet).hasSize(1);
    }

    @Test
    void findById() {
        //GIVEN
        Visit visit = new Visit();
        given(repository.findById(anyLong())).willReturn(Optional.of(visit));

        //WHEN
        Visit foundVisit = service.findById(35L);

        //THEN
        assertNotNull(foundVisit);
        then(repository).should().findById(anyLong());
    }

    @Test
    void save() {
        //GIVEN
        Visit visit = new Visit(12L, LocalDate.now());
        given(repository.save(any(Visit.class))).willReturn(visit);

        //WHEN
        Visit foundVisit = service.save(visit);

        //THEN
        assertNotNull(foundVisit);
        then(repository).should().save(any(Visit.class));
    }

    @Test
    void delete() {
        //NO GIVEN

        //WHEN
        Visit visit = new Visit(12L);
        repository.delete(visit);

        //THEN
        then(repository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //NO GIVEN

        //WHEN
        repository.deleteById(13L);

        //THEN
        then(repository).should().deleteById(anyLong());
    }
}