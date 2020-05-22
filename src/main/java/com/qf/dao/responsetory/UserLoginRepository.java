package com.qf.dao.responsetory;

import com.qf.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 54110 on 2020/5/18.
 */
public interface UserLoginRepository extends JpaRepository<UserLogin,Integer> {
}
