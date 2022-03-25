package kr.co.team.planner.entity;
//from LDJ
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"plan","user"})
public class Share extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String text;
}
