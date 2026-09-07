package com.example.construction_api.model.persisitece;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long roleID;


    @Column(name = "app_user_role")
    private String appUserRole;

    public Role(String appUserRole) {
        this.appUserRole = appUserRole;
    }


}
