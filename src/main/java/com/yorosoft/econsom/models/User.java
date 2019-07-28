package com.yorosoft.econsom.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User extends Auditable<String>  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private boolean enabled=true;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
                joinColumns={
                    @JoinColumn(name = "user_id", referencedColumnName = "id") },
                inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            })
    private Collection<Role> roles;

}
