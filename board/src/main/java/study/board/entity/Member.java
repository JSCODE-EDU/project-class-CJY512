package study.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
/*

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

*/

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    private Member(String email, String password) {
        this.email = email;
        this.password = password;
        roles.add("ROLE_USER");
    }

    //==생성 메서드==//
    public static Member createMember(String email, String password) {
        return new Member(email, password);
    }

}
