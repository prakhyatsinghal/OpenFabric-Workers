package ai.openfabric.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.WorkerStatistics;

@Repository
public interface WorkerStatisticsRepository extends JpaRepository<WorkerStatistics, Long> {

    WorkerStatistics findByWorker(Worker worker);
}
