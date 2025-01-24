package com.StarkIndustries.JwtAuthentication.Repository;

import com.StarkIndustries.JwtAuthentication.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepository extends JpaRepository<Users,Integer> {

    public Users findByUsername(String username);
}
