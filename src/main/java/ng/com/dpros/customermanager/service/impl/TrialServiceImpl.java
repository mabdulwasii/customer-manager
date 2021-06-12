package ng.com.dpros.customermanager.service.impl;

import ng.com.dpros.customermanager.service.TrialService;
import ng.com.dpros.customermanager.domain.Trial;
import ng.com.dpros.customermanager.repository.TrialRepository;
import ng.com.dpros.customermanager.repository.search.TrialSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Trial}.
 */
@Service
@Transactional
public class TrialServiceImpl implements TrialService {

    private final Logger log = LoggerFactory.getLogger(TrialServiceImpl.class);

    private final TrialRepository trialRepository;

    private final TrialSearchRepository trialSearchRepository;

    public TrialServiceImpl(TrialRepository trialRepository, TrialSearchRepository trialSearchRepository) {
        this.trialRepository = trialRepository;
        this.trialSearchRepository = trialSearchRepository;
    }

    @Override
    public Trial save(Trial trial) {
        log.debug("Request to save Trial : {}", trial);
        Trial result = trialRepository.save(trial);
        trialSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trial> findAll() {
        log.debug("Request to get all Trials");
        return trialRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Trial> findOne(Long id) {
        log.debug("Request to get Trial : {}", id);
        return trialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trial : {}", id);
        trialRepository.deleteById(id);
        trialSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trial> search(String query) {
        log.debug("Request to search Trials for query {}", query);
        return StreamSupport
            .stream(trialSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
