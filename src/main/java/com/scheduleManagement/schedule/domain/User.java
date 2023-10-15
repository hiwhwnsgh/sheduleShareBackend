package com.scheduleManagement.schedule.domain;

import com.scheduleManagement.schedule.dto.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="login_Id")
    private String loginId;

    @Column(name="name")
    private String name;

    @Column(name="nickname")
    private String nickname;

    @Column
    private String password;

    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostApplication> postApplications;

    public List<String> getAll(){
        List<String> list = new ArrayList<>();
        list.add(getLoginId());
        list.add(getName());
        list.add(getNickname());
        list.add(getPassword());
        return list;
    }

}
