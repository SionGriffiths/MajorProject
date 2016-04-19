$$@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bar_code"}))
public class Plant {

    $$@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="plant_meta_data_id")
    public Metadata getMetadata() {
        return metadata;
    }

    @OneToMany(mappedBy = "plant",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public List<PlantDay> getPlantDays() {
        return plantDays;
    }