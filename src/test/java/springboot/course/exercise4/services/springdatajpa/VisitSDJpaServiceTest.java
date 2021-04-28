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

        when(repository.findAll()).thenReturn(visitSet);

        Set<Visit> foundVisitSet = service.findAll();

        assertNotNull(foundVisitSet);

        verify(repository).findAll();

        assertThat(foundVisitSet).hasSize(1);
    }

    @Test
    void findById() {
        Visit visit = new Visit();
        when(repository.findById(anyLong())).thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(35L);

        assertNotNull(foundVisit);

        verify(repository).findById(anyLong());
    }

    @Test
    void save() {
        Visit visit = new Visit(12L, LocalDate.now());
        when(repository.save(any(Visit.class))).thenReturn(visit);

        Visit foundVisit = service.save(visit);

        assertNotNull(foundVisit);

        verify(repository).save(any(Visit.class));
    }

    @Test
    void delete() {
        Visit visit = new Visit(12L);

        repository.delete(visit);

        verify(repository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        repository.deleteById(13L);

        verify(repository).deleteById(anyLong());
    }
}