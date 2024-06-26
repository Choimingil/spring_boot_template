package com.hanati.team1.src.players.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanati.team1.src.players.entities.Player;
import com.hanati.team1.src.players.models.GetPlayerDetailRes;
import com.hanati.team1.src.players.models.GetProspectDetailRes;
import com.hanati.team1.src.players.models.GetProspectRes;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	@Query(
		"select new com.hanati.team1.src.players.models.GetProspectRes(p.playerId, p.playerName, (select count(s.subscribeId) from Subscribe s where s.playerId = p.playerId and s.subscribeStatus = 100), p.playerProfile, po.positionName) "
			+ "from Player p "
			+ "inner join Position po on po.playerId = p.playerId "
			+ "where p.playerName like concat('%', :keyword, '%') and p.playerStatus = 200")
	List<GetProspectRes> findAllProspects(@Param("keyword") String keyword);

	@Query(
		"select new com.hanati.team1.src.players.models.GetProspectDetailRes(p.playerProfile, p.playerName, p.playerBirth, p.playerNation, p.playerWeight, p.playerHeight, p.playerVision, p.playerEffort, p.playerAdvantage, p.playerVideo, po.positionName, case when (select count(s.subscribeId) from Subscribe s where s.playerId = p.playerId and s.subscribeStatus = 100 and s.userId = :userId) > 0 then true else false end, p.playerYouth) "
			+ "from Player p "
			+ "inner join Position po on po.playerId = p.playerId "
			+ "where p.playerId = :prospectId")
	Optional<GetProspectDetailRes> findProspectById(@Param("prospectId") long prospectId, @Param("userId") long userId);

	@Query(
		"select new com.hanati.team1.src.players.models.GetPlayerDetailRes(p.playerProfile, p.playerName, p.playerBirth, p.playerNation, p.playerWeight, p.playerHeight, p.playerYouth, case when pp.positionName = 'GK' then true else false end, pp.positionName, (select tbp.playerBacknum from TeamByPlayer tbp where tbp.playerId = p.playerId and tbp.endDate >= now()), (select tt.tokenPrice from Trade tt where tt.playerId = p.playerId order by tt.tradeDate desc limit 1)) "
			+ "from Player p "
			+ "inner join Position pp on pp.playerId = p.playerId and pp.positionLevel = 10 "
			+ "where p.playerId = :playerId")
	Optional<GetPlayerDetailRes> findPlayerById(@Param("playerId") long playerId);
}
