package cn.houtaroy.authorization.server.userdetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 *
 * @author Houtaroy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private String nickname;

    private String picture;

    private String gender;
    
    private String phoneNumber;
}
