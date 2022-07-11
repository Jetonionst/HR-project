package kz.jusan.hrapp.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "photos")
@NoArgsConstructor
@Setter
@Getter
public class Photo{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "data")
    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Photo(String name, String type, byte[] data, User user) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.user = user;
    }

}
