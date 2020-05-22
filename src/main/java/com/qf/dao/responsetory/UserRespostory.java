package com.qf.dao.responsetory;

import com.qf.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 54110 on 2020/5/18.
 */
public interface UserRespostory extends JpaRepository<User,Integer> {
}
