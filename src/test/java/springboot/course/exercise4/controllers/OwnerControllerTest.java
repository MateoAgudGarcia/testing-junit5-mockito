package springboot.course.exercise4.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.course.exercise4.fauxspring.BindingResult;
import springboot.course.exercise4.model.Owner;
import springboot.course.exercise4.services.OwnerService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult result;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @DisplayName("Method 'processFindForm'")
    @Test
    void processFindForm(){
        //GIVEN
        Owner owner = new Owner(123L,"Pepito","Perez");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> CAPTOR = ArgumentCaptor.forClass(String.class); //Ask about it
        given(service.findAllByLastNameLike(CAPTOR.capture())).willReturn(ownerList);

        //WHEN
        String viewName = controller.processFindForm(owner,result,null);

        //THEN
        assertThat("%Perez%").isEqualToIgnoringCase(CAPTOR.getValue());
    }

    @DisplayName("Method 'processFindForm' with Captor Annotation")
    @Test
    void processFindFormCaptorAnnotation(){
        //GIVEN
        Owner owner = new Owner(123L,"Pepito","Perez");
        List<Owner> ownerList = new ArrayList<>();
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        //WHEN
        String viewName = controller.processFindForm(owner,result,null);

        //THEN
        assertThat("%Perez%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
    }

    @DisplayName("Method 'processCreationForm' has errors")
    @Test
    void processCreationFormHasErrors() {
        //GIVEN
        Owner owner = new Owner(123L,"Pepito","Perez");
        given(result.hasErrors()).willReturn(true);

        //WHEN
        String viewName = controller.processCreationForm(owner,result);

        //THEN
        assertThat(viewName.equalsIgnoreCase("owners/createOrUpdateOwnerForm"));
    }

    @DisplayName("Method 'processCreationForm' doesn't have errors")
    @Test
    void processCreationFormNoErrors() {
        //GIVEN
        Owner owner = new Owner(123L,"Pepito","Perez");
        given(result.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);

        //WHEN
        String viewName = controller.processCreationForm(owner,result);

        //THEN
        //assertThat(viewName.equalsIgnoreCase("redirect:/owners/123"));
        assertThat(viewName).isEqualToIgnoringCase("redirect:/owners/123");
    }

}