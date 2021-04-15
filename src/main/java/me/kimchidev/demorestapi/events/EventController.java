package me.kimchidev.demorestapi.events;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events",produces = MediaTypes.HAL_JSON_VALUE)
//produce 설정시 해당타입으로 모든응답을 보낸다.
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) throws Exception{
        if(errors.hasErrors()){
            System.out.println("Bad Request !");
            return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto,errors);
        if(errors.hasErrors()){
            System.out.println("Bad Request !");
            return ResponseEntity.badRequest().body(errors);
        }

        System.out.println("Not Bad Request !");
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        Event newEvents = eventRepository.save(event);
        URI createUri = linkTo(EventController.class).slash(newEvents.getId()).toUri();
        return ResponseEntity.created(createUri).body(event);
    }
}
