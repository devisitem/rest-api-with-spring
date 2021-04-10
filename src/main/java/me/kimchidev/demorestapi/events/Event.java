package me.kimchidev.demorestapi.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/*
    Equals , HashCode를 구현할 떄 모든필드를 기본적으로사용함 엔티티간의 연관관계가 있을 시,
    상호 참조하는 관계가 되버리면 Equals 와 HashCode를 구현한 코드 안에서 StackoverFlow 예외가 발생할 수 있음.
    그런이유로 id의 값만 가지고 Equals랑 HashCode의 값을 비교할 수 있도록 지정함
*/
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional)
    private int basePrice; // (optional)
    private int maxPrice; // (optional) private int
    private int limitOfEnrollment;
    private boolean offLine;
    private boolean free;


    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;


}
