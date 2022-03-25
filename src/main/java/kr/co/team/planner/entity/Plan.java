package kr.co.team.planner.entity;
//from LDJ
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private Long priority;

    @Column(nullable = false)
    private String title;

    private String description;

    private String location;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    private String category;

    @Column(length= 20, nullable = false)
    private String share;

    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.REMOVE)
    // commit 시점에만 삭제
    private User writer;

    public void changePriority(Long priority){
        this.priority=priority;
    }
    public void changeTitle(String title){
        this.title=title;
    }
    public void changeDescription(String description){
        this.description=description;
    }
    public void changeLocation(String location){
        this.location=location;
    }
    public void changeStart(LocalDateTime start){
        this.start= LocalDateTime.from(start);
    }
    public void changeEnd(LocalDateTime end){
        this.end= LocalDateTime.from(end);
    }
    public void changeCategory(String category){
        this.category=category;
    }
    public void changeShare(String share){
        this.share=share;
    }

}