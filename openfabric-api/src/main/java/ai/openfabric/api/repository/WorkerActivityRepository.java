package ai.openfabric.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.WorkerActivity;

@Repository
public interface WorkerActivityRepository extends JpaRepository<WorkerActivity, Long> {

    @Query("SELECT wa FROM WorkerActivity wa WHERE wa.worker = :worker AND wa.status = 'ACTIVE'")
    Optional<WorkerActivity> findActiveActivity(@Param("worker") Worker worker);
}

