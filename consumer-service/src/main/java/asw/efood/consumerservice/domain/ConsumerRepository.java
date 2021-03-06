package asw.efood.consumerservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConsumerRepository extends CrudRepository<Consumer, Long> {

    public List<Consumer> findAll();
}


