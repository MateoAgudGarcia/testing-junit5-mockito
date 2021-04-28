package springboot.course.exercise4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

public class AnnotationsMocksTest {

    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this); // initMocks is deprecated for current version
    }

    @Test
    void testMock(){
        mapMock.put("keyvalue","mag");
    }
}
