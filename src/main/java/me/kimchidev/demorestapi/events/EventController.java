package me.kimchidev.demorestapi.events;

import lombok.RequiredArgsConstructor;
import me.kimchidev.demorestapi.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
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
            return badRequest(errors);
        }

        eventValidator.validate(eventDto,errors);
        if(errors.hasErrors()){
            System.out.println("Bad Request !");
            return badRequest(errors);
        }

        System.out.println("Not Bad Request !");
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        Event newEvents = eventRepository.save(event);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvents.getId());
        URI createUri = selfLinkBuilder.toUri();

        //Usage for RepresentationModel
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withSelfRel());
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));

        //Usage for EntityModel
        EntityModel eventResource2 = EntityModel.of(event);
        eventResource2.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
        eventResource2.add(linkTo(EventController.class).withRel("query-events"));
        eventResource2.add(selfLinkBuilder.withRel("update-event"));
        return ResponseEntity.created(createUri).body(eventResource);
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) throws Exception{
        Page<Event> page = this.eventRepository.findAll(pageable);
        var pagedResources = assembler.toModel(page,e -> new EventResource(e));
        System.out.println("EventController.queryEvents pagedResources : " +pagedResources);
        System.out.println("EventController.queryEvents pagedResources : " +pagedResources);
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity<ErrorsResource> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


}
