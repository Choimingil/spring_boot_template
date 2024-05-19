package com.hanati.team1.src.customers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanati.team1.src.customers.entities.User;
import com.hanati.team1.src.customers.models.GetMyInfoRes;
import com.hanati.team1.src.customers.models.GetTokenRes;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(
		"select new com.hanati.team1.src.customers.models.GetMyInfoRes(u.userName, u.userProfile, (select sum(t.tokenPrice) from Trade t where t.userId = u.userId), (select sum(t.tokenNum) from Trade t where t.userId = u.userId)) "
			+ "from User u "
			+ "where u.userId = :userId")
	Optional<GetMyInfoRes> findMyInfo(long userId);

	@Query("")
	List<GetTokenRes> findMyTokenList(long userId);
}
