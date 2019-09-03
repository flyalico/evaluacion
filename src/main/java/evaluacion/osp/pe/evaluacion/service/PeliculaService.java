package evaluacion.osp.pe.evaluacion.service;



import evaluacion.osp.pe.evaluacion.model.Pelicula;
import evaluacion.osp.pe.evaluacion.repository.PeliculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
@Service
public class PeliculaService {
    private static final Logger log = Logger.getLogger(PeliculaService.class.getName());
    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository){
        this.peliculaRepository = peliculaRepository;
    }
    @Transactional
    public Pelicula create(Pelicula pelicula){
        return this.peliculaRepository.save(pelicula);
    }

    @Transactional(readOnly = true)
    public Pelicula findById(Long id){
        Pelicula pelicula = this.peliculaRepository.findById(id).orElse(null); //.findOne(id);
        return pelicula;
    }
    @Transactional(readOnly = true)
    public List<Pelicula> findAll(){
        return this.peliculaRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Page<Pelicula> findAllPageable(Pageable pageable){
        return this.peliculaRepository.findAll(pageable);
    }
    @Transactional
    public void delete(Long id){
        this.peliculaRepository.deleteById(id);
    }
}
