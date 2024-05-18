package eci.cvds.armario.repository;

import eci.cvds.armario.model.Prenda;
import eci.cvds.armario.model.Session;
import eci.cvds.armario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrendaRepository extends JpaRepository<Prenda, UUID> {
    List<Prenda> findByUser(User user);
}
