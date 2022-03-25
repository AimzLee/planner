package kr.co.team.planner.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    //title 을 수정하는 메서드
    public void changeTitle(String title){
        this.title = title;
    }

    //content 를 수정하는 메서드
    public void changeContent(String content){
        this.content = content;
    }

}