package kr.co.team.planner.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity{
    @Id
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    @OneToMany
    private List<Board> board;

    //권한을 하나만 가지는 경우
    //private ClubMemberRole roles;

    //권한을 여러개 가질 수 있는 경우
    @Builder.Default
    @ElementCollection(fetch= FetchType.LAZY)
    private Set<MemberRole> roleSet = new HashSet<>();

    //권한을 추가하는 메서드
    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

}