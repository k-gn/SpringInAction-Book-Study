package restTest.ani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import restTest.ani.entity.Animation;

public interface AnimationRepository extends JpaRepository<Animation, Long> {

}
