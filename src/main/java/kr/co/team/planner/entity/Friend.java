package kr.co.team.planner.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"request","response"})
@DynamicInsert
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    @Column (columnDefinition = "varchar(5) check (stage in ('요청중','수락','거절','차단')) default '요청중'")
    private String stage;

    @Column
    private String msg;

    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.REMOVE)
    private User request;

    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.REMOVE)
    private User response;

    public void changeStage(String stage){
        this.stage=stage;
    }
}
