package org.tix.hospital_sorting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tix.hospital_sorting.model.Patient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SortingAlgorithm {
    private final Map<String, TriageStrategy> strategies;
    private TriageStrategy currentStrategy;
    private final List<Patient> triageQueue = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    public SortingAlgorithm(List<TriageStrategy> strategyList) {
        strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        s -> s.getClass().getSimpleName()
                                .replace("TriageStrategy", "")
                                .toLowerCase(),
                        Function.identity()
                ));
        currentStrategy = strategies.get("basic");
    }

    public void setSortingStrategy(String strategyName) {
        currentStrategy = strategies.getOrDefault(
                strategyName.toLowerCase(),
                strategies.get("basic")
        );
    }

    public int calculateTriageLevel(String symptoms, int heartRate,
                                    int systolicBP, int respiratoryRate) {
        return currentStrategy.calculateTriageLevel(
                symptoms, heartRate, systolicBP, respiratoryRate
        );
    }

    // Остальные методы остаются без изменений
    public void addToQueue(Patient patient) {
        patient.setId(idCounter.getAndIncrement());
        triageQueue.add(patient);
    }

    public List<Patient> getSortedQueue() {
        return triageQueue.stream()
                .sorted(Comparator
                        .comparingInt(Patient::getTriageLevel)
                        .thenComparing(Patient::getAdmissionTime)
                )
                .collect(Collectors.toList());
    }
}
