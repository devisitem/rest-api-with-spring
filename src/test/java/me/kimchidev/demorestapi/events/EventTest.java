package me.kimchidev.demorestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class EventTest {

    @Test
    public void builder() throws Exception {
        //given
        Event event = Event.builder()
                .name("Kimchi Rest API")
                .description("REST API development with Spring")
                .build();


        //when

        //then
        Assertions.assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        //given
        String name = "Event";
        String description = "Spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //than
        Assertions.assertThat(event.getName()).isEqualTo(name);
        Assertions.assertThat(event.getDescription()).isEqualTo(description);

    }

    @ParameterizedTest
    @MethodSource("paramsForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) throws Exception {
        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        Assertions.assertThat(event.isFree()).isTrue();

        //given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        //when
        event.update();

        //then
        Assertions.assertThat(event.isFree()).isEqualTo(isFree);


    }
    
    @ParameterizedTest
    @MethodSource("paramsForTestOffline")
    public void testOffline(String location,boolean isOffLine) throws Exception {
        //given
        Event event = Event.builder()
                .location(location)
                .build();
        
        //when
        event.update();
        
        //then
        Assertions.assertThat(event.isOffLine()).isEqualTo(isOffLine);
    }

    private static Stream<Arguments> paramsForTestOffline(){
        return Stream.of(
                Arguments.of("강남역",true),
                Arguments.of(null,false),
                Arguments.of("      ",false)
        );
    }

    private static Stream<Arguments> paramsForTestFree(){
        return Stream.of(
                Arguments.of(0,0,false)
        );
    }

}