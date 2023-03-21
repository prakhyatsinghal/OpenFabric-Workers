package ai.openfabric.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.WorkerActivity;
import ai.openfabric.api.model.WorkerStatistics;

import ai.openfabric.api.repository.WorkerActivityRepository;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.repository.WorkerStatisticsRepository;


@RestController
@RequestMapping("/api/v1/worker")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerActivityRepository workerActivityRepository;

    @Autowired
    private WorkerStatisticsRepository workerStatisticsRepository;

    @GetMapping
    public ResponseEntity<List<Worker>> listWorkers(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Worker> workers = workerRepository.findAll(pageable);
        return new ResponseEntity<>(workers.getContent(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Worker> createWorker(@RequestBody Worker worker) {
        Worker savedWorker = workerRepository.save(worker);
        return ResponseEntity.ok(savedWorker);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        if (worker.isPresent()) {
            return new ResponseEntity<>(worker.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startWorker(@PathVariable Long id) {
        Optional<Worker> workerOptional = workerRepository.findById(id);
        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();
            WorkerActivity workerActivity = new WorkerActivity(worker, LocalDateTime.now(), null, "ACTIVE");
            workerActivityRepository.save(workerActivity);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<HttpStatus> stopWorker(@PathVariable Long id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();
            Optional<WorkerActivity> optionalActivity = workerActivityRepository.findActiveActivity(worker);
            if (optionalActivity.isPresent()) {
                WorkerActivity activity = optionalActivity.get();
                activity.setEndTime(LocalDateTime.now());
                workerActivityRepository.save(activity);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<WorkerStatistics> getWorkerStatistics(@PathVariable Long id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();
            WorkerStatistics workerStatistics = workerStatisticsRepository.findByWorker(worker);
            if (workerStatistics != null) {
                return new ResponseEntity<>(workerStatistics, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/statistics")
    public ResponseEntity<WorkerStatistics> createWorkerStatistics(@PathVariable Long id, @RequestBody WorkerStatistics workerStatistics) {
    Optional<Worker> optionalWorker = workerRepository.findById(id);
    if (optionalWorker.isPresent()) {
        Worker worker = optionalWorker.get();
        workerStatistics.setWorker(worker);
        WorkerStatistics savedWorkerStatistics = workerStatisticsRepository.save(workerStatistics);
        return new ResponseEntity<>(savedWorkerStatistics, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
}