package FishFarmManagementSystem.service;

import FishFarmManagementSystem.Season;
import FishFarmManagementSystem.repository.SeasonRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {
    private final SeasonRepository repository;

    public SeasonService (SeasonRepository repository){
        this.repository = repository;
    }

    public List<Season> getAllSeasons(){
        return repository.findAll();
    }

    public Optional<Season> getSeasonById(Integer id){
        return repository.findById(id);
    }

    public List<Season> getAllSeasonsByStartDateBetween (LocalDate date1, LocalDate date2){
        return repository.findAllByStartDateBetween(date1, date2);
    }

    public List<Season> getAllSeasonsByEndDateBetween(LocalDate date1, LocalDate date2){
        return repository.findAllByEndDateBetween(date1, date2);
    }

    public List<Season> getAllSeasonsByTypeOfFish(String fish){
        return repository.findAllByTypeOfFish(fish);
    }

    public Season createSeason(Season season){
        return repository.save(season);
    }

    public Season updateSeason(Integer id, Season season){
        Optional<Season> seasonOptional = repository.findById(id);
        if(seasonOptional.isPresent()){
            Season season1 = seasonOptional.get();
            season1.setTypeOfFish(season.getTypeOfFish());
            season1.setStartDate(season.getStartDate());
            season1.setEndDate(season.getEndDate());
            return repository.save(season1);
        }
        return null;
    }

    public void deleteSeason(Integer id) throws ChangeSetPersister.NotFoundException {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else
            throw new ChangeSetPersister.NotFoundException();
    }
}
