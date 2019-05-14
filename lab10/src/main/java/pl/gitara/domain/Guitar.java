package pl.gitara.domain;

import javax.persistence.*;

@Entity(name = "Guitar")
@Table(name = "guitar")
@NamedQueries({
        @NamedQuery(name = "guitar.all", query = "Select p from Guitar p"),
        @NamedQuery(name = "guitar.findGuitarsByManufacturer", query = "Select c from Guitar c where c.manufacturer like :manufacturerNameFragment"),
        @NamedQuery(name = "guitar.findGuitarsByModel", query = "Select c from Guitar c where c.model like :modelNameFragment")
})
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String manufacturer;
    private String model;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Guitar))
            return false;

        return
                id != null &&
                        id.equals(((Guitar) o).getId());
    }

    @Override
    public int hashCode() {
        return 997;
    }
}
