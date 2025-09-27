package com.masprog.repositories;

import com.masprog.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);
    //select * from tbl_profiles where activation_token = ?
    Optional<Profile> findByActivationToken(String activationToken);

    List<Profile> findAllByIsActiveTrue();
}
