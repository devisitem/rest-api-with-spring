package me.kimchidev.demorestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}