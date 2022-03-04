package io.mentusa.whouses.psi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepo extends JpaRepository<Element, Long> {
    Element findByIdentifierName(String identifierName);
}
