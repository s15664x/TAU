package pl.gitara.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "Owner")
@Table(name = "owner")
@NamedQueries({
        @NamedQuery(name = "owner.all", query = "Select o from Owner o")
})
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;


	@OneToMany(cascade = CascadeType.PERSIST,
    fetch = FetchType.EAGER,
    orphanRemoval=false,
    mappedBy = "owner"
    )

    private List<Guitar> guitars = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Guitar> getGuitars() {
        return guitars;
    }

    public void setGuitars(List<Guitar> list) {
        this.guitars = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Owner))
            return false;

        return
                id != null &&
                        id.equals(((Owner) o).getId());
    }

    @Override
    public int hashCode() {
        return 112;
    }
}
