package ai.openfabric.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "worker_statistics")
@Getter
@Setter
public class WorkerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @Column(name = "total_tasks_completed")
    private int totalTasksCompleted;

    @Column(name = "avg_time_per_task")
    private int avgTimePerTask;

    public WorkerStatistics() {}

    public WorkerStatistics(Worker worker, int totalTasksCompleted, int avgTimePerTask) {
        this.worker = worker;
        this.totalTasksCompleted = totalTasksCompleted;
        this.avgTimePerTask = avgTimePerTask;
    }

}

