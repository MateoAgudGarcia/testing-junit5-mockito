package springboot.course.exercise4.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.course.exercise4.fauxspring.BindingResult;
import springboot.course.exercise4.fauxspring.Model;
import springboot.course.exercise4.model.Owner;
import springboot.course.exercise4.services.OwnerService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult result;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

//    @DisplayName("Method 'processFindForm'")
//    @Test
//    void processFindForm(){
//        //GIVEN
//        Owner owner = new Owner(123L,"Pepito","Perez");
//        List<Owner> ownerList = new ArrayList<>();
//        final ArgumentCaptor<String> CAPTOR = ArgumentCaptor.forClass(String.class); //Ask about it
//        given(service.findAllByLastNameLike(CAPTOR.capture())).willReturn(ownerList);
//
//        //WHEN
//        String viewName = controller.processFindForm(owner,result,null);
//
//        //THEN
//        assertThat("%Perez%").isEqualToIgnoringCase(CAPTOR.getValue());
//    }

    @Nested
    @DisplayName("Class for Captor tests")
    class CaptorTests{
        @BeforeEach
        void setUp(){
            given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocationOnMock -> {
                List<Owner> ownerList = new ArrayList<>();

                String name = invocationOnMock.getArgument(0);

                switch (name) {
                    case "%Perez%":
                        ownerList.add(new Owner(123L, "Pepito", "Perez"));
                        return ownerList;
                    case "%NotFound%":
                        return ownerList;
                    case "%FindMe%":
                        ownerList.add(new Owner(123L, "Pepito", "Perez"));
                        ownerList.add(new Owner(124L, "Joe", "Arroyo"));
                        return ownerList;
                }

                throw new RuntimeException("Invalid Argument Inserted");
            });
        }

        @DisplayName("Method 'processFindForm' with Captor Annotation")
        @Test
        void processFindFormCaptorAnnotation(){
            //GIVEN
            Owner owner = new Owner(123L,"Pepito","Perez");

            //WHEN
            String viewName = controller.processFindForm(owner,result,null);

            //THEN
            assertThat("%Perez%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            assertThat("redirect:/owners/123").isEqualToIgnoringCase(viewName);
            verifyNoInteractions(model);
        }

        @DisplayName("Method 'processFindForm' Negation")
        @Test
        void processFindFormCaptorNegation(){
            //GIVEN
            Owner owner = new Owner(123L,"Pepito","NotFound");

            //WHEN
            String viewName = controller.processFindForm(owner,result,null);

            //THEN
            assertThat("%NotFound%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
            verifyNoInteractions(model);
        }

        @DisplayName("Method 'processFindForm' Affirmation")
        @Test
        void processFindFormCaptorFindPerson(){
            //GIVEN
            Owner owner = new Owner(123L,"Pepito","FindMe");
            InOrder inOrder = Mockito.inOrder(service, model);

            //WHEN
            String viewName = controller.processFindForm(owner,result, model); //In the code using Model method, so i need to mock this class

            //THEN
            assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

            //InOrder ASSERTIONS
            inOrder.verify(service).findAllByLastNameLike(anyString());
            inOrder.verify(model,times(1)).addAttribute(anyString(),anyList());
            verifyNoMoreInteractions(model);
        }

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