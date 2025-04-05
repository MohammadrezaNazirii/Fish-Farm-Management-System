package FishFarmManagementSystem.service;

import FishFarmManagementSystem.Farm;
import FishFarmManagementSystem.Line;
import FishFarmManagementSystem.Pool;
import FishFarmManagementSystem.repository.PoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoolService {
    private final PoolRepository repository;

    public PoolService(PoolRepository repository){
        this.repository = repository;
    }

    public List<Pool> getAllPools(){
        return repository.findAll();
    }

    public Pool getPoolById(Integer id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pool not found with id: " + id));
    }

    public List<Pool> getAllPoolsByFarmId(Integer farmId){
        return repository.findAllByFarmId(farmId);
    }

    public List<Pool> getAllPoolsByOtherEquipmentsContains(String equip){
        return repository.findAllByOtherEquipmentsContains(equip);
    }

    public List<Pool> getAllPoolsBySizeBetween(Integer size1, Integer size2){
        return repository.findAllBySizeBetween(size1, size2);
    }

    public Pool createPool(Pool pool){
        return repository.save(pool);
    }

    public Pool updatePool(Integer id, Pool pool){
        Optional<Pool> poolOptional = repository.findById(id);
        if(poolOptional.isPresent()){
            Pool pool1 = poolOptional.get();
            pool1.setOtherEquipments(pool.getOtherEquipments());
//            pool1.setFarmId(pool.getFarmId());
            pool1.setSize(pool.getSize());
            return repository.save(pool1);
        }
        return null;
    }

    public void updateFarmID(Integer poolId, Integer farmId){
        Optional<Pool> poolOptional = repository.findById(poolId);
        if (poolOptional.isPresent()) {
            Pool pool = poolOptional.get();
            pool.setFarmId(farmId);
            repository.save(pool);
        }
    }

    public void deletePool(Integer id){
        repository.deleteById(id);
    }
}
