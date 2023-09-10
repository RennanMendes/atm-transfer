package com.atm.transfer.domain.model;

import com.atm.transfer.domain.enumerated.UserType;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String cpf;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private boolean activeStatus;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", \n\tfullName='" + fullName + '\'' +
                ", \n\tcpf='" + cpf + '\'' +
                ", \n\temail='" + email + '\'' +
                ", \n\tpassword='" + password + '\'' +
                ", \n\tuserType=" + userType +
                ", \n\tactiveStatus=" + activeStatus +
                "}\n";
    }
}



