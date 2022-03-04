package io.mentusa.whouses.psi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepo extends JpaRepository<SourceFile, Integer> {
    boolean existsSourceByHash(String hash);

    SourceFile findOneByHash(String hash);
}
