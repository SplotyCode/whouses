package io.mentusa.whouses.psi;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = @Index(columnList = "hash"))
@NoArgsConstructor
public class SourceFile {
    @Column
    @GeneratedValue
    @Id
    private int id;
    @Column
    private String hash;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="source_location", joinColumns = @JoinColumn(name="id"))
    @Column(name="location")
    private List<String> locations = new ArrayList<>();

    public SourceFile(String hash) {
        this.hash = hash;
    }

    public void addPath(Path path) {
        locations.add(path.toAbsolutePath().toString());
    }
}
