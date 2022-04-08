package com.example.spring1.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring1.entities.Comment;
import com.example.spring1.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	List<Like> findByUserIdAndPostId(Long userId, Long postId);

	List<Like> findByUserId(Long userId);

	List<Like> findByPostId(Long postId);

	@Query(value="select 'liked', l.post_id , u.avatar , u.username from"
			+ "p_like l left join users u  on  u.id = l.user_id "
			+ "where l.post_id in :postIds limit 5" , nativeQuery=true)
	List<Object> findUserLikesByPostId(@Param("postIds")List<Long> postIds);
	

}
