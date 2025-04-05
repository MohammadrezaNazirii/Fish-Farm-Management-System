package FishFarmManagementSystem.service;

import FishFarmManagementSystem.Line;
import FishFarmManagementSystem.repository.LineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class LineService {
    private final LineRepository repository;

    public LineService(LineRepository repository){
        this.repository =repository;
    }

    // Find all lines by fish count range
    public List<Line> getAllLinesByFishCountBetween(Integer start, Integer end) {
        return repository.findAllByFishCountBetween(start, end);
    }

    // Find all lines by seasonId
    public List<Line> getAllLinesBySeasonId(Integer seasonId) {
        return repository.findAllBySeasonId(seasonId);
    }

    // Find all lines by poolId
    public List<Line> getAllLinesByPoolId(Integer poolId) {
        return repository.findAllByPoolId(poolId);
    }

    public List<Line> getAllLines(){
        return repository.findAll();
    }

    // Find a line by its id
    public Line getLineById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Line not found with id: " + id));
    }

    // Save a new or updated line
    public Line createLine(Line line) {
        return repository.save(line);
    }

    // Delete a line by its id
    public void deleteLine(Integer id) {
        repository.deleteById(id);
    }

    // Update an existing line
    public Line updateLine(Integer id, Line updatedLine) {
        Optional<Line> lineOptional = repository.findById(id);
        if (lineOptional.isPresent()) {
            Line line = lineOptional.get();
            line.setFishCount(updatedLine.getFishCount());
//            line.setPoolId(updatedLine.getPoolId());
            line.setSeasonId(updatedLine.getSeasonId());
            return repository.save(line);
        }
        return null; // or throw a custom exception
    }

    public void updatePoolId(Integer lineId, Integer poolId){
        Optional<Line> lineOptional = repository.findById(lineId);
        if (lineOptional.isPresent()) {
            Line line = lineOptional.get();
            line.setPoolId(poolId);
            repository.save(line);
        }
    }

}
